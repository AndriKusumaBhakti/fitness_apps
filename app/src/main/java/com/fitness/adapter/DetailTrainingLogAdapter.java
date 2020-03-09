package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.EventLogModel;
import com.fitness.model.TrainingModel;
import com.fitness.view.TextViewLight;

import java.util.ArrayList;

public class DetailTrainingLogAdapter extends RecyclerView.Adapter<DetailTrainingLogAdapter.ViewHolder>{
    private ArrayList<EventLogModel> mDataSet;
    private Context mContext;

    public DetailTrainingLogAdapter(Context context, ArrayList<EventLogModel> DataSet){
        this.mDataSet = DataSet;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewLight titleCard;
        public TextViewLight bodyCard;
        public LinearLayout linearLayoutTitle;
        public ViewHolder(View v){
            super(v);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
            titleCard = (TextViewLight) v.findViewById(R.id.titleCard);
            bodyCard = (TextViewLight) v.findViewById(R.id.bodyCard);
        }
    }

    @Override
    public DetailTrainingLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_detail_training_log,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.titleCard.setText(mDataSet.get(position).getValue_1());
        holder.bodyCard.setText(mDataSet.get(position).getValue_2());
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }
}