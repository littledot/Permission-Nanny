package com.sdchang.permissionpolice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import timber.log.Timber;

/**
 *
 */
public class ExternalRequestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.wtf("got intent: " + intent);
        // TODO #2: Validate intents received by ExternalRequestReceiver
        context.startActivity(new Intent(context, ConfirmRequestActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtras(intent));
    }
}
