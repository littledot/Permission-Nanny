package com.permissionnanny;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.permissionnanny.lib.request.PermissionRequest;

/**
 *
 */
public class TextDialogStub {

    @InjectView(R.id.tvReason) TextView tvReason;

    public void inflateViewStub(ViewStub stub, Bundle args) {
        String reason = args.getString(PermissionRequest.REQUEST_REASON);

        stub.setLayoutResource(R.layout.dialog_text);
        View view = stub.inflate();
        ButterKnife.inject(this, view);
        tvReason.setText(reason);
    }
}
