package com.permissionnanny.simple;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.os.Bundle;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.AccountsUpdateEvent;

/**
 *
 */
public class ProxyOnAccountsUpdateListener extends ProxyListener implements OnAccountsUpdateListener {

    public ProxyOnAccountsUpdateListener(ProxyService service, String clientAddr) {
        super(service, clientAddr, AccountsUpdateEvent.FILTER);
    }

    @Override
    public void register(Context context, RequestParams request) {
        AccountManager.get(context).addOnAccountsUpdatedListener(this, null, request.boolean0);
    }

    @Override
    public void unregister(Context context) {
        AccountManager.get(context).removeOnAccountsUpdatedListener(this);
    }

    @Override
    public void onAccountsUpdated(Account[] accounts) {
        Bundle entity = new Bundle();
        entity.putParcelableArray(AccountsUpdateEvent.ACCOUNTS, accounts);
        sendBroadcast(okResponse(entity));
    }
}
