package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewSemiBold;

public class ListFAQAdapter extends RecyclerView.Adapter<ListFAQAdapter.ViewHolder>{
    private String[][] mDataSet;
    private Context mContext;


    public ListFAQAdapter(Context context, String[][] DataSet){
        this.mDataSet = DataSet;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewBold noPertanyaan;
        public TextViewBold pertanyaan;
        public TextViewSemiBold jawaban;
        public ViewHolder(View v){
            super(v);
            noPertanyaan = (TextViewBold) v.findViewById(R.id.noPertanyaan);
            pertanyaan = (TextViewBold) v.findViewById(R.id.pertanyaan);
            jawaban = (TextViewSemiBold) v.findViewById(R.id.jawaban);
        }
    }

    @Override
    public ListFAQAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_faq,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.noPertanyaan.setText(String.valueOf((position+1)+". "));
        holder.pertanyaan.setText(String.valueOf(mContext.getResources().getString(R.string.label_tanya)+" : "+mDataSet[position][0]));
        holder.jawaban.setText(String.valueOf(mContext.getResources().getString(R.string.label_jawab)+" : "+mDataSet[position][1]));
    }

    @Override
    public int getItemCount(){
        return mDataSet.length;
    }
}