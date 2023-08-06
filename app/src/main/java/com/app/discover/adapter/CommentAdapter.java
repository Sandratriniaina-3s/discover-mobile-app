package com.app.discover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.discover.R;
import com.app.discover.model.Comment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private Comment[] comments;

    public CommentAdapter(Context context, Comment[] comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_item_layout,parent,false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments[position];

        holder.txInital.setText(String.valueOf(comment.getUser().getFullName().charAt(0)));
        holder.txCommentator.setText(comment.getUser().getFullName());
        holder.txValue.setText(comment.getValue());
        holder.txDate.setText(dateFormatter(comment.getDate()));
        /*if(comment.getDate() != null){

            Log.i("aaaa", dateFormatter(comment.getDate()));
        }*/

        /*holder.btnPostComment.setOnClickListener();*/

    }

    @Override
    public int getItemCount() {
        return comments.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txInital, txCommentator, txValue, txDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txInital = itemView.findViewById(R.id.first_name_initial);
            txCommentator = itemView.findViewById(R.id.commentator_name);
            txValue = itemView.findViewById(R.id.value);
            txDate = itemView.findViewById(R.id.date);

        }
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

}
