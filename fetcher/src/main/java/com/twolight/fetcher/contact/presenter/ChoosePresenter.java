package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.contact.view.ChooseView;
import com.twolight.fetcher.model.Data;
import com.twolight.fetcher.model.DataUseCase;
import com.twolight.fetcher.model.Entity;

/**
 * Created by twolight on 17/3/16.
 */

public class ChoosePresenter extends BasePresenter<ChooseView> {

    public ChoosePresenter(ChooseView baseView) {
        super(baseView);
    }

    public void getEntityByFolder(String folderName){
        if(getView() != null){
            getView().getFolderComplete(DataUseCase.getFolder(folderName));
        }
    }

    public void selectItem(Entity entity, int position){
        if(!Load.getInstance().isSingle()){
            Data.getInstance().choose(entity);
            if(getView() != null){
                getView().onSelectComplete(entity,position);
            }
        }
    }

    public void clickItem(Entity entity, int position){
        if(Load.getInstance().isSingle()){
            Data.getInstance().choose(entity);
            if(getView() != null){
                getView().preview(0);
            }
        }else{
            if(getView() != null){
                getView().preview(position);
            }
        }
    }
}
