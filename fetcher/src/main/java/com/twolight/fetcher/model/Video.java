package com.twolight.fetcher.model;

import android.os.Parcel;

/**
 * Created by twolight on 16/11/21.
 */

public class Video extends Entity {
    private long length;
    private String thumbnails;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.length);
        dest.writeString(this.thumbnails);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        super(in);
        this.length = in.readLong();
        this.thumbnails = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
