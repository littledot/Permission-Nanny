package com.permissionnanny.demo.content;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.permissionnanny.demo.DemoAdapter;
import com.permissionnanny.demo.DemoViewHolder;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.content.CursorListener;
import org.apache.http.HttpStatus;

/**
 *
 */
public class CursorDemoAdapter extends DemoAdapter {
    private CursorRequestFactory mFactory;
    private Bundle[] mResults;
    private String[] mContent;

    public CursorDemoAdapter(CursorRequestFactory factory) {
        super(factory);
        mFactory = factory;
        mResults = new Bundle[mFactory.getCount()];
        mContent = new String[mFactory.getCount()];
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFactory.getRequest(position).startRequest(v.getContext(), "demo", new CursorListener() {
                    @Override
                    public void onResult(Bundle result, Cursor data, Uri inserted, int rowsUpdated, int rowsDeleted) {
                        mResults[position] = result;
                        mContent[position] = DatabaseUtils.dumpCursorToString(data);
                        notifyItemChanged(position);
                    }
                });
            }
        });

        Bundle results = mResults[position];
        if (results == null) {
            holder.tvResponse.setText(null);
            holder.itemView.setBackgroundColor(0);
        } else if (HttpStatus.SC_OK == results.getInt(Nanny.STATUS_CODE)) {
            holder.tvResponse.setText("Allowed\n" + mContent[position]);
            holder.itemView.setBackgroundColor(0xFF00FF00);
        } else {
            holder.tvResponse.setText("Denied");
            holder.itemView.setBackgroundColor(0xFFFF0000);
        }
    }
}
