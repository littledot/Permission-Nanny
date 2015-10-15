package com.permissionnanny.lib.request.simple;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
    public void process(Context context, Intent intent) {
        final Account[] accounts = (Account[]) intent.getParcelableArrayExtra(ACCOUNTS);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onAccountsUpdated(accounts);
            }
        });
    }
}