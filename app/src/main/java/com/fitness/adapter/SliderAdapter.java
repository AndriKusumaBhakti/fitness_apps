package com.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fitness.R;
import com.fitness.model.ModelMaps;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {

    private final int count;
    private Context context;
    private ArrayList<ModelMaps> dataMaps;
    private final View.OnClickListener listener;

    public SliderAdapter(Context context, ArrayList<ModelMaps> dataMaps, int count, View.OnClickListener listener) {
        this.context = context;
        this.dataMaps = dataMaps;
        this.count = count;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ViewHolder(View v){
            super(v);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public SliderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(context).inflate(R.layout.layout_slider_card,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /*@Override
    public SliderCard onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_slider_card, parent, false);

        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }

        return new SliderCard(view);
    }*/

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(dataMaps.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }

    /*@Override
    public void onBindViewHolder(SliderCard holder, int position) {
//        holder.setContent(content[position % content.length], this.context);
        Glide.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public void onViewRecycled(SliderCard holder) {
        holder.clearContent();
    }*/

    @Override
    public int getItemCount() {
        return count;
    }

}
