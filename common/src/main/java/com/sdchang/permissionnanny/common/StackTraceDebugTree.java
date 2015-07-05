package com.sdchang.permissionnanny.common;

import timber.log.Timber;

public class StackTraceDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + "#" + element.getLineNumber() + " (" + element.getMethodName()
                + ")";
    }
}
