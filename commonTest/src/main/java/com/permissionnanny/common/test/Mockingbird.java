package com.permissionnanny.common.test;

import android.app.PendingIntent;
import android.content.IntentSender;

import static org.mockito.Mockito.*;

/**
 *
 */
public class Mockingbird {
    public static void mockPendingIntent(PendingIntent mock, String senderPackage) {
        IntentSender mockSender = mock(IntentSender.class);
        when(mock.getIntentSender()).thenReturn(mockSender);
        when(mockSender.getTargetPackage()).thenReturn(senderPackage);
    }
}
