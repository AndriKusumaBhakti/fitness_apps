package com.fitness.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.fitness.R;
import com.fitness.aplication.APP;

import java.util.Calendar;
import java.util.Date;

public class DateL1 {
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogbuilder;
    private View view;
    private DatePicker datepicker;

    private Context mContext;
    private String mTitle;
    private String mPositiveText;
    private String mNegativeText;
    private Object mObject;

    public DateL1(Context p_context) {
        dialogbuilder = new AlertDialog.Builder(p_context);
        mContext = p_context;
        mTitle = "";
        mPositiveText = "";
        mNegativeText = "";
        mObject = null;
    }

    public final DateL1 setTitle(String p_title) {
        mTitle = p_title == null ? "" : p_title;
        return this;
    }

    public final DateL1 setPositive(String p_text) {
        dialogbuilder.setPositiveButton(mPositiveText = p_text == null ? "" : p_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
                    onPositive(calendar.getTime());
                } catch (Exception e) {
                    APP.logError("DateL1 " + e.getMessage());
                } catch (Error e) {
                    APP.logError("DateL1 " + e.getMessage());
                }
            }
        });
        return this;
    }

    public final DateL1 setNegative(String p_text) {
        dialogbuilder.setNegativeButton(mNegativeText = p_text == null ? "" : p_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface p_dialog, int id) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
                    onNegative(calendar.getTime());
                } catch (Exception e) {
                    APP.logError("DateL1 " + e.getMessage());
                } catch (Error e) {
                    APP.logError("DateL1 " + e.getMessage());
                }
            }
        });
        return this;
    }

    public final DateL1 setObject(Object p_object) {
        mObject = p_object;
        return this;
    }

    public final DateL1 create() {
        // @Step 1 : Create Components
        view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.core_view_datel1, null);
        // @Step 2 : Initialize  Components
        datepicker = (DatePicker) view.findViewById(R.id.l1_datepicker);
        // @Step 3 : Create Dialog
        if (!mTitle.equals("")) dialogbuilder.setTitle(mTitle);
        dialogbuilder.setView(view);

        alertDialog = dialogbuilder.create();

        return this;
    }

    public final void show() {
        alertDialog.show();
    }

    public final String getTitle() {
        return mTitle;
    }

    public final String getPositiveText() {
        return mPositiveText;
    }

    public final String getNegativeText() {
        return mNegativeText;
    }

    public final Object getObject() {
        return mObject;
    }

    public void onPositive(Date p_date) {
        ;
    }

    public void onNegative(Date p_date) {
        ;
    }
}
