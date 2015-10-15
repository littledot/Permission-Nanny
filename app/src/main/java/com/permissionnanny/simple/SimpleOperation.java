package com.permissionnanny.simple;

import android.support.v4.util.ArrayMap;
import com.permissionnanny.Operation;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.lib.request.RequestParams;

import java.util.Map;

/**
 *
 */
public class SimpleOperation extends Operation {
    public static final Map<String, SimpleOperation> operations = new ArrayMap<>();

    static {
        for (SimpleOperation operation : AccountOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (SimpleOperation operation : LocationOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (SimpleOperation operation : TelephonyOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (SimpleOperation operation : SmsOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
        for (SimpleOperation operation : WifiOperation.operations) {
            operations.put(operation.mOpCode, operation);
        }
    }

    public static SimpleOperation getOperation(RequestParams params) {
        return operations.get(params.opCode);
    }

    public final String mOpCode;
    public final String mPermission;
    public final ProxyFunction mFunction;

    public SimpleOperation(String opCode,
                           String permission,
                           int protectionLevel,
                           int dialogTitle,
                           int minSdk,
                           ProxyFunction function) {
        super(dialogTitle, minSdk, protectionLevel);
        mOpCode = opCode;
        mPermission = permission;
        mFunction = function;
    }
}
