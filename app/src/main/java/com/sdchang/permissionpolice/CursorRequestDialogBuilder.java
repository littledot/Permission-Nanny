package com.sdchang.permissionpolice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.AlertDialog;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.CursorRequest;
import com.sdchang.permissionpolice.lib.request.CursorRequestPermissionReceiver;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class CursorRequestDialogBuilder implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    Activity mActivity;
    CursorRequest mRequest;
    String mTitle;
    String mReason;

    public CursorRequestDialogBuilder(Activity context, Bundle args) {
        String senderPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mActivity = context;
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
        mReason = args.getString(BaseRequest.REQUEST_REASON);

        CharSequence senderLabel;
        try {
            ApplicationInfo senderInfo = context.getPackageManager().getApplicationInfo(senderPackage, 0);
            senderLabel = context.getPackageManager().getApplicationLabel(senderInfo);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage=%s", senderPackage);
            senderLabel = senderPackage;
        }

        if (Contacts.CONTENT_URI.equals(mRequest.uri())) {
            mTitle = senderLabel.toString() + " would like to access your contacts.";
        }
    }

    public AlertDialog build() {
        return new AlertDialog.Builder(mActivity)
                .setTitle(mTitle)
                .setMessage(mReason)
                .setPositiveButton("OK", this)
                .setNegativeButton("NO", this)
                .setOnDismissListener(this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (DialogInterface.BUTTON_POSITIVE == which) {
            acceptRequest();
        }
    }

    public void acceptRequest() {
        long nonce = new SecureRandom().nextLong();

        // cache request params
        CursorContentProvider.approvedRequests.put(nonce, mRequest);

        // return nonce to client
        mActivity.sendBroadcast(new Intent(CursorRequestPermissionReceiver.ACTION_FILTER)
                .putExtra(CursorRequestPermissionReceiver.NONCE, nonce));
        Timber.wtf("nonce=" + nonce);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mActivity.finish();
    }
}
