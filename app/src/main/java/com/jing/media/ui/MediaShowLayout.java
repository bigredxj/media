package com.jing.media.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.R;
import com.jing.media.music.Music;
import com.jing.media.music.MusicService;
import com.jing.media.ui.music.MusicPlayerFragment;
import com.jing.media.utils.ScreenUtils;


public class MediaShowLayout extends LinearLayout {
    private int screenWidth = 1080;
    private MusicService musicService;
    private ExoPlayer player;
    private PlayerView playerView;
    private View rootView;

    @SuppressLint("MissingInflatedId")
    public MediaShowLayout(Context context) {
        super(context);
        View floatView = LayoutInflater.from(context).inflate(
                R.layout.float_desklayout, null);
        musicService=MusicService.getInstance();
        player = new ExoPlayer.Builder(getContext()).build();
        playerView =floatView.findViewById(R.id.float_player_view);
        playerView.setPlayer(player);
        musicService.setPlayer(player);
        musicService.setPlayerView(playerView);
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

        musicService.play(music);

    }



}