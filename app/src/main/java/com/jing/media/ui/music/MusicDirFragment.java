package com.jing.media.ui.music;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.jing.media.R;
import com.jing.media.databinding.FragmentMusicDirBinding;
import com.jing.media.databinding.FragmentMusicHomeBinding;
import com.jing.media.music.MusicDir;
import com.jing.media.music.MusicDirArrayAdapter;
import com.jing.media.music.MusicService;

import java.util.List;


public class MusicDirFragment extends Fragment {

    private FragmentMusicDirBinding binding;

    private MusicService musicService = MusicService.getInstance();

    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            binding = FragmentMusicDirBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();

            final ListView listView = rootView.findViewById(R.id.music_dir_listview);

            List<MusicDir> musicDirs = listMusicDir();
            MusicDirArrayAdapter adapter = new MusicDirArrayAdapter(getActivity(), R.layout.music_dir_item, musicDirs);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceType")
                @Override
                // 重写onItemClick方法，用于处理ListView列表项被点击时的操作
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MusicDir musicDir = musicDirs.get(position);
                    openFragment(musicDir);
                }
            });
        }
        return rootView;
    }


    private void openFragment(MusicDir musicDir) {
        //Toast.makeText(getContext(), musicDir.getName(), Toast.LENGTH_SHORT).show();
        MusicFragment musicFragment = new MusicFragment(musicDir);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_music_dir_fragment, musicFragment); // 确保你的Activity有一个容器ID为fragment_container的FrameLayout
        transaction.addToBackStack(null); // 添加到返回栈，允许返回操作
        transaction.commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<MusicDir> listMusicDir() {
        return musicService.listCustomMusicDir();
    }
}