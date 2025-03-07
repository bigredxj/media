package com.jing.media.music;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.jing.media.R;
import com.jing.media.play.PlayService;
import com.jing.media.ui.play.MediaShowLayout;

import java.util.List;

public class MusicArrayAdapter extends ArrayAdapter<Music> {
    private Context context;
    private PlayService playService;
    private WindowManager showWindowManager;
    private WindowManager.LayoutParams showParam;
    private long startTime;
    // 声明屏幕的宽高
    float x, y;
    MediaShowLayout desktopLayout;
    public MusicArrayAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        playService = PlayService.getInstance();
        createShowWindowManager();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Inflate the layout for each item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.music_item, parent, false);
        }

        // Set up the TextView and Button
        TextView tvItem = (TextView) convertView.findViewById(R.id.music_tv);

        // Get the item
        final Music item = getItem(position);

        // Set the text for the TextView
        tvItem.setText(item.getName());

        tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click here
                // For example, you can add a new item to the list
                // and notify the adapter that the data set has changed
                showPlayLayout(item);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**
     * 显示DesktopLayout
     */
    private void showPlayLayout(Music music) {
        desktopLayout = createMediaShowLayout();

        desktopLayout.play(music);
    }


    /**
     * 设置WindowManager
     */
    private void createShowWindowManager() {
        // 取得系统窗体
        showWindowManager = playService.getShowWindowManager();
        if(showWindowManager == null) {
            showWindowManager = (WindowManager) context.getApplicationContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            playService.setShowWindowManager(showWindowManager);
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
    }



    /**
     * 创建悬浮窗体
     */
    private MediaShowLayout createMediaShowLayout() {
        MediaShowLayout desktopLayout =  playService.getMediaShowLayout();
        if(desktopLayout == null) {
            desktopLayout=new MediaShowLayout(context);
            showWindowManager.addView(desktopLayout, showParam);
            playService.setMediaShowLayout(desktopLayout);
            desktopLayout.setOnTouchListener(new View.OnTouchListener() {
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
        return  desktopLayout;
    }
}
