package dicoding1.moviecatalog.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.adapter.ListAdapter;
import dicoding1.moviecatalog.model.MovieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dicoding1.moviecatalog.ui.MainActivity.searchView;
import static dicoding1.moviecatalog.ui.SplashScreenActivity.api;
import static dicoding1.moviecatalog.ui.SplashScreenActivity.listGenres;
import static dicoding1.moviecatalog.utilities.mAppCompatActivity.mCurrentLocale;

public class SearchFragment extends Fragment {

    private TextView textBackground;
    private RecyclerView rv;

    public SearchFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textBackground = view.findViewById(R.id.text_background);
        rv = view.findViewById(R.id.card_recycler_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //If enter is pressed
                if (textBackground.getVisibility() == View.VISIBLE) {
                    textBackground.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Searching " + query, Toast.LENGTH_SHORT).show();
                    getMovie(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    private void getMovie(String tittle) {
        api.getMovie(tittle.trim(), mCurrentLocale)
                .enqueue(new Callback<MovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                        if (response.isSuccessful()) {
                            ListAdapter adapter = new ListAdapter(getActivity(), response.body() != null ? response.body().getResults() : null, listGenres, false);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv.setAdapter(adapter);
                        } else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
