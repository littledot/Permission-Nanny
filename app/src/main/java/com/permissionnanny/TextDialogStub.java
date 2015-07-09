package com.permissionnanny;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.permissionnanny.lib.Nanny;

/**
 *
 */
public class TextDialogStub {

    @Bind(R.id.tvReason) TextView tvReason;

    public void inflateViewStub(ViewStub stub, Bundle args) {
        String reason = args.getString(Nanny.REQUEST_REASON);

        stub.setLayoutResource(R.layout.dialog_text);
        View view = stub.inflate();
        ButterKnife.bind(this, view);
        tvReason.setText(reason);
    }
}
