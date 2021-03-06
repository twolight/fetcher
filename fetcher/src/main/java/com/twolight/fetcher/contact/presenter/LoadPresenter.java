package com.twolight.fetcher.contact.presenter;

import android.content.Context;

import com.twolight.fetcher.SettingRepertory;
import com.twolight.fetcher.contact.view.LoadView;
import com.twolight.fetcher.interfaces.Setting;
import com.twolight.fetcher.model.Data;
import com.twolight.fetcher.model.Folder;
import com.twolight.fetcher.model.Image;
import com.twolight.fetcher.model.Video;
import com.twolight.fetcher.util.ContentResProvider;
import com.twolight.fetcher.util.RxUtil;

import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by twolight on 17/3/16.
 */

public class LoadPresenter extends BasePresenter<LoadView> {

    private Subscription mSubscription;

    public LoadPresenter(LoadView baseView) {
        super(baseView);
    }

    public void load(Context context){

        if(SettingRepertory.getInstance().getType() == Setting.Type.IMAGE){
            mSubscription = ContentResProvider.getSystemPicture(context)
                    .compose(RxUtil.<Map<String,Folder<Image>>>normalSchedulers())
                    .subscribe(new Action1<Map<String, Folder<Image>>>() {
                        @Override
                        public void call(Map<String, Folder<Image>> stringFolderMap) {
                            Data.getInstance().set(stringFolderMap);
                            if(getView() != null){
                                getView().loadComplete(true);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if(getView() != null){
                                getView().loadComplete(false);
                            }
                        }
                    });
        }else{
            mSubscription = ContentResProvider.getSystemVideo(context)
                    .compose(RxUtil.<Map<String,Folder<Video>>>normalSchedulers())
                    .subscribe(new Action1<Map<String, Folder<Video>>>() {
                        @Override
                        public void call(Map<String, Folder<Video>> stringFolderMap) {
                            Data.getInstance().set(stringFolderMap);
                            if(getView() != null){
                                getView().loadComplete(true);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if(getView() != null){
                                getView().loadComplete(false);
                            }
                        }
                    });
        }
    }

    public void release(){
        //清除单例
        Data.destroy();
        SettingRepertory.destroy();
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscription.unsubscribe();
    }
}
