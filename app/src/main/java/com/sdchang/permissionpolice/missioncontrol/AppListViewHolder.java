package com.sdchang.permissionpolice.missioncontrol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionpolice.R;

public class AppListViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tvAppName) TextView tvAppName;

    public AppListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
