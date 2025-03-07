package com.jing.media.video;

import com.jing.media.music.MusicDir;
import lombok.Data;

import java.io.File;

@Data
public class VideoDir {
    private File file;
    private String path;
    private String name;
    private Integer size;

    public static VideoDir build(File file){
        VideoDir dir = new VideoDir();
        dir.setPath(file.getAbsolutePath());
        dir.setFile(file);
        dir.setName(file.getName());
        dir.setSize(file.listFiles().length);
        return dir;
    }
}
