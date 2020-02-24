package com.fitness.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.Date;

public class HInputView extends RelativeLayout {
    protected OnInputClickListener mOnInputClickListener;
    protected OnInputChangeListener mOnInputChangeListener;
    protected OnDialogPositiveClick mOnDialogPositiveClick;

    public OnDialogPositiveClick getmOnDialogPositiveClick() {
        return mOnDialogPositiveClick;
    }

    public void setmOnDialogPositiveClick(OnDialogPositiveClick mOnDialogPositiveClick) {
        this.mOnDialogPositiveClick = mOnDialogPositiveClick;
    }

    public HInputView(Context p_context) {
        super(p_context);
    }

    public HInputView(Context p_context, AttributeSet p_attrs) {
        super(p_context, p_attrs);
    }

    public RelativeLayout setSingleLine(boolean p_singleLine) {
        return this;
    }

    public RelativeLayout setKey(CharSequence p_value) {
        return this;
    }

    public CharSequence getKey() {
        return null;
    }

    public RelativeLayout setValue(CharSequence p_value) {
        return this;
    }

    public CharSequence getValue() {
        return null;
    }

    public RelativeLayout setOnInputClickListener(OnInputClickListener p_listener) {
        mOnInputClickListener = p_listener;
        return this;
    }

    public RelativeLayout setOnInputChangeListener(OnInputChangeListener p_listener) {
        mOnInputChangeListener = p_listener;
        return this;
    }

    public static interface OnInputClickListener {
        public void onClick(HInputView p_view);
    }

    public static interface OnDialogPositiveClick {
        public void onPositiveClick(Date date);
    }


    public static interface OnInputChangeListener {
        public void onChange(HInputView p_view, CharSequence p_before, CharSequence p_after);
    }
}
