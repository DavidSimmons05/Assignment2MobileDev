package com.example.assignment2.viewModel;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2.model.Movie;
import com.example.assignment2.utils.ClientApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    String baseUrl= "https://www.omdbapi.com/?t";
    String key= "&apikey=62627a90";
    public LiveData<List<Movie>> getMovies() {
        return movieList;
    }

    public void searchMovies(String movieQ) {
        String url = baseUrl + movieQ + key;

        ClientApi.get(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //Toast.makeText(this), "System has failed, please try again soon", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String bodyResponse = response.body().string();
                    Movie movie = new Gson().fromJson(bodyResponse, Movie.class);
                    List<Movie> movies = new ArrayList<>();
                    movies.add(movie);
                    movieList.postValue(movies);
                }
            }
        });
    }
}

