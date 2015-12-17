package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.dagger.DaggerMockContextComponent;
import com.permissionnanny.dagger.MockComponentFactory;
import com.permissionnanny.dagger.MockContextComponent;
import com.permissionnanny.dagger.MockContextModule;
import com.permissionnanny.data.AppPermissionManager;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(NannyAppTestRunner.class)
public class UninstallReceiverTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyAppTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyAppTestRunner.newTestRules(this);

    private UninstallReceiver mUninstallReceiver;
    private Intent mIntent;
    @Mock private Context mContext;
    @Inject AppPermissionManager mAppPermissionManager;

    @Before
    public void setUp() throws Exception {
        MockContextComponent component = DaggerMockContextComponent.builder()
                .appComponent(MockComponentFactory.getAppComponent())
                .contextModule(new MockContextModule()).build();
        component.inject(this);
        mUninstallReceiver = new UninstallReceiver();
        mUninstallReceiver.setComponent(component);
        mIntent = new Intent();
    }

    @Test
    public void onReceive() throws Exception {
        mIntent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, false)
                .setData(Uri.parse("package:3rd.party.app"));

        mUninstallReceiver.onReceive(mContext, mIntent);

        verify(mAppPermissionManager).removeApp("3rd.party.app");
    }

    @Test
    public void onReceive_shouldIgnoreIntent_whenUpgradeFlow() throws Exception {
        mIntent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, true)
                .setData(Uri.parse("package:3rd.party.app"));

        mUninstallReceiver.onReceive(mContext, mIntent);

        verify(mAppPermissionManager, never()).removeApp("3rd.party.app");
    }
}
