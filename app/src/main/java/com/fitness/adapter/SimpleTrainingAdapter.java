package com.fitness.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.RecyclerView;

import com.fitness.R;
import com.fitness.model.SimpleTrainingModel;
import com.fitness.view.ButtonRegular;
import com.fitness.view.TextViewBold;
import com.fitness.view.TextViewRegular;
import com.fitness.view.TextViewSemiBold;

import java.util.ArrayList;

public class SimpleTrainingAdapter extends RecyclerView.Adapter<SimpleTrainingAdapter.ViewHolder>{
    private ArrayList<SimpleTrainingModel> mDataSet;
    private Context mContext;
    ClickListener listener;


    public SimpleTrainingAdapter(Context context, ArrayList<SimpleTrainingModel>  DataSet, ClickListener listener){
        this.mDataSet = DataSet;
        this.mContext = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public VideoView youtube_view;
        public LinearLayout linearLayoutTitle;
        public TextViewBold titleYoutobe;
        public ImageView imageArrow;
        public ViewAnimator select_filter_animator;
        public TextViewRegular deskripsiYoutobe;
        public TextViewSemiBold channelYoutobe;
        public ButtonRegular btn_detail;
        public ImageView imagePlay;
        public ViewHolder(View v){
            super(v);
            youtube_view = (VideoView) v.findViewById(R.id.youtube_view);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
            titleYoutobe = (TextViewBold) v.findViewById(R.id.titleYoutobe);
            imageArrow = (ImageView) v.findViewById(R.id.imageArrow);
            select_filter_animator = (ViewAnimator) v.findViewById(R.id.select_filter_animator);
            deskripsiYoutobe = (TextViewRegular) v.findViewById(R.id.deskripsiYoutobe);
            channelYoutobe = (TextViewSemiBold) v.findViewById(R.id.channelYoutobe);
            btn_detail = (ButtonRegular) v.findViewById(R.id.btn_detail);
            imagePlay = (ImageView) v.findViewById(R.id.imagePlay);
        }
    }

    @Override
    public SimpleTrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_simple_training,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.btn_detail.setVisibility(View.GONE);
        final Animation inAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top);
        final Animation outAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);
        holder.select_filter_animator.setInAnimation(inAnim);
        holder.select_filter_animator.setOutAnimation(outAnim);

        if(!mDataSet.get(position).isStatusClick()) {
            holder.select_filter_animator.setAnimation(outAnim);
            holder.select_filter_animator.setVisibility(View.GONE);
            holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_right));
        }else{
            holder.select_filter_animator.setAnimation(inAnim);
            holder.select_filter_animator.setVisibility(View.VISIBLE);
            holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_arrow_down_red));
        }
        holder.youtube_view.setVideoPath(mDataSet.get(position).getUrlYoutobe());
        if (mDataSet.get(position).isPlayVideo()){
            if (holder.youtube_view.isPlaying()){
                holder.youtube_view.stopPlayback();
                holder.youtube_view.start();
            }else{
                holder.youtube_view.start();
            }
            holder.imagePlay.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_pause));
        }else{
            if (holder.youtube_view.isPlaying()) {
                holder.youtube_view.stopPlayback();
            }
            holder.imagePlay.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_play));
        }
        holder.imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlay(position);
            }
        });
        holder.youtube_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.imagePlay.getVisibility() == View.VISIBLE){
                    holder.imagePlay.setVisibility(View.GONE);
                }else{
                    holder.imagePlay.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.titleYoutobe.setText(mDataSet.get(position).getTitle());
        holder.deskripsiYoutobe.setText(mDataSet.get(position).getDeskripsi());
        holder.channelYoutobe.setText(mDataSet.get(position).getChannel());
        holder.linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDataSet.get(position).isStatusClick()) {
                    holder.select_filter_animator.setAnimation(outAnim);
                    holder.select_filter_animator.setVisibility(View.GONE);
                    holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.arrow_right));
                }else{
                    holder.select_filter_animator.setAnimation(inAnim);
                    holder.select_filter_animator.setVisibility(View.VISIBLE);
                    holder.imageArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_arrow_down_red));
                }
                updateList(position);
            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDetail(mDataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public interface ClickListener {
        void onDetail(SimpleTrainingModel model);
    }

    public void updateList(int id){
        if (mDataSet.size()>0){
            for (int i=0; i<mDataSet.size(); i++){
                if (i == id){
                    if (mDataSet.get(i).isStatusClick()){
                        mDataSet.get(i).setStatusClick(false);
                    }else{
                        mDataSet.get(i).setStatusClick(true);
                    }
                }else{
                    mDataSet.get(i).setStatusClick(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updatePlay(int id){
        if (mDataSet.size()>0){
            for (int i=0; i<mDataSet.size(); i++){
                if (i == id){
                    if (mDataSet.get(i).isPlayVideo()){
                        mDataSet.get(i).setPlayVideo(false);
                    }else{
                        mDataSet.get(i).setPlayVideo(true);
                    }
                }else{
                    mDataSet.get(i).setPlayVideo(false);
                }
            }
        }
        notifyDataSetChanged();
    }
}