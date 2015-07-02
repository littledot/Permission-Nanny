package com.sdchang.permissionpolice;

import android.support.annotation.StringRes;
import android.support.v4.util.SimpleArrayMap;
import com.sdchang.permissionpolice.lib.request.RequestParams;
import com.sdchang.permissionpolice.location.LocationOperation;
import com.sdchang.permissionpolice.sms.SmsOperation;
import com.sdchang.permissionpolice.telephony.TelephonyOperation;
import com.sdchang.permissionpolice.wifi.WifiOperation;

/**
 *
 */
public class ProxyOperation {
    public static final SimpleArrayMap<String, ProxyOperation> operations = new SimpleArrayMap<>();

    static {
        for (ProxyOperation operation : LocationOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (ProxyOperation operation : TelephonyOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (ProxyOperation operation : SmsOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (ProxyOperation operation : WifiOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
    }

    public static ProxyOperation getOperation(RequestParams params) {
        return operations.get(params.opCode);
    }

    public final String mOpCode;
    public final String mPermission;
    @StringRes public final int mDialogTitle;
    public final int mMinSdk;
    public final ProxyFunction mFunction;

    public ProxyOperation(String opCode,
                          String permission,
                          int dialogTitle,
                          int minSdk,
                          ProxyFunction function) {
        mOpCode = opCode;
        mPermission = permission;
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
        mFunction = function;
    }
}
