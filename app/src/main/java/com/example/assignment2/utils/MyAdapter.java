package com.example.assignment2.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.model.Movie;
import com.example.assignment2.view.MyViewHolder;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Movie> movies;
    Context context;

    public MovieClickListener clickListener;
    public MyAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }
    public void setClickListener(MovieClickListener myListener){
        this.clickListener = myListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView, this.clickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = movies.get(position);

        //Finish when Movie class is done
        holder.
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
