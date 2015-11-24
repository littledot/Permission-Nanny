package com.permissionnanny.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DemoPermissionManifestActivity extends BaseActivity {
    @Bind(R.id.rv) RecyclerView rv;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manifest_activity);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter = new ManifestAdapter());
    }

    @OnClick(R.id.sendManifest)
    void sendManifest() {
        new DemoPermissionManifestReceiver().onReceive(this, new Intent());
    }

    public static class ManifestAdapter extends RecyclerView.Adapter<ManifestViewHolder> {
        @Override
        public ManifestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manifest_listitem, viewGroup,
                    false);
            return new ManifestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ManifestViewHolder manifestViewHolder, int i) {
            DemoPermissionManifestReceiver.Value value = DemoPermissionManifestReceiver.data.get(i);
            manifestViewHolder.cbSend.setChecked(value.send);
            manifestViewHolder.cbSend.setText(value.permissionName);
            manifestViewHolder.cbSend.setTag(i);
            ButterKnife.bind(this, manifestViewHolder.itemView);
        }

        @Override
        public int getItemCount() {
            return DemoPermissionManifestReceiver.data.size();
        }

        @OnCheckedChanged(R.id.checkbox)
        void changeState(CheckBox view, boolean state) {
            DemoPermissionManifestReceiver.data.get((Integer) view.getTag()).send = state;
        }
    }

    public static class ManifestViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.checkbox) CheckBox cbSend;

        public ManifestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
