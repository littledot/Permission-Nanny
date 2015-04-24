package com.sdchang.permissionpolice;

import android.app.IntentService;
import android.content.Intent;
import android.provider.ContactsContract;
import com.sdchang.permissionpolice.request.CursorRequest;

/**
 *
 */
public class VerificationService extends IntentService {

    public static final String REQUEST_TYPE = "requestType";
    public static final String REQUEST = "request";

    public VerificationService() {
        super(VerificationService.class.getCanonicalName());
    }

    public VerificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra(REQUEST_TYPE, 0);
        if (type == 1) {
            CursorRequest request = intent.getParcelableExtra(REQUEST);
            if (ContactsContract.Contacts.CONTENT_URI.equals(request.uri())) {
                startActivity(new Intent(this, ConfirmRequestActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtras(intent));
            }
        }
    }
}
