package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditTextRegular extends EditText {

    public EditTextRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Regular.otf");
        setTypeface(type);

    }

    public EditTextRegular(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);

    }
}