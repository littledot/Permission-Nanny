package com.permissionnanny.lib.request.content;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Listener to attach to {@link ContentRequest}s to receive responses from Permission Nanny.
 */
public interface ContentListener {
    /**
     * Callback when Permission Nanny returns a response.
     *
     * @param response    Response metadata
     * @param data        Query results
     * @param inserted    Insert results
     * @param rowsUpdated Update results
     * @param rowsDeleted Delete results
     */
    void onResponse(@NonNull Bundle response,
                    @Nullable Cursor data,
                    @Nullable Uri inserted,
                    int rowsUpdated,
                    int rowsDeleted);
}
