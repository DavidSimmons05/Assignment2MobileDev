package com.example.assignment2.view;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.utils.MovieClickListener;

public class MyViewHolder extends RecyclerView.ViewHolder {

    MovieClickListener clickListener;
    public ImageView imageView;
    public TextView title;
    public TextView year;
    public MyViewHolder(@NonNull View itemView, MovieClickListener clickListener) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageview);
        title = itemView.findViewById(R.id.title_txt);
        year = itemView.findViewById(R.id.year);

        this.clickListener = clickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getAdapterPosition());
            }
        });
    }
}