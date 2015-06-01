package com.sdchang.permissionpolice.content;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import com.sdchang.permissionpolice.BaseDialogBuilder;
import com.sdchang.permissionpolice.R;
import com.sdchang.permissionpolice.lib.request.content.CursorRequest;
import com.sdchang.permissionpolice.lib.request.content.CursorRequestHandshakeReceiver;
import org.apache.http.protocol.HTTP;
import timber.log.Timber;

import java.security.SecureRandom;

/**
 *
 */
public class CursorRequestDialogBuilder extends BaseDialogBuilder<CursorRequest> {

    public CursorRequestDialogBuilder(Activity activity, Bundle args) {
        super(activity, args);
    }

    @Override
    protected CharSequence buildDialogTitle(CharSequence appLabel) {
        SpannableStringBuilder boldAppLabel = new SpannableStringBuilder(appLabel);
        boldAppLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, appLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // TODO #4: Parse other types of content provider URIs to show client intent to the user
        int string = 0;
        if (authorityMatches(Contacts.CONTENT_FILTER_URI)) {
            string = R.string.dialogTitle_contactsContentFilterUri;
        } else if (authorityMatches(Contacts.CONTENT_GROUP_URI)) {
            string = R.string.dialogTitle_contactsContentGroupUri;
        } else if (authorityMatches(Contacts.CONTENT_LOOKUP_URI)) {
            string = R.string.dialogTitle_contactsContentLookupUri;
        } else if (authorityMatches(Contacts.CONTENT_STREQUENT_FILTER_URI)) {
            string = R.string.dialogTitle_contactsContentStrequentFilterUri;
        } else if (authorityMatches(Contacts.CONTENT_STREQUENT_URI)) {
            string = R.string.dialogTitle_contactsContentStrequentUri;
        } else if (authorityMatches(Contacts.CONTENT_URI)) {
            string = R.string.dialogTitle_contactsContentUri;
        } else if (authorityMatches(Contacts.CONTENT_VCARD_URI)) {
            string = R.string.dialogTitle_contactsContentVcardUri;
        }
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            if (authorityMatches(Contacts.CONTENT_FREQUENT_URI)) {
                string = R.string.dialogTitle_contactsContentFilterUri;
            } else if (authorityMatches(Contacts.CONTENT_MULTI_VCARD_URI)) {
                string = R.string.dialogTitle_contactsContentMultiVcardUri;
            }
        }
        return boldAppLabel.append(mActivity.getText(string));
    }

    boolean authorityMatches(Uri target) {
        return mRequest.uri().toString().startsWith(target.toString());
    }

    @Override
    protected Intent onAllowRequest() {
        long nonce = new SecureRandom().nextLong();
        Timber.wtf("nonce=" + nonce);

        // cache request params
        CursorContentProvider.approvedRequests.put(nonce, mRequest);

        // return nonce to client
        return super.onAllowRequest()
                .putExtra(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE)
                .putExtra(CursorRequestHandshakeReceiver.NONCE, nonce);
    }
}
