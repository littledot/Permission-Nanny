package com.permissionnanny.common;

import android.content.Intent;
import android.support.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

/**
 *
 */
public class IntentUtil {
    public static String toString(@Nullable Intent intent) {
        if (intent == null) {
            return "null intent";
        }

        JSONObject json = new JSONObject();
        try {
            json.put("action", intent.getAction())
                    .put("categories", intent.getCategories())
                    .put("data", intent.getDataString())
                    .put("flags", Integer.toBinaryString(intent.getFlags()))
                    .put("package", intent.getPackage())
                    .put("scheme", intent.getScheme())
                    .put("type", intent.getType())
                    .put("extras", BundleUtil.toString(intent));
            return json.toString(4);
        } catch (JSONException e) {
            Timber.e(e, "Error parsing intent to json.");
            return "Error parsing intent to json.";
        }
    }
}
