package com.jing.media.music;

import lombok.Data;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Data
public class MusicService {
    private String customRootPath="aaa";

    private List<Music> musicPlayList;
    private List<MusicDir> musicDirList;

    private Map<String,List<Music>> mapMusic = new HashMap<>();

    private HashSet<String> formatSet = new HashSet<>();
    {
        formatSet.add("mp3");
        formatSet.add("ape");
        formatSet.add("flac");
        formatSet.add("wav");
    }

    private static MusicService service= new MusicService();
    public static MusicService getInstance(){
        return service;
    }

    public String test(){
        File file = new File("/storage/emulated/0/"+customRootPath);
        return file.getAbsolutePath();

    }
    public List<MusicDir> listCustomMusicDir(){
        if(musicDirList==null||musicDirList.size()==0) {
            musicDirList = new ArrayList<>();
            File file = new File("/storage/emulated/0/" + customRootPath);
            listFileMusicDir(file, musicDirList);
        }
        return musicDirList;
    }

    public void  listFileMusicDir(File file,List<MusicDir> list){
        if(file.isDirectory()){
            if(containsMusic(file)){
                list.add(MusicDir.build(file));
            }
            File[] files = file.listFiles();
            Arrays.stream(files).forEach(f->{
                if(f.isDirectory()){
                    listFileMusicDir(f,list);
                }
            });
        }
    }

    private boolean containsMusic(File file){
        boolean b = false;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                if(!f.isDirectory()){
                    if(isMusicFile(f.getName())){
                        b=true;
                        break;
                    }

                }
            }
        }
        return b;
    }

    public boolean isMusicFile(String name){
        boolean b = false;
        String[] arr = name.split("\\.");
        String format = arr[arr.length-1];
        if(formatSet.contains(format)){
            b=true;
        }
        return b;
    }

    public List<Music> getMusicPlayList(){
        return musicPlayList;
    }

    public List<Music> listMusic(File file){
        String path = file.getAbsolutePath();
        List<Music> musicList = mapMusic.get(path);
        if(musicList==null||musicList.size()==0) {
            File[] files = file.listFiles();
            musicList = Arrays.stream(files).map(f -> Music.build(f)
            ).collect(Collectors.toList());
            mapMusic.put(path,musicList);
        }
        musicPlayList = musicList;
        return musicList;
    }




}
