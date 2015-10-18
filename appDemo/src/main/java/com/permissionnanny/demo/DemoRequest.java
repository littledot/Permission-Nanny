package com.permissionnanny.demo;

import com.permissionnanny.demo.account.AccountRequestFactory;
import com.permissionnanny.demo.extra.Extra;

/**
 *
 */
public class DemoRequest {
    public final String mOp;
    public final Extra[] mExtras;
    public final String[] mExtrasLabels;
    public final AccountRequestFactory.Factory mFactory;

    public DemoRequest(String op, Extra[] extras, String[] extrasLabels, AccountRequestFactory.Factory factory) {
        mOp = op;
        mExtras = extras;
        mExtrasLabels = extrasLabels;
        mFactory = factory;
    }
}
