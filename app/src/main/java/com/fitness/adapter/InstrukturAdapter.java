package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.aplication.APP;
import com.fitness.util.CircleTransform;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;

public class InstrukturAdapter extends RecyclerView.Adapter<InstrukturAdapter.ViewHolder>{
    private String[][] mDataSet;
    private Context mContext;

    public InstrukturAdapter(Context context, String[][] DataSet){
        this.mDataSet = DataSet;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextViewBold titleCard;
        public ImageView imageArrow;
        public ViewAnimator animator;
        public ImageView fotoProfil;
        public TextViewRegular diskripsiProfile;
        public LinearLayout linearLayoutTitle;
        public ViewHolder(View v){
            super(v);
            titleCard = (TextViewBold) v.findViewById(R.id.titleCard);
            imageArrow = (ImageView) v.findViewById(R.id.imageArrow);
            animator = (ViewAnimator) v.findViewById(R.id.animator);
            fotoProfil = (ImageView) v.findViewById(R.id.fotoProfil);
            diskripsiProfile = (TextViewRegular) v.findViewById(R.id.diskripsiProfile);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
        }
    }

    @Override
    public InstrukturAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_intruktur,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.titleCard.setText(String.valueOf(mDataSet[position][0]));
        holder.diskripsiProfile.setText(String.valueOf(mDataSet[position][1]));

        Glide.with(mContext)
                .load(mDataSet[position][2])
                .centerCrop()
                .fitCenter()
                .dontAnimate()
                .placeholder(R.drawable.icon_flash)
                .error(R.drawable.icon_flash)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleTransform(mContext))
                .into(holder.fotoProfil);

        final Animation inAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top);
        final Animation outAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);

        holder.animator.setInAnimation(inAnim);
        holder.animator.setOutAnimation(outAnim);

        if(!Boolean.parseBoolean(mDataSet[position][3])) {
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
                updateList(position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.length;
    }

    public void updateList(int position){
        if (mDataSet.length>0){
            for (int i=0; i<mDataSet.length; i++){
                if (i == position){
                    if(!Boolean.parseBoolean(mDataSet[i][3])) {
                        mDataSet[i][3] = "true";
                    }else{
                        mDataSet[i][3] = "false";
                    }
                }else{
                    mDataSet[i][3] = "false";
                }
            }
        }
        APP.log("Data : "+mDataSet[position][3] + " posisi"+position);
        notifyDataSetChanged();
    }
}