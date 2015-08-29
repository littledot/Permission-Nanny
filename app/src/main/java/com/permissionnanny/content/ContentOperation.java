package com.permissionnanny.content;

import android.annotation.TargetApi;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore.Files;
import android.provider.Telephony.Carriers;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.Sms;
import com.permissionnanny.Operation;
import com.permissionnanny.R;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
@TargetApi(VERSION_CODES.LOLLIPOP)
public class ContentOperation extends Operation {

    public static final int CONTENT_CALENDAR = 1;
    public static final int CONTENT_CONTACTS = 2;
    public static final int CONTENT_EXTERNAL_STORAGE = 3;
    public static final int CONTENT_SMS = 4;

    // TODO #4: Parse other types of content provider URIs to show client intent to the user
    public static ContentOperation[] operations = new ContentOperation[]{
            // CalendarContract
            // TODO #23: Implement CalendarContract's static query methods in a request builder
            new ContentOperation(CalendarContract.CONTENT_URI, CONTENT_CALENDAR,
                    R.string.dialogTitle_contentCalendar, 14),

            // ContactContract
            new ContentOperation(ContactsContract.AUTHORITY_URI, CONTENT_CONTACTS,
                    R.string.dialogTitle_contentContacts, 5),

            // MediaStore
            new ContentOperation(Uri.parse("content://media/external/audio"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentAudio, 1),
            new ContentOperation(Uri.parse("content://media/internal/audio"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentAudio, 1),
            new ContentOperation(Files.getContentUri("external"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentFiles, 11),
            new ContentOperation(Files.getContentUri("internal"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentFiles, 11),
            new ContentOperation(Uri.parse("content://media/external/images"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentImages, 1),
            new ContentOperation(Uri.parse("content://media/internal/images"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentImages, 1),
            new ContentOperation(Uri.parse("content://media/external/video"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentVideos, 1),
            new ContentOperation(Uri.parse("content://media/internal/video"), CONTENT_EXTERNAL_STORAGE,
                    R.string.dialogTitle_contentVideos, 1),

            // Telephony
            new ContentOperation(Carriers.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentCarriers, 19),
            new ContentOperation(Mms.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentMms, 19),
            new ContentOperation(MmsSms.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentMmsSms, 19),
            new ContentOperation(Sms.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentSms, 19),
    };

    public static ContentOperation getOperation(RequestParams request) {
        String requestUri = request.uri0.toString();
        for (ContentOperation operation : ContentOperation.operations) {
            if (requestUri.startsWith(operation.mUri.toString())) {
                return operation;
            }
        }
        return null;
    }

    public final Uri mUri;
    public final int mContentType;

    public ContentOperation(Uri uri, int contentType, int dialogTitle, int minSdk) {
        super(dialogTitle, minSdk, PermissionInfo.PROTECTION_DANGEROUS);
        mUri = uri;
        mContentType = contentType;
    }
}
