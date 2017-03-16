package com.twolight.fetcher.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by twolight on 16/11/15.
 */

public class Folder<T> implements Parcelable {

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的文件夹路径
     */
    private String dir;


    private String firstImagePath;

    private List<T> children;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.dir);
        dest.writeString(this.firstImagePath);
        dest.writeList(this.children);
    }

    public Folder() {
    }

    protected Folder(Parcel in) {
        this.name = in.readString();
        this.dir = in.readString();
        this.firstImagePath = in.readString();
        this.children = new ArrayList<T>();
        in.readList(this.children, File.class.getClassLoader());
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel source) {
            return new Folder(source);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
}
