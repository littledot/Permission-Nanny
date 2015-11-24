package com.permissionnanny.simple;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import com.permissionnanny.ProxyListener;
import com.permissionnanny.ProxyService;
import com.permissionnanny.ResponseFactory;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.request.RequestParams;
import com.permissionnanny.lib.request.simple.AccountManagerEvent;
import java.io.IOException;

/**
 *
 */
public abstract class ProxyAccountManagerListener<T> extends ProxyListener implements AccountManagerCallback<T> {
    public ProxyAccountManagerListener(ProxyService service, String clientAddr) {
        super(service, clientAddr, AccountManagerEvent.FILTER);
    }

    @Override
    public void run(AccountManagerFuture<T> future) {
        try {
            T value = future.getResult();
            Bundle entity = new Bundle();
            parse(entity, value);
            sendBroadcast(ResponseFactory.newAllowResponse(mServer)
                    .connection(Nanny.CLOSE)
                    .entity(entity)
                    .build());
        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
            // TODO #75: Handle OCE & IOE as 500s?
            sendBroadcast(ResponseFactory.newBadRequestResponse(e).build());
        }
    }

    public abstract void parse(Bundle entity, T value);

    public static class GetAccountsByTypeAndFeatures extends ProxyAccountManagerListener<Account[]> {
        public GetAccountsByTypeAndFeatures(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            AccountManager.get(context).getAccountsByTypeAndFeatures(request.string0, request.stringArray0, this, null);
        }

        @Override
        public void parse(Bundle entity, Account[] value) {
            entity.putParcelableArray(AccountManagerEvent.CALLBACK, value);
        }
    }

    public static class GetAuthToken1 extends ProxyAccountManagerListener<Bundle> {
        public GetAuthToken1(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            AccountManager.get(context).getAuthToken(request.account0, request.string0, request.boolean0, this, null);
        }

        @Override
        public void parse(Bundle entity, Bundle value) {
            entity.putBundle(AccountManagerEvent.CALLBACK, value);
        }
    }

    public static class GetAuthToken2 extends ProxyAccountManagerListener<Bundle> {
        public GetAuthToken2(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                AccountManager.get(context).getAuthToken(request.account0, request.string0, request.bundle0, request
                        .boolean0, this, null);
            }
        }

        @Override
        public void parse(Bundle entity, Bundle value) {
            entity.putBundle(AccountManagerEvent.CALLBACK, value);
        }
    }

    public static class HasFeatures extends ProxyAccountManagerListener<Boolean> {
        public HasFeatures(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            AccountManager.get(context).hasFeatures(request.account0, request.stringArray0, this, null);
        }

        @Override
        public void parse(Bundle entity, Boolean value) {
            entity.putBoolean(AccountManagerEvent.CALLBACK, value);
        }
    }

    public static class RemoveAccount extends ProxyAccountManagerListener<Boolean> {
        public RemoveAccount(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            AccountManager.get(context).removeAccount(request.account0, this, null);
        }

        @Override
        public void parse(Bundle entity, Boolean value) {
            entity.putBoolean(AccountManagerEvent.CALLBACK, value);
        }
    }

    public static class RenameAccount extends ProxyAccountManagerListener<Account> {
        public RenameAccount(ProxyService service, String clientAddr) {
            super(service, clientAddr);
        }

        @Override
        public void register(Context context, RequestParams request) {
            if (Build.VERSION.SDK_INT >= 21) {
                AccountManager.get(context).renameAccount(request.account0, request.string0, this, null);
            }
        }

        @Override
        public void parse(Bundle entity, Account value) {
            entity.putParcelable(AccountManagerEvent.CALLBACK, value);
        }
    }
}
