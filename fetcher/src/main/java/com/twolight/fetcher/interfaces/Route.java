package com.twolight.fetcher.interfaces;

/**
 * Created by twolight on 17/3/16.
 */

public interface Route {
    void album();
    void cancel();
    void preview(String path,int position);
    void preview();
    void ok();

    void select(String folderName);
}
