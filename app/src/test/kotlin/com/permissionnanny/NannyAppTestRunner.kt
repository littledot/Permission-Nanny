package com.permissionnanny

import com.permissionnanny.common.test.NannyTestRunner
import org.junit.runners.model.InitializationError
import java.util.*

/**
 * Test Runner for the 'app' module.
 */
class NannyAppTestRunner @Throws(InitializationError::class)
constructor(klass: Class<*>) : NannyTestRunner(klass) {

    override fun getConfigProperties(): Properties {
        val properties = Properties()
        properties.put("sdk", "21")
        properties.put("constants", BuildConfig::class.java.canonicalName)
        properties.put("application", RoboApp::class.java.canonicalName)
        properties.put("packageName", "com.permissionnanny")
        return properties
    }
}
