package com.twolight.fetcher.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by twolight on 17/3/17.
 */

public class DataUseCase {

    public static List<Entity> getFolder(String folderName){
        Map<String,Folder> data = Data.getInstance().get();

        List<Entity> entities = new ArrayList<>();
        if(folderName == null){
            for(Map.Entry<String,Folder> entry : data.entrySet()){
                entities.addAll(entry.getValue().getChildren());
            }
        }else{
            Folder folder = data.get(folderName);
            if(folder != null){
                entities.addAll(folder.getChildren());
            }
        }

        return entities;
    }

    public static boolean hasSelected(){
        return !Data.getInstance().getSelected().isEmpty();
    }

    public static List<Entity> getChooses(){
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Data.getInstance().getSelected());
        return entities;
    }

    public static List<Album> getAlbum(){
        List<Album> albums = new ArrayList<>();
        Map<String,Folder> data = Data.getInstance().get();

        int allCount = 0;
        String allName = "全部照片";
        String allImage = null;
        int allSelectCount = 0;


        for(Map.Entry<String,Folder> entry : data.entrySet()){
            Folder folder = entry.getValue();
            List<Entity> entities = Data.getInstance().getSelected();

            int selectCount = 0;
            for(Entity entity : entities){
                if(folder.getName().equals(entity.getParentName())){
                    selectCount++;
                    allSelectCount++;
                }
            }

            allCount = allCount + folder.getChildren().size();

            if(allImage == null){
                allImage = folder.getFirstImagePath();
            }

            Album album = new Album();
            album.setPath(folder.getDir());
            album.setName(folder.getName());
            album.setImage(folder.getFirstImagePath());
            album.setCount(folder.getChildren().size());
            album.setSelectCount(selectCount);

            albums.add(album);
        }

        Album album = new Album();
        album.setPath(null);
        album.setName(allName);
        album.setImage(allImage);
        album.setCount(allCount);
        album.setSelectCount(allSelectCount);
        albums.add(0,album);

        return albums;
    }
}
