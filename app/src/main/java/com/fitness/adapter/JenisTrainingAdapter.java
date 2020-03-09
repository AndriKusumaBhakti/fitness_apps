package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.DetailTrainingModel;
import com.fitness.model.TrainingModel;
import com.fitness.view.TextViewLight;

import java.util.ArrayList;

public class JenisTrainingAdapter extends RecyclerView.Adapter<JenisTrainingAdapter.ViewHolder>{
    private ArrayList<TrainingModel> mDataSet;
    private Context mContext;
    private ClickListener listener;

    public JenisTrainingAdapter(Context context, ArrayList<TrainingModel> DataSet, ClickListener listener){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewLight titleCard;
        public LinearLayout linearLayoutTitle;
        public ViewHolder(View v){
            super(v);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
            titleCard = (TextViewLight) v.findViewById(R.id.titleCard);
        }
    }

    @Override
    public JenisTrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_training,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.titleCard.setText(mDataSet.get(position).getJenisTraining());
        holder.linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_black));
                listener.onCLick(mDataSet.get(position).getId(), mDataSet.get(position).getJenisTraining());
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public interface ClickListener {
        void onCLick(int id, String nameLabel);
    }
}