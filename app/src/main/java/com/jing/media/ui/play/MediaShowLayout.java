package com.jing.media.ui.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.R;
import com.jing.media.music.Music;
import com.jing.media.music.MusicService;
import com.jing.media.play.PlayService;
import com.jing.media.utils.ScreenUtils;


public class MediaShowLayout extends LinearLayout {
    private int screenWidth = 1080;
    private PlayService playService;

    @SuppressLint("MissingInflatedId")
    public MediaShowLayout(Context context) {
        super(context);
        View floatView = LayoutInflater.from(context).inflate(
                R.layout.float_desklayout, null);
        playService=PlayService.getInstance();
        ExoPlayer player = new ExoPlayer.Builder(getContext()).build();
        PlayerView playerView =floatView.findViewById(R.id.float_player_view);
        playerView.setPlayer(player);
        playService.setPlayer(player);
        playService.setPlayerView(playerView);
        playerView.setPadding(15, 0, 15, 0);
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.width = screenWidth;
        params.height = screenWidth/2;
        playerView.setLayoutParams(params);
       // MusicPlayerFragment playerFragment = new MusicPlayerFragment();


        screenWidth = ScreenUtils.getScreenWidth(context);
        this.addView(floatView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 屏幕旋转发生时的处理逻辑
    }



    public void play(Music music){
        playService.playMusic(music);

    }



}