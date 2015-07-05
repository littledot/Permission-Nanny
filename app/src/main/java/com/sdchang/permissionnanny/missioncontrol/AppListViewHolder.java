package com.sdchang.permissionnanny.missioncontrol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionnanny.R;

public class AppListViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.appName) TextView tvAppName;
    @InjectView(R.id.appIcon) ImageView ivAppIcon;

    public AppListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
