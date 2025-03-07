package com.jing.media.ui.video;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import com.jing.media.R;
import com.jing.media.databinding.FragmentMusicBinding;
import com.jing.media.databinding.FragmentVideoBinding;
import com.jing.media.music.Music;
import com.jing.media.music.MusicArrayAdapter;
import com.jing.media.music.MusicDir;
import com.jing.media.music.MusicService;
import com.jing.media.video.Video;
import com.jing.media.video.VideoArrayAdapter;
import com.jing.media.video.VideoDir;
import com.jing.media.video.VideoService;

import java.util.List;

public class VideoFragment extends Fragment {
    private FragmentVideoBinding binding;
    private VideoDir videoDir;


    private VideoService videoService=VideoService.getInstance();

    public VideoFragment(VideoDir videoDir){
        this.videoDir=videoDir;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        final ListView listView = binding.videoListview;
        List<Video> videoList = listVideo(videoDir);
        VideoArrayAdapter adapter = new VideoArrayAdapter(getActivity(), R.layout.video_item, videoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            // 重写onItemClick方法，用于处理ListView列表项被点击时的操作
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = videoList.get(position);
                playVideo(video);
            }
        });
        return root;
    }

    private List<Video> listVideo(VideoDir videoDir){
        return  videoService.listVideo(videoDir.getFile());
    }

    private void playVideo(Video video){
       videoService.play(video);
    }

}

