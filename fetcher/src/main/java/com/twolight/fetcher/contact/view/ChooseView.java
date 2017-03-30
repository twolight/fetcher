package com.twolight.fetcher.contact.view;

import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 17/3/16.
 */

public interface ChooseView extends BaseView{
    void getFolderComplete(List<Entity> entities,boolean single);
    void onSelectComplete(Entity entity,int position);
    void preview(int position);
}
