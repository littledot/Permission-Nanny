package com.permissionnanny.simple

import android.content.Context
import android.os.Bundle
import android.support.v4.util.ArrayMap
import com.permissionnanny.Operation
import com.permissionnanny.lib.request.RequestParams

/**

 */
open class SimpleOperation(
        val opCode: String,
        val permission: String,
        protectionLevel: Int,
        dialogTitle: Int,
        minSdk: Int,
        val proxyFunction: ((Context, RequestParams, Bundle) -> Unit)?)
    : Operation(dialogTitle, minSdk, protectionLevel) {

    companion object {
        val operations: MutableMap<String, SimpleOperation> = ArrayMap()

        init {
//            for (operation in AccountOperation.operations) {
//                operations.put(operation.opCode, operation)
//            }
//            for (operation in LocationOperation.operations) {
//                operations.put(operation.opCode, operation)
//            }
//            for (operation in TelephonyOperation.operations) {
//                operations.put(operation.opCode, operation)
//            }
//            for (operation in SmsOperation.operations) {
//                operations.put(operation.opCode, operation)
//            }
//            for (operation in WifiOperation.operations) {
//                operations.put(operation.opCode, operation)
//            }
        }

        fun getOperation(params: RequestParams): SimpleOperation? {
            if(operations.isEmpty()){
                for (operation in AccountOperation.operations) {
                    operations.put(operation.opCode, operation)
                }
                for (operation in LocationOperation.operations) {
                    operations.put(operation.opCode, operation)
                }
                for (operation in TelephonyOperation.operations) {
                    operations.put(operation.opCode, operation)
                }
                for (operation in SmsOperation.operations) {
                    operations.put(operation.opCode, operation)
                }
                for (operation in WifiOperation.operations) {
                    operations.put(operation.opCode, operation)
                }
            }
            return operations[params.opCode]
        }
    }
}
