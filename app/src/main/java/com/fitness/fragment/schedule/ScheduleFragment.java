package com.fitness.fragment.schedule;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.EventListAdapter;
import com.fitness.aplication.APP;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBEvent;
import com.fitness.fragment.BaseFragment;
import com.fitness.model.EventModel;
import com.fitness.util.Constants;
import com.fitness.view.EventDecorator;
import com.fitness.view.HighlightWeekendsDecorator;
import com.fitness.view.OneDayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

public class ScheduleFragment extends BaseFragment implements OnDateSelectedListener, OnMonthChangedListener {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MaterialCalendarView calendarView;
    private int currentYear;
    private int currentMonth;

    private FloatingActionButton addEventButton;
    private FloatingActionsMenu dashboardAction;
    private View choiceMonthYear;

    private View transparentBlackBG;

    SimpleDateFormat sdfMonth = new SimpleDateFormat("M");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private RecyclerView list_data;
    private DBEvent event;
    private EventListAdapter adapter;

    public ScheduleFragment() {}

    public static ScheduleFragment newInstance() {
        return newInstance("","");
    }

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = new DBEvent(getBaseActivity());
    }

    @Override
    public void initView(View view) {
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendar);
        choiceMonthYear = (View) view.findViewById(R.id.choiceMonthYear);
        transparentBlackBG = (View) view.findViewById(R.id.dashboard_backgrounds);
        dashboardAction = (FloatingActionsMenu) view.findViewById(R.id.dashboard_actions);
        addEventButton = (FloatingActionButton) view.findViewById(R.id.add_new_event);
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        transparentBlackBG.setVisibility(View.GONE);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.addDecorators(
                new HighlightWeekendsDecorator(),
                new OneDayDecorator()
        );

        currentMonth = Integer.valueOf(sdfMonth.format(calendarView.getCurrentDate().getDate())) - 2;
        currentYear = Integer.valueOf(sdfYear.format(calendarView.getCurrentDate().getDate()));

        CalendarDay day = CalendarDay.from(currentYear, currentMonth, 1);
        CalendarDay day2 = CalendarDay.from(currentYear, currentMonth+1, Integer.valueOf(sdfDay.format(new Date())));
        reloadCalendarView(day);
        getData(day2);
        APP.log("Date : "+day2);
        getEventBymonth();
    }

    @Override
    public void setUICallbacks() {
        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {

            }

            @Override
            public void onRightIconClick() {

            }

            @Override
            public void onRight2IconClick() {

            }
        });
        dashboardAction.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                transparentBlackBG.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                transparentBlackBG.setVisibility(View.GONE);
            }
        });
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventFragment fragment = new AddEventFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                DashboardActivity dashboard = DashboardActivity.instance;
                dashboard.pushFragmentDashboard(fragment);
            }
        });
        choiceMonthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getBaseActivity(), new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        currentMonth = Integer.valueOf(sdfMonth.format(calendarView.getCurrentDate().getDate())) - 2;
                        CalendarDay day = CalendarDay.from(selectedYear, currentMonth, 1);
                        reloadCalendarView(day);
                    }
                }, currentYear, 0);
                builder.showYearOnly()
                        .setYearRange(currentYear-100, currentYear+100)
                        .build()
                        .show();
            }
        });
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.icon_profil);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(true);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_schedule);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_schedule;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        APP.log("Date : "+date);
        getData(date);
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        currentMonth = Integer.valueOf(sdfMonth.format(date.getDate()));
    }

    private void reloadCalendarView(CalendarDay day){
        calendarView.setCurrentDate(day);
        calendarView.goToNext();
    }

    @Override
    public void onResume() {
        super.onResume();
        DashboardActivity.instance.showBottomMenu();
        dashboardAction.collapse();
    }

    private void getEventBymonth(){
        if (event.getAllLanguage().size()>0){
            ArrayList<CalendarDay> dateList = new ArrayList<>();
            for (int i = 0; i<event.getAllLanguage().size(); i++){
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL);

                try {
                    Date date = formatter.parse(event.getAllLanguage().get(i).getTanggalEvent());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    CalendarDay day = CalendarDay.from(calendar);
                    dateList.add(day);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            calendarView.addDecorator(new EventDecorator(dateList));
        }
    }

    private void getData(CalendarDay date){
        if (event.getAllLanguage().size()>0) {
            ArrayList<EventModel> list = new ArrayList<>();
            for (int i = 0; i < event.getAllLanguage().size(); i++) {
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
                try {
                    Date dateStart = formatter.parse(event.getAllLanguage().get(i).getTanggalEvent());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateStart);

                    CalendarDay day = CalendarDay.from(calendar);
                    if (date.equals(day)) {
                        EventModel model = new EventModel();
                        model.setId(event.getAllLanguage().get(i).getId());
                        model.setNamaClass(String.valueOf(event.getAllLanguage().get(i).getNamaClass()));
                        model.setJamEvent(String.valueOf(event.getAllLanguage().get(i).getJamEvent()));
                        model.setDateEvent(String.valueOf(event.getAllLanguage().get(i).getTanggalEvent()));
                        APP.log("Data Schedule: " + event.getAllLanguage().get(i).getTanggalEvent());
                        list.add(model);
                    }
                } catch (Exception e) {
                    APP.log("" + e);
                }
            }
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            list_data.setHasFixedSize(true);
            list_data.setLayoutManager(mLayoutManager);
            adapter = new EventListAdapter(getActivity(), list, new MyAdapterListener());
            list_data.setAdapter(adapter);
        }
    }

    private class MyAdapterListener implements EventListAdapter.ClickListener{
        @Override
        public void onRemove(int posisi) {
            APP.log("id : "+posisi);
            event.deleteAllReminder(posisi);
            adapter.removeList(posisi);
            calendarView.invalidate();
            calendarView.removeDecorators();
            calendarView.state().edit()
                    .setFirstDayOfWeek(Calendar.SUNDAY)
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();

            calendarView.addDecorators(
                    new HighlightWeekendsDecorator(),
                    new OneDayDecorator()
            );
            getEventBymonth();
        }
    }
}
