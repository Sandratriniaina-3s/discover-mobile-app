package com.app.discover.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.discover.R;
import com.app.discover.adapter.ImageAdapter;


public class PhotoFragment extends Fragment {

    private String[] photos;
    private Context context;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    public PhotoFragment() {
        // Required empty public constructor
    }


    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
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
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        init(view);
        if (getArguments() != null) {
            photos = getArguments().getStringArray("PHOTOS");
            if(photos != null){
                updateRecyclerView(context,photos);
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(View view){
        imageAdapter =null;
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recycler_view_photo);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void updateRecyclerView(Context context, String[] photos){
        imageAdapter = new ImageAdapter(context,photos);
        recyclerView.setAdapter(imageAdapter);
    }
}