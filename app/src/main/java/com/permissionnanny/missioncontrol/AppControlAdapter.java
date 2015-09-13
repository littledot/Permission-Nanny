package com.permissionnanny.missioncontrol;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.permissionnanny.R;
import com.permissionnanny.SimpleOnItemSelectedListener;
import com.permissionnanny.Util;

import java.util.Map;

/**
 *
 */
public class AppControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int[] settingsToSelection = new int[]{0, 1, 2};
    private static int[] selectionToSettings = new int[]{PermissionConfig.ALWAYS_ASK, PermissionConfig.ALWAYS_ALLOW,
            PermissionConfig.ALWAYS_DENY};

    private Context mContext;
    private PackageManager mPM;
    private PermissionConfigDataManager mConfigManager;

    private SparseArray<String> mAppsPositions = new SparseArray<>();
    private SparseArray<PermissionConfig> mPermissionPositions = new SparseArray<>();
    private SparseArray<Boolean> mDisplayDescription = new SparseArray<>();

    public AppControlAdapter(Context context, PermissionConfigDataManager manager) {
        mContext = context;
        mPM = mContext.getPackageManager();
        mConfigManager = manager;
    }

    public void setData(Map<String, Map<String, PermissionConfig>> usage) {
        mAppsPositions.clear();
        mPermissionPositions.clear();
        int index = 0;

        for (String appName : usage.keySet()) {
            mAppsPositions.put(index++, appName);
            Map<String, PermissionConfig> appConfigs = usage.get(appName);
            for (String permissionName : appConfigs.keySet()) {
                mPermissionPositions.put(index++, appConfigs.get(permissionName));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAppsPositions.size() + mPermissionPositions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mAppsPositions.get(position) != null ? R.layout.item_app : R.layout.item_switch;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return viewType == R.layout.item_app ? new AppInfoViewHolder(view) : new PermissionInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppInfoViewHolder) {
            bindAppViewHolder((AppInfoViewHolder) holder, position);
        } else if (holder instanceof PermissionInfoViewHolder) {
            bindPermissionSwitchViewHolder((PermissionInfoViewHolder) holder, position);
        }
    }

    private void bindAppViewHolder(AppInfoViewHolder holder, int position) {
        String appPackage = mAppsPositions.get(position);
        String appName = appPackage;
        Drawable appIcon = null;

        ApplicationInfo info = Util.getApplicationInfo(mContext, appPackage);
        if (info != null) {
            CharSequence appLabel = mPM.getApplicationLabel(info);
            if (appLabel != null) {
                appName = appLabel.toString();
            }
            appIcon = mPM.getApplicationIcon(info);
        }

        holder.ivAppIcon.setImageDrawable(appIcon);
        holder.tvAppName.setText(appName);
    }

    private void bindPermissionSwitchViewHolder(PermissionInfoViewHolder holder, final int position) {
        final PermissionConfig config = mPermissionPositions.get(position);

        String permName = config.permissionName;
        String permDesc = null;

        int permNameRes = PermRes.getLabel(config.permissionName);
        if (permNameRes > 0) {
            permName = capitalize(mContext.getString(permNameRes));
        }
        int permDescRes = PermRes.getDescription(config.permissionName);
        if (permDescRes > 0) {
            permDesc = mContext.getString(permDescRes);
        }

        holder.tvPermissionName.setText(permName);
        holder.tvPermissionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDisplayDescription.put(position, !displayDescription(position));
                notifyItemChanged(position);
            }
        });

        holder.tvPermissionDesc.setText(permDesc);
        holder.tvPermissionDesc.setVisibility(displayDescription(position) ? View.VISIBLE : View.GONE);

        holder.sPermissionAccess.setAdapter(ArrayAdapter.createFromResource(mContext, R.array.access_configurations,
                android.R.layout.simple_spinner_dropdown_item));
        holder.sPermissionAccess.setSelection(settingsToSelection[config.setting]);
        holder.sPermissionAccess.setOnItemSelectedListener(new SimpleOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mConfigManager.changeConfig(config, selectionToSettings[position]);
            }
        });
    }

    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private boolean displayDescription(int position) {
        return mDisplayDescription.get(position) == Boolean.TRUE;
    }
}
