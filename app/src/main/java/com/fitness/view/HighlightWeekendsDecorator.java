package com.fitness.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class HighlightWeekendsDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    private final Drawable highlightDrawable;
    private static final int colorBackground = Color.parseColor("#f2f2f3");
    private static final int colorText = Color.parseColor("#9b0000");

    public HighlightWeekendsDecorator() {
        highlightDrawable = new ColorDrawable(colorBackground);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

        //Jika Ingin Hari Sabtu & Minggu sebagai Weekend
        //return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;

        //Jika Ingin Hari Minggu sebagai Weekend
        return weekDay == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        //Jika Ingin Mengubah Warna Background
        view.setBackgroundDrawable(highlightDrawable);
        //Jika Ingin Mengubah Warna Teks
        view.addSpan(new ForegroundColorSpan(colorText));
    }
}
