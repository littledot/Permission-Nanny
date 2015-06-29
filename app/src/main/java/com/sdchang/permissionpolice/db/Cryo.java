package com.sdchang.permissionpolice.db;

import android.support.v4.util.Pools;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import timber.log.Timber;

import javax.inject.Inject;

/**
 *
 */
public class Cryo {
    public static final int BUF_SIZE = 8192;
    public static final int BUF_MAX_SIZE = -1;
    private static final byte[] EMPTY_BUF = new byte[0];

    private Kryo mKryo;
    private Pools.Pool<Output> mOutputPool;
    private Pools.Pool<Input> mInputPool;

    @Inject
    public Cryo(Kryo kryo) {
        mKryo = kryo;
        mOutputPool = new Pools.SynchronizedPool<>(5);
        mInputPool = new Pools.SynchronizedPool<>(5);
    }

    public byte[] serialize(Object val) {
        registerType(val.getClass());
        Output output = acquireOutput();
        mKryo.writeObject(output, val);
        byte[] bytes = output.toBytes();
        release(output);
        return bytes;
    }

    public <T> T deserialize(byte[] data, Class<T> type) {
        registerType(type);
        Input input = acquireInput();
        input.setBuffer(data);
        T val = mKryo.readObject(input, type);
        release(input);
        return val;
    }

    public <T> T deserialize(byte[] data) {
        Input input = acquireInput();
        input.setBuffer(data);
        Class type = mKryo.readClass(input).getType();
        T val = null;
        try {
            val = (T) mKryo.readObject(input, type);
        } catch (ClassCastException e) {
            Timber.wtf(e, "Actual type: " + type.getCanonicalName());
        }
        release(input);
        return val;
    }

    private Output acquireOutput() {
        Output instance = mOutputPool.acquire();
        return (instance != null) ? instance : new Output(BUF_SIZE, BUF_MAX_SIZE);
    }

    private void release(Output instance) {
        instance.clear();
        mOutputPool.release(instance);
    }

    private Input acquireInput() {
        Input instance = mInputPool.acquire();
        return (instance != null) ? instance : new Input();
    }

    private void release(Input instance) {
        instance.setBuffer(EMPTY_BUF);
        mInputPool.release(instance);
    }

    private void registerType(Class type) {
        mKryo.register(type);
        if (type.isArray()) { // register component type if value is an array
            mKryo.register(type.getComponentType());
        }
    }
}
