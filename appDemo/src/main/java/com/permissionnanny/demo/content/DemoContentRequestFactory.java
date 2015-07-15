package com.permissionnanny.demo.content;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import com.permissionnanny.demo.ContentRequestFactory;
import com.permissionnanny.lib.request.content.ContentRequest;

/**
 *
 */
@TargetApi(19)
public class DemoContentRequestFactory implements ContentRequestFactory {
    private String[] mLabels = new String[]{
            "Calendar",
            "Contacts",
            "Audio",
            "Files",
            "Files - insert",
            "Files - update",
            "Files - delete",
            "Images",
            "Video",
            "Carriers",
            "MMS",
            "MMS+SMS",
            "SMS",
    };

    private ContentRequest[] mRequests = new ContentRequest[]{
            ContentRequest.newBuilder().select()
                    .uri(CalendarContract.Calendars.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(ContactsContract.Contacts.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(MediaStore.Files.getContentUri("external"))
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            null,
            null,
            null,
            ContentRequest.newBuilder().select()
                    .uri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(Telephony.Carriers.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(Telephony.Mms.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(Telephony.MmsSms.CONTENT_CONVERSATIONS_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build(),
            ContentRequest.newBuilder().select()
                    .uri(Telephony.Sms.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build()
    };

    static final String FILE = "/storage/sdcard/permission-nanny";

    @Override
    public ContentRequest getRequest(int position) {
        ContentValues val = new ContentValues();
        switch (position) {
        case 4:
            val.put(MediaStore.Files.FileColumns.DATA, FILE);
            return ContentRequest.newBuilder().insert()
                    .uri(MediaStore.Files.getContentUri("external"))
                    .contentValues(val)
                    .build();
        case 5:
            val.put("latitude", 123);
            val.put("longitude", 456);
            return ContentRequest.newBuilder().update()
                    .uri(MediaStore.Files.getContentUri("external"))
                    .contentValues(val)
                    .selection(MediaStore.Files.FileColumns.DATA + "='" + FILE + "'")
                    .build();
        case 6:
            return ContentRequest.newBuilder().delete()
                    .uri(MediaStore.Files.getContentUri("external"))
                    .selection(MediaStore.Files.FileColumns.DATA + "='" + FILE + "'")
                    .build();
        }
        return mRequests[position];
    }

    @Override
    public int getCount() {
        return mLabels.length;
    }

    @Override
    public String getLabel(int position) {
        return mLabels[position];
    }

    @Override
    public boolean hasExtras(int position) {
        return false;
    }

    @Override
    public Dialog buildDialog(Context context, int position) {
        return null;
    }
}
