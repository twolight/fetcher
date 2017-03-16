package com.twolight.fetcher.interfaces;


import com.twolight.fetcher.model.Folder;
import com.twolight.fetcher.model.Image;

import java.util.List;

/**
 * Created by twolight on 16/11/28.
 */

public interface ChooseImageView {
    void choose();
    void choose(int folderPosition);

    /**
     * 预览当前文件夹下的照片
     * @param folderPosition
     * @param position
     */
    void preview(int folderPosition, int position);

    /**
     * 预览全部图片
     * @param position
     */
    void preview(int position);

    /**
     * 预览选中的照片
     */
    void preview();

    /**
     * 选择文件夹
     */
    void folder();


    void select(Image systemImage, boolean select);
    boolean isSelect(Image systemImage);
    List<Image> getSelectedImage();

    List<Folder<Image>> getFolders();


    void submit();
    void cancel();
}
