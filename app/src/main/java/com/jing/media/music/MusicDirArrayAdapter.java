package com.jing.media.music;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import com.jing.media.MainActivity;
import com.jing.media.R;

import java.util.List;

public class MusicDirArrayAdapter extends ArrayAdapter<MusicDir> {
    private Context context;
    private WindowManager showWindowManager;
    private WindowManager editWindowManager;
    private WindowManager.LayoutParams showParam;
    private WindowManager.LayoutParams editParam;

    public MusicDirArrayAdapter(Context context, int resource, List<MusicDir> objects) {
        super(context, resource, objects);
        this.context = context;
      //  createShowWindowManager();
       // createEditWindowManager();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Inflate the layout for each item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.music_dir_item, parent, false);
        }

        // Set up the TextView and Button
        TextView name = (TextView) convertView.findViewById(R.id.music_dir_name_tv);
        TextView size = (TextView) convertView.findViewById(R.id.music_dir_size_tv);

        // Get the item
        final MusicDir item = getItem(position);

        // Set the text for the TextView
        name.setText(item.getName());
        size.setText("(共"+item.getSize()+"首)");


        return convertView;
    }


    /**
     * 设置WindowManager
     */
    private void createShowWindowManager() {
        // 取得系统窗体
        showWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // 窗体的布局样式
        showParam = new WindowManager.LayoutParams();
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0+
            showParam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
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

    private void createEditWindowManager() {
        // 取得系统窗体
        editWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // 窗体的布局样式
        editParam = new WindowManager.LayoutParams();
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0+
            editParam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            editParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        //editParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        editParam.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        editParam.gravity = Gravity.TOP | Gravity.LEFT;
        // 设置窗体宽度和高度
        editParam.width = WindowManager.LayoutParams.MATCH_PARENT;
        editParam.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
