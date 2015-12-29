package com.permissionnanny.lib.request.simple;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import com.permissionnanny.common.test.NannyTestCase;
import com.permissionnanny.common.test.NannyTestRunner;
import com.permissionnanny.lib.Nanny;
import com.permissionnanny.lib.NannyLibTestRunner;
import com.permissionnanny.lib.request.Ack;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

@RunWith(NannyLibTestRunner.class)
public class LocationEventTest extends NannyTestCase {

    @ClassRule public static final RuleChain CLASS_RULES = NannyTestRunner.newClassRules();
    @Rule public final RuleChain TEST_RULES = NannyTestRunner.newTestRules(this);

    LocationEvent mEventFilter;
    Intent mIntent;
    Bundle mBundle;
    Bundle mExtras;
    Location mLocation;
    @Mock LocationListener mLocationListener;
    @Mock Handler mHandler;
    @Mock Ack mAck;
    @Mock Context mContext;
    @Captor ArgumentCaptor<Runnable> mRunnableCaptor;

    @Before
    public void setUp() throws Exception {
        mEventFilter = new LocationEvent(mLocationListener, mHandler, mAck);
        mIntent = new Intent();
        mBundle = new Bundle();
        mExtras = new Bundle();
        mLocation = new Location("a");
    }

    @Test
    public void process_onLocationChanged() throws Exception {
        mBundle.putString(Nanny.TYPE, LocationEvent.ON_LOCATION_CHANGED);
        mBundle.putParcelable(LocationEvent.LOCATION, mLocation);
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        verify(mLocationListener).onLocationChanged(mLocation);
    }

    @Test
    public void process_onProviderDisabled() throws Exception {
        mBundle.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_DISABLED);
        mBundle.putString(LocationEvent.PROVIDER, "a");
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        verify(mLocationListener).onProviderDisabled("a");
    }

    @Test
    public void process_onProviderEnabled() throws Exception {
        mBundle.putString(Nanny.TYPE, LocationEvent.ON_PROVIDER_ENABLED);
        mBundle.putString(LocationEvent.PROVIDER, "a");
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        verify(mLocationListener).onProviderEnabled("a");
    }

    @Test
    public void process_onStatusChanged() throws Exception {
        mBundle.putString(Nanny.TYPE, LocationEvent.ON_STATUS_CHANGED);
        mBundle.putString(LocationEvent.PROVIDER, "a");
        mBundle.putInt(LocationEvent.STATUS, 1);
        mBundle.putBundle(LocationEvent.EXTRAS, mExtras);
        mIntent.putExtra(Nanny.ENTITY_BODY, mBundle);

        runProcess();

        verify(mLocationListener).onStatusChanged("a", 1, mExtras);
    }

    private void runProcess() {
        mEventFilter.process(mContext, mIntent);

        verify(mAck).sendAck(mContext, mIntent);
        verify(mHandler).post(mRunnableCaptor.capture());

        mRunnableCaptor.getValue().run();
    }
}
