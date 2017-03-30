package com.twolight.fetcher.contact.presenter;

import com.twolight.fetcher.SettingRepertory;
import com.twolight.fetcher.contact.view.SelectView;
import com.twolight.fetcher.model.Data;

/**
 * Created by twolight on 17/3/20.
 */

public class SelectStatusPresenter extends BasePresenter<SelectView> {

    public SelectStatusPresenter(SelectView baseView) {
        super(baseView);
    }

    public void checkSubmitStatus(){
        if(getView() != null && !SettingRepertory.getInstance().isSingle()){
            getView().showSubmitStatus(!Data.getInstance().getSelected().isEmpty());
        }
    }
}
