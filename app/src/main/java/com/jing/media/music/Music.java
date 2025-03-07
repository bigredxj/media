package com.jing.media.music;

import lombok.Data;

import java.io.File;

@Data
public class Music {
    private File file;
    private String path;
    private String name;
    private String format;

    public static Music build(File file){
        Music music = new Music();
        music.setPath(file.getAbsolutePath());
        music.setFile(file);
        music.setName(file.getName());

        String[] arr = file.getName().split("\\.");
        String format = arr[arr.length-1];
        music.setFormat(format);
        return music;
    }
}
