package com.permissionnanny.demo.extra;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.permissionnanny.demo.R;

/**
 *
 */
public class AccountExtra implements Extra<Account> {
    private StringExtra mName;
    private StringExtra mType;

    public AccountExtra(String name, String type) {
        mName = new StringExtra(name);
        mType = new StringExtra(type);
    }

    @Override
    public View getView(Context context, ViewGroup parent, String label) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.extras_account, parent, false);
        ((TextView) view.findViewById(R.id.tvLabel)).setText(label);
        view.addView(mName.getView(context, parent, "Name"));
        view.addView(mType.getView(context, parent, "Type"));
        return view;
    }

    @Override
    public Account getValue() {
        return new Account(mName.getValue(), mType.getValue());
    }
}
