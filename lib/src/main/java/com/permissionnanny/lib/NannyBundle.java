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
    public String getSenderIdentity() {
        Bundle entity = getEntityBody();
        if (entity != null) {
            PendingIntent sender = entity.getParcelable(Nanny.SENDER_IDENTITY);
            if (sender != null) {
                return sender.getIntentSender().getTargetPackage();
            }
        }
        return null;
    }

    @Nullable
    public RequestParams getRequest() {
        Bundle entity = getEntityBody();
        return entity != null ? (RequestParams) entity.getParcelable(Nanny.REQUEST_PARAMS) : null;
    }

    @Nullable
    public String getRequestRationale() {
        String value = null;
        Bundle entity = getEntityBody();
        if (entity != null) {
            value = entity.getString(Nanny.REQUEST_RATIONALE);
            if (value == null) {
                value = entity.getString(Nanny.REQUEST_REASON);
            }
        }
        return value;
    }

    public String getAckAddress() {
        Bundle entity = getEntityBody();
        return entity != null ? entity.getString(Nanny.ACK_SERVER_ADDRESS) : null;
    }

    public static class Builder {
        public String mProtocolVersion = Nanny.PPP_0_1;
        public int mStatusCode;
        public String mClientAddress;
        public String mConnection;
        public String mServer;
        public Throwable mError;

        public Bundle mEntity;
        public PendingIntent mSender;
        public RequestParams mParams;
        public String mRationale;
        public String mAckAddress;

        public Builder statusCode(int statusCode) {
            mStatusCode = statusCode;
            return this;
        }

        public Builder clientAddress(String clientAddress) {
            mClientAddress = clientAddress;
            return this;
        }

        public Builder connection(String connection) {
            mConnection = connection;
            return this;
        }

        public Builder server(String server) {
            mServer = server;
            return this;
        }

        public Builder error(Throwable error) {
            mError = error;
            return this;
        }

        public Builder entity(Bundle body) {
            mEntity = body;
            return this;
        }

        public Builder sender(PendingIntent sender) {
            mSender = sender;
            return this;
        }

        public Builder params(RequestParams params) {
            mParams = params;
            return this;
        }

        public Builder rationale(String rationale) {
            mRationale = rationale;
            return this;
        }

        public Builder ackAddress(String ackAddress) {
            mAckAddress = ackAddress;
            return this;
        }

        public Bundle build() {
            if (mEntity == null) {
                mEntity = new Bundle();
            }
            if (mSender != null) {
                mEntity.putParcelable(Nanny.SENDER_IDENTITY, mSender);
            }
            if (mParams != null) {
                mEntity.putParcelable(Nanny.REQUEST_PARAMS, mParams);
            }
            if (mRationale != null) {
                mEntity.putString(Nanny.REQUEST_REASON, mRationale);
                mEntity.putString(Nanny.REQUEST_RATIONALE, mRationale);
            }
            if (mAckAddress != null) {
                mEntity.putString(Nanny.ACK_SERVER_ADDRESS, mAckAddress);
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
            if (!mEntity.isEmpty()) {
                ppp.putBundle(Nanny.ENTITY_BODY, mEntity);
            }
            return ppp;
        }
    }
}
