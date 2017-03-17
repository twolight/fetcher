package com.twolight.fetcher.contact.view;

import com.twolight.fetcher.model.Entity;

import java.util.List;

/**
 * Created by twolight on 17/3/16.
 */

public interface PreviewView extends BaseView {
    void showSubmitStatus(boolean show);
    void setSelectStatus(boolean selected);
    void loadDataComplte(List<Entity> entities);
}
