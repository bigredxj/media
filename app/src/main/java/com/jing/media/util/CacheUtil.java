package com.jing.media.util;


import android.view.View;
import androidx.fragment.app.Fragment;
import com.jing.media.music.MusicService;
import lombok.Data;

@Data
public class CacheUtil {
    private static CacheUtil service= new CacheUtil();
    public static CacheUtil getInstance(){
        return service;
    }
    private View videoHomeView;
    private View videoDirView;
    private View videoPlayView;
    private Fragment videoDirFragment;
    private Fragment videoPlayFragment;
    private Boolean videoHomeLoad =false;
}
