package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

@SuppressLint("AppCompatCustomView")
public class CheckBoxRegular extends CheckBox {

    public CheckBoxRegular(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub

    }

    public CheckBoxRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Regular.otf");
        setTypeface(type);
    }
}
