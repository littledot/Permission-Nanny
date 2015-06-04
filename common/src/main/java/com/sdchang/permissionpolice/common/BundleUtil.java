package com.sdchang.permissionpolice.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 *
 */
public class BundleUtil {
    public static String toString(@Nullable Bundle bundle) {
        if (bundle == null) {
            return "null bundle";
        }

        StringBuilder str = new StringBuilder("{");
        for (String key : bundle.keySet()) {
            str.append("\"" + key + "\" : " + bundle.get(key) + ",");
        }
        str.deleteCharAt(str.length() - 1);
        return str.append("}").toString();
    }

    public static String toString(@Nullable Intent intent) {
        if (intent == null) {
            return "null intent";
        }
        return toString(intent.getExtras());
    }
}
