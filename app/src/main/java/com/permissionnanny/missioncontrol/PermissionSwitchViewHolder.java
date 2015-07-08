package com.permissionnanny.missioncontrol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.R;

public class PermissionSwitchViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.permissionName) TextView tvPermissionName;
    @Bind(R.id.permissionDesc) TextView tvPermissionDesc;
    @Bind(R.id.permissionAccess) Spinner sPermissionAccess;

    public PermissionSwitchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
