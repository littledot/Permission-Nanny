package com.sdchang.permissionnanny.missioncontrol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionnanny.R;

public class PermissionSwitchViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.permissionName) TextView tvPermissionName;
    @InjectView(R.id.permissionDesc) TextView tvPermissionDesc;
    @InjectView(R.id.permissionAccess) Spinner sPermissionAccess;

    public PermissionSwitchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
