package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.DetailTrainingModel;
import com.fitness.view.TextViewLight;

import java.util.ArrayList;

public class AddTrainingAdapter extends RecyclerView.Adapter<AddTrainingAdapter.ViewHolder>{
    private ArrayList<DetailTrainingModel> mDataSet;
    private Context mContext;

    public AddTrainingAdapter(Context context, ArrayList<DetailTrainingModel> DataSet){
        this.mDataSet = DataSet;
        this.mContext = context;
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
    public AddTrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_training,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.titleCard.setText(mDataSet.get(position).getTraining());
        if (mDataSet.get(position).isStatus()){
            holder.linearLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_black));
        }else{
            holder.linearLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        holder.linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateList(position);
            }
        });
    }

    private void updateList(int position){
        if (mDataSet.get(position).isStatus()){
            mDataSet.get(position).setStatus(false);
        }else{
            mDataSet.get(position).setStatus(true);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public ArrayList<DetailTrainingModel> getList(){
        ArrayList<DetailTrainingModel> data = new ArrayList<>();
        for (int i=0; i<this.mDataSet.size(); i++){
            if (this.mDataSet.get(i).isStatus()){
                data.add(this.mDataSet.get(i));
            }
        }
        return data;
    }
}