package com.sdchang.permissionpolice.content;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore.Files;
import android.provider.Telephony.Carriers;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.Sms;
import android.support.annotation.StringRes;
import com.sdchang.permissionpolice.R;

/**
 *
 */
@TargetApi(VERSION_CODES.LOLLIPOP)
class ContentOperation {

    // TODO #4: Parse other types of content provider URIs to show client intent to the user
    public static ContentOperation[] operations = new ContentOperation[]{
            // CalendarContract
            // TODO #23: Implement CalendarContract's static query methods in a request builder
            new ContentOperation(CalendarContract.CONTENT_URI, R.string.dialogTitle_contentCalendar, 14),

            // ContactContract
            new ContentOperation(ContactsContract.AUTHORITY_URI, R.string.dialogTitle_contentContacts, 5),

            // MediaStore
            new ContentOperation(Uri.parse("content://media/external/audio"), R.string.dialogTitle_contentAudio, 1),
            new ContentOperation(Uri.parse("content://media/internal/audio"), R.string.dialogTitle_contentAudio, 1),
            new ContentOperation(Files.getContentUri("external"), R.string.dialogTitle_contentFiles, 11),
            new ContentOperation(Files.getContentUri("internal"), R.string.dialogTitle_contentFiles, 11),
            new ContentOperation(Uri.parse("content://media/external/images"), R.string.dialogTitle_contentImages, 1),
            new ContentOperation(Uri.parse("content://media/internal/images"), R.string.dialogTitle_contentImages, 1),
            new ContentOperation(Uri.parse("content://media/external/video"), R.string.dialogTitle_contentVideos, 1),
            new ContentOperation(Uri.parse("content://media/internal/video"), R.string.dialogTitle_contentVideos, 1),

            // Telephony
            new ContentOperation(Carriers.CONTENT_URI, R.string.dialogTitle_contentCarriers, 19),
            new ContentOperation(Mms.CONTENT_URI, R.string.dialogTitle_contentMms, 19),
            new ContentOperation(MmsSms.CONTENT_URI, R.string.dialogTitle_contentMmsSms, 19),
            new ContentOperation(Sms.CONTENT_URI, R.string.dialogTitle_contentSms, 19),
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
