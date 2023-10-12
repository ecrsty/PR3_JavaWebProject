package ru.mirea.soloveva.task2;

import retrofit2.http.GET;
import retrofit2.Call;

import java.util.List;

public interface MovieService {
    @GET("movies.json")
    Call<List<MovieDTO>> getMovies();
}
