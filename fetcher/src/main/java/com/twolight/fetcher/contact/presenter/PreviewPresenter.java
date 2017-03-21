package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.contact.view.PreviewView;
import com.twolight.fetcher.model.Data;
import com.twolight.fetcher.model.DataUseCase;
import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 17/3/16.
 */

public class PreviewPresenter extends BasePresenter<PreviewView> {

    public PreviewPresenter(PreviewView baseView) {
        super(baseView);
    }

    public void loadData(int type , String folderName){
        List<Entity> entities = null;
        if (type == 0) {
            entities = DataUseCase.getFolder(folderName);
        }else{
            entities = DataUseCase.getChooses();
        }
        if(getView() != null && entities != null){
            getView().loadDataComplete(entities);
        }
    }

    public void checkSelectStatus(Entity entity){
        if(getView() != null){
            getView().setSelectStatus(entity.isSelected());
        }
    }

    public void checkSubmitStatus(){
        if(getView() != null){
            getView().showSubmitStatus(!Data.getInstance().getSelected().isEmpty());
        }
    }

    public void select(Entity entity){
        Data.getInstance().choose(entity);
        if(getView() != null){
            getView().selectComplete();
        }
        checkSelectStatus(entity);
    }
}
