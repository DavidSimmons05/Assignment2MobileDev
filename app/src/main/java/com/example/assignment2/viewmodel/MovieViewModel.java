package com.example.assignment2.viewModel;

import static android.app.ProgressDialog.show;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2.model.Movie;
import com.example.assignment2.utils.ClientApi;

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
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Movie> detailedMovie = new MutableLiveData<>();
    private List<Movie> movieData = new ArrayList<>();
    String baseUrl = "https://www.omdbapi.com/?type=movie&s=";
    String detailUrl = "https://www.omdbapi.com/?t=";
    String key = "&apikey=62627a90";

    public LiveData<List<Movie>> getMovies() {
        return movieList;
    }
    public LiveData<String> getToastMessage(){
        return toastMessage;
    }

    public void searchMovies(String movieQ) {
        String url = baseUrl + movieQ + key;
        Log.i("tag", url);

        ClientApi.get(url, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String bodyResponse = response.body().string();
                    try {
                        JSONObject json = new JSONObject(bodyResponse);
                        if(json.has("Search")) {
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
                            movieList.postValue(movieData);  // Update the LiveData with the new list of movie
                        }
                        else{
                            movieList.postValue(new ArrayList<>());
                            Log.i("MovieViewModel", "No movie found");//for debuging cehcing
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Error", "Request Failed", e);
            }
        });
    }

    //movie specifics
    public MutableLiveData<Movie> movieDetails(String name){
        String url = detailUrl + name + key;
        Log.i("tag",url);

        ClientApi.get(url, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String bodyResponse = response.body().string();
                JSONObject json = null;
                try {
                    json = new JSONObject(bodyResponse);
                    String title = json.getString("Title");
                    String year = json.getString("Year");
                    String poster = json.getString("Poster");
                    String plot = json.getString("Plot");
                    String director = json.getString("Director");
                    String imdbRating = json.getString("imdbRating");
                    String genre = json.getString("Genre");
                    String metascore = json.getString("Metascore");

                    Movie movie = new Movie();
                    movie.setTitle(title);
                    movie.setDate(year);
                    movie.setImageUrl(poster);
                    movie.setPlot(plot);
                    movie.setDirector(director);
                    movie.setImdbRating(imdbRating);
                    movie.setGenre(genre);
                    movie.setMetascore(metascore);
                    detailedMovie.postValue(movie);
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Error","Request Failed", e);
            }
        });
        return detailedMovie;
    }
}


