package com.permissionnanny.lib.request.simple;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.OnAccountsUpdateListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.PPP;
import com.permissionnanny.lib.request.RequestParams;

public class AccountRequest extends SimpleRequest {

    //    @PPP public static final String ADD_ACCOUNT = "addAccount";
    @PPP public static final String ADD_ACCOUNT_EXPLICITLY = "addAccountExplicitly";
    @PPP public static final String ADD_ON_ACCOUNTS_UPDATED_LISTENER = "addOnAccountsUpdatedListener";
    @PPP public static final String BLOCKING_GET_AUTH_TOKEN = "blockingGetAuthToken";
    @PPP public static final String CLEAR_PASSWORD = "clearPassword";
    //    @PPP public static final String CONFIRM_CREDENTIALS = "confirmCredentials";
    //    @PPP public static final String EDIT_PROPERTIES = "editProperties";
    @PPP public static final String GET_ACCOUNTS = "getAccounts";
    @PPP public static final String GET_ACCOUNTS_BY_TYPE = "getAccountsByType";
    @PPP
    public static final String GET_ACCOUNTS_BY_TYPE_AND_FEATURES = "getAccountsByTypeAndFeatures";
    //    @PPP public static final String GET_AUTH_TOKEN_BY_FEATURES = "getAuthTokenByFeatures";
    //    @PPP public static final String GET_AUTH_TOKEN = "getAuthToken";
    @PPP public static final String GET_AUTH_TOKEN1 = "getAuthToken1";
    @PPP public static final String GET_AUTH_TOKEN2 = "getAuthToken2";
    @PPP public static final String GET_PASSWORD = "getPassword";
    @PPP public static final String GET_USER_DATA = "getUserData";
    @PPP public static final String HAS_FEATURES = "hasFeatures";
    @PPP public static final String INVALIDATE_AUTH_TOKEN = "invalidateAuthToken";
    @PPP public static final String PEEK_AUTH_TOKEN = "peekAuthToken";
    @PPP public static final String REMOVE_ACCOUNT = "removeAccount";
    //    @PPP public static final String REMOVE_ACCOUNT1 = "removeAccount1";
    @PPP public static final String REMOVE_ACCOUNT_EXPLICITLY = "removeAccountExplicitly";
    @PPP public static final String RENAME_ACCOUNT = "renameAccount";
    @PPP public static final String SET_AUTH_TOKEN = "setAuthToken";
    @PPP public static final String SET_PASSWORD = "setPassword";
    @PPP public static final String SET_USER_DATA = "setUserData";
//    @PPP public static final String UPDATE_CREDENTIALS = "updateCredentials";

//    public static AccountRequest addAccount(String accountType,
//                                            String authTokenType,
//                                            String[] requiredFeatures,
//                                            Bundle addAccountOptions,
//                                            Activity activity,
//                                            AccountManagerCallback<Bundle> callback,
//                                            Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = ADD_ACCOUNT;
//        p.string0 = accountType;
//        p.string1 = authTokenType;
//        p.stringArray0 = requiredFeatures;
//        p.bundle0 = addAccountOptions;
//        p.boolean0 = activity != null;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

    public static AccountRequest addAccountExplicitly(Account account,
                                                      String password,
                                                      Bundle userdata) {
        RequestParams p = new RequestParams();
        p.opCode = ADD_ACCOUNT_EXPLICITLY;
        p.account0 = account;
        p.string0 = password;
        p.bundle0 = userdata;
        return new AccountRequest(p);
    }

    public static AccountRequest addOnAccountsUpdatedListener(OnAccountsUpdateListener listener,
                                                              @Nullable Handler handler,
                                                              boolean updateImmediately) {
        RequestParams p = new RequestParams();
        p.opCode = ADD_ON_ACCOUNTS_UPDATED_LISTENER;
        p.boolean0 = updateImmediately;
        AccountRequest r = new AccountRequest(p);
        r.addFilter(new AccountsUpdateEvent(listener, handler));
        return r;
    }

    public static AccountRequest blockingGetAuthToken(Account account,
                                                      String authTokenType,
                                                      boolean notifyAuthFailure) {
        RequestParams p = new RequestParams();
        p.opCode = BLOCKING_GET_AUTH_TOKEN;
        p.account0 = account;
        p.string0 = authTokenType;
        p.boolean0 = notifyAuthFailure;
        return new AccountRequest(p);
    }

    public static AccountRequest clearPassword(Account account) {
        RequestParams p = new RequestParams();
        p.opCode = CLEAR_PASSWORD;
        p.account0 = account;
        return new AccountRequest(p);
    }

//    public static AccountRequest confirmCredentials(Account account,
//                                                    Bundle options,
//                                                    Activity activity,
//                                                    AccountManagerCallback<Bundle> callback,
//                                                    Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = CONFIRM_CREDENTIALS;
//        p.account0 = account;
//        p.bundle0 = options;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

//    public static AccountRequest editProperties(String accountType,
//                                                Activity activity,
//                                                AccountManagerCallback<Bundle> callback,
//                                                Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = EDIT_PROPERTIES;
//        p.string0 = accountType;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

    public static AccountRequest getAccounts() {
        RequestParams p = new RequestParams();
        p.opCode = GET_ACCOUNTS;
        return new AccountRequest(p);
    }

    public static AccountRequest getAccountsByType(String type) {
        RequestParams p = new RequestParams();
        p.opCode = GET_ACCOUNTS_BY_TYPE;
        p.string0 = type;
        return new AccountRequest(p);
    }

    public static AccountRequest getAccountsByTypeAndFeatures(String type,
                                                              String[] features,
                                                              @Nullable AccountManagerCallback<Account[]> callback,
                                                              @Nullable Handler handler) {
        RequestParams p = new RequestParams();
        p.opCode = GET_ACCOUNTS_BY_TYPE_AND_FEATURES;
        p.string0 = type;
        p.stringArray0 = features;
        AccountRequest r = new AccountRequest(p);
        if (callback != null) {
            r.addFilter(new AccountManagerEvent<>(callback, handler, Account[].class));
        }
        return r;
    }

//    public static AccountRequest getAuthToken(Account account,
//                                              String authTokenType,
//                                              Bundle options,
//                                              Activity activity,
//                                              AccountManagerCallback<Bundle> callback,
//                                              Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = GET_AUTH_TOKEN;
//        p.account0 = account;
//        p.string0 = authTokenType;
//        p.bundle0 = options;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

    @Deprecated
    public static AccountRequest getAuthToken(Account account,
                                              String authTokenType,
                                              boolean notifyAuthFailure,
                                              @Nullable AccountManagerCallback<Bundle> callback,
                                              @Nullable Handler handler) {
        RequestParams p = new RequestParams();
        p.opCode = GET_AUTH_TOKEN1;
        p.account0 = account;
        p.string0 = authTokenType;
        p.boolean0 = notifyAuthFailure;
        AccountRequest r = new AccountRequest(p);
        if (callback != null) {
            r.addFilter(new AccountManagerEvent<>(callback, handler, Bundle.class));
        }
        return r;
    }

    public static AccountRequest getAuthToken(Account account,
                                              String authTokenType,
                                              Bundle options,
                                              boolean notifyAuthFailure,
                                              @Nullable AccountManagerCallback<Bundle> callback,
                                              @Nullable Handler handler) {
        RequestParams p = new RequestParams();
        p.opCode = GET_AUTH_TOKEN2;
        p.string0 = authTokenType;
        p.bundle0 = options;
        p.boolean0 = notifyAuthFailure;
        AccountRequest r = new AccountRequest(p);
        if (callback != null) {
            r.addFilter(new AccountManagerEvent<>(callback, handler, Bundle.class));
        }
        return r;
    }

//    public static AccountRequest getAuthTokenByFeatures(String accountType,
//                                                        String authTokenType,
//                                                        String[] features,
//                                                        Activity activity,
//                                                        Bundle addAccountOptions,
//                                                        Bundle getAuthTokenOptions,
//                                                        AccountManagerCallback<Bundle> callback,
//                                                        Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = GET_AUTH_TOKEN_BY_FEATURES;
//        p.string0 = accountType;
//        p.string1 = authTokenType;
//        p.stringArray0 = features;
//        p.bundle0 = addAccountOptions;
//        p.bundle1 = getAuthTokenOptions;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

    public static AccountRequest getPassword(Account account) {
        RequestParams p = new RequestParams();
        p.opCode = GET_PASSWORD;
        p.account0 = account;
        return new AccountRequest(p);
    }

    public static AccountRequest getUserData(Account account, String key) {
        RequestParams p = new RequestParams();
        p.opCode = GET_USER_DATA;
        p.account0 = account;
        p.string0 = key;
        return new AccountRequest(p);
    }

    public static AccountRequest hasFeatures(Account account,
                                             String[] features,
                                             @Nullable AccountManagerCallback<Boolean> callback,
                                             @Nullable Handler handler) {
        RequestParams p = new RequestParams();
        p.opCode = HAS_FEATURES;
        p.account0 = account;
        p.stringArray0 = features;
        AccountRequest r = new AccountRequest(p);
        if (callback != null) {
            r.addFilter(new AccountManagerEvent<>(callback, handler, Boolean.class));
        }
        return r;
    }

    public static AccountRequest invalidateAuthToken(String accountType, String authToken) {
        RequestParams p = new RequestParams();
        p.opCode = INVALIDATE_AUTH_TOKEN;
        p.string0 = accountType;
        p.string1 = authToken;
        return new AccountRequest(p);
    }

    public static AccountRequest peekAuthToken(Account account, String authTokenType) {
        RequestParams p = new RequestParams();
        p.opCode = PEEK_AUTH_TOKEN;
        p.account0 = account;
        p.string0 = authTokenType;
        return new AccountRequest(p);
    }

    @Deprecated
    public static AccountRequest removeAccount(Account account,
                                               @Nullable AccountManagerCallback<Boolean> callback,
                                               @Nullable Handler handler) {
        RequestParams p = new RequestParams();
        p.opCode = REMOVE_ACCOUNT;
        p.account0 = account;
        AccountRequest r = new AccountRequest(p);
        if (callback != null) {
            r.addFilter(new AccountManagerEvent<>(callback, handler, Boolean.class));
        }
        return r;
    }

//    public static AccountRequest removeAccount(Account account,
//                                               Activity activity,
//                                               AccountManagerCallback<Bundle> callback,
//                                               Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = REMOVE_ACCOUNT1;
//        p.account0 = account;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

    public static AccountRequest removeAccountExplicitly(Account account) {
        RequestParams p = new RequestParams();
        p.opCode = REMOVE_ACCOUNT_EXPLICITLY;
        p.account0 = account;
        return new AccountRequest(p);
    }

    public static AccountRequest renameAccount(Account account,
                                               String newName,
                                               @Nullable AccountManagerCallback<Account> callback,
                                               @Nullable Handler handler) {
        RequestParams p = new RequestParams();
        p.opCode = RENAME_ACCOUNT;
        p.account0 = account;
        p.string0 = newName;
        AccountRequest r = new AccountRequest(p);
        if (callback != null) {
            r.addFilter(new AccountManagerEvent<>(callback, handler, Account.class));
        }
        return r;
    }

    public static AccountRequest setAuthToken(Account account,
                                              String authTokenType,
                                              String authToken) {
        RequestParams p = new RequestParams();
        p.opCode = SET_AUTH_TOKEN;
        p.account0 = account;
        p.string0 = authTokenType;
        p.string1 = authToken;
        return new AccountRequest(p);
    }

    public static AccountRequest setPassword(Account account, String password) {
        RequestParams p = new RequestParams();
        p.opCode = SET_PASSWORD;
        p.account0 = account;
        p.string0 = password;
        return new AccountRequest(p);
    }

    public static AccountRequest setUserData(Account account, String key, String value) {
        RequestParams p = new RequestParams();
        p.opCode = SET_USER_DATA;
        p.account0 = account;
        p.string0 = key;
        p.string1 = value;
        return new AccountRequest(p);
    }

//    public static AccountRequest updateCredentials(Account account,
//                                                   String authTokenType,
//                                                   Bundle options,
//                                                   Activity activity,
//                                                   AccountManagerCallback<Bundle> callback,
//                                                   Handler handler) {
//        RequestParams p = new RequestParams();
//        p.opCode = UPDATE_CREDENTIALS;
//        p.account0 = account;
//        p.string0 = authTokenType;
//        p.bundle0 = options;
//        AccountRequest r = new AccountRequest(p);
//        return r;
//    }

    public AccountRequest(RequestParams params) {
        super(params);
    }
}
