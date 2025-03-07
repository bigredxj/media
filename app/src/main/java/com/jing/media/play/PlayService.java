package com.jing.media.play;

import android.view.WindowManager;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.music.Music;
import com.jing.media.music.MusicService;
import com.jing.media.ui.play.MediaShowLayout;
import com.jing.media.video.VideoService;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlayService {
    private ExoPlayer player;
    private PlayerView playerView;
    private WindowManager showWindowManager;
    private MediaShowLayout mediaShowLayout;

    private MusicService musicService = MusicService.getInstance();
    private VideoService videoService = VideoService.getInstance();

    private static PlayService service= new PlayService();
    public static PlayService getInstance(){
        return service;
    }
    public void playMusic(Music music){
        List<Music> musicList = musicService.getCacheMusicList();
        player.clearMediaItems();
        player.addMediaItem(buildMusicItem(music));
        if(musicList!=null){
            List<MediaItem> items =  musicList.stream()
                    .map(m->buildMusicItem(m)).collect(Collectors.toList());
            player.addMediaItems(items);
        }
        player.prepare();
        player.setPlayWhenReady(true);
    }

    public MediaItem buildMusicItem(Music music){
        MediaItem item =  new MediaItem.Builder()
                //.setMediaId(music.getName())
                .setTag(music.getName())
                .setUri(music.getPath())
                .build();
        return item;
    }
}
