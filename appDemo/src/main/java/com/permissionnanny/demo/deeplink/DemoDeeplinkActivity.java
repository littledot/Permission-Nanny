package com.permissionnanny.demo.deeplink;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.demo.BaseActivity;
import com.permissionnanny.demo.R;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.deeplink.DeepLinkRequest;

/**
 *
 */
public class DemoDeepLinkActivity extends BaseActivity {
    @Bind(R.id.rv) RecyclerView rv;
    RecyclerView.Adapter mAdapter;

    static final String[] DEEP_LINKS = new String[]{Nanny.MANAGE_APPLICATIONS_SETTINGS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deep_link_activity);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter = new DeepLinkAdapter());
    }

    public static class DeepLinkAdapter extends RecyclerView.Adapter<DeepLinkViewHolder> {
        @Override
        public DeepLinkViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1,
                    viewGroup, false);
            return new DeepLinkViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DeepLinkViewHolder deepLinkViewHolder, final int i) {
            deepLinkViewHolder.tvDeepLinkTarget.setText(DEEP_LINKS[i]);
            deepLinkViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeepLinkRequest request = new DeepLinkRequest(DEEP_LINKS[i]);
                    request.startRequest(v.getContext());
                }
            });
        }

        @Override
        public int getItemCount() {
            return DEEP_LINKS.length;
        }
    }

    public static class DeepLinkViewHolder extends RecyclerView.ViewHolder {
        @Bind(android.R.id.text1) TextView tvDeepLinkTarget;

        public DeepLinkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}