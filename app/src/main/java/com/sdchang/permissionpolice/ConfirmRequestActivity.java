package com.sdchang.permissionpolice;

import android.os.Bundle;
import com.sdchang.permissionpolice.content.CursorRequestDialogBuilder;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.wifi.WifiManagerRequestDialogBuilder;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    private Bundle mArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = getIntent().getExtras();

        int type = mArgs.getInt(BaseRequest.REQUEST_TYPE, 0);
        if (type == BaseRequest.CURSOR_REQUEST) {
            new CursorRequestDialogBuilder(this, mArgs).build().show();
        } else if (type == BaseRequest.WIFI_MGR_REQUEST) {
            new WifiManagerRequestDialogBuilder(this, mArgs).build().show();
        }
    }
}
