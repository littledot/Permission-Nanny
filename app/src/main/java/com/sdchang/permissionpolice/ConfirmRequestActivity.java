package com.sdchang.permissionpolice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.CursorRequest;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class ConfirmRequestActivity extends BaseActivity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getIntent().getExtras();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("XYZ would like to access contacts.")
                .setMessage(args.getString(BaseRequest.REQUEST_REASON))
                .setPositiveButton("OK", this)
                .setNegativeButton("NO", this)
                .setOnDismissListener(this)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (DialogInterface.BUTTON_POSITIVE == which) {
            acceptRequest();
        }
    }

    public void acceptRequest() {
        int type = args.getInt(BaseRequest.REQUEST_TYPE, 0);
        if (type == BaseRequest.CURSOR_REQUEST) {
            long nonce = new SecureRandom().nextLong();
            // cache request params
            CursorRequest request = args.getParcelable(BaseRequest.REQUEST_BODY);
            ContactsListContentProvider.approvedRequests.put(nonce, request);

            Timber.wtf("nonce=" + nonce);
            sendBroadcast(new Intent("ppResult").putExtra("nonce", nonce));
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Timber.wtf("dismissed");
        finish();
    }
}
