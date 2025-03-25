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
    private final MutableLiveData<Movie> movieList = new MutableLiveData<>();
    Movie movie = new Movie();
    String baseUrl= "https://www.omdbapi.com/?s=";
    String key= "&apikey=62627a90";
    public LiveData<Movie> getMovies() {
        return movieList;
    }

    public void searchMovies(String movieQ) {
        String url = baseUrl + movieQ + key;
        Log.i("tag",url);
        ClientApi.get(url, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String bodyResponse = response.body().string();
                JSONObject json = null;

                try{
                    json = new JSONObject(bodyResponse);
                    JSONArray searchArray = json.getJSONArray("Search");
                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject Json = searchArray.getJSONObject(i);
                        String title = Json.getString("Title");
                        String year = Json.getString("Year");
                        String poster = Json.getString("Poster");
                        movie.setTitle(title);
                        movie.setDate(year);
                        movie.setImageUrl(poster);
                        Log.i("tag",movie.getTitle());
                        movieList.postValue(movie);
                    }
                }
                catch (JSONException e){
                    throw new RuntimeException(e);
                }
//                Movie movie = new Gson().fromJson(bodyResponse, Movie.class);
//                List<Movie> movies = new ArrayList<>();
//                movies.add(movie);
//                Log.i("tag", movies.toString());
//                movieList.postValue(movies);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //Toast.makeText(this), "System has failed, please try again soon", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

