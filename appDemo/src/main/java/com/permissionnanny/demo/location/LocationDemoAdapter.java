package com.permissionnanny.demo.location;

import android.animation.ObjectAnimator;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.demo.C;
import com.permissionnanny.demo.Config;
import com.permissionnanny.demo.DemoViewHolder;
import com.permissionnanny.demo.EzMap;
import com.permissionnanny.demo.R;
import com.permissionnanny.lib.request.simple.SimpleListener;
import com.permissionnanny.lib.Nanny;
import com.thedeanda.lorem.Lorem;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import org.apache.http.HttpStatus;

/**
 *
 */
public class LocationDemoAdapter extends RecyclerView.Adapter<DemoViewHolder> {

    LocationRequestFactory mFactory = new LocationRequestFactory();
    Bundle[] mResults = new Bundle[mFactory.getCount()];
    String[] mData = new String[mFactory.getCount()];

    public LocationDemoAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public int getItemCount() {
        return mFactory.getCount();
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_listitem, parent, false));
    }

    @Override
    public void onBindViewHolder(final DemoViewHolder holder, final int position) {
        holder.tvRequest.setText(mFactory.getLabel(position));
        holder.btnExtras.setVisibility(mFactory.hasExtras(position) ? View.VISIBLE : View.GONE);
        holder.btnExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFactory.buildDialog(v.getContext(), position).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFactory.getRequest(position).listener(new SimpleListener() {
                    @Override
                    public void onResponse(@NonNull Bundle results) {
                        mResults[position] = results;
                        Bundle response = results.getBundle(Nanny.ENTITY_BODY);
                        if (response != null) {
                            mData[position] = BundleUtil.toString(response);
                        }
                        notifyItemChanged(position);
                    }
                }).startRequest(v.getContext(), Config.longReason ? Lorem.getParagraphs(10, 10) : "demo reason");
            }
        });

        Bundle results = mResults[position];
        if (results == null) {
            holder.tvResponse.setText(null);
            holder.itemView.setBackgroundColor(0);
        } else if (HttpStatus.SC_OK == results.getInt(Nanny.STATUS_CODE)) {
            String newData = "Allowed\n" + mData[position];
            if (!holder.tvResponse.getText().equals(newData)) {
                if (VERSION.SDK_INT >= 11) {
                    ObjectAnimator.ofFloat(holder.itemView, "alpha", 1, 0, 1, 0, 1).setDuration(2000).start();
                }
            }
            holder.tvResponse.setText(newData);
            holder.itemView.setBackgroundColor(0xFF00FF00);
        } else {
            holder.tvResponse.setText("Denied");
            holder.itemView.setBackgroundColor(0xFFFF0000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(EzMap event) {
        int position = event.get(C.POS);
        mData[position] = event.get(C.DATA);
        notifyItemChanged(position);
    }
}
