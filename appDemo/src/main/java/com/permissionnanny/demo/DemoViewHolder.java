package com.permissionnanny.demo;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class DemoViewHolder extends ViewHolder {
    @Bind(R.id.tvRequest) public TextView tvRequest;
    @Bind(R.id.tvResponse) public TextView tvResponse;
    @Bind(R.id.btnExtras) public Button btnExtras;

    public DemoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
