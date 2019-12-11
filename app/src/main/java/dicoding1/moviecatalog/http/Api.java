package dicoding1.moviecatalog.http;

import dicoding1.moviecatalog.BuildConfig;
import dicoding1.moviecatalog.model.GenreMovieListModel;
import dicoding1.moviecatalog.model.MovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("movie/now_playing?api_key="+ BuildConfig.API_KEY)
    Call<MovieModel> getPlaying(@Query("language") String language);

    @GET("movie/upcoming?api_key="+BuildConfig.API_KEY)
    Call<MovieModel> getUpcoming(@Query("language") String language);

    @GET("search/movie?api_key="+BuildConfig.API_KEY)
    Call<MovieModel> getMovie(@Query("query") String query, @Query("language") String language);

    @GET("genre/movie/list?api_key="+BuildConfig.API_KEY)
    Call<GenreMovieListModel> getGenres(@Query("language") String language);
}
