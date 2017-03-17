package com.twolight.fetcher.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 16/11/16.
 */

public class PreviewAdapter extends PagerAdapter {
    private Context mContext;
    private List<Entity> mData;

    public PreviewAdapter(Context context,
                          List<Entity> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imagePath = mData.get(position).getPath();

        ImageView imageView = new ImageView(mContext);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        imageView.setLayoutParams(new ViewPager.LayoutParams());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Load.getInstance().load(mContext,imageView,imagePath);
        container.addView(imageView);
        return imageView;
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
