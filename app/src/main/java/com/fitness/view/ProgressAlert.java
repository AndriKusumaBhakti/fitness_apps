package com.fitness.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fitness.R;

public class ProgressAlert {

    private ProgressDialog mProgressDialog;

    protected TextView titleView;
    protected TextView contentView;
    protected ProgressBar progress;

    private String mMessage;
    private String mTitle;

    public void setTitleAndContent(String title, String content){
        mMessage = content;
        mTitle = title;
    }

    public ProgressAlert(Context p_context) {
        mProgressDialog = new ProgressDialog(p_context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mMessage = p_context.getResources().getString(R.string.please_wait);
        mTitle = p_context.getResources().getString(R.string.alert);

    }

    public final ProgressAlert setOnCancelListener(DialogInterface.OnCancelListener p_listener) {
        mProgressDialog.setOnCancelListener(p_listener);
        return this;
    }

    private void initView(){
        titleView = (TextView) mProgressDialog.findViewById(R.id.loading_dialog_title);
        contentView = (TextView) mProgressDialog.findViewById(R.id.loading_dialog_content);
        progress = (ProgressBar) mProgressDialog.findViewById(R.id.loading_dialog_progress);
    }

    public final void show() {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.loading_dialog);

        initView();

        titleView.setText(mTitle);
        contentView.setText(mMessage);
    }

    public final void dismiss() {
        mProgressDialog.dismiss();
    }

    public final boolean isShowing() {
        return mProgressDialog.isShowing();
    }

}