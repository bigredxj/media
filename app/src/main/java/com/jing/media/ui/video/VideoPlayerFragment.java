package com.jing.media.ui.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.R;
import com.jing.media.databinding.FragmentVideoPlayerBinding;
import com.jing.media.utils.CacheUtil;
import com.jing.media.video.VideoService;

import static androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL;

public class VideoPlayerFragment extends Fragment {
    private FragmentVideoPlayerBinding binding;
    private View rootView;

    @OptIn(markerClass = UnstableApi.class)
    public View onCreateView(@NonNull LayoutInflater inflater,
                                                                     ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentVideoPlayerBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();


        return rootView;

    }
}