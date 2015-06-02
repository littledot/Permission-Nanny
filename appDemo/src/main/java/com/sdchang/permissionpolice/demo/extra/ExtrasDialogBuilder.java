package com.sdchang.permissionpolice.demo.extra;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sdchang.permissionpolice.demo.R;

/**
 *
 */
public class ExtrasDialogBuilder {
    public Dialog build(Context context, Extra[] extras, String[] labels) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.extras_dialog, null);
        for (int i = 0, len = extras.length; i < len; i++) {
            View extraView = extras[i].getView(context, view);
            ((TextView) extraView.findViewById(R.id.tvLabel)).setText(labels[i]);
            view.addView(extraView);
        }
        return new AlertDialog.Builder(context)
                .setView(view)
                .create();
    }
}
