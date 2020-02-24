package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextViewRegular extends TextView {

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Regular.otf");
        setTypeface(type);
    }

    public TextViewRegular(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);
    }

}