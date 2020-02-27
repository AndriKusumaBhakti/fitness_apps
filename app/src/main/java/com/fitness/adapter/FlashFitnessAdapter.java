package com.fitness.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.ModelMaps;
import com.fitness.view.ButtonRegular;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;
import com.fitness.view.TextViewSemiBold;

import java.util.ArrayList;

public class FlashFitnessAdapter extends RecyclerView.Adapter<FlashFitnessAdapter.ViewHolder>{
    private ArrayList<ModelMaps> mDataSet;
    private Context mContext;
    private ClickListener listener;
    private int menuStatus;

    public FlashFitnessAdapter(Context context, int menuStatus, ArrayList<ModelMaps> DataSet, ClickListener listener){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.listener = listener;
        this.menuStatus = menuStatus;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewAnimator animator;
        public TextViewRegular labelLocation;
        public ImageView imageArrow;
        public TextViewBold titleCard;
        public LinearLayout linearLayoutTitle;
        public ButtonRegular btn_detail;
        public LinearLayout scheduleForm;
        public TextViewRegular scheduleData;
        public ViewHolder(View v){
            super(v);
            animator = (ViewAnimator) v.findViewById(R.id.select_filter_animator);
            labelLocation = (TextViewRegular) v.findViewById(R.id.labelLocation);
            imageArrow = (ImageView) v.findViewById(R.id.imageArrow);
            titleCard = (TextViewBold) v.findViewById(R.id.titleCard);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
            btn_detail = (ButtonRegular) v.findViewById(R.id.btn_detail);
            scheduleForm = (LinearLayout) v.findViewById(R.id.scheduleForm);
            scheduleData = (TextViewRegular) v.findViewById(R.id.scheduleData);
        }
    }

    @Override
    public FlashFitnessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_flash,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        if (menuStatus == 1){
            holder.scheduleForm.setVisibility(View.GONE);
            holder.btn_detail.setVisibility(View.GONE);
            holder.titleCard.setText(mDataSet.get(position).getName());
            holder.labelLocation.setText(mDataSet.get(position).getLokasi());
        }else if (menuStatus == 2){
            holder.scheduleForm.setVisibility(View.VISIBLE);
            holder.btn_detail.setVisibility(View.VISIBLE);
            holder.titleCard.setText(mDataSet.get(position).getName());
            holder.labelLocation.setText(mDataSet.get(position).getLokasi());
            holder.scheduleData.setText(mContext.getResources().getStringArray(R.array.hari)[Integer.parseInt(mDataSet.get(position).getHari())-1]+", "+mDataSet.get(position).getJamStart()+" - "+mDataSet.get(position).getJamEnd());
        }else{
            holder.scheduleForm.setVisibility(View.VISIBLE);
            holder.btn_detail.setVisibility(View.VISIBLE);
            holder.titleCard.setText(mDataSet.get(position).getNamaEvent());
            holder.labelLocation.setText(mDataSet.get(position).getLokasi());
            holder.scheduleData.setText(mContext.getResources().getStringArray(R.array.hari)[Integer.parseInt(mDataSet.get(position).getHari())-1]+", "+mDataSet.get(position).getJamStart()+" - "+mDataSet.get(position).getJamEnd());
        }
        final Animation inAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top);
        final Animation outAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);

        holder.animator.setInAnimation(inAnim);
        holder.animator.setOutAnimation(outAnim);

        if(!mDataSet.get(position).getView()) {
            holder.animator.setAnimation(outAnim);
            holder.animator.setVisibility(View.GONE);
            holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_right));
        }else{
            holder.animator.setAnimation(inAnim);
            holder.animator.setVisibility(View.VISIBLE);
            holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_arrow_down_red));
        }
        holder.linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDataSet.get(position).getView()) {
                    holder.animator.setAnimation(outAnim);
                    holder.animator.setVisibility(View.GONE);
                    holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_right));
                }else{
                    holder.animator.setAnimation(inAnim);
                    holder.animator.setVisibility(View.VISIBLE);
                    holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_arrow_down_red));
                }
                updateList(position);
                if (menuStatus == 1){
                    listener.onClick(position);
                }
            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDetail(mDataSet.get(position));
            }
        });
    }

    public void updateList(int id){
        if (mDataSet.size()>0){
            for (int i=0; i<mDataSet.size(); i++){
                if (i == id){
                    if (mDataSet.get(i).getView()){
                        mDataSet.get(i).setView(false);
                    }else{
                        mDataSet.get(i).setView(true);
                    }
                }else{
                    mDataSet.get(i).setView(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(int posisi);
        void onDetail(ModelMaps modelMaps);
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }


}