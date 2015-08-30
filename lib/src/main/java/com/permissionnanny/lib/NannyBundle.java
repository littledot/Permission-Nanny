package com.permissionnanny.lib;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class NannyBundle {
    private Bundle mBundle;

    public NannyBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public String getProtocol() {
        return mBundle.getString(Nanny.PROTOCOL_VERSION);
    }

    public int getStatusCode() {
        return mBundle.getInt(Nanny.STATUS_CODE);
    }

    @Nullable
    public String getClientAddress() {
        return mBundle.getString(Nanny.CLIENT_ADDRESS);
    }

    @Nullable
    public String getConnection() {
        return mBundle.getString(Nanny.CONNECTION);
    }

    @Nullable
    public String getServer() {
        return mBundle.getString(Nanny.SERVER);
    }

    @Nullable
    public Bundle getEntityBody() {
        return mBundle.getBundle(Nanny.ENTITY_BODY);
    }

    @Nullable
    public PendingIntent getSenderIdentity() {
        Bundle entity = getEntityBody();
        return entity != null ? (PendingIntent) entity.getParcelable(Nanny.SENDER_IDENTITY) : null;
    }

    @Nullable
    public RequestParams getRequest() {
        Bundle entity = getEntityBody();
        return entity != null ? (RequestParams) entity.getParcelable(Nanny.REQUEST_PARAMS) : null;
    }

    @Nullable
    public String getRequestReason() {
        Bundle entity = getEntityBody();
        return entity != null ? entity.getString(Nanny.REQUEST_REASON) : null;
    }

    public static class Builder {
        public String mProtocolVersion = Nanny.PPP_0_1;
        public int mStatusCode;
        public String mClientAddress;
        public String mConnection;
        public String mServer;
        public Throwable mError;

        public Bundle mBody;
        public PendingIntent mSender;
        public RequestParams mParams;
        public String mReason;

        public Bundle build() {
            if (mBody == null) {
                mBody = new Bundle();
            }
            if (mSender != null) {
                mBody.putParcelable(Nanny.SENDER_IDENTITY, mSender);
            }
            if (mParams != null) {
                mBody.putParcelable(Nanny.REQUEST_PARAMS, mParams);
            }
            if (mReason != null) {
                mBody.putString(Nanny.REQUEST_REASON, mReason);
            }

            Bundle ppp = new Bundle();
            ppp.putString(Nanny.PROTOCOL_VERSION, mProtocolVersion);
            if (mStatusCode > 0) {
                ppp.putInt(Nanny.STATUS_CODE, mStatusCode);
            }
            if (mClientAddress != null) {
                ppp.putString(Nanny.CLIENT_ADDRESS, mClientAddress);
            }
            if (mConnection != null) {
                ppp.putString(Nanny.CONNECTION, mConnection);
            }
            if (mServer != null) {
                ppp.putString(Nanny.SERVER, mServer);
            }
            if (mError != null) {
                ppp.putSerializable(Nanny.ENTITY_ERROR, mError);
            }
            if (!mBody.isEmpty()) {
                ppp.putBundle(Nanny.ENTITY_BODY, mBody);
            }
            return ppp;
        }
    }
}
