package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.contact.view.ChooseView;
import com.twolight.fetcher.model.Data;
import com.twolight.fetcher.model.Entity;
import com.twolight.fetcher.model.Folder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by twolight on 17/3/16.
 */

public class ChoosePresenter extends BasePresenter<ChooseView> {

    public ChoosePresenter(ChooseView baseView) {
        super(baseView);
    }

    public void getFolder(String folderName){
        Map<String,Folder> data = Data.getInstance().get();

        List<Entity> entities = new ArrayList<>();
        if(folderName == null){

            for(Map.Entry<String,Folder> entry : data.entrySet()){
                entities.addAll(entry.getValue().getChildren());
            }
        }else{
            Folder folder = data.get(folderName);
            if(folder != null){
                entities.addAll(folder.getChildren());
            }
        }

        if(getView() != null){
            getView().getFolderComplete(entities);
        }
    }

    public void select(Entity entity){
        if(Load.getInstance().isSingle()){
            entity.setSelected(!entity);
        }
    }

    public void click(Entity entity){
        if(Load.getInstance().isSingle()){
            entity.setSelected(true);
            if(getView() != null){
                getView().preview();
            }
        }
    }
}
