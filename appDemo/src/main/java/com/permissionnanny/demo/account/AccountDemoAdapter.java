package com.permissionnanny.demo.account;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.permissionnanny.demo.Config;
import com.permissionnanny.demo.DataAdapter;
import com.permissionnanny.demo.DemoViewHolder;
import com.permissionnanny.demo.R;
import com.permissionnanny.lib.Nanny;
import com.thedeanda.lorem.Lorem;
import java.net.HttpURLConnection;

/**
 *
 */
public class AccountDemoAdapter extends RecyclerView.Adapter<DemoViewHolder> implements DataAdapter {

    AccountRequestFactory mFactory = new AccountRequestFactory();
    Bundle[] mResults = new Bundle[mFactory.getCount()];
    String[] mData = new String[mFactory.getCount()];

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(parent.getContext())
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
                mFactory.getRequest(position, AccountDemoAdapter.this)
                        .startRequest(v.getContext(), Config.longReason ? Lorem.getParagraphs(10, 10) : "demo reason");
            }
        });

        Bundle results = mResults[position];
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
    public int getItemCount() {
        return mFactory.getCount();
    }

    @Override
    public void onResponse(int position, Bundle response) {
        mResults[position] = response;
        notifyItemChanged(position);
    }

    @Override
    public void onDisplay(int position, String data) {
        mData[position] = data;
        notifyItemChanged(position);
    }
}
