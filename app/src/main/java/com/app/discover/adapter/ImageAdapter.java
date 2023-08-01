package com.app.discover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.discover.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context  context;
    private String[] images;

    private String apiUrl = "http://192.168.56.1:8000";

    public ImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_item_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        String imagePath = images[position];
        Picasso.get().load(apiUrl+imagePath).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.grid_item);
        }
    }
}
