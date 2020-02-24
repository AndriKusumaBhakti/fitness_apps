package com.fitness.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fitness.R;
import com.fitness.adapter.SpinnerAdapter;
import com.fitness.util.ScreenUtil;

import java.util.ArrayList;

public class CustomSpinnerView extends LinearLayout {

    public ArrayList<String> contentString;
    public int selectedIndex;
    public String title = "";
    protected Dialog spinnerDialog;
    private Context mContext;
    private TextView contentText;
    private SpinnerOnClickListener OnSpinnerChanged;
    private LinearLayout spinnerBorder;
    private ImageView imageViews;
    private TextView spinnerLabel;
    private TextView spinnerError;
    private RelativeLayout relative;
    private boolean setDefaultHeight;
    private boolean setAutomaticClose;
    private String emptyText="";

    public CustomSpinnerView(Context p_context) {
        super(p_context);
        mContext = p_context;
        setDefaultHeight = false;
        setAutomaticClose = true;
    }

    public CustomSpinnerView(Context p_context, AttributeSet p_attrs) {
        super(p_context, p_attrs);
        mContext = p_context;

        setDefaultHeight = false;
        setAutomaticClose = true;

        LayoutInflater.from(p_context).inflate(R.layout.spinner_model, this, true);
        if (isInEditMode() == false) {
            contentText = (TextView) findViewById(R.id.spinner_text_data);
            imageViews = (ImageView) findViewById(R.id.drop_down_icn_1);
            spinnerLabel = (TextView) findViewById(R.id.spinner_label);
            spinnerError = (TextView) findViewById(R.id.spinner_label_error);
            relative = (RelativeLayout) findViewById(R.id.spinner_main_layout);
            spinnerBorder = (LinearLayout) findViewById(R.id.spinner_border);
            selectedIndex = 0;
            contentString = new ArrayList<String>();
            title = "";

            spinnerDialog = new Dialog(p_context, R.style.AppTheme_FullScreenDialog);
            spinnerDialog.setCanceledOnTouchOutside(true);
            spinnerDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            spinnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            spinnerDialog.setContentView(R.layout.spinner_dialog_box);

            setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    initSpinerDialog();

                }
            });
        }
    }

    public void setDefaultHeights(boolean isDefault) {
        setDefaultHeight = isDefault;
    }

    public void setAutoDismis(boolean isDismiss) {
        setAutomaticClose = isDismiss;
    }

    public void dismisDialogs() {
        spinnerDialog.dismiss();
    }

    public void setEmptyText(String txt){
        emptyText = txt;
    }

    private void initSpinerDialog() {
        if(!isEnabled()){
            return;
        }
        final SpinnerAdapter adapter;
        final TextView theTitle;
        final ListView listView;
        final TextView cancelBtn;
        final LinearLayout closeBg;
        final TextView empty;

        listView = (ListView) spinnerDialog.findViewById(R.id._lsvw_11);
        theTitle = (TextView) spinnerDialog.findViewById(R.id._txvw_header);
        cancelBtn = (TextView) spinnerDialog.findViewById(R.id.close_x_text);
        closeBg = (LinearLayout) spinnerDialog.findViewById(R.id.close_bg);
        empty = (TextView) spinnerDialog.findViewById(R.id.spinner_dialog_empty);

        adapter = new SpinnerAdapter(mContext,
                R.layout.spinner_row_layout, contentString);
        listView.setAdapter(adapter);
        if(contentString.size()==0) {
            if(!emptyText.isEmpty()) {
                empty.setText(emptyText);
                empty.setVisibility(VISIBLE);
                listView.setEmptyView(empty);
            }
        } else {
            empty.setVisibility(GONE);
        }
        theTitle.setText(title);//(StringUtil.capitalizeAllString(title));
        //theTitle.setTextColor(getResources().getColor(R.color.news_purple));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("Dialog", "ItemClick " + position);
                if (setAutomaticClose) {
                    setSelection2(position);
                    spinnerLabel.setText(title);
                    spinnerDialog.dismiss();
                } else {
                    if (OnSpinnerChanged != null) {
                        String selectedText = "";
                        if (position < contentString.size()) {
                            selectedText = contentString.get(position);
                        }

                        OnSpinnerChanged.onClick(position, selectedText);
                    }
                }
            }

        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.dismiss();
            }
        });

        closeBg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.dismiss();
            }
        });

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixelsDp = (int) (210 * scale + 0.5f);

        int pixels = pixelsDp;
        if (setDefaultHeight == false) {
            pixels = ScreenUtil.getListViewHeightBasedOnChildrenWithMaxSize(listView, pixelsDp);
        }

        int margins = (int) (10 * scale + 0.5f);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, pixels);
        params.setMargins(0, margins, 0, margins);

        listView.setLayoutParams(params);


        spinnerDialog.show();
    }

    public void setSelection(int selectedPosition) {
        selectedIndex = selectedPosition;

        if (selectedPosition < 0) {
            return;
        } else if (selectedPosition == 0) {
            spinnerLabel.setText("");
            contentText.setText(title);
            contentText.setTextColor(getResources().getColor(R.color.grey1));
            setErrorText("");
            return;
        }

        int selecteds = selectedPosition - 1;

        if (selecteds < contentString.size()) {
            contentText.setText(contentString.get(selecteds));
            contentText.setTextColor(getResources().getColor(R.color.grey2));
            setErrorText("");
        }

        if (setAutomaticClose) {
            if (OnSpinnerChanged != null) {
                String selectedText = "";
                if (selectedPosition < contentString.size()) {
                    selectedText = contentString.get(selecteds);
                    contentText.setTextColor(getResources().getColor(R.color.grey2));
                }
                OnSpinnerChanged.onClick(selectedIndex, selectedText);
            }
        }
    }


    public void setSelection2(int selectedPosition) {

        if (selectedPosition < 0) {
            spinnerLabel.setText("");
            contentText.setText(title);
            contentText.setTextColor(getResources().getColor(R.color.grey1));
            return;
        }

        selectedIndex = selectedPosition + 1;
        spinnerLabel.setText(title);

        if (selectedPosition < contentString.size()) {
            contentText.setText(contentString.get(selectedPosition));
            contentText.setTextColor(getResources().getColor(R.color.grey2));
            setErrorText("");
        }

        if (setAutomaticClose) {
            if (OnSpinnerChanged != null) {
                String selectedText = "";
                if (selectedPosition < contentString.size()) {

                    selectedText = contentString.get(selectedPosition);

                }

                OnSpinnerChanged.onClick(selectedIndex, selectedText);
            }
        }
    }

    public void setSpinnerLabel(String label){
        spinnerLabel.setText(label);
    }

    public void setSpinnerBorderGray(){
        spinnerBorder.setBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_border));
    }

    public void setSpinnerBorderNormal(){
        spinnerBorder.setBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_border));
    }

    public void setArray(ArrayList<String> arrayString) {

        this.title = arrayString.get(0);

        this.contentString = new ArrayList<String>();

        for (int p = 1; p < arrayString.size(); p++) {
            this.contentString.add(arrayString.get(p));
        }

        contentText.setText(arrayString.get(0));

        //spinnerLabel.setText(title);

        setSelection2(-1);
    }

    public int getSelectedItemPosition() {
        return selectedIndex;
    }

    public String getText() {
        return contentText.getText().toString();
    }

    public String getSelectedItem() {
        return contentText.getText().toString();
    }

    public void setErrorText(String text){
        if(!text.isEmpty()) {
            spinnerError.setText(text);
            spinnerError.setVisibility(VISIBLE);
        } else {
            spinnerError.setVisibility(GONE);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(!enabled){
            hideIcon();
        } else {
            showIcon();
        }
    }

    public void hideIcon(){
        imageViews.setVisibility(INVISIBLE);
    }

    public void showIcon(){
        imageViews.setVisibility(VISIBLE);
    }

    public void setListener(SpinnerOnClickListener theListener) {
        OnSpinnerChanged = theListener;
    }

    public interface SpinnerOnClickListener {
        public void onClick(int item, String selectedItem);
    }

}
