package com.permissionnanny.lib.request.simple;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyLibTestCase;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class AccountManagerEventTest extends NannyLibTestCase {

    AccountManagerEvent<Account> mEventFilter;
    Intent mIntent;
    Bundle mBundle;
    Account mAccount;
    @Mock AccountManagerCallback<Account> mAccountManagerCallback;
    @Mock Handler mHandler;
    @Mock Context mContext;
    @Captor ArgumentCaptor<Runnable> mRunnableCaptor;
    @Captor ArgumentCaptor<AccountManagerFuture<Account>> mAccountManagerFutureCaptor;

    @Before
    public void setUp() throws Exception {
        mEventFilter = new AccountManagerEvent<>(mAccountManagerCallback, mHandler, Account.class);
        mIntent = new Intent();
        mBundle = new Bundle();
        mAccount = new Account("name", "type");
    }

    @Test
    public void process() throws Exception {
        mBundle.putParcelable(AccountManagerEvent.CALLBACK, mAccount);
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        AccountManagerFuture<Account> future = mAccountManagerFutureCaptor.getValue();
        assertThat(future.cancel(false), is(false));
        assertThat(future.isCancelled(), is(false));
        assertThat(future.isDone(), is(true));
        assertThat(future.getResult(1, TimeUnit.DAYS), sameInstance(mAccount));
    }

    @Test
    public void process_shouldReturnNull_whenNullResult() throws Exception {
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        AccountManagerFuture<Account> future = mAccountManagerFutureCaptor.getValue();
        assertThat(future.getResult(1, TimeUnit.DAYS), nullValue());
    }

    @Test
    public void process_shouldReturnNull_whenTypeMismatch() throws Exception {
        mBundle.putParcelable(AccountManagerEvent.CALLBACK, new Location(""));
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        AccountManagerFuture<Account> future = mAccountManagerFutureCaptor.getValue();
        assertThat(future.getResult(1, TimeUnit.DAYS), nullValue());
    }

    private void runProcess() {
        mEventFilter.process(mContext, mIntent);

        verify(mHandler).post(mRunnableCaptor.capture());
        mRunnableCaptor.getValue().run();
        verify(mAccountManagerCallback).run(mAccountManagerFutureCaptor.capture());
    }
}
