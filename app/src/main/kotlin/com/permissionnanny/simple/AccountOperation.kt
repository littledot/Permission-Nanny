package com.permissionnanny.simple

import android.accounts.AccountManager
import android.accounts.AuthenticatorException
import android.accounts.OperationCanceledException
import android.content.Context
import android.content.pm.PermissionInfo
import android.os.Build
import android.os.Bundle
import com.permissionnanny.Manifest
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.AccountRequest
import java.io.IOException

/**

 */
class AccountOperation(
        opCode: String,
        permission: String,
        protectionLevel: Int,
        dialogTitle: Int,
        minSdk: Int,
        proxyFunction: ((Context, RequestParams, Bundle) -> Unit)?)
    : SimpleOperation(opCode, permission, protectionLevel, dialogTitle, minSdk, proxyFunction) {

    companion object {
        val operations = arrayOf(
                SimpleOperation(AccountRequest.ADD_ACCOUNT_EXPLICITLY,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            response.putBoolean(request.opCode, am.addAccountExplicitly(request.account0, request.string0, request.bundle0))
                        }),
                SimpleOperation(AccountRequest.ADD_ON_ACCOUNTS_UPDATED_LISTENER,
                        Manifest.permission.GET_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5, null),
                SimpleOperation(AccountRequest.BLOCKING_GET_AUTH_TOKEN,
                        Manifest.permission.USE_CREDENTIALS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            try {
                                response.putString(request.opCode, am.blockingGetAuthToken(request.account0, request.string0,
                                        request.boolean0))
                            } catch (e: OperationCanceledException) {
                                throw RuntimeException(e)
                            } catch (e: IOException) {
                                throw RuntimeException(e)
                            } catch (e: AuthenticatorException) {
                                throw RuntimeException(e)
                            }
                        }),
                SimpleOperation(AccountRequest.CLEAR_PASSWORD,
                        Manifest.permission.MANAGE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            am.clearPassword(request.account0)
                        }),
                SimpleOperation(AccountRequest.GET_ACCOUNTS,
                        Manifest.permission.GET_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            response.putParcelableArray(request.opCode, am.accounts)
                        }),
                SimpleOperation(AccountRequest.GET_ACCOUNTS_BY_TYPE,
                        Manifest.permission.GET_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            response.putParcelableArray(request.opCode, am.getAccountsByType(request.string0))
                        }),
                SimpleOperation(AccountRequest.GET_ACCOUNTS_BY_TYPE_AND_FEATURES,
                        Manifest.permission.GET_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5, null),
                SimpleOperation(AccountRequest.GET_AUTH_TOKEN1,
                        Manifest.permission.USE_CREDENTIALS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5, null),
                SimpleOperation(AccountRequest.GET_AUTH_TOKEN2,
                        Manifest.permission.USE_CREDENTIALS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 14, null),
                SimpleOperation(AccountRequest.GET_PASSWORD,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            response.putString(request.opCode, am.getPassword(request.account0))
                        }),
                SimpleOperation(AccountRequest.GET_USER_DATA,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            response.putString(request.opCode, am.getUserData(request.account0, request.string0))
                        }),
                SimpleOperation(AccountRequest.HAS_FEATURES,
                        Manifest.permission.GET_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 8, null),
                SimpleOperation(AccountRequest.INVALIDATE_AUTH_TOKEN,
                        Manifest.permission.MANAGE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            am.invalidateAuthToken(request.string0, request.string1)
                        }),
                SimpleOperation(AccountRequest.PEEK_AUTH_TOKEN,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            response.putString(request.opCode, am.peekAuthToken(request.account0, request.string0))
                        }),
                SimpleOperation(AccountRequest.REMOVE_ACCOUNT_EXPLICITLY,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 22,
                        { context, request, response ->
                            if (Build.VERSION.SDK_INT >= 22) {
                                val am = AccountManager.get(context)
                                response.putBoolean(request.opCode, am.removeAccountExplicitly(request.account0))
                            }
                        }),
                SimpleOperation(AccountRequest.RENAME_ACCOUNT,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 21, null),
                SimpleOperation(AccountRequest.SET_AUTH_TOKEN,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            am.setAuthToken(request.account0, request.string0, request.string1)
                        }),
                SimpleOperation(AccountRequest.SET_PASSWORD,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            am.setPassword(request.account0, request.string0)
                        }),
                SimpleOperation(AccountRequest.SET_USER_DATA,
                        Manifest.permission.AUTHENTICATE_ACCOUNTS,
                        PermissionInfo.PROTECTION_NORMAL,
                        0, 5,
                        { context, request, response ->
                            val am = AccountManager.get(context)
                            am.setUserData(request.account0, request.string0, request.string1)
                        })
        )
    }
}
