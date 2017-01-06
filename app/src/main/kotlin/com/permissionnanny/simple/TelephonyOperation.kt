package com.permissionnanny.simple

import android.Manifest
import android.content.Context
import android.content.pm.PermissionInfo
import android.os.Build.VERSION
import android.telephony.TelephonyManager
import com.permissionnanny.R
import com.permissionnanny.lib.request.simple.TelephonyRequest
import java.util.*
import javax.inject.Inject

/**

 */
class TelephonyOperation
@Inject
constructor() {
    companion object {
        val operations = arrayOf(
                SimpleOperation(TelephonyRequest.GET_ALL_CELL_INFO,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetAllCellInfo, 17,
                        { context, request, response ->
                            if (VERSION.SDK_INT >= 17) {
                                val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                                response.putParcelableArrayList(request.opCode, ArrayList(mgr.allCellInfo))
                            }
                        }),
                SimpleOperation(TelephonyRequest.GET_DEVICE_ID,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetDeviceId, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.deviceId)
                        }),
                SimpleOperation(TelephonyRequest.GET_DEVICE_SOFTWARE_VERSION,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetDeviceSoftwareVersion, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.deviceSoftwareVersion)
                        }),
                SimpleOperation(TelephonyRequest.GET_GROUP_ID_LEVEL_1,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetGroupIdLevel1, 18,
                        { context, request, response ->
                            if (VERSION.SDK_INT >= 18) {
                                val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                                response.putString(request.opCode, mgr.groupIdLevel1)
                            }
                        }),
                SimpleOperation(TelephonyRequest.GET_LINE_1_NUMBER,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetLine1Number, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.line1Number)
                        }),
                SimpleOperation(TelephonyRequest.GET_NEIGHBORING_CELL_INFO,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetNeighboringCellInfo, 3,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putParcelableArrayList(request.opCode, ArrayList(mgr.neighboringCellInfo))
                        }),
                SimpleOperation(TelephonyRequest.GET_SIM_SERIAL_NUMBER,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetSimSerialNumber, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.simSerialNumber)
                        }),
                SimpleOperation(TelephonyRequest.GET_SUBSCRIBER_ID,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetSubscriberId, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.subscriberId)
                        }),
                SimpleOperation(TelephonyRequest.GET_VOICE_MAIL_ALPHA_TAG,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetVoiceMailAlphaTag, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.voiceMailAlphaTag)
                        }),
                SimpleOperation(TelephonyRequest.GET_VOICE_MAIL_NUMBER,
                        Manifest.permission.READ_PHONE_STATE,
                        PermissionInfo.PROTECTION_DANGEROUS,
                        R.string.dialogTitle_telephonyGetVoiceMailNumber, 1,
                        { context, request, response ->
                            val mgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            response.putString(request.opCode, mgr.voiceMailNumber)
                        })
        )

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
