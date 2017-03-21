package com.twolight.fetcherdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.twolight.fetcher.adapter.BaseRecyclerViewAdapter;
import com.twolight.fetcher.model.Entity;

/**
 * Created by twolight on 16/7/13.
 */
public class DataAdapter extends BaseRecyclerViewAdapter<Entity> {



    public DataAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_data_list,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final Entity entity = mData.get(position);
        final Holder holder = (Holder)h;

        Glide.with(mContext).load(entity.getPath()).into(holder.picture);
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public Holder(View itemView) {
            super(itemView);
            picture = (ImageView)itemView.findViewById(R.id.item_choose_picture_image);
        }
    }
}
