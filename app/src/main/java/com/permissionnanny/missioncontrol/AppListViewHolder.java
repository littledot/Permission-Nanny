package com.permissionnanny.missioncontrol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.R;

public class AppListViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.appName) TextView tvAppName;
    @Bind(R.id.appIcon) ImageView ivAppIcon;

    public AppListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
