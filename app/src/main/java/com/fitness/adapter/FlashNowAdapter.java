package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.ModelMaps;
import com.fitness.view.ButtonRegular;
import com.fitness.view.TextViewLight;
import com.fitness.view.TextViewRegular;

import java.util.ArrayList;

public class FlashNowAdapter extends RecyclerView.Adapter<FlashNowAdapter.ViewHolder>{
    private ArrayList<ModelMaps> mDataSet;
    private Context mContext;
    private ClickListener listener;

    public FlashNowAdapter(Context context, ArrayList<ModelMaps> DataSet, ClickListener listener){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewLight titleCard;
        public TextViewRegular timeClass;
        public ButtonRegular btn_detail;
        public ViewHolder(View v){
            super(v);
            titleCard = (TextViewLight) v.findViewById(R.id.titleCard);
            timeClass = (TextViewRegular) v.findViewById(R.id.timeClass);
            btn_detail = (ButtonRegular) v.findViewById(R.id.btn_detail);
        }
    }

    @Override
    public FlashNowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_now,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.timeClass.setText(mDataSet.get(position).getJamStart()+"-"+mDataSet.get(position).getJamEnd());
        holder.titleCard.setText(mDataSet.get(position).getNamaEvent());
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(mDataSet.get(position));
            }
        });
    }

    public interface ClickListener {
        void onClick(ModelMaps data);
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }


}