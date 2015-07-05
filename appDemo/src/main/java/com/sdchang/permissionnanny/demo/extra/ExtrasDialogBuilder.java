package com.sdchang.permissionnanny.demo.extra;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sdchang.permissionnanny.demo.R;

/**
 *
 */
public class ExtrasDialogBuilder {
    public Dialog build(Context context, Extra[] extras, String[] labels) {
        View view = LayoutInflater.from(context).inflate(R.layout.extras_dialog, null);
        ViewGroup vg = (ViewGroup) view.findViewById(R.id.vg);
        for (int i = 0, len = extras.length; i < len; i++) {
            vg.addView(extras[i].getView(context, vg, labels[i]));
        }
        return new AlertDialog.Builder(context)
                .setView(view)
                .create();
    }
}
