package com.sdchang.permissionpolice;

import android.os.Bundle;
import com.sdchang.permissionpolice.lib.request.BaseRequest;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity {

    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getIntent().getExtras();

        int type = args.getInt(BaseRequest.REQUEST_TYPE, 0);
        if (type == BaseRequest.CURSOR_REQUEST) {
            new CursorRequestDialogBuilder(this, args).build().show();
        }
    }
}
