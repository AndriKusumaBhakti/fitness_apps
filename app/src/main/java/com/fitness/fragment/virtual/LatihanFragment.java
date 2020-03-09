package com.fitness.fragment.virtual;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.adapter.EventListAdapter;
import com.fitness.adapter.JenisTrainingAdapter;
import com.fitness.adapter.SimpleTrainingAdapter;
import com.fitness.adapter.TrainingLogAdapter;
import com.fitness.aplication.APP;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBEvent;
import com.fitness.database.DBEventLog;
import com.fitness.database.DBEventTraining;
import com.fitness.database.DBTraining;
import com.fitness.entities.TrainingEntity;
import com.fitness.fragment.BaseFragment;
import com.fitness.model.EventLogModel;
import com.fitness.model.EventModel;
import com.fitness.model.EventTrainingModel;
import com.fitness.model.SimpleTrainingModel;
import com.fitness.model.TrainingModel;
import com.fitness.util.Constants;
import com.fitness.view.ButtonRegular;
import com.fitness.view.EventDecorator;
import com.fitness.view.HighlightWeekendsDecorator;
import com.fitness.view.OneDayDecorator;
import com.fitness.view.TextViewBold;
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
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

public class LatihanFragment extends BaseFragment implements OnDateSelectedListener, OnMonthChangedListener {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SimpleTrainingAdapter adapter;
    private RecyclerView listVideo;
    private ArrayList<SimpleTrainingModel> data;

    private MaterialCalendarView calendarView;
    private int currentYear;
    private int currentMonth;

    private FloatingActionButton add_timer, add_reminder, add_training;
    private FloatingActionsMenu dashboard_menu;
    private View choiceMonthYear;

    private View transparentBlackBG;

    SimpleDateFormat sdfMonth = new SimpleDateFormat("M");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private RecyclerView list_data, list_data_training;
    private DBEvent event;
    private EventListAdapter adapterEvent;
    private TrainingLogAdapter trainingLogAdapter;

    private DBTraining dbTraining;
    private List<TrainingEntity> listTraining;
    private LinearLayout formReminder, formTraining;
    private DBEventTraining dbEventTraining;
    private DBEventLog dbEventLog;

    public LatihanFragment() {}

    public static LatihanFragment newInstance() {
        return newInstance("","");
    }

    public static LatihanFragment newInstance(String param1, String param2) {
        LatihanFragment fragment = new LatihanFragment();
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
        data = new ArrayList<>();
        for (int i=0; i<3; i++){
            SimpleTrainingModel model = new SimpleTrainingModel();
            model.setStatusClick(false);
            model.setChannel("Andri Kusuma Bhakti");
            model.setDeskripsi("Tenangkan pikiran anda dengan latihan yoga berintensitas tinggi dan durasi kelas yang lebih lama. Kami akan menantang kemampuan anda dengan kombinasi gerakan yang lebih sulit dan menambah pengalaman melalui eksplorasi dan pencarian. Anda akan termotivasi untuk mengembangkan kepercayaan, potensi dan terbuka dengan kemungkinan-kemungkinan baru. Anda akan mengembangkan persepsi dan meningkatkan kedisiplinan anda. Anda akan merasakan tubuh anda lebih kuat, sehat dan lebih fleksibel.");
            model.setTitle("Body Building");
            model.setUrlYoutobe("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
            model.setPlayVideo(false);
            data.add(model);
        }
        dbEventLog = new DBEventLog(getBaseActivity());
        dbTraining = new DBTraining(getBaseActivity());
        dbEventTraining = new DBEventTraining(getBaseActivity());
        listTraining = new ArrayList<>();
        listTraining = dbTraining.getAll();
    }

    @Override
    public void initView(View view) {
        listVideo = (RecyclerView) view.findViewById(R.id.listVideo);
        formReminder = (LinearLayout) view.findViewById(R.id.formReminder);
        formTraining = (LinearLayout) view.findViewById(R.id.formTraining);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendar);
        choiceMonthYear = (View) view.findViewById(R.id.choiceMonthYear);
        transparentBlackBG = (View) view.findViewById(R.id.dashboard_backgrounds);
        dashboard_menu = (FloatingActionsMenu) view.findViewById(R.id.dashboard_menu);
        add_timer = (FloatingActionButton) view.findViewById(R.id.add_timer);
        add_reminder = (FloatingActionButton) view.findViewById(R.id.add_reminder);
        add_training = (FloatingActionButton) view.findViewById(R.id.add_training);
        list_data = (RecyclerView) view.findViewById(R.id.list_data);
        list_data_training = (RecyclerView) view.findViewById(R.id.list_data_training);
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        listVideo.setHasFixedSize(true);
        listVideo.setLayoutManager(mLayoutManager);
        adapter = new SimpleTrainingAdapter(getBaseActivity(), data, new SimpleTrainingAdapter.ClickListener() {
            @Override
            public void onDetail(SimpleTrainingModel model) {

            }
        });
        listVideo.setAdapter(adapter);
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
        dashboard_menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
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
        add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventFragment fragment = new AddEventFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                DashboardActivity dashboard = DashboardActivity.instance;
                dashboard.pushFragmentDashboard(fragment);
            }
        });
        add_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilData();
            }
        });
        add_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity dashboard = DashboardActivity.instance;
                TimerIntervalFragment fragment = new TimerIntervalFragment();
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
        getBaseActivity().setLeftIcon(0);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(false);
        DashboardActivity.instance.setBarView(false);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.label_virtual);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_latihan;
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
        dashboard_menu.collapse();
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
        if (dbEventTraining.getAll().size()>0){
            ArrayList<CalendarDay> dateList = new ArrayList<>();
            for (int i = 0; i<dbEventTraining.getAll().size(); i++){
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL);

                try {
                    Date date = formatter.parse(dbEventTraining.getAll().get(i).getTanggalEvent());
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
            formReminder.setVisibility(View.VISIBLE);
            ArrayList<EventModel> listReminder = new ArrayList<>();
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
                        listReminder.add(model);
                    }
                } catch (Exception e) {
                    APP.log("" + e);
                }
            }
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            list_data.setHasFixedSize(true);
            list_data.setLayoutManager(mLayoutManager);
            adapterEvent = new EventListAdapter(getActivity(), listReminder, new MyAdapterListener());
            list_data.setAdapter(adapterEvent);
            if (listReminder.size()>0){
                formReminder.setVisibility(View.VISIBLE);
            }else{
                formReminder.setVisibility(View.GONE);
            }
        }

        if (dbEventTraining.getAll().size()>0) {
            ArrayList<EventTrainingModel> listTraining = new ArrayList<>();
            for (int i = 0; i < dbEventTraining.getAll().size(); i++) {
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
                try {
                    Date dateStart = formatter.parse(dbEventTraining.getAll().get(i).getTanggalEvent());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateStart);

                    CalendarDay day = CalendarDay.from(calendar);
                    if (date.equals(day)) {
                        EventTrainingModel model = new EventTrainingModel();
                        model.setId(dbEventTraining.getAll().get(i).getId());
                        model.setIdJenisTraining(dbEventTraining.getAll().get(i).getIdJenisTraining());
                        model.setDateEvent(dbEventTraining.getAll().get(i).getTanggalEvent());
                        model.setIdTraining(dbEventTraining.getAll().get(i).getIdTraining());
                        listTraining.add(model);
                    }
                } catch (Exception e) {
                    APP.log("" + e);
                }
            }
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            list_data_training.setHasFixedSize(true);
            list_data_training.setLayoutManager(mLayoutManager);
            trainingLogAdapter = new TrainingLogAdapter(getBaseActivity(), listTraining, new TrainingLogAdapter.ClickListener() {
                @Override
                public void onRemove(int posisi) {
                    APP.log("id : "+posisi);
                    dbEventLog.deleteAll(posisi);
                    dbEventTraining.deleteAll(posisi);
                    trainingLogAdapter.removeList(posisi);
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

                @Override
                public void addData(int id) {
                    tambahData(id);
                }
            });
            list_data_training.setAdapter(trainingLogAdapter);
            if (listTraining.size()>0){
                formTraining.setVisibility(View.VISIBLE);
            }else{
                formTraining.setVisibility(View.GONE);
            }
        }
    }

    private class MyAdapterListener implements EventListAdapter.ClickListener{
        @Override
        public void onRemove(int posisi) {
            APP.log("id : "+posisi);
            event.deleteAllReminder(posisi);
            adapterEvent.removeList(posisi);
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

        @Override
        public void onDetail(EventModel model) {
            SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL2);
            SimpleDateFormat formatter2 = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
            Date dateStart = new Date();
            try {
                dateStart = formatter2.parse(model.getDateEvent());
            }catch (Exception e){}
            getBaseActivity().showAlertDialog(model.getNamaClass(), String.valueOf(formatter.format(dateStart))+" "+model.getJamEvent());
        }
    }

    private void tampilData() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.training_detail);
        ArrayList<TrainingModel> trainingModels = new ArrayList<>();
        if (listTraining.size()>0){
            for (int i = 0; i<listTraining.size(); i++){
                TrainingModel model = new TrainingModel();
                model.setId(listTraining.get(i).getId());
                model.setJenisTraining(listTraining.get(i).getJenisTraining());
                trainingModels.add(model);
            }
        }
        RecyclerView list_data = (RecyclerView) dialog.findViewById(R.id.list_data);
        TextViewBold close = (TextViewBold) dialog.findViewById(R.id.close_x_text);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_data.setHasFixedSize(true);
        list_data.setLayoutManager(mLayoutManager);
        JenisTrainingAdapter trainingAdapter = new JenisTrainingAdapter(getBaseActivity(), trainingModels, new JenisTrainingAdapter.ClickListener() {
            @Override
            public void onCLick(int id, String nameLabel) {
                APP.log("posisi : "+id+", "+nameLabel);
                dialog.dismiss();
                AddTrainingFragment fragment = new AddTrainingFragment();
                Bundle args = new Bundle();
                args.putInt("id", id);
                args.putString("nama_label", nameLabel);
                fragment.setArguments(args);
                DashboardActivity dashboard = DashboardActivity.instance;
                dashboard.pushFragmentDashboard(fragment);
            }
        });
        list_data.setAdapter(trainingAdapter);
        dialog.show();
    }

    private void tambahData(int id) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_data_training);
        TextViewBold close = (TextViewBold) dialog.findViewById(R.id.close_x_text);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        EditText input_weight = (EditText) dialog.findViewById(R.id.input_weight);
        EditText input_reps = (EditText) dialog.findViewById(R.id.input_reps);
        EventLogModel eventLogModel = new EventLogModel();
        eventLogModel.setId(dbEventLog.getDataById());
        eventLogModel.setIdEventTraining(id);
        eventLogModel.setValue_1(input_weight.getText().toString().trim());
        eventLogModel.setValue_2(input_reps.getText().toString().trim());
        ButtonRegular save_btn = (ButtonRegular) dialog.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbEventLog.parse(eventLogModel);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
