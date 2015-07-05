package com.sdchang.permissionnanny.demo.extra;

import android.content.Context;
import android.location.Criteria;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sdchang.permissionnanny.demo.R;

/**
 *
 */
public class CriteriaExtra implements Extra<Criteria> {

    private IntegerExtra mAcccuracy = new IntegerExtra();
    private IntegerExtra mHorizontalAccuracy = new IntegerExtra();
    private IntegerExtra mVerticalAccuracy = new IntegerExtra();
    private IntegerExtra mSpeedAccuracy = new IntegerExtra();
    private IntegerExtra mBearingAccuracy = new IntegerExtra();
    private IntegerExtra mPowerRequirement = new IntegerExtra();
    private BooleanExtra mAltitudeRequired = new BooleanExtra();
    private BooleanExtra mBearingRequired = new BooleanExtra();
    private BooleanExtra mSpeedRequired = new BooleanExtra();
    private BooleanExtra mCostAllowed = new BooleanExtra();

    @Override
    public View getView(Context context, ViewGroup parent, String label) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.extras_criteria, parent, false);
        ((TextView) view.findViewById(R.id.tvLabel)).setText(label);
        view.addView(mAcccuracy.getView(context, parent, "Accuracy"));
        view.addView(mHorizontalAccuracy.getView(context, parent, "Horizontal Accuracy"));
        view.addView(mVerticalAccuracy.getView(context, parent, "Vertical Accuracy"));
        view.addView(mSpeedAccuracy.getView(context, parent, "Speed Accuracy"));
        view.addView(mBearingAccuracy.getView(context, parent, "Bearing Accuracy"));
        view.addView(mPowerRequirement.getView(context, parent, "Power Requirement"));
        view.addView(mAltitudeRequired.getView(context, parent, "Altitude Required"));
        view.addView(mBearingRequired.getView(context, parent, "Bearing Required"));
        view.addView(mSpeedRequired.getView(context, parent, "Speed Required"));
        view.addView(mCostAllowed.getView(context, parent, "Cost Allowed"));
        return view;
    }

    @Override
    public Criteria getValue() {
        Criteria value = new Criteria();
        value.setAccuracy(mAcccuracy.mValue);
        value.setHorizontalAccuracy(mHorizontalAccuracy.mValue);
        value.setVerticalAccuracy(mVerticalAccuracy.mValue);
        value.setSpeedAccuracy(mSpeedAccuracy.mValue);
        value.setBearingAccuracy(mBearingAccuracy.mValue);
        value.setPowerRequirement(mPowerRequirement.mValue);
        value.setAltitudeRequired(mAltitudeRequired.mValue);
        value.setBearingRequired(mBearingRequired.mValue);
        value.setSpeedRequired(mSpeedRequired.mValue);
        value.setCostAllowed(mCostAllowed.mValue);
        return value;
    }
}
