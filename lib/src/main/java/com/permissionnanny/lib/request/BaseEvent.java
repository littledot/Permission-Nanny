package com.permissionnanny.lib.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.permissionnanny.lib.Event;
import com.permissionnanny.lib.Nanny;

/**
 *
 */
public abstract class BaseEvent implements Event {

    protected void sendAck(Context context, Intent response) {
        Bundle entity = response.getBundleExtra(Nanny.ENTITY_BODY);
        String ackServerAddr = entity.getString(Nanny.ACK_SERVER_ADDRESS);
        String clientAddr = response.getAction();

        Bundle ack = new Bundle();
        ack.putString(Nanny.CLIENT_ADDRESS, clientAddr);
        Intent ackIntent = new Intent(ackServerAddr)
                .putExtra(Nanny.PROTOCOL_VERSION, Nanny.PPP_0_1)
                .putExtra(Nanny.ENTITY_BODY, ack);
        context.sendBroadcast(ackIntent);
    }
}
