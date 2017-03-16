package com.twolight.fetcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twolight on 16/8/4.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter{
    protected List<T> mData;
    protected Context mContext;

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void add(List<T> data,boolean refresh){
        if(data != null){
            if(refresh){
                mData.clear();
            }
            if(!data.isEmpty()){
                mData.addAll(data);
            }
        }
    }
    @Override
    public final int getItemCount() {
        return mData.size();
    }
}
