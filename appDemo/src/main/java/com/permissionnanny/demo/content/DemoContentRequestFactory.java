package com.permissionnanny.demo.content;

import android.annotation.TargetApi;
import android.app.Dialog;
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
public class DemoContentRequestFactory implements ContentRequestFactory {
    private String[] mLabels = new String[]{
            "Calendar",
            "Contacts",
            "Audio",
            "Files",
            "Images",
            "Video",
            "Carriers",
            "MMS",
            "MMS+SMS",
            "SMS",
    };

    @TargetApi(19)
    @Override
    public ContentRequest getRequest(int position) {
        switch (position) {
        case 0:
            return ContentRequest.newBuilder().select()
                    .uri(CalendarContract.Calendars.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 1:
            return ContentRequest.newBuilder().select()
                    .uri(ContactsContract.Contacts.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 2:
            return ContentRequest.newBuilder().select()
                    .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 3:
            return ContentRequest.newBuilder().select()
                    .uri(MediaStore.Files.getContentUri("external"))
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 4:
            return ContentRequest.newBuilder().select()
                    .uri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 5:
            return ContentRequest.newBuilder().select()
                    .uri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 6:
            return ContentRequest.newBuilder().select()
                    .uri(Telephony.Carriers.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 7:
            return ContentRequest.newBuilder().select()
                    .uri(Telephony.Mms.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 8:
            return ContentRequest.newBuilder().select()
                    .uri(Telephony.MmsSms.CONTENT_CONVERSATIONS_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        case 9:
            return ContentRequest.newBuilder().select()
                    .uri(Telephony.Sms.CONTENT_URI)
                    .sortOrder(BaseColumns._ID + " limit 5")
                    .build();
        }
        return null;
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
