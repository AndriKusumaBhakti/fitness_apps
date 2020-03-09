package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.aplication.APP;
import com.fitness.database.DBEventLog;
import com.fitness.database.DBTraining;
import com.fitness.entities.EventLogEntity;
import com.fitness.model.EventLogModel;
import com.fitness.model.EventTrainingModel;
import com.fitness.util.Constants;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrainingLogAdapter extends RecyclerView.Adapter<TrainingLogAdapter.ViewHolder>{
    private List<EventTrainingModel> mDataSet;
    private Context mContext;
    SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL2);
    SimpleDateFormat formatter2 = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
    ClickListener listener;
    private DBTraining dbTraining;
    private DBEventLog dbEventLog;
    private DetailTrainingLogAdapter adapter;


    public TrainingLogAdapter(Context context, List<EventTrainingModel> DataSet, ClickListener listener){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewBold titleCard;
        public RecyclerView list_data_log;
        public ImageView removeData;
        public LinearLayout addData;
        public ViewHolder(View v){
            super(v);
            titleCard = (TextViewBold) v.findViewById(R.id.titleCard);
            list_data_log = (RecyclerView) v.findViewById(R.id.list_data_log);
            removeData = (ImageView) v.findViewById(R.id.removeData);
            addData = (LinearLayout) v.findViewById(R.id.addData);
        }
    }

    @Override
    public TrainingLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_training_log,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        dbTraining = new DBTraining(mContext);
        dbEventLog = new DBEventLog(mContext);
        ArrayList<EventLogModel> list = new ArrayList<>();
        List<EventLogEntity> data = dbEventLog.getAllById(mDataSet.get(position).getId());
        if (data.size()>0){
            for (int k = 0; k<data.size(); k++){
                EventLogModel model = new EventLogModel();
                model.setId(data.get(k).getId());
                model.setIdEventTraining(data.get(k).getIdEventTraining());
                model.setValue_1(data.get(k).getValue1());
                model.setValue_2(data.get(k).getValue2());
                list.add(model);
            }
        }
        APP.log("data : "+list.size());
        holder.titleCard.setText(String.valueOf(dbTraining.getById(mDataSet.get(position).getIdTraining()).getJenisTraining()));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.list_data_log.setHasFixedSize(true);
        holder.list_data_log.setLayoutManager(mLayoutManager);

        adapter = new DetailTrainingLogAdapter(mContext, list);
        holder.list_data_log.setAdapter(adapter);
        holder.removeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemove(mDataSet.get(position).getId());
            }
        });
        holder.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addData(mDataSet.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public interface ClickListener {
        void onRemove(int posisi);
        void addData(int id);
    }

    public void removeList(int id){
        if (mDataSet.size()>0){
            for (int i=0; i<mDataSet.size(); i++){
                if (mDataSet.get(i).getId() == id){
                    mDataSet.remove(i);
                }
            }
        }
        notifyDataSetChanged();
    }
}