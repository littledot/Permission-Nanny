package com.sdchang.permissionpolice.content;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.StringRes;
import com.sdchang.permissionpolice.R;

/**
 *
 */
@TargetApi(VERSION_CODES.LOLLIPOP)
class ContentOperation {

    // TODO #4: Parse other types of content provider URIs to show client intent to the user
    static ContentOperation[] operations = new ContentOperation[]{
            new ContentOperation(Contacts.CONTENT_FILTER_URI, R.string.dialogTitle_contactsContentFilterUri, 5),
            new ContentOperation(Contacts.CONTENT_GROUP_URI, R.string.dialogTitle_contactsContentGroupUri, 5),
            new ContentOperation(Contacts.CONTENT_LOOKUP_URI, R.string.dialogTitle_contactsContentLookupUri, 5),
            new ContentOperation(Contacts.CONTENT_STREQUENT_FILTER_URI,
                    R.string.dialogTitle_contactsContentStrequentFilterUri, 5),
            new ContentOperation(Contacts.CONTENT_STREQUENT_URI, R.string.dialogTitle_contactsContentStrequentUri, 5),
            new ContentOperation(Contacts.CONTENT_URI, R.string.dialogTitle_contactsContentUri, 5),
            new ContentOperation(Contacts.CONTENT_VCARD_URI, R.string.dialogTitle_contactsContentVcardUri, 5),
            new ContentOperation(Contacts.CONTENT_FREQUENT_URI, R.string.dialogTitle_contactsContentFilterUri, 21),
            new ContentOperation(Contacts.CONTENT_MULTI_VCARD_URI, R.string.dialogTitle_contactsContentMultiVcardUri,
                    21),
            new ContentOperation(CalendarContract.CONTENT_URI, R.string.dialogTitle_calendarContentUri, 14),
            new ContentOperation(Events.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Events.CONTENT_EXCEPTION_URI,
                    R.string.dialogTitle_calendarEventsContentExceptionUri, 14),
    };

    public final Uri mUri;
    @StringRes public final int mDialogTitle;
    public final int mMinSdk;

    public ContentOperation(Uri uri, int dialogTitle, int minSdk) {
        mUri = uri;
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
    }
}
