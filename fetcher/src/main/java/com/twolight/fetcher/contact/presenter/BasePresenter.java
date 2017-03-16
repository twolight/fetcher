package com.twolight.fetcher.contact.presenter;

/**
 * Created by twolight on 16/7/28.
 */
public abstract class BasePresenter<T>{

    private T mBaseView;

    public BasePresenter(T baseView){
        mBaseView = baseView;
    }


    public T getView(){
        return mBaseView;
    }

    public void detachView(){
        mBaseView = null;
    }
}
