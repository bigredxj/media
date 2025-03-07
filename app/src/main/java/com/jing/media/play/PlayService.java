package com.jing.media.play;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.music.Music;
import com.jing.media.music.MusicService;
import com.jing.media.ui.play.MediaShowLayout;
import com.jing.media.video.Video;
import com.jing.media.video.VideoService;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlayService {
    private Context context;
    private WindowManager showWindowManager;
    private WindowManager.LayoutParams showParam;
    private long startTime;
    // 声明屏幕的宽高
    float x, y;

    private ExoPlayer player;
    private PlayerView playerView;
    private MediaShowLayout mediaShowLayout;


    private MusicService musicService = MusicService.getInstance();
    private VideoService videoService = VideoService.getInstance();

    private static PlayService service = new PlayService();

    public static PlayService getInstance() {
        return service;
    }

    public PlayService() {

    }


    public void playMusic(Music music) {
        createMediaShowLayout();
        List<Music> musicList = musicService.getMusicPlayList();
        player.clearMediaItems();
        player.addMediaItem(buildMusicItem(music));
        if (musicList != null) {
            List<MediaItem> items = musicList.stream()
                    .map(m -> buildMusicItem(m)).collect(Collectors.toList());
            player.addMediaItems(items);
        }
        player.prepare();
        player.setPlayWhenReady(true);
    }

    public MediaItem buildMusicItem(Music music) {
        MediaItem item = new MediaItem.Builder()
                //.setMediaId(music.getName())
                .setTag(music.getName())
                .setUri(music.getPath())
                .build();
        return item;
    }

    public void playVideo(Video video){
        createMediaShowLayout();
        List<Video> videoList = videoService.getVideoPlayList();
        player.clearMediaItems();
        player.addMediaItem(buildVideoItem(video));
        if(videoList!=null){
            List<MediaItem> items =  videoList.stream()
                    .map(m->buildVideoItem(m)).collect(Collectors.toList());
            player.addMediaItems(items);
        }

        player.prepare();
        player.setPlayWhenReady(true);
        //player.play();
    }

    public MediaItem buildVideoItem(Video video){
        MediaItem item =  new MediaItem.Builder()
                //.setMediaId(music.getName())
                .setTag(video.getName())
                .setUri(video.getPath())
                .build();
        return item;
    }

    /**
     * 设置WindowManager
     */
    private void createShowWindowManager(Context context) {
        // 取得系统窗体

        showWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        // 窗体的布局样式
        showParam = new WindowManager.LayoutParams();
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0+
            showParam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            showParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        showParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        showParam.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        showParam.gravity = Gravity.TOP | Gravity.LEFT;
        // 设置窗体宽度和高度
        showParam.width = WindowManager.LayoutParams.WRAP_CONTENT;
        showParam.height = WindowManager.LayoutParams.WRAP_CONTENT;

    }


    /**
     * 创建悬浮窗体
     */
    private void createMediaShowLayout() {
        if (mediaShowLayout == null) {
            createShowWindowManager(context);
            mediaShowLayout = new MediaShowLayout(context);
            showWindowManager.addView(mediaShowLayout, showParam);

            mediaShowLayout.setOnTouchListener(new View.OnTouchListener() {
                float mTouchStartX;
                float mTouchStartY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 获取相对屏幕的坐标，即以屏幕左上角为原点
                    x = event.getRawX();
                    y = event.getRawY();
                    Log.i("startP", "startX" + mTouchStartX + "====startY"
                            + mTouchStartY);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // 获取相对View的坐标，即以此View左上角为原点
                            mTouchStartX = event.getX();
                            mTouchStartY = event.getY();
                            Log.i("startP", "startX" + mTouchStartX + "====startY"
                                    + mTouchStartY);
                            long end = System.currentTimeMillis() - startTime;
                            // 双击的间隔在 300ms以下
                            if (end < 500) {
                                showWindowManager.removeView(v);
                            }
                            startTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // 更新浮动窗口位置参数
                            showParam.x = (int) (x - mTouchStartX);
                            showParam.y = (int) (y - mTouchStartY);
                            showWindowManager.updateViewLayout(v, showParam);
                            break;

                    }
                    return true;
                }
            });
        }
    }
}
