package com.jing.media.ui.video;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.jing.media.R;
import com.jing.media.databinding.FragmentMusicDirBinding;
import com.jing.media.databinding.FragmentVideoBinding;
import com.jing.media.databinding.FragmentVideoDirBinding;
import com.jing.media.music.MusicDir;
import com.jing.media.music.MusicDirArrayAdapter;
import com.jing.media.music.MusicService;
import com.jing.media.ui.music.MusicFragment;
import com.jing.media.util.CacheUtil;
import com.jing.media.video.VideoDir;
import com.jing.media.video.VideoDirArrayAdapter;
import com.jing.media.video.VideoService;

import java.util.List;

public class VideoDirFragment extends Fragment {

    private FragmentVideoDirBinding binding;
    private CacheUtil cacheUtil = CacheUtil.getInstance();
    private VideoService videoService=VideoService.getInstance();
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentVideoDirBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();
            cacheUtil.setVideoDirView(rootView);
            final ListView listView = rootView.findViewById(R.id.video_dir_listview);

            List<VideoDir> videoDirs = listVideoDir();
            VideoDirArrayAdapter adapter = new VideoDirArrayAdapter(getActivity(), R.layout.video_dir_item, videoDirs);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceType")
                @Override
                // 重写onItemClick方法，用于处理ListView列表项被点击时的操作
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VideoDir dir = videoDirs.get(position);
                    openFragment(dir);
                }
            });

        return rootView;
    }


    private void openFragment(VideoDir videoDir){
        //Toast.makeText(getContext(), musicDir.getName(), Toast.LENGTH_SHORT).show();
        VideoFragment videoFragment = new VideoFragment(videoDir);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.show(musicFragment);
        transaction.replace(R.id.container_video_dir_fragment, videoFragment); // 确保你的Activity有一个容器ID为fragment_container的FrameLayout
        transaction.addToBackStack(null); // 添加到返回栈，允许返回操作
        transaction.commit();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<VideoDir> listVideoDir(){
        return videoService.listCustomVideoDir();
    }
}