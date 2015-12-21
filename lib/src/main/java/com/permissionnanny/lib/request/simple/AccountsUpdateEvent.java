package com.permissionnanny.lib.request.simple;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.permissionnanny.lib.Err;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.Ack;
import timber.log.Timber;

/**
 * Event filter that handles {@link Nanny#ACCOUNTS_UPDATE_SERVICE} responses.
 */
public class AccountsUpdateEvent implements Event {

    @PPP public static final String FILTER = "AccountsUpdateEvent";

    @PPP public static final String ACCOUNTS = "accounts";

    private final OnAccountsUpdateListener mListener;
    private final Handler mHandler;
    private final Ack mAck;

    public AccountsUpdateEvent(OnAccountsUpdateListener listener, @Nullable Handler handler) {
        this(listener, handler, new Ack());
    }

    @VisibleForTesting
    public AccountsUpdateEvent(OnAccountsUpdateListener listener, @Nullable Handler handler, Ack ack) {
        mListener = listener;
        mHandler = handler != null ? handler : new Handler(Looper.getMainLooper());
        mAck = ack;
    }

    @Override
    public String filter() {
        return FILTER;
    }

    @Override
    public void process(Context context, Intent intent) {
        mAck.sendAck(context, intent);

        Bundle entity = new NannyBundle(intent).getEntityBody();
        if (entity == null) {
            Timber.wtf(Err.NO_ENTITY);
            return;
        }

        final Parcelable[] parcels = entity.getParcelableArray(ACCOUNTS);
        if (parcels == null) {
            Timber.wtf(Err.NO_ACCOUNTS);
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Account[] accounts = new Account[parcels.length];
                System.arraycopy(parcels, 0, accounts, 0, parcels.length);
                mListener.onAccountsUpdated(accounts);
            }
        });
    }
}
