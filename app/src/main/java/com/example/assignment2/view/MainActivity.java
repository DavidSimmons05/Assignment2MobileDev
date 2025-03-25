package com.example.assignment2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMainBinding;
import com.example.assignment2.model.Movie;
import com.example.assignment2.utils.MovieClickListener;
import com.example.assignment2.utils.MyAdapter;
import com.example.assignment2.viewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieClickListener{
    ActivityMainBinding binding;
    MovieViewModel movieViewModel;
    MyAdapter myAdapter;
    List<Movie> listOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //filling out adapter and what not
        listOfMovies = new ArrayList<>();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, movieList ->{
            if (movieList != null) {
                // Update the list of movies in the adapter
                listOfMovies.clear();  // Clear the old list
                listOfMovies.addAll(movieList);  // Add the new movies to the list

                // Notify the adapter to update the RecyclerView
                myAdapter.notifyDataSetChanged();
            }
        });
        //On button click
        binding.searchButton.setOnClickListener(v ->{
            String movieQ = binding.searchMoive.getText().toString().trim();
            if(!movieQ.isEmpty()) {
                Log.i("tag", movieQ);
                movieViewModel.searchMovies(movieQ);
            }
            else{
                Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });

        //setup for recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(getApplicationContext(), listOfMovies);
        binding.recyclerView.setAdapter(myAdapter);

        myAdapter.setClickListener(this);

    }

    @Override
    public void onClick(View v, int pos) {

    }
}