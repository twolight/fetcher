package com.twolight.fetcher.contact.view;

import com.twolight.fetcher.model.Album;

import java.util.List;

/**
 * Created by twolight on 17/3/16.
 */

public interface AlbumView extends BaseView {
    void initContent(List<Album> albums);
}
