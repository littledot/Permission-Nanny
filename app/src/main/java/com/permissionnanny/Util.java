package com.permissionnanny;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

/**
 * Utilities.
 */
public class Util {

    @Nullable
    public static ApplicationInfo getApplicationInfo(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
