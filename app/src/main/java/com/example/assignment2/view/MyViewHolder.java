package com.example.assignment2.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.utils.MovieClickListener;

public class MyViewHolder extends RecyclerView.ViewHolder {

    MovieClickListener clickListener;
    ImageView imageView;
    TextView title;
    TextView year;
    public MyViewHolder(@NonNull View itemView, MovieClickListener clickListener) {
        super(itemView);

    }
}
