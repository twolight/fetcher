package com.twolight.fetcher.interfaces;

import android.widget.ImageView;

/**
 * Created by twolight on 17/3/15.
 */

public interface Setting {
    boolean isSingle();
    void load(ImageView imageView, String path);
    int type();


}
