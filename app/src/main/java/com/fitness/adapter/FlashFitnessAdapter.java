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
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;

import java.util.ArrayList;

public class FlashFitnessAdapter extends RecyclerView.Adapter<FlashFitnessAdapter.ViewHolder>{
    private ArrayList<ModelMaps> mDataSet;
    private Context mContext;
    private boolean isShowSelectProfile;


    public FlashFitnessAdapter(Context context, ArrayList<ModelMaps> DataSet){
        this.mDataSet = DataSet;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewAnimator animator;
        public TextViewRegular labelLocation;
        public ImageView imageArrow;
        public TextViewBold titleCard;
        public LinearLayout linearLayoutTitle;
        public ViewHolder(View v){
            super(v);
            animator = (ViewAnimator) v.findViewById(R.id.select_filter_animator);
            labelLocation = (TextViewRegular) v.findViewById(R.id.labelLocation);
            imageArrow = (ImageView) v.findViewById(R.id.imageArrow);
            titleCard = (TextViewBold) v.findViewById(R.id.titleCard);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
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
        holder.titleCard.setText(mDataSet.get(position).getName());
        holder.labelLocation.setText(mDataSet.get(position).getLokasi());
        holder.linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation inAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top);
                final Animation outAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);

                holder.animator.setInAnimation(inAnim);
                holder.animator.setOutAnimation(outAnim);

                if(isShowSelectProfile) {
                    isShowSelectProfile = false;
                    holder.animator.setAnimation(outAnim);
                    holder.animator.setVisibility(View.GONE);
                    holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_right));
                }else{
                    isShowSelectProfile = true;
                    holder.animator.setAnimation(inAnim);
                    holder.animator.setVisibility(View.VISIBLE);
                    holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_arrow_down_red));
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }
}