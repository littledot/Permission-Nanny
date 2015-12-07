package com.permissionnanny.common.test;

import org.powermock.core.classloader.annotations.PowerMockIgnore;

/**
 * Base test.
 * <p/>
 * Facilitates PowerMock integration.
 */
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.json.*"})
public class NannyTestCase {
}
