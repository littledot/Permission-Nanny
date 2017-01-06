package com.permissionnanny.simple

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.OnAccountsUpdateListener
import android.content.Context
import android.os.Bundle
import com.permissionnanny.ProxyListener
import com.permissionnanny.ProxyService
import com.permissionnanny.lib.request.RequestParams
import com.permissionnanny.lib.request.simple.AccountsUpdateEvent

/**

 */
class ProxyOnAccountsUpdateListener<L>(
        service: ProxyService,
        clientAddr: String)
    : ProxyListener<L>(service, clientAddr, AccountsUpdateEvent.FILTER), OnAccountsUpdateListener {

    override fun register(context: Context, request: RequestParams) {
        AccountManager.get(context).addOnAccountsUpdatedListener(this, null, request.boolean0)
    }

    override fun unregister(context: Context) {
        AccountManager.get(context).removeOnAccountsUpdatedListener(this)
    }

    override fun onAccountsUpdated(accounts: Array<Account>) {
        val entity = Bundle()
        entity.putParcelableArray(AccountsUpdateEvent.ACCOUNTS, accounts)
        sendBroadcast(okResponse(entity))
    }
}
