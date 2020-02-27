package com.fitness.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.fragment.profile.ChangeLanguageFragment;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewLight;
import com.fitness.view.TextViewSemiBold;

public class MenuFlashAdapter extends RecyclerView.Adapter<MenuFlashAdapter.ViewHolder>{
    private String[] mDataSet;
    private Integer[] mThumbIds;
    private Context mContext;
    ClickListener listener;


    public MenuFlashAdapter(Context context, String[] mDataSet, Integer[] mThumbIds, ClickListener listener){
        this.mDataSet = mDataSet;
        this.mContext = context;
        this.mThumbIds = mThumbIds;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewSemiBold titileMenu;
        public ImageView imageIcon;
        public ViewHolder(View v){
            super(v);
            titileMenu = (TextViewSemiBold) v.findViewById(R.id.titileMenu);
            imageIcon = (ImageView) v.findViewById(R.id.imageIcon);
        }
    }

    @Override
    public MenuFlashAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_menu,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.titileMenu.setText(mDataSet[position]);
        holder.imageIcon.setImageResource(mThumbIds[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCLick(position, mDataSet[position]);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.length;
    }

    public interface ClickListener {
        void onCLick(int position, String nameLabel);
    }
}