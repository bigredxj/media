package com.jing.media.video;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.jing.media.R;
import com.jing.media.music.Music;
import com.jing.media.play.PlayService;

import java.util.List;

public class VideoArrayAdapter extends ArrayAdapter<Video> {
    private Context context;
    private PlayService playService;
    public VideoArrayAdapter(Context context, int resource, List<Video> objects) {
        super(context, resource, objects);
        this.context = context;
        playService = PlayService.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Inflate the layout for each item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_item, parent, false);
        }

        // Set up the TextView and Button
        TextView tvItem = (TextView) convertView.findViewById(R.id.video_tv);

        // Get the item
        final Video item = getItem(position);

        // Set the text for the TextView
        tvItem.setText(item.getName());
        tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click here
                // For example, you can add a new item to the list
                // and notify the adapter that the data set has changed
                playService.playVideo(item);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}
