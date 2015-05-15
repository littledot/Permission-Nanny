package com.sdchang.permissionpolice.content;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.Police;
import com.sdchang.permissionpolice.lib.request.BaseRequest;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import com.sdchang.permissionpolice.lib.request.content.CursorRequestHandshakeReceiver;
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

    public CursorRequestDialogBuilder(Activity activity, Bundle args) {
        mActivity = activity;
        String appPackage = args.getString(BaseRequest.SENDER_PACKAGE);
        mRequest = args.getParcelable(BaseRequest.REQUEST_BODY);
        mReason = args.getString(BaseRequest.REQUEST_REASON);

        CharSequence appLabel;
        try {
            ApplicationInfo senderInfo = activity.getPackageManager().getApplicationInfo(appPackage, 0);
            appLabel = activity.getPackageManager().getApplicationLabel(senderInfo);
        } catch (NameNotFoundException e) {
            Timber.e(e, "senderPackage=%s", appPackage);
            appLabel = appPackage;
        }
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // TODO #4: Parse other types of content provider URIs to show client intent to the user
        if (authorityMatches(Contacts.CONTENT_FILTER_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentFilterUri));
        } else if (authorityMatches(Contacts.CONTENT_GROUP_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentGroupUri));
        } else if (authorityMatches(Contacts.CONTENT_LOOKUP_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentLookupUri));
        } else if (authorityMatches(Contacts.CONTENT_STREQUENT_FILTER_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentStrequentFilterUri));
        } else if (authorityMatches(Contacts.CONTENT_STREQUENT_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentStrequentUri));
        } else if (authorityMatches(Contacts.CONTENT_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentUri));
        } else if (authorityMatches(Contacts.CONTENT_VCARD_URI)) {
            mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentVcardUri));
        }
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            if (authorityMatches(Contacts.CONTENT_FREQUENT_URI)) {
                mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentFilterUri));
            } else if (authorityMatches(Contacts.CONTENT_MULTI_VCARD_URI)) {
                mTitle = boldAppLabel.append(mActivity.getText(R.string.dialogTitle_contactsContentMultiVcardUri));
            }
        }
    }

    boolean authorityMatches(Uri target) {
        return mRequest.uri().toString().startsWith(target.toString());
    }

    public AlertDialog build() {
        return new AlertDialog.Builder(mActivity)
                .setTitle(mTitle)
                .setMessage(mReason)
                .setPositiveButton(R.string.dialog_allow, this)
                .setNegativeButton(R.string.dialog_deny, this)
                .setOnDismissListener(this)
                .setCancelable(false)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (DialogInterface.BUTTON_POSITIVE == which) {
            acceptRequest();
        } else if (DialogInterface.BUTTON_NEGATIVE == which) {
            denyRequest();
        }
    }

    private void acceptRequest() {
        long nonce = new SecureRandom().nextLong();
        Timber.wtf("nonce=" + nonce);

        // cache request params
        CursorContentProvider.approvedRequests.put(nonce, mRequest);

        // return nonce to client
        mActivity.sendBroadcast(new Intent(CursorRequest.CURSOR_INTENT_FILTER)
                .putExtra(Police.APPROVED, true)
                .putExtra(CursorRequestHandshakeReceiver.NONCE, nonce));
    }

    private void denyRequest() {
        mActivity.sendBroadcast(new Intent(CursorRequest.CURSOR_INTENT_FILTER)
                .putExtra(Police.APPROVED, false));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mActivity.finish();
    }
}
