package com.twolight.fetcher;

import android.content.Context;
import android.widget.ImageView;

import com.twolight.fetcher.interfaces.Setting;

/**
 * Created by twolight on 17/3/16.
 */

public class Load {
    private static Load mInstance;

    public static Load getInstance(){
        if(mInstance == null){
            mInstance = new Load();
        }
        return mInstance;
    }

    private Setting mSetting;

    public void init(Setting setting){
        mSetting = setting;
    }

    public void load(Context context, ImageView imageView, String path){
        mSetting.load(context,imageView,path);
    }

    public boolean isSingle(){
        return mSetting.isSingle();
    }

    public int getType(){
        return mSetting.type();
    }

    public int resultCode(){
        return mSetting.resultCode();
    }


}
