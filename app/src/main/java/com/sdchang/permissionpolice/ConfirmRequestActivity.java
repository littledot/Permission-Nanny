package com.sdchang.permissionpolice;

import android.os.Bundle;
import com.sdchang.permissionpolice.content.CursorRequestDialogBuilder;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.sms.SmsRequestDialogBuilder;
import com.sdchang.permissionpolice.telephony.TelephonyRequestDialogBuilder;
import com.sdchang.permissionpolice.wifi.WifiRequestDialogBuilder;

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
        } else if (type == BaseRequest.WIFI_REQUEST) {
            new WifiRequestDialogBuilder(this, mArgs).build().show();
        } else if (type == BaseRequest.TELEPHONY_REQUEST) {
            new TelephonyRequestDialogBuilder(this, mArgs).build().show();
        } else if (type == BaseRequest.SMS_REQUEST) {
            new SmsRequestDialogBuilder(this, mArgs).build().show();
        }
    }
}
