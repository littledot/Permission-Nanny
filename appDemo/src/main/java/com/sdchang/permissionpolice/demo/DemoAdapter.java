package com.sdchang.permissionpolice.demo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sdchang.permissionpolice.common.BundleUtil;
import com.sdchang.permissionpolice.lib.BundleListener;
import com.sdchang.permissionpolice.lib.Police;
import org.apache.http.HttpStatus;

/**
 *
 */
public class DemoAdapter extends Adapter<DemoViewHolder> {

    private DemoRequestFactory mFactory;
    private Bundle[] mResults;

    public DemoAdapter(DemoRequestFactory factory) {
        mFactory = factory;
        mResults = new Bundle[mFactory.getCount()];
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
        holder.btnExtras.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFactory.buildDialog(v.getContext(), position).show();
            }
        });
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFactory.getRequest(position).listener(new BundleListener() {
                    @Override
                    public void onResult(Bundle results) {
                        mResults[position] = results;
                        DemoAdapter.this.notifyItemChanged(position);
                    }
                }).startRequest(v.getContext(), "Demo Reason");
            }
        });

        Bundle results = mResults[position];
        if (results == null) {
            holder.tvResponse.setText(null);
            holder.itemView.setBackgroundColor(0);
        } else if (HttpStatus.SC_OK == results.getInt(Police.STATUS_CODE)) {
            Bundle response = results.getBundle(Police.ENTITY_BODY);
            holder.tvResponse.setText("Allowed\n" + BundleUtil.toString(response));
            holder.itemView.setBackgroundColor(0xFF00FF00);
        } else {
            holder.tvResponse.setText("Denied");
            holder.itemView.setBackgroundColor(0xFFFF0000);
        }
    }
}
