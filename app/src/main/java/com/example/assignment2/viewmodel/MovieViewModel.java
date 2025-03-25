package com.example.assignment2.viewModel;

import static android.app.ProgressDialog.show;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2.model.Movie;
import com.example.assignment2.utils.ClientApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private List<Movie> movieData = new ArrayList<>();
    String baseUrl = "https://www.omdbapi.com/?s=";
    String key = "&apikey=62627a90";

    public LiveData<List<Movie>> getMovies() {
        return movieList;
    }

    public void searchMovies(String movieQ) {
        String url = baseUrl + movieQ + key;
        Log.i("tag", url);

        ClientApi.get(url, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String bodyResponse = response.body().string();
                JSONObject json = null;

                try {
                    json = new JSONObject(bodyResponse);
                    JSONArray searchArray = json.getJSONArray("Search");
                    movieData.clear();  // Clear any previous results

                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject Json = searchArray.getJSONObject(i);
                        String title = Json.getString("Title");
                        String year = Json.getString("Year");
                        String poster = Json.getString("Poster");

                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setDate(year);
                        movie.setImageUrl(poster);
                        movieData.add(movie);  // Add each movie to the list
                    }

                    movieList.postValue(movieData);  // Update the LiveData with the new list of movies
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Error", "Request failed", e);
            }
        });
    }
}


