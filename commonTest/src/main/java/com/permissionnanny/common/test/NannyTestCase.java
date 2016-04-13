package com.permissionnanny.common.test;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

/**
 * Base test.
 * <p/>
 * Facilitates PowerMock integration.
 */
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.json.*"})
public class NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);
}
