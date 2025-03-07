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

    MediaShowLayout desktopLayout;
    public MusicArrayAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        playService = PlayService.getInstance();
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
                playService.playMusic(item);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
