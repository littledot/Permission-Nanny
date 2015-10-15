package com.permissionnanny.demo.account;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import com.permissionnanny.demo.SimpleRequestFactory;
import com.permissionnanny.demo.extra.AccountExtra;
import com.permissionnanny.demo.extra.BooleanExtra;
import com.permissionnanny.demo.extra.Extra;
import com.permissionnanny.demo.extra.ExtrasDialogBuilder;
import com.permissionnanny.demo.extra.StringExtra;
import com.permissionnanny.lib.request.simple.AccountRequest;
import com.permissionnanny.lib.request.simple.SimpleRequest;
import de.greenrobot.event.EventBus;

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
        put(0, new Extra[]{new AccountExtra("myAcc", "myType"), new StringExtra("pw"), null});
        put(2, new Extra[]{new AccountExtra("myAcc", "myType"), new StringExtra("oauth"), new BooleanExtra()});
        put(3, new Extra[]{new AccountExtra("myAcc", "myType")});
        put(5, new Extra[]{new StringExtra("myType")});
        put(6, new Extra[]{new StringExtra("myType"), null});
    }};
    private SparseArray<String[]> mExtrasLabels = new SparseArray<String[]>() {{
        put(0, new String[]{"account", "password", null});
        put(2, new String[]{"account", "authTokenType", "notifyAuthFailure"});
        put(3, new String[]{"account"});
        put(5, new String[]{"type"});
        put(6, new String[]{"type", null});
    }};
    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    @Override
    public SimpleRequest getRequest(final int position) {
        Extra[] extras = mExtras.get(position);
        switch (position) {
        case 0:
            return AccountRequest.addAccountExplicitly((Account) extras[0].getValue(), (String) extras[1].getValue(),
                    null);
        case 1:
            return AccountRequest.addOnAccountsUpdatedListener(new OnAccountsUpdateListener() {
                @Override
                public void onAccountsUpdated(Account[] accounts) {

                }
            }, null, true);
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
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
}
