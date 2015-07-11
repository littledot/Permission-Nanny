package com.permissionnanny.demo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.permissionnanny.common.BundleUtil;
import com.permissionnanny.lib.BundleListener;
import com.permissionnanny.lib.Nanny;
import com.thedeanda.lorem.Lorem;
import org.apache.http.HttpStatus;

/**
 *
 */
public class DemoSimpleRequestAdapter extends Adapter<DemoViewHolder> {

    private SimpleRequestFactory mFactory;
    private Bundle[] mResults;

    public DemoSimpleRequestAdapter(SimpleRequestFactory factory) {
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
                    public void onResponse(Bundle response) {
                        mResults[position] = response;
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
            holder.tvResponse.setText("Allowed\n" + BundleUtil.toString(results));
            holder.itemView.setBackgroundColor(0xFF00FF00);
        } else {
            holder.tvResponse.setText("Denied\n" + BundleUtil.toString(results));
            holder.itemView.setBackgroundColor(0xFFFF0000);
        }
    }
}
