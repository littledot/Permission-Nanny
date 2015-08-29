package com.permissionnanny.lib;

import com.permissionnanny.common.test.NannyTestRunner;
import org.junit.runners.model.InitializationError;

import java.util.Properties;

/**
 * Test runner for the 'lib' module.
 */
public class NannyLibTestRunner extends NannyTestRunner {

    public NannyLibTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Properties getConfigProperties() {
        Properties properties = new Properties();
        properties.put("sdk", "21");
        properties.put("constants", BuildConfig.class.getCanonicalName());
        properties.put("application", RoboApp.class.getCanonicalName());
        return properties;
    }
}
