package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.contact.view.AlbumView;

/**
 * Created by twolight on 17/3/16.
 */

public class AlbumPresenter extends BasePresenter<AlbumView>{

    public AlbumPresenter(AlbumView baseView) {
        super(baseView);
    }

    public void loadAlbumData(){

    }

    public void checkSubmitStatus(){

    }

    public boolean canPreview(){
        return false;
    }

}
