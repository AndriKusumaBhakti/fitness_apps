package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextViewSemiBold  extends TextView {

    public TextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Semibold.otf");
        setTypeface(type);
    }

    public TextViewSemiBold(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);
    }
}
