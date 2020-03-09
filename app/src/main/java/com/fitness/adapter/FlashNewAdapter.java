package com.fitness.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.activity.DashboardActivity;
import com.fitness.fragment.profile.DetailFlashFitnessFragment;
import com.fitness.model.ModelMaps;
import com.fitness.model.ModelNewMaps;
import com.fitness.util.Constants;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewLight;
import com.fitness.view.TextViewRegular;

import java.util.ArrayList;

public class FlashNewAdapter extends RecyclerView.Adapter<FlashNewAdapter.ViewHolder>{
    private ArrayList<ModelNewMaps> mDataSet;
    private Context mContext;
    private FlashNowAdapter adapter;
    private boolean type;

    public FlashNewAdapter(Context context, ArrayList<ModelNewMaps> DataSet, boolean type){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.type = type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayoutTitle;
        public TextViewBold titleCard;
        public ViewAnimator animator;
        public ImageView imageArrow;
        public RecyclerView listClassNow;
        public ViewHolder(View v){
            super(v);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
            titleCard = (TextViewBold) v.findViewById(R.id.titleCard);
            imageArrow = (ImageView) v.findViewById(R.id.imageArrow);
            animator = (ViewAnimator) v.findViewById(R.id.animator);
            listClassNow = (RecyclerView) v.findViewById(R.id.listClassNow);
        }
    }

    @Override
    public FlashNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        if (type){
            v = LayoutInflater.from(mContext).inflate(R.layout.item_card_class,parent,false);
        }else{
            v = LayoutInflater.from(mContext).inflate(R.layout.item_card_new,parent,false);
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.titleCard.setText(mDataSet.get(position).getNamaEvent());
        final Animation inAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top);
        final Animation outAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);

        holder.animator.setInAnimation(inAnim);
        holder.animator.setOutAnimation(outAnim);

        if(!mDataSet.get(position).isView()) {
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.listClassNow.setHasFixedSize(true);
        holder.listClassNow.setLayoutManager(mLayoutManager);
        adapter = new FlashNowAdapter(mContext, mDataSet.get(position).getDataMaps(), new MyClickAdapter());
        holder.listClassNow.setAdapter(adapter);
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public void updateList(int position){
        if (mDataSet.size()>0){
            for (int i=0; i<mDataSet.size(); i++){
                if (i == position){
                    if(!mDataSet.get(i).isView()) {
                        mDataSet.get(i).setView(true);
                    }else{
                        mDataSet.get(i).setView(false);
                    }
                }else{
                    mDataSet.get(i).setView(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateListSearch(ArrayList<ModelNewMaps> mDataSet){
        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    private class MyClickAdapter implements FlashNowAdapter.ClickListener{
        @Override
        public void onClick(ModelMaps data) {
            DashboardActivity dashboard = DashboardActivity.instance;
            DetailFlashFitnessFragment fragment = new DetailFlashFitnessFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.labelBardetail, data.getNamaEvent());
            bundle.putSerializable(Constants.detailFlash, data);
            fragment.setArguments(bundle);
            dashboard.pushFragmentDashboard(fragment);
        }
    }
}