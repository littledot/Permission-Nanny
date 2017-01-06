package com.permissionnanny.data

import android.os.Build
import android.support.v4.util.ArrayMap
import com.permissionnanny.Manifest
import com.permissionnanny.R.string.*

/**

 */
class PermissionDetail(val label: Int, val description: Int) {
    companion object {
        private val resMap = ArrayMap<String, PermissionDetail>()

        init {
            resMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, PermissionDetail(permlab_accessCoarseLocation, permdesc_accessCoarseLocation))
            resMap.put(Manifest.permission.ACCESS_FINE_LOCATION, PermissionDetail(permlab_accessFineLocation, permdesc_accessFineLocation))
            resMap.put(Manifest.permission.ACCESS_WIFI_STATE, PermissionDetail(permlab_accessWifiState, permdesc_accessWifiState))
            resMap.put(Manifest.permission.CHANGE_WIFI_STATE, PermissionDetail(permlab_changeWifiState, permdesc_changeWifiState))
            resMap.put(Manifest.permission.READ_CALENDAR, PermissionDetail(permlab_readCalendar, permdesc_readCalendar))
            resMap.put(Manifest.permission.READ_CONTACTS, PermissionDetail(permlab_readContacts, permdesc_readContacts))
            if (Build.VERSION.SDK_INT >= 16) {
                resMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, PermissionDetail(permlab_sdcardRead, permdesc_sdcardRead))
            }
            resMap.put(Manifest.permission.READ_PHONE_STATE, PermissionDetail(permlab_readPhoneState, permdesc_readPhoneState))
            resMap.put(Manifest.permission.READ_SMS, PermissionDetail(permlab_readSms, permdesc_readSms))
            resMap.put(Manifest.permission.SEND_SMS, PermissionDetail(permlab_sendSms, permdesc_sendSms))
            resMap.put(Manifest.permission.WRITE_CALENDAR, PermissionDetail(permlab_writeCalendar, permdesc_writeCalendar))
        }

        fun getLabel(permission: String): Int {
            val res = resMap[permission]
            return res?.label ?: 0
        }

        fun getDescription(permission: String): Int {
            val res = resMap[permission]
            return res?.description ?: 0
        }
    }
}
