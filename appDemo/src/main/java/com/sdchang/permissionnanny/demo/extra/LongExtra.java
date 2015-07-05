package com.sdchang.permissionnanny.demo.extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import com.sdchang.permissionnanny.demo.R;

/**
 *
 */
public class LongExtra implements Extra<Long> {

    long mValue;

    @Override
    public View getView(Context context, ViewGroup parent, String label) {
        View view = LayoutInflater.from(context).inflate(R.layout.extras_integer, parent, false);
        ButterKnife.inject(this, view);
        ((TextView) view.findViewById(R.id.tvLabel)).setText(label);
        EditText etValue = (EditText) view.findViewById(R.id.etValue);
        etValue.setText("" + mValue);
        etValue.setSelection(etValue.getText().length());
        return view;
    }

    @Override
    public Long getValue() {
        return mValue;
    }

    @OnTextChanged(R.id.etValue)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            mValue = Long.parseLong(s.toString());
        } catch (NumberFormatException e) {
            mValue = 0;
        }
    }
}
