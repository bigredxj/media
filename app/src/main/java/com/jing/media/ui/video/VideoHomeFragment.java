package com.jing.media.ui.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.jing.media.R;
import com.jing.media.databinding.FragmentVideoHomeBinding;
import com.jing.media.utils.CacheUtil;


public class VideoHomeFragment extends Fragment {
    private FragmentVideoHomeBinding binding;
    private View rootView;
    private boolean first =true;
    private CacheUtil cacheUtil = CacheUtil.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentVideoHomeBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();
            cacheUtil.setVideoHomeView(rootView);


        return rootView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // 创建并添加子Fragment到容器中
            FragmentManager fragmentManager = getChildFragmentManager();
            // 或者使用 getActivity().getSupportFragmentManager() 如果你使用的是支持库的Fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //VideoPlayerFragment playerFragment = new VideoPlayerFragment();
            //fragmentTransaction.add(R.id.container_video_player_fragment, playerFragment);
            VideoDirFragment dirFragment = new VideoDirFragment();
            fragmentTransaction.add(R.id.container_video_dir_fragment, dirFragment);
            fragmentTransaction.commit();
            cacheUtil.setVideoHomeLoad(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
