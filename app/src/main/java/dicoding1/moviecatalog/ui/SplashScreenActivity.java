package dicoding1.moviecatalog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import dicoding1.moviecatalog.database.DB;
import dicoding1.moviecatalog.http.Api;
import dicoding1.moviecatalog.http.Helper;
import dicoding1.moviecatalog.model.GenreMovieListModel;
import dicoding1.moviecatalog.model.ResultGenres;
import dicoding1.moviecatalog.utilities.mAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends mAppCompatActivity {

    public static Api api;
    public static ArrayList<ResultGenres> listGenres;
    public static DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(this);
        api = Helper.getInstanceRetrofit();

        api.getGenres(mCurrentLocale)
                .enqueue(new Callback<GenreMovieListModel>() {
                    @Override
                    public void onResponse(Call<GenreMovieListModel> call, Response<GenreMovieListModel> response) {
                        if (response.code() == 200) {
                            listGenres = response.body().getResults();
                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(SplashScreenActivity.this, "Get Genres Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreMovieListModel> call, Throwable t) {
                        Toast.makeText(SplashScreenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
