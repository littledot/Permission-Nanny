package com.sdchang.permissionpolice.demo.wifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.sdchang.permissionpolice.demo.R;
import com.sdchang.permissionpolice.lib.BundleListener;
import com.sdchang.permissionpolice.lib.Police;

/**
 *
 */
public class WifiRequestDemoActivity extends AppCompatActivity {
    @InjectView(R.id.rv) RecyclerView rv;
    WifiAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity);
        ButterKnife.inject(this);

        mAdapter = new WifiAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    class WifiAdapter extends Adapter<WifiViewHolder> {

        WifiRequestFactory mFactory = new WifiRequestFactory();
        Bundle[] mResults = new Bundle[mFactory.getCount()];

        @Override
        public int getItemCount() {
            return mFactory.getCount();
        }

        @Override
        public WifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WifiViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.wifi_listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(final WifiViewHolder holder, final int position) {
            holder.tvRequest.setText(mFactory.getLabel(position));
            holder.btnExtras.setVisibility(mFactory.hasExtras(position) ? View.VISIBLE : View.GONE);
            holder.btnExtras.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFactory.getDialog(v.getContext(), position).show();
                }
            });
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFactory.getRequest(position).startRequest(v.getContext(), "Demo Reason", new BundleListener() {
                        @Override
                        public void onResult(Bundle results) {
                            mResults[position] = results;
                            mAdapter.notifyItemChanged(position);
                        }
                    });
                }
            });

            Bundle results = mResults[position];
            if (results == null) {
                holder.tvResponse.setText(null);
                holder.itemView.setBackgroundColor(0);
            } else if (results.getBoolean(Police.APPROVED)) {
                Bundle response = results.getBundle(Police.RESPONSE);
                String ans = "Allowed\n";
                for (String key : response.keySet()) {
                    ans += key + " : " + response.get(key) + "\n";
                }
                holder.tvResponse.setText(ans);
                holder.itemView.setBackgroundColor(0xFF00FF00);
            } else {
                holder.tvResponse.setText("Denied");
                holder.itemView.setBackgroundColor(0xFFFF0000);
            }
        }
    }

    class WifiViewHolder extends ViewHolder {
        @InjectView(R.id.tvRequest) TextView tvRequest;
        @InjectView(R.id.tvResponse) TextView tvResponse;
        @InjectView(R.id.btnExtras) Button btnExtras;

        public WifiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
