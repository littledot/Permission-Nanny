package com.permissionnanny.simple

import android.accounts.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.permissionnanny.ProxyListener
import com.permissionnanny.ProxyService
import com.permissionnanny.ResponseFactory
import com.permissionnanny.lib.Nanny
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.AccountManagerEvent
import java.io.IOException

/**

 */
abstract class ProxyAccountManagerListener<T>(
        service: ProxyService,
        clientAddr: String)
    : ProxyListener<Unit>(service, clientAddr, AccountManagerEvent.FILTER), AccountManagerCallback<T> {

    override fun run(future: AccountManagerFuture<T>) {
        try {
            val value = future.result
            val entity = Bundle()
            parse(entity, value)
            sendBroadcast(ResponseFactory.newAllowResponse(server)
                    .connection(Nanny.CLOSE)
                    .entity(entity)
                    .build())
        } catch (e: OperationCanceledException) {
            // TODO #75: Handle OCE & IOE as 500s?
            sendBroadcast(ResponseFactory.newBadRequestResponse(e).build())
        } catch (e: IOException) {
            sendBroadcast(ResponseFactory.newBadRequestResponse(e).build())
        } catch (e: AuthenticatorException) {
            sendBroadcast(ResponseFactory.newBadRequestResponse(e).build())
        }
    }

    abstract fun parse(entity: Bundle, value: T)

    class GetAccountsByTypeAndFeatures(service: ProxyService, clientAddr: String) : ProxyAccountManagerListener<Array<Account>>(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            AccountManager.get(context).getAccountsByTypeAndFeatures(request.string0, request.stringArray0, this, null)
        }

        override fun parse(entity: Bundle, value: Array<Account>) {
            entity.putParcelableArray(AccountManagerEvent.CALLBACK, value)
        }
    }

    class GetAuthToken1(service: ProxyService, clientAddr: String) : ProxyAccountManagerListener<Bundle>(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            AccountManager.get(context).getAuthToken(request.account0, request.string0, request.boolean0, this, null)
        }

        override fun parse(entity: Bundle, value: Bundle) {
            entity.putBundle(AccountManagerEvent.CALLBACK, value)
        }
    }

    class GetAuthToken2(service: ProxyService, clientAddr: String) : ProxyAccountManagerListener<Bundle>(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                AccountManager.get(context).getAuthToken(request.account0, request.string0, request.bundle0, request
                        .boolean0, this, null)
            }
        }

        override fun parse(entity: Bundle, value: Bundle) {
            entity.putBundle(AccountManagerEvent.CALLBACK, value)
        }
    }

    class HasFeatures(service: ProxyService, clientAddr: String) : ProxyAccountManagerListener<Boolean>(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            AccountManager.get(context).hasFeatures(request.account0, request.stringArray0, this, null)
        }

        override fun parse(entity: Bundle, value: Boolean) {
            entity.putBoolean(AccountManagerEvent.CALLBACK, value)
        }
    }

    class RemoveAccount(service: ProxyService, clientAddr: String) : ProxyAccountManagerListener<Boolean>(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            AccountManager.get(context).removeAccount(request.account0, this, null)
        }

        override fun parse(entity: Bundle, value: Boolean) {
            entity.putBoolean(AccountManagerEvent.CALLBACK, value)
        }
    }

    class RenameAccount(service: ProxyService, clientAddr: String) : ProxyAccountManagerListener<Account>(service, clientAddr) {

        override fun register(context: Context, request: RequestParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                AccountManager.get(context).renameAccount(request.account0, request.string0, this, null)
            }
        }

        override fun parse(entity: Bundle, value: Account) {
            entity.putParcelable(AccountManagerEvent.CALLBACK, value)
        }
    }
}
