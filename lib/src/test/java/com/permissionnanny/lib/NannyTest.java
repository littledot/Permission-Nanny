package com.permissionnanny.lib;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.permissionnanny.common.test.Mockingbird.mockPendingIntent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NannyTest extends NannyLibTestCase {

    Intent intent;
    Bundle entity;
    @Mock PendingIntent sender;

    @Before
    public void setUp() throws Exception {
        intent = new Intent();
        entity = new Bundle();
    }

    @Test
    public void isIntentFromPermissionNannyShouldReturnTrueWhenIntentIsValid() throws Exception {
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        mockPendingIntent(sender, Nanny.SERVER_APP_ID);

        boolean ans = Nanny.isIntentFromPermissionNanny(intent);

        assertThat(ans, is(true));
    }

    @Test(expected = NannyException.class)
    public void isIntentFromPermissionNannyShouldThrowWhenSenderIdentityIsWrong() throws Exception {
        intent.putExtra(Nanny.ENTITY_BODY, entity);
        entity.putParcelable(Nanny.SENDER_IDENTITY, sender);
        mockPendingIntent(sender, "3rd.party.app");

        Nanny.isIntentFromPermissionNanny(intent);
    }

    @Test(expected = NannyException.class)
    public void isIntentFromPermissionNannyShouldThrowWhenSenderIdentityIsMissing() throws Exception {
        intent.putExtra(Nanny.ENTITY_BODY, entity);

        Nanny.isIntentFromPermissionNanny(intent);
    }

    @Test(expected = NannyException.class)
    public void isIntentFromPermissionNannyShouldThrowWhenEntityIsMissing() throws Exception {
        Nanny.isIntentFromPermissionNanny(intent);
    }
}
