package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.contact.view.AlbumView;
import com.twolight.fetcher.model.Data;
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

    public void checkSubmitStatus(){
        if(getView() != null){
            getView().showSubmitStatus(!Data.getInstance().getSeleccted().isEmpty());
        }
    }

    public boolean canPreview(){
        return !Data.getInstance().getSeleccted().isEmpty();
    }

}
