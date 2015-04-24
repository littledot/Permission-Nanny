package com.sdchang.permissionpolice;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import timber.log.Timber;

/**
 *
 */
public class ContactsListService extends BaseService implements Loader.OnLoadCompleteListener<Cursor> {
    CursorLoader loader;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        loader = new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        loader.registerListener(1, this);
        loader.startLoading();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loader.stopLoading();
        stopSelf();
    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
        Timber.wtf(DatabaseUtils.dumpCursorToString(data));
        stopSelf();
    }
}
