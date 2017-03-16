package com.twolight.fetcher.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by twolight on 16/11/21.
 */

public class Entity implements Parcelable {
    protected String name;
    protected String path;
    private String parentName;
    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeString(this.parentName);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    public Entity() {
    }

    protected Entity(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.parentName = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
}
