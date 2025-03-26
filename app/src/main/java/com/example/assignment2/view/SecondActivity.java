package com.example.assignment2.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.assignment2.R;
import com.example.assignment2.model.Movie;
import com.example.assignment2.viewModel.MovieViewModel;
import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {

    private MovieViewModel movieViewModel;
    //Gave up on view binding cause im tired
    private TextView titleTextView, yearTextView, plotTextView, directorTextView, ratingTextView, genreTextView, metascoreTextView, runtimeView;
    private ImageView posterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        titleTextView = findViewById(R.id.movieTitle);
        yearTextView = findViewById(R.id.year);
        plotTextView = findViewById(R.id.description);
        directorTextView = findViewById(R.id.directors);
        ratingTextView = findViewById(R.id.imbRating);
        genreTextView = findViewById(R.id.genre);
        metascoreTextView = findViewById(R.id.metascore);
        posterImageView = findViewById(R.id.poster);
        runtimeView = findViewById(R.id.runTime);


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");

        movieViewModel.movieDetails(movieTitle).observe(this, movie -> {
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                yearTextView.setText("Release Year:  " + movie.getDate());
                plotTextView.setText("--= PLOT =--  \n" + movie.getPlot());
                directorTextView.setText("Director(s):  " + movie.getDirector());
                ratingTextView.setText("IMDB Rating:  " + movie.getImdbRating());
                genreTextView.setText(movie.getGenre());
                metascoreTextView.setText("Metascore:  " + movie.getMetascore());
                runtimeView.setText("Run Time:  " + movie.getRunTime());

                Picasso.get().load(movie.getImageUrl()).into(posterImageView);
            } else {
                Toast.makeText(this, "Failed to load movie details", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            //when button is clicked, finish this activity and go back to the previous screen
            finish();
        });
    }
}
