package com.jing.media.video;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.video.Video;
import com.jing.media.video.VideoDir;
import lombok.Data;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Data
public class VideoService {
    private String customRootPath="aaa";

    private List<VideoDir> videoDirList;
    private List<Video> videoPlayList;
    private Map<String,List<Video>> mapVideo = new HashMap<>();

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

    public List<VideoDir> listCustomVideoDir(){
        if(videoDirList==null||videoDirList.size()==0) {
            videoDirList=new ArrayList<>();
            File file = new File("/storage/emulated/0/" + customRootPath);
            listFileVideoDir(file, videoDirList);
        }
        return videoDirList;
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

    public List<Video> getVideoPlayList(){
        return videoPlayList;
    }
    public List<Video> listVideo(File file){
        String path = file.getAbsolutePath();
        List<Video> videoList = mapVideo.get(path);
        if(videoList==null||videoList.size()==0) {
            File[] files = file.listFiles();
            videoList = Arrays.stream(files).map(f -> Video.build(f)
            ).collect(Collectors.toList());
            mapVideo.put(path,videoList);
        }
        videoPlayList = videoList;
        return videoList;
    }


}
