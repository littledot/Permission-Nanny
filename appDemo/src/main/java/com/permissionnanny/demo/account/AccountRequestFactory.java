package com.permissionnanny.demo.account;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import com.permissionnanny.demo.DataAdapter;
import com.permissionnanny.demo.ResponseDisplayListener;
import com.permissionnanny.demo.ResponseListener;
import com.permissionnanny.demo.SimpleRequestFactory;
import com.permissionnanny.demo.extra.AccountExtra;
import com.permissionnanny.demo.extra.BooleanExtra;
import com.permissionnanny.demo.extra.Extra;
import com.permissionnanny.demo.extra.ExtrasDialogBuilder;
import com.permissionnanny.demo.extra.StringExtra;
import com.permissionnanny.lib.request.simple.AccountRequest;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import de.greenrobot.event.EventBus;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class AccountRequestFactory implements SimpleRequestFactory {
    EventBus bus = EventBus.getDefault();
    private String[] mLabels = new String[]{
            AccountRequest.ADD_ACCOUNT_EXPLICITLY,
            AccountRequest.ADD_ON_ACCOUNTS_UPDATED_LISTENER,
            AccountRequest.BLOCKING_GET_AUTH_TOKEN,
            AccountRequest.CLEAR_PASSWORD,
            AccountRequest.GET_ACCOUNTS,
            AccountRequest.GET_ACCOUNTS_BY_TYPE,
            AccountRequest.GET_ACCOUNTS_BY_TYPE_AND_FEATURES,
    };
    private SparseArray<Extra[]> mExtras = new SparseArray<Extra[]>() {{
        put(0, new Extra[]{new AccountExtra("myAcc", "myType"), new StringExtra("pw")});
        put(2, new Extra[]{new AccountExtra("myAcc", "myType"), new StringExtra("oauth"), new BooleanExtra()});
        put(3, new Extra[]{new AccountExtra("myAcc", "myType")});
        put(5, new Extra[]{new StringExtra("myType")});
        put(6, new Extra[]{new StringExtra("myType")});
    }};
    private SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(0, new String[]{"account", "password"});
        put(2, new String[]{"account", "authTokenType", "notifyAuthFailure"});
        put(3, new String[]{"account"});
        put(5, new String[]{"type"});
        put(6, new String[]{"type"});
    }};
    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    public SimpleRequest getRequest(final int position, final DataAdapter adapter) {
        Extra[] extras = mExtras.get(position);
        switch (position) {
        case 0:
            return AccountRequest.addAccountExplicitly((Account) extras[0].getValue(), (String) extras[1].getValue(),
                    null).listener(new ResponseDisplayListener(position, adapter));
        case 1:
            return AccountRequest.addOnAccountsUpdatedListener(new OnAccountsUpdateListener() {
                @Override
                public void onAccountsUpdated(Account[] accounts) {
                    adapter.onDisplay(position, Arrays.toString(accounts));
                }
            }, null, true)
                    .listener(new ResponseListener(position, adapter));
        case 2:
            return AccountRequest.blockingGetAuthToken((Account) extras[0].getValue(), (String) extras[1].getValue(),
                    (boolean) extras[2].getValue())
                    .listener(new ResponseDisplayListener(position, adapter));
        case 3:
            return AccountRequest.clearPassword((Account) extras[0].getValue())
                    .listener(new ResponseDisplayListener(position, adapter));
        case 4:
            return AccountRequest.getAccounts()
                    .listener(new ResponseDisplayListener(position, adapter));
        case 5:
            return AccountRequest.getAccountsByType((String) extras[0].getValue())
                    .listener(new ResponseDisplayListener(position, adapter));
        case 6:
            return AccountRequest.getAccountsByTypeAndFeatures((String) extras[0].getValue(), null,
                    new AccountManagerCallback<Account[]>() {
                        @Override
                        public void run(AccountManagerFuture<Account[]> future) {
                            try {
                                adapter.onDisplay(position, Arrays.toString(future.getResult()));
                            } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null)
                    .listener(new ResponseListener(position, adapter));
        }
        return null;
    }

    @Override
    public int getCount() {
        return mLabels.length;
    }

    @Override
    public String getLabel(int position) {
        return mLabels[position];
    }

    @Override
    public boolean hasExtras(int position) {
        return mExtras.get(position) != null;
    }

    @Override
    public Dialog buildDialog(Context context, int position) {
        return mBuilder.build(context, mExtras.get(position), mExtrasLabels.get(position));
    }

    @Override
    public SimpleRequest getRequest(int position) {
        return null;
    }
}
