package com.permissionnanny;

import com.permissionnanny.common.test.NannyTestRunner;
import org.junit.runners.model.InitializationError;

import java.util.Properties;

/**
 * Test Runner for the 'app' module.
 */
public class NannyAppTestRunner extends NannyTestRunner {

    public NannyAppTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Properties getConfigProperties() {
        Properties properties = new Properties();
        properties.put("sdk", "21");
        properties.put("constants", BuildConfig.class.getCanonicalName());
        properties.put("application", RoboApp.class.getCanonicalName());
        properties.put("packageName", "com.permissionnanny");
        return properties;
    }
}
