package com.sdchang.permissionpolice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
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
    CharSequence mTitle;
    String mReason;

    public CursorRequestDialogBuilder(Activity context, Bundle args) {
        mActivity = context;
        String appPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
        mReason = args.getString(BaseRequest.REQUEST_REASON);

        CharSequence appLabel;
        try {
            ApplicationInfo senderInfo = context.getPackageManager().getApplicationInfo(appPackage, 0);
            appLabel = context.getPackageManager().getApplicationLabel(senderInfo);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage=%s", appPackage);
            appLabel = appPackage;
        }
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (Contacts.CONTENT_URI.equals(mRequest.uri())) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_accessContacts));
        }
    }

    public AlertDialog build() {
        return new AlertDialog.Builder(mActivity)
                .setTitle(mTitle)
                .setMessage(mReason)
                .setPositiveButton(R.string.dialog_allow, this)
                .setNegativeButton(R.string.dialog_deny, this)
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
        Timber.wtf("nonce=" + nonce);

        // cache request params
        CursorContentProvider.approvedRequests.put(nonce, mRequest);

        // return nonce to client
        mActivity.sendBroadcast(new Intent(CursorRequestPermissionReceiver.ACTION_FILTER)
                .putExtra(CursorRequestPermissionReceiver.NONCE, nonce));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mActivity.finish();
    }
}
