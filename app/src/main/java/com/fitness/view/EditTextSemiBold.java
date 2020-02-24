package com.fitness.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditTextSemiBold  extends EditText {

    public EditTextSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),
                "ProximaNova-Semibold.otf");
        setTypeface(type);

    }

    public EditTextSemiBold(Context context) {
        // TODO Auto-generated constructor stub
        super(context, null);

    }
}
