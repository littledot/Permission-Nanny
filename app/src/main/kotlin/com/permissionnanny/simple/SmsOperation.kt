package com.permissionnanny.simple

import android.Manifest
import android.content.pm.PermissionInfo
import android.os.Build.VERSION
import android.telephony.SmsManager
import com.permissionnanny.R
import com.permissionnanny.lib.request.simple.SmsRequest
import javax.inject.Inject

/**

 */
class SmsOperation
@Inject
constructor() {
    companion object {
        val operations = arrayOf(
                SimpleOperation(SmsRequest.SEND_DATA_MESSAGE,
                        Manifest.permission.SEND_SMS,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_smsSendDataMessage, 4,
                        { context, request, response ->
                            val mgr = SmsManager.getDefault()
                            mgr.sendDataMessage(request.string0, request.string1, request.int0.toShort(), request.byteArray0,
                                    request.pendingIntent0, request.pendingIntent1)
                        }),
                SimpleOperation(SmsRequest.SEND_MULTIMEDIA_MESSAGE,
                        Manifest.permission.SEND_SMS,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_smsSendMultimediaMessage, 21,
                        { context, request, response ->
                            if (VERSION.SDK_INT >= 21) {
                                val mgr = SmsManager.getDefault()
                                mgr.sendMultimediaMessage(null, request.uri0, request.string0, request.bundle0,
                                        request.pendingIntent0)
                            }
                        }),
                SimpleOperation(SmsRequest.SEND_MULTIPART_TEXT_MESSAGE,
                        Manifest.permission.SEND_SMS,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_smsSendMultipartTextMessage, 4,
                        { context, request, response ->
                            val mgr = SmsManager.getDefault()
                            mgr.sendMultipartTextMessage(request.string0, request.string1, request.arrayListOfStrings0,
                                    request.arrayListOfPendingIntents0, request.arrayListOfPendingIntents1)
                        }),
                SimpleOperation(SmsRequest.SEND_TEXT_MESSAGE,
                        Manifest.permission.SEND_SMS,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_smsSendTextMessage, 4,
                        { context, request, response ->
                            val mgr = SmsManager.getDefault()
                            mgr.sendTextMessage(request.string0, request.string1, request.string2,
                                    request.pendingIntent0, request.pendingIntent1)
                        }))

        fun getOperation(opCode: String): SimpleOperation? {
            for (operation in operations) {
                if (operation.opCode == opCode) {
                    return operation
                }
            }
            return null
        }
    }
}
