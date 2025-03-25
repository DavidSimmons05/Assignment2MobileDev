package com.example.assignment2.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMainBinding;
import com.example.assignment2.utils.MovieClickListener;
import com.example.assignment2.utils.MyAdapter;
import com.example.assignment2.viewModel.MovieViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieClickListener{
    ActivityMainBinding binding;
    MovieViewModel movieViewModel;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //adding moive stuff to list
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, movies ->{
            if(movies != null) {
                myAdapter.submitList(movies);
            }else {
                Toast.makeText(MainActivity.this, "No movies available", Toast.LENGTH_SHORT);
            }
        });
        binding.searchButton.setOnClickListener(v ->{
            String movieQ = binding.searchMoive.getText().toString();
            movieViewModel.searchMovies(movieQ);
        });


        //setup for recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(getApplicationContext(), new ArrayList<>());
        binding.recyclerView.setAdapter(myAdapter);

        myAdapter.setClickListener(this);

    }

    @Override
    public void onClick(View v, int pos) {
        Toast.makeText(MainActivity.this, "No movies available", Toast.LENGTH_SHORT);

    }
}