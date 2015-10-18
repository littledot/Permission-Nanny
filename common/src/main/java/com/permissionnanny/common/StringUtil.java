package com.permissionnanny.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.Arrays;

/**
 *
 */
public class StringUtil {
    public static String toString(@Nullable Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj.getClass().isArray()) {
            return Arrays.deepToString((Object[]) obj);
        }
        if (obj instanceof Bundle) {
            return BundleUtil.toString((Bundle) obj);
        }
        return obj.toString();
    }
}
