package com.permissionnanny;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.permissionnanny.dagger.DaggerMockContextComponent;
import com.permissionnanny.dagger.MockComponentFactory;
import com.permissionnanny.dagger.MockContextComponent;
import com.permissionnanny.dagger.MockContextModule;
import com.permissionnanny.data.AppPermissionManager;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UninstallReceiverTest extends NannyAppTestCase {

    UninstallReceiver mReceiver;
    Intent mIntent;
    @Mock Context mContext;
    @Inject AppPermissionManager mAppPermissionManager;

    @Before
    public void setUp() throws Exception {
        MockContextComponent component = DaggerMockContextComponent.builder()
                .appComponent(MockComponentFactory.getAppComponent())
                .contextModule(new MockContextModule()).build();
        component.inject(this);
        mReceiver = new UninstallReceiver();
        mReceiver.setTestComponent(component);
        mIntent = new Intent();
    }

    @Test
    public void onReceive() throws Exception {
        mIntent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, false)
                .setData(Uri.parse("package:3rd.party.app"));

        mReceiver.onReceive(mContext, mIntent);

        verify(mAppPermissionManager).removeApp("3rd.party.app");
    }

    @Test
    public void onReceive_shouldIgnoreIntent_whenUpgradeFlow() throws Exception {
        mIntent.setAction(Intent.ACTION_PACKAGE_REMOVED)
                .putExtra(Intent.EXTRA_REPLACING, true)
                .setData(Uri.parse("package:3rd.party.app"));

        mReceiver.onReceive(mContext, mIntent);

        verify(mAppPermissionManager, never()).removeApp("3rd.party.app");
    }
}
