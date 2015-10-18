package com.permissionnanny.demo.account;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.content.Context;
import com.permissionnanny.common.StringUtil;
import com.permissionnanny.demo.DataAdapter;
import com.permissionnanny.demo.DemoRequest;
import com.permissionnanny.demo.ResponseDisplayListener;
import com.permissionnanny.demo.ResponseListener;
import com.permissionnanny.demo.SimpleRequestFactory;
import com.permissionnanny.demo.extra.AccountExtra;
import com.permissionnanny.demo.extra.BooleanExtra;
import com.permissionnanny.demo.extra.Extra;
import com.permissionnanny.demo.extra.ExtrasDialogBuilder;
import com.permissionnanny.demo.extra.StringExtra;
import com.permissionnanny.lib.request.PermissionRequest;
import com.permissionnanny.lib.request.simple.AccountRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class AccountRequestFactory implements SimpleRequestFactory {
    private AccountExtra mAccount = new AccountExtra("permissionnanny@gmail.com", "com.google");
    private StringExtra mPassword = new StringExtra("pw");
    private StringExtra mAuthTokenType = new StringExtra("oauth://");
    private StringExtra mType = new StringExtra("com.google");
    private StringExtra mKey = new StringExtra("myKey");
    private StringExtra mAuthToken = new StringExtra("myAuthToken");
    private StringExtra mNewName = new StringExtra("Brown Smith Account");
    private StringExtra mValue = new StringExtra("myValue");
    private BooleanExtra mUpdateImmediately = new BooleanExtra();
    private BooleanExtra mNotifyAuthFailure = new BooleanExtra(true);

    public interface Factory {
        PermissionRequest newRequest(int position, DataAdapter adapter);
    }

    private DemoRequest[] mRequests = new DemoRequest[]{
            new DemoRequest(AccountRequest.ADD_ACCOUNT_EXPLICITLY,
                    new Extra[]{mAccount, mPassword},
                    new String[]{"account", "password"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.addAccountExplicitly(mAccount.getValue(), mPassword.getValue(), null)
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.ADD_ON_ACCOUNTS_UPDATED_LISTENER,
                    new Extra[]{mUpdateImmediately},
                    new String[]{"updateImmediately"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(final int position, final DataAdapter adapter) {
                            return AccountRequest.addOnAccountsUpdatedListener(new OnAccountsUpdateListener() {
                                @Override
                                public void onAccountsUpdated(Account[] accounts) {
                                    adapter.onData(position, Arrays.toString(accounts));
                                }
                            }, null, true)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.BLOCKING_GET_AUTH_TOKEN,
                    new Extra[]{mAccount, mAuthTokenType, mNotifyAuthFailure},
                    new String[]{"account", "authTokenType", "notifyAuthFailure"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.blockingGetAuthToken(mAccount.getValue(), mAuthTokenType.getValue(),
                                    mNotifyAuthFailure.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.CLEAR_PASSWORD,
                    new Extra[]{mAccount},
                    new String[]{"account"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.clearPassword(mAccount.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_ACCOUNTS,
                    new Extra[]{},
                    new String[]{},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.getAccounts()
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_ACCOUNTS_BY_TYPE,
                    new Extra[]{mType},
                    new String[]{"type"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.getAccountsByType(mType.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_ACCOUNTS_BY_TYPE_AND_FEATURES,
                    new Extra[]{mType},
                    new String[]{"type"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(final int position, final DataAdapter adapter) {
                            return AccountRequest.getAccountsByTypeAndFeatures(mType.getValue(), null,
                                    new StringifyAccountManagerCallback(position, adapter), null)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_AUTH_TOKEN1,
                    new Extra[]{mAccount, mAuthTokenType, mNotifyAuthFailure},
                    new String[]{"account", "authTokenType", "notifyAuthFailure"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.getAuthToken(mAccount.getValue(), mAuthTokenType.getValue(),
                                    mNotifyAuthFailure.getValue(),
                                    new StringifyAccountManagerCallback(position, adapter), null)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_AUTH_TOKEN2,
                    new Extra[]{mAccount, mAuthTokenType, mNotifyAuthFailure},
                    new String[]{"account", "authTokenType", "notifyAuthFailure"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.getAuthToken(mAccount.getValue(), mAuthTokenType.getValue(),
                                    mNotifyAuthFailure.getValue(),
                                    new StringifyAccountManagerCallback(position, adapter), null)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_PASSWORD,
                    new Extra[]{mAccount},
                    new String[]{"account"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.getPassword(mAccount.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.GET_USER_DATA,
                    new Extra[]{mAccount, mKey},
                    new String[]{"account", "key"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.getUserData(mAccount.getValue(), mKey.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.HAS_FEATURES,
                    new Extra[]{mAccount},
                    new String[]{"account"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.hasFeatures(mAccount.getValue(), null,
                                    new StringifyAccountManagerCallback(position, adapter), null)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.INVALIDATE_AUTH_TOKEN,
                    new Extra[]{mType, mAuthToken},
                    new String[]{"accountType", "authToken"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.invalidateAuthToken(mType.getValue(), mAuthToken.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.PEEK_AUTH_TOKEN,
                    new Extra[]{mAccount, mAuthTokenType},
                    new String[]{"account", "authTokenType"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.peekAuthToken(mAccount.getValue(), mAuthTokenType.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.REMOVE_ACCOUNT,
                    new Extra[]{mAccount},
                    new String[]{"account"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.removeAccount(mAccount.getValue(),
                                    new StringifyAccountManagerCallback(position, adapter), null)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.REMOVE_ACCOUNT_EXPLICITLY,
                    new Extra[]{mAccount},
                    new String[]{"account"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.removeAccountExplicitly(mAccount.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.RENAME_ACCOUNT,
                    new Extra[]{mAccount, mNewName},
                    new String[]{"account", "newName"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.renameAccount(mAccount.getValue(), mNewName.getValue(),
                                    new StringifyAccountManagerCallback(position, adapter), null)
                                    .listener(new ResponseListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.SET_AUTH_TOKEN,
                    new Extra[]{mAccount, mAuthTokenType, mAuthToken},
                    new String[]{"account", "authTokenType", "authToken"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.setAuthToken(mAccount.getValue(), mAuthTokenType.getValue(),
                                    mAuthToken.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.SET_PASSWORD,
                    new Extra[]{mAccount, mPassword},
                    new String[]{"account", "password"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.setPassword(mAccount.getValue(), mPassword.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
            new DemoRequest(AccountRequest.SET_USER_DATA,
                    new Extra[]{mAccount, mKey, mValue},
                    new String[]{"account", "key", "value"},
                    new Factory() {
                        @Override
                        public PermissionRequest newRequest(int position, DataAdapter adapter) {
                            return AccountRequest.setUserData(mAccount.getValue(), mKey.getValue(), mValue.getValue())
                                    .listener(new ResponseDisplayListener(position, adapter));
                        }
                    }),
    };

    private ExtrasDialogBuilder mBuilder = new ExtrasDialogBuilder();

    @Override
    public PermissionRequest getRequest(final int position, final DataAdapter adapter) {
        return mRequests[position].mFactory.newRequest(position, adapter);
    }

    @Override
    public int getCount() {
        return mRequests.length;
    }

    @Override
    public String getLabel(int position) {
        return mRequests[position].mOp;
    }

    @Override
    public boolean hasExtras(int position) {
        Extra[] extras = mRequests[position].mExtras;
        return extras != null && extras.length > 0;
    }

    @Override
    public Dialog buildDialog(Context context, int position) {
        return mBuilder.build(context, mRequests[position].mExtras, mRequests[position].mExtrasLabels);
    }

    public static class StringifyAccountManagerCallback implements AccountManagerCallback {
        public DataAdapter mAdapter;
        public int mPosition;

        public StringifyAccountManagerCallback(int position, DataAdapter adapter) {
            mAdapter = adapter;
            mPosition = position;
        }

        @Override
        public void run(AccountManagerFuture future) {
            try {
                Object val = future.getResult();
                mAdapter.onData(mPosition, StringUtil.toString(val));
            } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                e.printStackTrace();
            }
        }
    }
}
