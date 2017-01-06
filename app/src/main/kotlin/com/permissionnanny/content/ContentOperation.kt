package com.permissionnanny.content

import android.annotation.TargetApi
import android.content.pm.PermissionInfo
import android.net.Uri
import android.os.Build.VERSION_CODES
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.MediaStore.Files
import android.provider.Telephony.*
import com.permissionnanny.Operation
import com.permissionnanny.R
import com.permissionnanny.lib.request.RequestParams

/**

 */
@TargetApi(VERSION_CODES.LOLLIPOP)
class ContentOperation(
        val uri: Uri,
        val contentType: Int,
        dialogTitle: Int,
        minSdk: Int)
    : Operation(dialogTitle, minSdk, PermissionInfo.PROTECTION_DANGEROUS) {

    companion object {

        val CONTENT_CALENDAR = 1
        val CONTENT_CONTACTS = 2
        val CONTENT_EXTERNAL_STORAGE = 3
        val CONTENT_SMS = 4

        // TODO #4: Parse other types of content provider URIs to show client intent to the user
        var operations = arrayOf(
                // CalendarContract
                // TODO #23: Implement CalendarContract's static query methods in a request builder
                ContentOperation(CalendarContract.CONTENT_URI, CONTENT_CALENDAR, R.string.dialogTitle_contentCalendar, 14),

                // ContactContract
                ContentOperation(ContactsContract.AUTHORITY_URI, CONTENT_CONTACTS, R.string.dialogTitle_contentContacts, 5),

                // MediaStore
                ContentOperation(Uri.parse("content://media/external/audio"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentAudio, 1),
                ContentOperation(Uri.parse("content://media/internal/audio"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentAudio, 1),
                ContentOperation(Files.getContentUri("external"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentFiles, 11),
                ContentOperation(Files.getContentUri("internal"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentFiles, 11),
                ContentOperation(Uri.parse("content://media/external/images"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentImages, 1),
                ContentOperation(Uri.parse("content://media/internal/images"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentImages, 1),
                ContentOperation(Uri.parse("content://media/external/video"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentVideos, 1),
                ContentOperation(Uri.parse("content://media/internal/video"), CONTENT_EXTERNAL_STORAGE, R.string.dialogTitle_contentVideos, 1),

                // Telephony
                ContentOperation(Carriers.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentCarriers, 19),
                ContentOperation(Mms.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentMms, 19),
                ContentOperation(MmsSms.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentMmsSms, 19),
                ContentOperation(Sms.CONTENT_URI, CONTENT_SMS, R.string.dialogTitle_contentSms, 19)
        )

        fun getOperation(request: RequestParams): ContentOperation? {
            val requestUri = request.uri0.toString()
            for (operation in ContentOperation.operations) {
                if (requestUri.startsWith(operation.uri.toString())) {
                    return operation
                }
            }
            return null
        }
    }
}
