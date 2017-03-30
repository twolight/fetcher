package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.SettingRepertory;
import com.twolight.fetcher.contact.view.AlbumView;
import com.twolight.fetcher.model.DataUseCase;

/**
 * Created by twolight on 17/3/16.
 */

public class AlbumPresenter extends BasePresenter<AlbumView>{

    public AlbumPresenter(AlbumView baseView) {
        super(baseView);
    }

    public void loadAlbumData(){
        if(getView() != null){
            getView().initContent(DataUseCase.getAlbum());
        }
    }

    public void loadBottom(){
        if(getView() != null){
            getView().initBottom(SettingRepertory.getInstance().isSingle());
        }
    }
}
