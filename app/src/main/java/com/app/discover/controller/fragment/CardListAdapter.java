package com.app.discover.controller.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.discover.R;
import com.app.discover.model.Site;

import java.util.ArrayList;

public class CardListAdapter extends BaseAdapter {
    private ArrayList<Site> sites;
    private LayoutInflater inflater;

    public CardListAdapter(Context context, ArrayList<Site> sites){
        this.sites = sites;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return sites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.site_card,null);
            holder.name = (TextView)view.findViewById(R.id.name);
            holder.location = (TextView)view.findViewById(R.id.location);
            holder.description = (TextView)view.findViewById(R.id.description);
            view.setTag(holder);
        }else{
            holder =(ViewHolder) view.getTag();
        }
        holder.name.setText(sites.get(i).getName());
        holder.description.setText(sites.get(i).getDescription());
        holder.location.setText(sites.get(i).getLocation());
        return view;
    }

    private class ViewHolder{
        TextView name;
        TextView location;
        TextView description;
    }
}
