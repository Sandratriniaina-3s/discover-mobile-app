package com.app.discover.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.discover.R;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private String[] videos;

    private MediaController mediaController;
    private String apiUrl = "https://discover-api.onrender.com/";

    public VideoAdapter(Context context, String[] videos){
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_item_layout,parent,false);

        return new VideoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.video.setOnPreparedListener(mp -> {
            // Start video playback when prepared
            holder.video.start();
        });
        mediaController = new MediaController(context);
        Uri apiUri = Uri.parse(apiUrl);
        String videoPath = videos[position];
        Uri videoUri = Uri.withAppendedPath(apiUri,videoPath);
        Log.d("--------------", String.valueOf(videoUri));
        holder.video.setVideoURI(videoUri);
        holder.video.setMediaController(mediaController);
    }

    @Override
    public int getItemCount() {
        return videos.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private VideoView video;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            video = itemView.findViewById(R.id.video_item);
        }
    }
}
