package com.permissionnanny.lib.deeplink;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringDef;
import com.permissionnanny.lib.C;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyBundle;
import com.permissionnanny.lib.NannyRequest;
import com.permissionnanny.lib.request.PermissionEvent;
import com.permissionnanny.lib.request.simple.SimpleListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
public class DeepLinkRequest extends NannyRequest {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef(Nanny.MANAGE_APPLICATIONS_SETTINGS)
    public @interface Res {}

    private final String mDeepLinkTarget;

    public DeepLinkRequest(@Res String deepLinkTarget) {
        mDeepLinkTarget = deepLinkTarget;
    }

    /**
     * Attach a listener.
     *
     * @param listener Response receiver
     * @return itself
     */
    public DeepLinkRequest listener(SimpleListener listener) {
        return (DeepLinkRequest) addFilter(new PermissionEvent(listener));
    }

    /**
     * Start the request.
     *
     * @param context Activity, Service, etc.
     */
    public void startRequest(Context context) {
        setPayload(newBroadcastIntent(context));
        super.startRequest(context);
    }

    protected Intent newBroadcastIntent(Context context) {
        NannyBundle.Builder builder = new NannyBundle.Builder()
                .sender(PendingIntent.getBroadcast(context, 0, C.EMPTY_INTENT, 0))
                .clientAddress(mClientAddr)
                .deepLinkTarget(mDeepLinkTarget);
        return new Intent()
                .setClassName(Nanny.getServerAppId(), Nanny.CLIENT_DEEP_LINK_RECEIVER)
                .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .putExtras(builder.build());
    }
}
