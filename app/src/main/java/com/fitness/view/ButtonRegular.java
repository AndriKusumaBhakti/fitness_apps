package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class ButtonRegular extends Button {

    public ButtonRegular(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub

    }

    public ButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Regular.otf");
        setTypeface(type);
    }

}
