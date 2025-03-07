package com.jing.media.ui.music;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.R;
import com.jing.media.databinding.FragmentMusicBinding;
import com.jing.media.music.*;

import java.io.File;
import java.util.List;

public class MusicFragment extends Fragment {
    private FragmentMusicBinding binding;
    private MusicDir musicDir;


    private MusicService musicService=MusicService.getInstance();

    public MusicFragment(MusicDir musicDir){
        this.musicDir=musicDir;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.musicListview;
        List<Music> musicList = listMusic(musicDir);
        MusicArrayAdapter adapter = new MusicArrayAdapter(getActivity(), R.layout.music_item, musicList);
        listView.setAdapter(adapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            // 重写onItemClick方法，用于处理ListView列表项被点击时的操作
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music music = musicList.get(position);
                playMusic(music);
            }
        });

         */
        return root;
    }

    private List<Music> listMusic(MusicDir musicDir){
        return  musicService.listMusic(musicDir.getFile());
    }

}

