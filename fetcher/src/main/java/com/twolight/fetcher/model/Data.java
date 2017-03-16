package com.twolight.fetcher.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twolight on 16/11/28.
 */

public class Data<T> {
    private static Data mInstance = null;


    public static Data getInstance(){
        if(mInstance == null){
            mInstance = new Data();
        }
        return mInstance;
    }

    private Map<String,Folder<T>> mFolders;

    public Data() {
        mFolders = new HashMap<>();
    }

    public void set(Map<String,Folder<T>> folders) {
        mFolders = folders;
    }

    public Map<String, Folder<T>> get() {
        return mFolders;
    }
}
