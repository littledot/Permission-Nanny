package com.permissionnanny;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.permissionnanny.common.test.NannyTestCase;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(NannyAppTestRunner.class)
public class UtilTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    @Mock private Context mContext;
    @Mock private PackageManager mPackageManager;
    private ApplicationInfo mApplicationInfo;

    @Before
    public void setUp() throws Exception {
        when(mContext.getPackageManager()).thenReturn(mPackageManager);
        mApplicationInfo = new ApplicationInfo();
    }

    @Test
    public void getApplicationInfo() throws Exception {
        when(mPackageManager.getApplicationInfo("a", 0)).thenReturn(mApplicationInfo);

        ApplicationInfo actual = Util.getApplicationInfo(mContext, "a");

        assertThat(actual, sameInstance(mApplicationInfo));
    }

    @Test
    public void getApplicationInfo_shouldReturnNull_whenQueryThrows() throws Exception {
        when(mPackageManager.getApplicationInfo(anyString(), anyInt()))
                .thenThrow(new PackageManager.NameNotFoundException());

        ApplicationInfo actual = Util.getApplicationInfo(mContext, "a");

        assertThat(actual, nullValue());
    }
}
