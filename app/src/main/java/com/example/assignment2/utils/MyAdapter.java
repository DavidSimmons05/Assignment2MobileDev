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
    private MovieClickListener clickListener;
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
        return new MyViewHolder(itemView, this.clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getDate());
        //To load poster image from url
    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }
        else{
            return 0;
        }
    }

    public void submitList(Object movies) {
        this.movies = (List<Movie>) movies;
        notifyDataSetChanged();
    }

}
