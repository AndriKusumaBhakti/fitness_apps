package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditTextBold  extends EditText {

    public EditTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Bold.otf");
        setTypeface(type);
    }

    public EditTextBold(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);
    }
}