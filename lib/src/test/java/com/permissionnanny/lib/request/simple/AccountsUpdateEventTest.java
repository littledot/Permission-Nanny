package com.permissionnanny.lib.request.simple;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyLibTestCase;
import com.permissionnanny.lib.request.Ack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class AccountsUpdateEventTest extends NannyLibTestCase {

    AccountsUpdateEvent mEventFilter;
    Intent mIntent;
    Bundle mBundle;
    Account mAccount;
    @Mock OnAccountsUpdateListener mOnAccountsUpdateListener;
    @Mock Handler mHandler;
    @Mock Ack mAck;
    @Mock Context mContext;
    @Captor ArgumentCaptor<Runnable> mRunnableCaptor;
    @Captor ArgumentCaptor<Account[]> mAccountsCaptor;

    @Before
    public void setUp() throws Exception {
        mEventFilter = new AccountsUpdateEvent(mOnAccountsUpdateListener, mHandler, mAck);
        mIntent = new Intent();
        mBundle = new Bundle();
        mAccount = new Account("a", "b");
    }

    @Test
    public void process() throws Exception {
        mBundle.putParcelableArray(AccountsUpdateEvent.ACCOUNTS, new Account[]{mAccount});
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        mEventFilter.process(mContext, mIntent);

        verify(mAck).sendAck(mContext, mIntent);
        verify(mHandler).post(mRunnableCaptor.capture());

        mRunnableCaptor.getValue().run();

        verify(mOnAccountsUpdateListener).onAccountsUpdated(mAccountsCaptor.capture());
        assertThat(mAccountsCaptor.getValue()[0], is(mAccount));
    }
}
