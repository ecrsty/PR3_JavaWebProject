package ru.mirea.soloveva.task2;

import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RMClient {

    public static void main(String[] args) throws IOException {
        Retrofit client = new Retrofit
                .Builder()
                .baseUrl("https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/")
                .addConverterFactory(JacksonConverterFactory.create(new JsonMapper()))
                .build();

        MovieService movieService = client.create(MovieService.class);
        Call<List<MovieDTO>> call = movieService.getMovies();

        try {
            Response<List<MovieDTO>> response = call.execute();
            if (response.isSuccessful()) {
                List<MovieDTO> movies = response.body();

                Optional<MovieDTO> result = movies.stream()
                        .filter(movie -> movie.getYear() < 2000)
                        .max((m1, m2) -> Integer.compare(m1.getCast().size(), m2.getCast().size()));

                if (result.isPresent()) {
                    MovieDTO movie = result.get();
                    System.out.println("Фильм до 2000 года с самым большим количеством актеров: " + movie.getTitle());
                    System.out.println("Количество актеров: " + movie.getCast().size());
                } else {
                    System.err.println("Фильмы не найдены.");
                }
            } else {
                System.err.println("Ошибка при выполнении запроса: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
