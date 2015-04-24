package com.sdchang.permissionpolice.external;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.sdchang.permissionpolice.ContactsListService;
import com.sdchang.permissionpolice.request.CursorRequest;
import com.sdchang.permissionpolice.R;

public class ExternalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.add(0, 1, 0, "broadcast");
        menu.add(0, 2, 0, "service");
        menu.add(0, 3, 0, "contacts");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 1) {
            return true;
        } else if (id == 2) {
            CursorRequest request = CursorRequest.newBuilder()
                    .uri(ContactsContract.Contacts.CONTENT_URI)
                    .selection("limit 5")
                    .build();
            startService(request.newIntent(this));
            return true;
        } else if (id == 3) {
            startService(new Intent(this, ContactsListService.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
