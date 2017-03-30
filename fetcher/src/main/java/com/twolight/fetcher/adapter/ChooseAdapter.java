package com.twolight.fetcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.R;
import com.twolight.fetcher.model.Entity;
import com.twolight.fetcher.model.Video;
import com.twolight.fetcher.util.DuringUtil;

/**
 * Created by twolight on 16/7/13.
 */
public class ChooseAdapter extends BaseRecyclerViewAdapter<Entity> {
    private OnChooseItemListener mOnChooseItemListener;

    public ChooseAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_picture,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final Entity entity = mData.get(position);
        final Holder holder = (Holder)h;

        if (entity instanceof Video) {
            Video video = (Video) entity;
            holder.during.setText(DuringUtil.converthhmmss(video.getLength() / 1000,"\""));
        }
        holder.during.setVisibility(entity instanceof Video ? View.VISIBLE : View.INVISIBLE);

        holder.pictureSelectStatus.setVisibility(Load.getInstance().isSingle() ? View.INVISIBLE : View.VISIBLE);
        holder.pictureSelectStatus.setSelected(entity.isSelected());
        holder.pictureSelectStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChooseItemListener != null) {
                    mOnChooseItemListener.onItemSelect(entity,position);
                }
            }
        });

        Load.getInstance().load(mContext,holder.picture,entity.getPath());
        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChooseItemListener != null) {
                    mOnChooseItemListener.onItemClick(entity,position);
                }
            }
        });
    }

    public OnChooseItemListener getOnChooseItemListener() {
        return mOnChooseItemListener;
    }

    public void setOnChooseItemListener(OnChooseItemListener onChooseItemListener) {
        mOnChooseItemListener = onChooseItemListener;
    }

    public interface OnChooseItemListener{
        void onItemClick(Entity entity,int position);
        void onItemSelect(Entity entity,int position);
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public ImageView pictureSelectStatus;
        public TextView during;
        public Holder(View itemView) {
            super(itemView);

            picture = (ImageView)itemView.findViewById(R.id.item_choose_picture_image);
            pictureSelectStatus = (ImageView)itemView.findViewById(R.id.item_choose_select_status);
            during = (TextView) itemView.findViewById(R.id.item_choose_during);
        }
    }
}
