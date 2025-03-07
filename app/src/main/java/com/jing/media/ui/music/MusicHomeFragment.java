package com.jing.media.ui.music;

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
import com.jing.media.databinding.FragmentMusicHomeBinding;


public class MusicHomeFragment extends Fragment {
    private FragmentMusicHomeBinding binding;
    private boolean first =true;
    private View rootView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null) {
            binding = FragmentMusicHomeBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();
        }
        return rootView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // 创建并添加子Fragment到容器中
        if(first) {
            FragmentManager fragmentManager = getChildFragmentManager();
            // 或者使用 getActivity().getSupportFragmentManager() 如果你使用的是支持库的Fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MusicPlayerFragment playerFragment = new MusicPlayerFragment();
            fragmentTransaction.add(R.id.container_music_player_fragment, playerFragment);
            MusicDirFragment dirFragment = new MusicDirFragment();
            fragmentTransaction.add(R.id.container_music_dir_fragment, dirFragment);

            fragmentTransaction.commit();
            first=false;
        }
    }

}
