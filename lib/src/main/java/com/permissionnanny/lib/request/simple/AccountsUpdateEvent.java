package com.permissionnanny.lib.request.simple;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.BaseEvent;

/**
 *
 */
public class AccountsUpdateEvent extends BaseEvent {

    @PPP public static final String FILTER = "AccountsUpdateEvent";

    @PPP public static final String ACCOUNTS = "accounts";

    private OnAccountsUpdateListener mListener;
    private Handler mHandler;

    public AccountsUpdateEvent(OnAccountsUpdateListener listener, @Nullable Handler handler) {
        mListener = listener;
        mHandler = handler != null ? handler : new Handler(Looper.getMainLooper());
    }

    @Override
    public String filter() {
        return FILTER;
    }

    @Override
    public void processEntity(Context context, final Bundle entity) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Parcelable[] parcels = entity.getParcelableArray(ACCOUNTS);
                Account[] accounts = new Account[parcels.length];
                System.arraycopy(parcels, 0, accounts, 0, parcels.length);
                mListener.onAccountsUpdated(accounts);
            }
        });
    }
}