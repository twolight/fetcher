package com.twolight.fetcher.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.twolight.fetcher.model.Folder;
import com.twolight.fetcher.model.Image;
import com.twolight.fetcher.model.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;


/**
 * Created by twolight on 16/11/15.
 */

public class ContentResProvider {

    /**
     * 获取系统所有图片
     */
    public static Observable<Map<String,Folder<Image>>> getSystemPicture(final Context context){
        return getMediaImage(context)
                .map(new Func1<Cursor, Map<String,Folder<Image>>>() {
                    @Override
                    public Map<String,Folder<Image>> call(Cursor cursor) {

                        if(cursor == null){
                            return null;
                        }
                        Map<String,Folder<Image>> folders = new HashMap<String, Folder<Image>>();
                        HashMap<String,Folder> dirs = new HashMap<String, Folder>();
                        Folder imageFloder = null;

                        while (cursor.moveToNext()) {
                            //获取图片的路径
                            String path = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Images.Media.DATA));

                            //获取该图片的父路径名
                            File file = new File(path);
                            if(!file.exists()){
                                continue;
                            }

                            File parentFile = file.getParentFile();
                            String parentPath = parentFile.getAbsolutePath();

                            Image systemImage = new Image();
                            systemImage.setPath(path);
                            systemImage.setParentName(parentFile.getName());

                            if(dirs.containsKey(parentPath)){
                                dirs.get(parentPath).getChildren().add(systemImage);
                            }else{
                                List<Image> child = new ArrayList<Image>();
                                child.add(systemImage);
                                // 初始化imageFloder

                                imageFloder = new Folder();
                                imageFloder.setDir(parentPath);
                                imageFloder.setName(parentFile.getName());
                                imageFloder.setFirstImagePath(path);
                                imageFloder.setChildren(child);

                                dirs.put(parentPath,imageFloder);
                                folders.put(parentPath,imageFloder);
                            }
                        }

                        cursor.close();
                        dirs = null;
                        return folders;
                    }
                });
    }



    public static Observable<Map<String,Folder<Video>>> getSystemVideo(final Context context){
        return  getMediaVideo(context)
                .map(new Func1<Cursor, Map<String,Folder<Video>>>() {
                    @Override
                    public Map<String,Folder<Video>> call(Cursor cursor) {
                        Map<String,Folder<Video>> folders = new HashMap<String, Folder<Video>>();
                        if(cursor == null){
                            return null;
                        }
                        Folder imageFloder = null;

                        while (cursor.moveToNext()) {
                            //获取图片的路径

                            String mimeType = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Video.Media.MIME_TYPE));


                            String path = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Video.Media.DATA));

                            //获取该图片的父路径名
                            File file = new File(path);
                            if (!file.exists()) {
                                continue;
                            }
                            File parentFile = file.getParentFile();
                            String parentPath = parentFile.getAbsolutePath();

                            long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                            String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));


                            Video video = new Video();
                            video.setName(name);
                            video.setPath(path);
                            video.setLength(duration);
                            video.setParentName(parentFile.getName());

                            String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                                    MediaStore.Video.Thumbnails.VIDEO_ID};

                            Cursor thumCursor = context.getContentResolver().query(
                                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                                    thumbColumns,
                                    MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id,
                                    null, null);

                            if (thumCursor.moveToFirst()) {
                                video.setThumbnails(thumCursor.getString(thumCursor
                                        .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                            }

                            if (folders.containsKey(parentPath)) {
                                folders.get(parentPath).getChildren().add(video);
                                continue;
                            } else {
                                List<Video> child = new ArrayList<Video>();
                                child.add(video);

                                imageFloder = new Folder();
                                imageFloder.setDir(parentPath);
                                imageFloder.setName(parentFile.getName());
                                imageFloder.setFirstImagePath(path);
                                imageFloder.setChildren(child);

                                folders.put(parentPath, imageFloder);
                            }
                        }
                        cursor.close();
                        return folders;
                    }
                });
    }



    public static Observable<Cursor> getMediaImage(final Context context){
        final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return Observable.just(uri).map(new Func1<Uri,Cursor>(){
            @Override
            public Cursor call(Uri uri) {
                return  context.getContentResolver()
                        .query(uri, null,
                                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                                        MediaStore.Images.Media.MIME_TYPE + "=?",
                                new String[] { "image/jpeg", "image/png"},
                                MediaStore.Images.Media.DATE_MODIFIED);
            }
        });
    }

    public static Observable<Cursor> getMediaVideo(final Context context){
        final Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        return Observable.just(uri).map(new Func1<Uri,Cursor>(){
            @Override
            public Cursor call(Uri uri) {
                return  context.getContentResolver()
                        .query(uri, new String[]{
                                        MediaStore.Video.Media._ID,
                                        MediaStore.Video.Media.MIME_TYPE,
                                        MediaStore.Video.Media.DATA,
                                        MediaStore.Video.Media.DURATION,
                                        MediaStore.Video.Media.DISPLAY_NAME},


                                        MediaStore.Video.Media.MIME_TYPE + "=? or " +
                                        MediaStore.Video.Media.MIME_TYPE + "=? or " +
                                        MediaStore.Video.Media.MIME_TYPE + "=? or " +
                                        MediaStore.Video.Media.MIME_TYPE + "=?",
                                new String[] {"video/mp4","video/3gp","video/wmv","video/3gpp"},
                                MediaStore.Video.Media.DATE_MODIFIED);
            }
        });
    }


//    public static List<File> listImages(File parent){
//        File[] itemPaths = parent.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String filename) {
//                if (filename.endsWith(".jpg")
//                        || filename.endsWith(".png")
//                        || filename.endsWith(".jpeg"))
//                    return true;
//                return false;
//            }
//        });
//
//        return Arrays.asList(itemPaths);
//    }
}
