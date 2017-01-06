package com.permissionnanny.content

import android.content.ContentProvider
import android.content.ContentValues
import android.database.CrossProcessCursorWrapper
import android.database.Cursor
import android.net.Uri
import android.os.Build.VERSION
import android.support.v4.util.LongSparseArray
import com.permissionnanny.lib.request.RequestParams

/**

 */
class ProxyContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun getType(uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if (VERSION.SDK_INT >= 15) {
            val request = validateRequest(uri)
            return if (request == null)
                null
            else
                CrossProcessCursorWrapper(context.contentResolver
                        .query(request.uri0, request.stringArray0, request.string0, request.stringArray1, request.string1))
        }
        return null
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    /**
     * @param uri
     * *
     * @return
     */
    private fun validateRequest(uri: Uri): RequestParams? {
        val nonce: Long
        try {
            nonce = java.lang.Long.parseLong(uri.lastPathSegment)
        } catch (e: NumberFormatException) {
            return null
        }

        val request = approvedRequests.get(nonce)
        approvedRequests.remove(nonce)
        return request
    }

    companion object {

        // TODO #1: Set a TTL for approved ContentRequests.
        var approvedRequests = LongSparseArray<RequestParams>()
    }
}
