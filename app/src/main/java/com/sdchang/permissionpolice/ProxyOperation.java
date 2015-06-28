package com.sdchang.permissionpolice;

import android.support.annotation.StringRes;

/**
 *
 */
public class ProxyOperation {
    public final String mOpCode;
    @StringRes public final int mDialogTitle;
    public final int mMinSdk;
    public final ProxyFunction mFunction;

    public ProxyOperation(String opCode,
                          int dialogTitle,
                          int minSdk,
                          ProxyFunction function) {
        mOpCode = opCode;
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
        mFunction = function;
    }
}
