package com.permissionnanny.lib.request.content;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public interface CursorListener {
    /**
     * @param data
     * @param inserted
     * @param rowsUpdated
     * @param rowsDeleted
     */
    void onResult(Bundle result, Cursor data, Uri inserted, int rowsUpdated, int rowsDeleted);
}
