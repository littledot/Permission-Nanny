package com.permissionnanny.common.test;

import org.junit.rules.RuleChain;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;

/**
 *
 */
public class NannyTestRunner extends RobolectricGradleTestRunner {

    public NannyTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    public static RuleChain newClassRules() {
        return RuleChain.emptyRuleChain();
    }

    public static RuleChain newTestRules(Object target) {
        return RuleChain.outerRule(new InitMocksRule(target));
    }
}
