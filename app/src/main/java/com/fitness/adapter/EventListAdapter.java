package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.EventModel;
import com.fitness.util.Constants;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{
    private List<EventModel> mDataSet;
    private Context mContext;
    SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_TANGGAL2);
    SimpleDateFormat formatter2 = new SimpleDateFormat(Constants.FORMAT_TANGGAL);
    ClickListener listener;


    public EventListAdapter(Context context, List<EventModel> DataSet, ClickListener listener){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewBold titleCard;
        public TextViewRegular bodyCard;
        public ImageView removeData;
        public ViewHolder(View v){
            super(v);
            titleCard = (TextViewBold) v.findViewById(R.id.titleCard);
            bodyCard = (TextViewRegular) v.findViewById(R.id.bodyCard);
            removeData = (ImageView) v.findViewById(R.id.removeData);
        }
    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_event,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Date dateStart = new Date();
        try {
            dateStart = formatter2.parse(mDataSet.get(position).getDateEvent());
        }catch (Exception e){}
        holder.titleCard.setText(mDataSet.get(position).getNamaClass());
        holder.bodyCard.setText(String.valueOf(formatter.format(dateStart))+" "+mDataSet.get(position).getJamEvent());
        holder.removeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemove(mDataSet.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public interface ClickListener {
        void onRemove(int posisi);
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