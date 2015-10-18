package com.permissionnanny.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

/**
 *
 */
public class BundleUtil {
    public static String toString(@Nullable Bundle bundle) {
        if (bundle == null) {
            return "null bundle";
        }

        JSONObject json = new JSONObject();
        try {
            for (String key : bundle.keySet()) {
                Object val = bundle.get(key);
                json.put(key, StringUtil.toString(val));
            }
            return json.toString(4);
        } catch (JSONException e) {
            Timber.e(e, "Error parsing bundle to json.");
            return "Error parsing bundle to json.";
        }
    }

    public static String toString(@Nullable Intent intent) {
        if (intent == null) {
            return "null intent";
        }
        return toString(intent.getExtras());
    }
}
