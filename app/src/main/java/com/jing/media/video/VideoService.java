package com.jing.media.video;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.video.Video;
import com.jing.media.video.VideoDir;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class VideoService {

    private ExoPlayer player;
    private PlayerView playerView;
    private String customRootPath="aaa";

    private List<Video> videoList;

    private HashSet<String> formatSet = new HashSet<>();
    {
        formatSet.add("mp4");
        formatSet.add("avi");
        formatSet.add("mkv");
        formatSet.add("wav");
    }

    private static VideoService service= new VideoService();
    public static VideoService getInstance(){
        return service;
    }

    public String test(){
        File file = new File("/storage/emulated/0/"+customRootPath);
        return file.getAbsolutePath();

    }
    public List<VideoDir> listCustomVideoDir(){
        List<VideoDir> videoList = new ArrayList<>();
        File file = new File("/storage/emulated/0/"+customRootPath);
        listFileVideoDir(file,videoList);
        return videoList;
    }

    public void  listFileVideoDir(File file,List<VideoDir> list){
        if(file.isDirectory()){
            if(containsVideo(file)){
                list.add(VideoDir.build(file));
            }
            File[] files = file.listFiles();
            Arrays.stream(files).forEach(f->{
                if(f.isDirectory()){
                    listFileVideoDir(f,list);
                }
            });
        }
    }

    private boolean containsVideo(File file){
        boolean b = false;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                if(!f.isDirectory()){
                    if(isVideoFile(f.getName())){
                        b=true;
                        break;
                    }

                }
            }
        }
        return b;
    }

    public boolean isVideoFile(String name){
        boolean b = false;
        String[] arr = name.split("\\.");
        String format = arr[arr.length-1];
        if(formatSet.contains(format)){
            b=true;
        }
        return b;
    }

    public List<Video> listVideo(File file){
        File[] files = file.listFiles();
        videoList = Arrays.stream(files).map(f-> Video.build(f)
        ).collect(Collectors.toList());

        return videoList;
    }

    public void play(Video video){
        player.clearMediaItems();
        player.addMediaItem(buildMediaItem(video));
        if(videoList!=null){
            List<MediaItem> items =  videoList.stream()
                    .map(m->buildMediaItem(m)).collect(Collectors.toList());
            player.addMediaItems(items);
        }

        player.prepare();
        player.setPlayWhenReady(true);
        //player.play();
    }

    public MediaItem buildMediaItem(Video video){
        MediaItem item =  new MediaItem.Builder()
                //.setMediaId(music.getName())
                .setTag(video.getName())
                .setUri(video.getPath())
                .build();
        return item;
    }
}
