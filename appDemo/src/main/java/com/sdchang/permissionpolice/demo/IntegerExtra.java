package com.sdchang.permissionpolice.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 *
 */
public class IntegerExtra implements Extra<Integer> {

    int mValue;

    @Override
    public View getView(Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.extras_integer, parent, false);
        ButterKnife.inject(this, view);

        EditText etValue = (EditText) view.findViewById(R.id.etValue);
        etValue.setText("" + mValue);
        etValue.setSelection(etValue.getText().length());
        return view;
    }

    @Override
    public Integer getValue() {
        return mValue;
    }

    @OnTextChanged(R.id.etValue)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            mValue = Integer.parseInt(s.toString());
        } catch (NumberFormatException e) {
            mValue = 0;
        }
    }
}
