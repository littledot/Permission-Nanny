package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.permissionnanny.lib.Err;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import timber.log.Timber;

/**
 * Acknowledgement responder.
 */
public class Ack {

    public void sendAck(Context context, Intent response) {
        NannyBundle bundle = new NannyBundle(response);
        String ackAddr = bundle.getAckAddress();
        if (TextUtils.isEmpty(ackAddr)) {
            Timber.wtf(Err.NO_ACK_ADDR, ackAddr);
            return;
        }
        String clientAddr = response.getAction();

        Intent ackIntent = new Intent(ackAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.CLIENT_ADDRESS, clientAddr);
        context.sendBroadcast(ackIntent);
    }
}
