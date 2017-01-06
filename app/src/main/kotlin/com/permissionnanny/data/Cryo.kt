package com.permissionnanny.data

import android.support.v4.util.Pools
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import javax.inject.Inject

/**

 */
class Cryo
@Inject
constructor(private val kryo: Kryo) {
    private val outputPool: Pools.Pool<Output>
    private val inputPool: Pools.Pool<Input>

    init {
        outputPool = Pools.SynchronizedPool<Output>(5)
        inputPool = Pools.SynchronizedPool<Input>(5)
    }

    fun serialize(value: Any): ByteArray {
        registerType(value.javaClass)
        val output = acquireOutput()
        kryo.writeObject(output, value)
        val bytes = output.toBytes()
        release(output)
        return bytes
    }

    fun <T> deserialize(data: ByteArray, type: Class<T>): T? {
        if (!isDataValid(data)) {
            return null
        }
        registerType(type)
        val input = acquireInput()
        input.buffer = data
        val value = kryo.readObject(input, type)
        release(input)
        return value
    }

    fun <T> deserialize(data: ByteArray): T? {
        if (!isDataValid(data)) {
            return null
        }
        val input = acquireInput()
        input.buffer = data
        val type = kryo.readClass(input).type
        var value: T? = null
        try {
            value = kryo.readObject(input, type) as T
        } catch (e: ClassCastException) {
            //            Timber.wtf(e, "Actual type: " + type.getCanonicalName());
        }

        release(input)
        return value
    }

    private fun isDataValid(data: ByteArray?): Boolean {
        return data != null && data.size > 0
    }

    private fun acquireOutput(): Output {
        val instance = outputPool.acquire()
        return instance ?: Output(BUF_SIZE, BUF_MAX_SIZE)
    }

    private fun release(instance: Output) {
        instance.clear()
        outputPool.release(instance)
    }

    private fun acquireInput(): Input {
        val instance = inputPool.acquire()
        return instance ?: Input()
    }

    private fun release(instance: Input) {
        instance.buffer = EMPTY_BUF
        inputPool.release(instance)
    }

    private fun registerType(type: Class<*>) {
        kryo.register(type)
        if (type.isArray) { // register component type if value is an array
            kryo.register(type.componentType)
        }
    }

    companion object {

        val BUF_SIZE = 8192
        val BUF_MAX_SIZE = -1
        private val EMPTY_BUF = ByteArray(0)
    }
}
