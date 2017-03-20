package com.twolight.fetcher.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by twolight on 16/11/28.
 */

public class Data<T extends Entity> {
    private static Data mInstance = null;


    public static Data getInstance(){
        if(mInstance == null){
            mInstance = new Data();
        }
        return mInstance;
    }

    private Map<String,Folder<T>> mFolders;
    private List<Entity> mEntities = new ArrayList<>();

    public Data() {
        mFolders = new HashMap<>();
    }

    public void set(Map<String,Folder<T>> folders) {
        mFolders = folders;
    }

    public Map<String, Folder<T>> get() {
        return mFolders;
    }

    public void choose(Entity entity){
        if(entity.isSelected()){
            mEntities.remove(entity);
        }else{
            mEntities.add(entity);
        }
        entity.setSelected(!entity.isSelected());
    }

    public List<Entity> getSelected(){
        return mEntities;
    }

    public void clear(){
        mEntities.clear();
    }
}
