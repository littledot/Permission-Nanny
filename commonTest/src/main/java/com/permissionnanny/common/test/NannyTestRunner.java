package com.permissionnanny.common.test;

import org.junit.rules.RuleChain;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;

/**
 *
 */
public class NannyTestRunner extends RobolectricGradleTestRunner {

    public static RuleChain newClassRules() {
        return RuleChain.emptyRuleChain();
    }

    public static RuleChain newTestRules(Object target) {
        return RuleChain.emptyRuleChain()
                .around(new PowerMockTestRule(target))
                .around(new InitMocksRule(target));
    }

    public NannyTestRunner(Class<?> type) throws InitializationError {
        super(type);
    }
}
