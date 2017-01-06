package com.permissionnanny.data

import android.support.v4.util.ArrayMap
import android.support.v4.util.SimpleArrayMap
import android.text.TextUtils
import org.iq80.leveldb.DB
import org.iq80.leveldb.Options
import org.iq80.leveldb.impl.Iq80DBFactory
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**

 */
class NannyDB @Throws(IOException::class)
constructor(
        private val file: File,
        private val cryo: Cryo) {

    private val mDB: DB

    init {
        val opt = Options()
        opt.createIfMissing(true)
        mDB = Iq80DBFactory.factory.open(file, opt)
    }

    fun open() {/* No-op: Already open in constructor. */
    }

    @Throws(IOException::class)
    fun close() {
        mDB.close()
    }

    @Throws(IOException::class)
    fun destroy() {
        mDB.close()
        Iq80DBFactory.factory.destroy(file, null)
    }

    operator fun <T> get(key: String, type: Class<T>): T? {
        val data = mDB.get(key.toByteArray(UTF_8))
        return cryo.deserialize(data, type)
    }

    operator fun <T> get(key: String): T? {
        val data = mDB.get(key.toByteArray(UTF_8))
        return cryo.deserialize(data)
    }

    fun put(key: String, value: Any) {
        val bytes = cryo.serialize(value)
        mDB.put(key.toByteArray(UTF_8), bytes)
    }

    fun del(key: String) {
        mDB.delete(key.toByteArray(UTF_8))
    }

    fun findKeys(startsWith: String): ArrayList<String> {
        val it = mDB.iterator()
        it.seek(startsWith.toByteArray(UTF_8))
        val list = ArrayList<String>()
        while (it.hasNext()) {
            list.add(String(it.next().key))
        }
        return list
    }

    fun <T> findVals(startsWith: String?, type: Class<T>): ArrayList<T> {
        val it = mDB.iterator()
        if (!TextUtils.isEmpty(startsWith)) {
            it.seek(startsWith!!.toByteArray(UTF_8))
        }

        val list = ArrayList<T>()
        while (it.hasNext()) {
            val value = it.next().value
            val data = cryo.deserialize(value, type)
            if (data != null) {
                list.add(data)
            }
        }
        return list
    }

    fun <V> findVal(startsWith: String?, type: Class<V>): ArrayMap<String, V> {
        val it = mDB.iterator()
        if (!TextUtils.isEmpty(startsWith)) {
            it.seek(startsWith!!.toByteArray())
        }

        val map = ArrayMap<String, V>()
        while (it.hasNext()) {
            val next = it.next()
            map.put(String(next.key), cryo.deserialize(next.value, type))
        }
        return map
    }

    fun <V> putAtomic(map: SimpleArrayMap<String, V>) {
        val op = mDB.createWriteBatch()

        var i = 0
        val len = map.size()
        while (i < len) {
            op.put(map.keyAt(i).toByteArray(), cryo.serialize(map.valueAt(i) as Any))
            i++
        }
        mDB.write(op)
    }

    companion object {
        private val UTF_8 = Charset.forName("UTF-8")
    }
}
