package com.permissionnanny.operation;

import android.support.v4.util.SimpleArrayMap;
import com.permissionnanny.Operation;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class ProxyOperation extends Operation {
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
    public final ProxyFunction mFunction;

    public ProxyOperation(String opCode,
                          String permission,
                          int dialogTitle,
                          int minSdk,
                          ProxyFunction function) {
        super(dialogTitle, minSdk);
        mOpCode = opCode;
        mPermission = permission;
        mFunction = function;
    }
}
