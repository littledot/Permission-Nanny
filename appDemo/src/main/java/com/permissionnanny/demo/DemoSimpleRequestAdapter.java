package com.permissionnanny.demo;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.permissionnanny.lib.Nanny;
import com.thedeanda.lorem.Lorem;
import java.net.HttpURLConnection;

/**
 *
 */
public class DemoSimpleRequestAdapter<VH extends DemoViewHolder> extends RecyclerView.Adapter<VH> implements DataAdapter {
    private final SimpleRequestFactory mFactory;
    private final Bundle[] mResponse;
    private final String[] mData;

    public DemoSimpleRequestAdapter(SimpleRequestFactory factory) {
        mFactory = factory;
        mResponse = new Bundle[mFactory.getCount()];
        mData = new String[mFactory.getCount()];
    }

    @Override
    public int getItemCount() {
        return mFactory.getCount();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return (VH) new DemoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_listitem, parent, false));
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, final int position) {
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
                mFactory.getRequest(position, DemoSimpleRequestAdapter.this)
                        .startRequest(v.getContext(), Config.longReason ? Lorem.getParagraphs(10, 10) : "demo reason");
            }
        });

        Bundle results = mResponse[position];
        if (results == null) {
            holder.tvResponse.setText(null);
            holder.itemView.setBackgroundColor(0);
        } else {
            int sc = results.getInt(Nanny.STATUS_CODE);
            String newData = sc + "\n" + mData[position];
            holder.tvResponse.setText(newData);

            if (HttpURLConnection.HTTP_OK == sc) {
                if (!holder.tvResponse.getText().equals(newData)) {
                    if (Build.VERSION.SDK_INT >= 11) {
                        ObjectAnimator.ofFloat(holder.itemView, "alpha", 1, 0, 1, 0, 1).setDuration(2000).start();
                    }
                }
                holder.itemView.setBackgroundColor(Color.GREEN);
            } else {
                holder.itemView.setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    public void onResponse(int position, Bundle response) {
        mResponse[position] = response;
        notifyItemChanged(position);
    }

    @Override
    public void onData(int position, String data) {
        mData[position] = data;
        notifyItemChanged(position);
    }
}
