package com.sdchang.permissionpolice.demo;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 */
public class DemoViewHolder extends ViewHolder {
    @InjectView(R.id.tvRequest) public TextView tvRequest;
    @InjectView(R.id.tvResponse) public TextView tvResponse;
    @InjectView(R.id.btnExtras) public Button btnExtras;

    public DemoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
