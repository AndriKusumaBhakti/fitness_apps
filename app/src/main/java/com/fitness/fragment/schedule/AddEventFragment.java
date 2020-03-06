package com.fitness.fragment.schedule;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.base.OnActionbarListener;
import com.fitness.database.DBEvent;
import com.fitness.fragment.BaseFragment;
import com.fitness.model.BooleanModel;
import com.fitness.model.EventModel;
import com.fitness.util.Constants;
import com.fitness.view.ButtonRegular;
import com.fitness.view.CustomSpinnerView;
import com.fitness.view.DateInput;
import com.fitness.view.TextViewLight;
import com.fitness.view.TextViewRegular;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEventFragment extends BaseFragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CustomSpinnerView spinner_class;
    private DateInput date_start;
    private TextViewLight dateinput_label;
    private RelativeLayout timeClock;
    private TextViewRegular parent_time_text;
    private final String[] countries = {"Advance Classes", "Body Jam Classes", "Classes Body Balance", "Classes Yoga", "Coreex Classes"};
    private final String[] places = {"Bangkalan", "Kediri", "Blitar", "Sumenep", "Sidoarjo"};
    private final String[] temperatures = {"60MINS", "45MINS", "60MINS", "30MINS", "60MINS"};
    private ButtonRegular save_btn;
    private DBEvent event;

    public AddEventFragment() {}

    public static AddEventFragment newInstance() {
        return newInstance("","");
    }

    public static AddEventFragment newInstance(String param1, String param2) {
        AddEventFragment fragment = new AddEventFragment();
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
        spinner_class = (CustomSpinnerView) view.findViewById(R.id.spinner_class);
        date_start = (DateInput) view.findViewById(R.id.date_start);
        dateinput_label = (TextViewLight) view.findViewById(R.id.dateinput_label);
        timeClock = (RelativeLayout) view.findViewById(R.id.timeClock);
        parent_time_text = (TextViewRegular) view.findViewById(R.id.parent_time_text);
        save_btn = (ButtonRegular) view.findViewById(R.id.save_btn);
        date_start.setHint(getResources().getString(R.string.select_date));
        ArrayList<String> arr = new ArrayList<>();
        arr.add(getActivity().getResources().getString(R.string.select_class));
        for(int i = 0; i < countries.length; i++){
            arr.add(countries[i]);
        }
        spinner_class.setArray(arr);
        spinner_class.setSelection(0);
        Calendar timeNow = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_JAM2);
        parent_time_text.setText(sdf.format(timeNow.getTime()));
    }

    @Override
    public void setUICallbacks() {
        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                getFragmentManager().popBackStack();
            }

            @Override
            public void onRightIconClick() {

            }

            @Override
            public void onRight2IconClick() {

            }
        });
        parent_time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] selectedTime = parent_time_text.getText().toString().split(":");

                int hour = 0;
                int minute = 0;

                try{
                    hour = Integer.parseInt(selectedTime[0]);
                    minute = Integer.parseInt(selectedTime[1]);
                }
                catch(Exception e){

                }

                TimePickerDialog mTimePicker;
                final BooleanModel isShowing = new BooleanModel(true);
                mTimePicker = new TimePickerDialog(getBaseActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (!isShowing.isValue()) {
                            isShowing.setValue(true);
                            return;
                        }
                        isShowing.setValue(false);
                        String selectedTime = "";
                        if (selectedHour < 10) {
                            if (selectedMinute < 10) {
                                selectedTime = "0" + selectedHour + ":0" + selectedMinute;
                            } else {
                                selectedTime = "0" + selectedHour + ":" + selectedMinute;
                            }
                        } else {
                            if (selectedMinute < 10) {
                                selectedTime = selectedHour + ":0" + selectedMinute;
                            } else {
                                selectedTime = selectedHour + ":" + selectedMinute;
                            }
                        }
                        parent_time_text.setText(selectedTime);
                    }
                }, hour, minute, true);
                //mTimePicker.setTitle("Select Start Hour");
                if (!mTimePicker.isShowing()) {
                    mTimePicker.show();
                }
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
                EventModel model = new EventModel();
                model.setId(event.getAllLanguage().size()+1);
                model.setDateEvent(String.valueOf(formatter.format(date_start.getDate())));
                model.setJamEvent(parent_time_text.getText().toString());
                model.setNamaClass(spinner_class.getSelectedItem().toString());
                event.parseEvent(model);
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.back);
        getBaseActivity().setRightIcon2(0);
        getBaseActivity().setRightIcon(0);
        getBaseActivity().showDisplayLogoTitle(false);
        getBaseActivity().changeHomeToolbarBackground(false);
        getBaseActivity().setBarView(true);
        DashboardActivity.instance.setBarView(true);
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.add_new_event);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_add_event;
    }
}
