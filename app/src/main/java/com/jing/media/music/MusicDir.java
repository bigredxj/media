package com.jing.media.music;

import lombok.Data;

import java.io.File;

@Data
public class MusicDir {
    private File file;
    private String path;
    private String name;
    private Integer size;

    public static MusicDir build(File file){
        MusicDir dir = new MusicDir();
        dir.setPath(file.getAbsolutePath());
        dir.setFile(file);
        dir.setName(file.getName());
        dir.setSize(file.listFiles().length);


        return dir;
    }
}
