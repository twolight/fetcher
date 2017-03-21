package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.Load;
import com.twolight.fetcher.contact.view.SelectView;
import com.twolight.fetcher.model.Data;

/**
 * Created by twolight on 17/3/20.
 */

public class SelectPresenter extends BasePresenter<SelectView> {

    public SelectPresenter(SelectView baseView) {
        super(baseView);
    }

    public void checkSubmitStatus(){
        if(getView() != null && !Load.getInstance().isSingle()){
            getView().showSubmitStatus(!Data.getInstance().getSelected().isEmpty());
        }
    }
}
