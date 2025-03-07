package com.jing.media.video;

import com.jing.media.music.Music;
import lombok.Data;

import java.io.File;

@Data
public class Video {
    private File file;
    private String path;
    private String name;
    private String format;

    public static Video build(File file){
        Video video = new Video();
        video.setPath(file.getAbsolutePath());
        video.setFile(file);
        video.setName(file.getName());

        String[] arr = file.getName().split("\\.");
        String format = arr[arr.length-1];
        video.setFormat(format);
        return video;
    }
}
