package com.app.discover.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.discover.R;
import com.app.discover.controller.activity.DetailActivity;
import com.app.discover.model.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private Context context;

    private Notification[] notifications;

    public NotificationAdapter(Context context, Notification[] notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    private String apiUrl =  "https://discover-api.onrender.com/";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications[position];
        holder.txtNotificationDate.setText(dateFormatter(notification.getCreatedAt()));
        holder.txtNotificationContent.setText(notification.getContent());
        holder.txtNotificationContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("siteId",notification.getSiteId());
                context.startActivity(intent);
            }
        });
    }
    private String dateFormatter(String date){

        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            Date javaDate = isoDateFormat.parse(date);
            SimpleDateFormat frenchDateFormat = new SimpleDateFormat("EE dd MMMM yyyy", Locale.FRENCH);
            String frenchFormattedDate = frenchDateFormat.format(javaDate);

            return frenchFormattedDate;

        } catch (ParseException e) {

        }

        return null;

    }


    @Override
    public int getItemCount() {
        return notifications.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNotificationDate, txtNotificationContent;
        private LinearLayout notificationLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNotificationContent = itemView.findViewById(R.id.textview_notification_content);
            txtNotificationDate = itemView.findViewById(R.id.textview_notification_date);
            notificationLayout = itemView.findViewById(R.id.notification_layout);
        }
    }
}
