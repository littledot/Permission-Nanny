package com.permissionnanny.common.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.powermock.modules.junit4.rule.PowerMockRule;

/**
 * Wrapper for {@link PowerMockRule} to integrate with {@link TestRule}.
 */
public class PowerMockTestRule implements TestRule {

    private final PowerMockRule mRule = new PowerMockRule();
    private final Object mTarget;

    public PowerMockTestRule(Object target) {
        mTarget = target;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return mRule.apply(base, null, mTarget);
    }
}
