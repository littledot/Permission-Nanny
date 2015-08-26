package com.permissionnanny.operation;

import android.support.v4.util.SimpleArrayMap;
import com.permissionnanny.Operation;
import com.permissionnanny.ProxyFunction;
import com.permissionnanny.lib.request.RequestParams;

/**
 *
 */
public class SimpleOperation extends Operation {
    public static final SimpleArrayMap<String, SimpleOperation> operations = new SimpleArrayMap<>();

    static {
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
    public final int mProtectionLevel;
    public final ProxyFunction mFunction;

    public SimpleOperation(String opCode,
                           String permission,
                           int protectionLevel,
                           int dialogTitle,
                           int minSdk,
                           ProxyFunction function) {
        super(dialogTitle, minSdk);
        mOpCode = opCode;
        mPermission = permission;
        mProtectionLevel = protectionLevel;
        mFunction = function;
    }
}
