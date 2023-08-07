package com.app.discover.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.discover.R;
import com.app.discover.adapter.VideoAdapter;

public class VideoFragment extends Fragment {

    private String[] videos;
    private Context context;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    public VideoFragment() {
        // Required empty public constructor
    }


    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        init(view);
        if(getArguments() !=null){
            videos = getArguments().getStringArray("VIDEOS");
            if(videos != null){
                updateRecyclerView(context,videos);
            }
        }
        return view;
    }

    private void init(View view){
        videoAdapter = null;
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recycler_view_video);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void updateRecyclerView(Context context,String[] videos){
        videoAdapter = new VideoAdapter(context,videos);
        recyclerView.setAdapter(videoAdapter);
    }

}