package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class ButtonBold  extends Button {

    public ButtonBold(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub

        setTransformationMethod(null);
    }

    public ButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Bold.otf");
        setTypeface(type);
        setTransformationMethod(null);
    }

}
