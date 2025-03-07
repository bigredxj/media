package com.jing.media.ui.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.R;
import com.jing.media.databinding.FragmentMusicPlayerBinding;
import com.jing.media.music.MusicService;

public class MusicPlayerFragment extends Fragment {
    private FragmentMusicPlayerBinding binding;
    private MusicService musicService=MusicService.getInstance();
    private ExoPlayer player;
    private PlayerView playerView;
    private View rootView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null) {
            binding = FragmentMusicPlayerBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();
        }
        return rootView;

    }
}