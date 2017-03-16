package com.twolight.fetcher.contact.view;

import com.twolight.fetcher.model.Folder;

/**
 * Created by twolight on 17/3/16.
 */

public interface DataView {

    Folder<?> loadFolderComplete(String folderName);

}
