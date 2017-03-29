package com.twolight.fetcher.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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

import java.util.List;

/**
 * Created by twolight on 16/11/16.
 */

public class PreviewAdapter extends PagerAdapter {
    private Context mContext;
    private List<Entity> mData;
    private LayoutInflater mLayoutInflater;

    public PreviewAdapter(Context context,
                          List<Entity> data) {
        mContext = context;
        mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Entity entity = mData.get(position);
        String imagePath = entity.getPath();
        View view = mLayoutInflater.inflate(R.layout.item_preview_item,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.preview_cover);
        Load.getInstance().load(mContext,imageView,imagePath);

        if(entity instanceof Video){
            Video video = (Video) entity;
            TextView textView = (TextView)view.findViewById(R.id.preview_during);
            textView.setText(DuringUtil.converthhmmss(video.getLength() / 1000,"\""));
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
