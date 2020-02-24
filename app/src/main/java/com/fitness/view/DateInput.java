package com.fitness.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fitness.R;
import com.fitness.aplication.APP;
import com.fitness.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateInput extends HInputView {
    private TextView label;
    private TextView error;
    private TextView textview;
    private DateL1 dateL1;
    private Dialog dateDialog;
    private String hint;
    private String errorText;
    private Calendar maxDate;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    private String mTitle;
    private boolean isMaxDateEnable = true;

    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd MMM yyyy");
    private OnDateSelected onDateSelected;


    public DateInput(Context p_context) {
        this(p_context, null);
    }

    public DateInput(Context p_context, AttributeSet p_attrs) {
        super(p_context, p_attrs);

        LayoutInflater.from(p_context).inflate(R.layout.core_view_dateinput, this, true);

        error = (TextView) findViewById(R.id.dateinput_label_error);
        label = (TextView) findViewById(R.id.dateinput_label);
        textview = (TextView) findViewById(R.id.coreview_dateinput_textview);
        textview.setHint(getResources().getString(R.string.date_hint));
        textview.setTextColor(getResources().getColor(R.color.grey1));
        setError("");
        mTitle = "";

        if (isInEditMode() == false) {
            dateDialog = new Dialog(p_context, R.style.AppTheme_FullScreenDialog);
            dateDialog.setCanceledOnTouchOutside(true);
            dateDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dateDialog.setContentView(R.layout.view_dialog_date);


            setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    initDateDialog();
                    APP.log("ASD "+ "ASD2");

                }
            });
        }
    }

    public void setHint(String hint){
        this.hint = hint;
        textview.setHint(this.hint);
    }

    public void setError(String error){
        if(!error.isEmpty()) {
            this.error.setVisibility(VISIBLE);
            this.error.setText(error);
        } else {
            this.error.setVisibility(GONE);
        }
    }

    public void setLabel() {
        label.setText(hint);
    }

    public void showLabel(boolean isShow) {
        if(isShow){
            label.setVisibility(View.VISIBLE);
            label.setText(hint);
        }
        else{
            label.setVisibility(View.INVISIBLE);
        }
    }

    public void setIsMaxDateEnable(boolean set){
        isMaxDateEnable = set;
    }

    private boolean isPostDate;
    public void enablePostDate(boolean set){
        isPostDate = set;
    }

    private void initDateDialog() {
        final TextView theTitle;
        final Button btnYes;
        final Button btnNo;
        final TextView cancelBtn;
        final LinearLayout closeBg;
        final DatePicker _1_dtpi_11;

        theTitle = (TextView) dateDialog.findViewById(R.id.view_dialog_date_header_text);
        cancelBtn = (TextView) dateDialog.findViewById(R.id.View_dialog_date_close);
        closeBg = (LinearLayout) dateDialog.findViewById(R.id.close_bg);
        btnYes = (Button) dateDialog.findViewById(R.id.view_dialog_date_btn_yes);
        btnNo = (Button) dateDialog.findViewById(R.id.view_dialog_date_btn_no);
        _1_dtpi_11 = (DatePicker) dateDialog.findViewById(R.id.view_dialog_date_picker);

        final Calendar maxDates = Calendar.getInstance();
        maxDates.set(Calendar.HOUR_OF_DAY, maxDates.getMaximum(Calendar.HOUR_OF_DAY));
        maxDates.set(Calendar.MINUTE, maxDates.getMaximum(Calendar.MINUTE));
        maxDates.set(Calendar.SECOND, maxDates.getMaximum(Calendar.SECOND));
        maxDates.set(Calendar.MILLISECOND, maxDates.getMaximum(Calendar.MILLISECOND));

        //tanggalnya diset dari TextView
        try {
            Date input = mDateFormat.parse(textview.getText().toString());

            Calendar targetDate = Calendar.getInstance();
            targetDate.setTime(input);

            if(targetDate.after(maxDates)){
                targetDate.set(Calendar.HOUR_OF_DAY, targetDate.getMaximum(Calendar.HOUR_OF_DAY));
                targetDate.set(Calendar.MINUTE, targetDate.getMaximum(Calendar.MINUTE));
                targetDate.set(Calendar.SECOND, targetDate.getMaximum(Calendar.SECOND));
                targetDate.set(Calendar.MILLISECOND, targetDate.getMaximum(Calendar.MILLISECOND));
                //_1_dtpi_11.setMaxDate(targetDate.getTimeInMillis());
            }
            else{
                //_1_dtpi_11.setMaxDate(maxDates.getTimeInMillis());
            }

            _1_dtpi_11.getCalendarView().setDate(input.getTime());

        } catch (Exception e) {
            e.printStackTrace();
            //_1_dtpi_11.setMaxDate(maxDates.getTimeInMillis());

            Calendar todayDates = Calendar.getInstance();
            todayDates.set(Calendar.HOUR_OF_DAY, todayDates.getMinimum(Calendar.HOUR_OF_DAY));
            todayDates.set(Calendar.MINUTE, todayDates.getMinimum(Calendar.MINUTE));
            todayDates.set(Calendar.SECOND, todayDates.getMinimum(Calendar.SECOND));
            todayDates.set(Calendar.MILLISECOND, todayDates.getMinimum(Calendar.MILLISECOND));
            _1_dtpi_11.getCalendarView().setDate(todayDates.getTime().getTime());
        }

        if (!mTitle.isEmpty()) {
            theTitle.setText(mTitle);
            hint = mTitle;
        } else {
            theTitle.setText(APP.getContext().getResources().getString(R.string.choose_date));
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
            }
        });

        closeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(_1_dtpi_11.getYear(), _1_dtpi_11.getMonth(), _1_dtpi_11.getDayOfMonth());
                    if(isPostDate){
                        if(!calendar.getTime().after(maxDates.getTime()) || !isMaxDateEnable) {
                            setDate(calendar.getTime());
                            dateDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Date must not be passed today", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        setDate(calendar.getTime());
                        dateDialog.dismiss();
                    }
                } catch (Exception e) {
                    APP.logError("DateL1 " + e.getMessage());
                } catch (Error e) {
                    APP.logError("DateL1 " + e.getMessage());
                }

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(_1_dtpi_11.getYear(), _1_dtpi_11.getMonth(), _1_dtpi_11.getDayOfMonth());
                    //onNegative(calendar.getTime());
                    dateDialog.dismiss();
                } catch (Exception e) {
                    APP.logError("DateL1 " + e.getMessage());
                } catch (Error e) {
                    APP.logError("DateL1 " + e.getMessage());
                }

            }
        });


        dateDialog.show();
    }

    public final void setDate(Date p_date) {
        if (p_date == null) {
            return;
        }
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
        mCalendar.setTime(p_date);
        textview.setText(mDateFormat.format(p_date));
        textview.setTextColor(getResources().getColor(R.color.grey2));
        setError("");
        label.setText(hint);

        CharSequence before = "Date";
        if (mOnInputChangeListener != null && isEnabled() && !before.equals(mDateFormat.format(p_date))) {
            try {
                mOnInputChangeListener.onChange(this, before, mDateFormat.format(p_date));
            } catch (Exception e) {
                APP.logError("DateL1 " + e.getMessage());
            } catch (Error e) {
                APP.logError("DateL1 " + e.getMessage());
            }
        }

        if (onDateSelected != null) {
            onDateSelected.onPositiveAction(p_date);
        }

    }

    public final void setDate(String p_dateString) {
        if (p_dateString == null || p_dateString.equals("") || p_dateString.equals(" ")) {
            setText(mTitle);
            return;
        }
        setDate(new Date(p_dateString));
    }

    @Override
    public final DateInput setValue(CharSequence p_dateString) {
        if (p_dateString == null || p_dateString.equals("") || p_dateString.equals(" ")) {
//			clear();
        }
        try {
            setDate(mDateFormat.parse(p_dateString.toString()));
            //		setDate(new Date(p_dateString));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public final Date getDate() {
        return mCalendar == null ? null : mCalendar.getTime();
    }

    @Override
    public final String getValue() {
        return textview.getText().toString();
    }

    public final String getDateString() {
        return textview.getText().toString();
    }

    public final String getDateString(String p_pattern) {
        if(p_pattern == null || getDate() == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(p_pattern);
        return StringUtil.checkNullString(sdf.format(getDate()));
    }

    public void setText(String text) {
        textview.setText(text);
        setError("");
    }

    public void setFontSize(int unit, float size) {
        textview.setTextSize(unit, size);
    }


    @Override
    public void setEnabled(boolean p_enabled) {
        if (p_enabled) {
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p_view) {
                    dateL1.setTitle("Choose Date").setPositive("OK").setNegative("Cancel").create().show();
                }
            });
        } else {
            setOnClickListener(null);
        }
    }

    public void setListener(OnDateSelected listener) {
        onDateSelected = listener;
    }

    public interface OnDateSelected {
        public void onPositiveAction(Date p_date);
    }
}
