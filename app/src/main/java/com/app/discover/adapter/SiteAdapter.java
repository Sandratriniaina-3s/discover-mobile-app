package com.app.discover.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.discover.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.discover.controller.activity.DetailActivity;
import com.app.discover.model.Site;
import com.squareup.picasso.Picasso;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {

    private Context context;
    private Site[] sites;
    private String apiUrl = "https://discover-api.onrender.com/";

    public SiteAdapter(Context context, Site[] sites) {
        this.context = context;
        this.sites = sites;
    }

    @NonNull
    @Override
    public SiteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.site_item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteAdapter.ViewHolder holder, int position) {
        Site site = sites[position];

        holder.txSiteName.setText(site.getName());
        holder.txSiteLocation.setText(site.getLocation());
        holder.txSiteDescription.setText(site.getDescription());
        if(site.getThumbnail() == null){
            holder.imgSite.setVisibility(View.GONE);
        }
        else{
            Picasso.get().load(apiUrl+site.getThumbnail()).into(holder.imgSite);
        }
        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("siteId",site.get_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sites.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txSiteName, txSiteLocation, txSiteDescription;
        private ImageView imgSite;
        private Button btnAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txSiteName = itemView.findViewById(R.id.textview_site_name);
            txSiteLocation = itemView.findViewById(R.id.textview_site_location);
            txSiteDescription = itemView.findViewById(R.id.textview_site_description);
            imgSite = itemView.findViewById(R.id.imageview_site);
            btnAction = itemView.findViewById(R.id.button_action_site);
        }
    }
}
