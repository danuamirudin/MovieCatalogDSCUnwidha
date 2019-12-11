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
import android.widget.Toast;

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

public class UpcomingFragment extends Fragment {
    public UpcomingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView rv = view.findViewById(R.id.upcoming_recycler_view);
            api.getUpcoming(mCurrentLocale)
                    .enqueue(new Callback<MovieModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                            if (response.isSuccessful()) {
                                ListAdapter adapter = new ListAdapter(getActivity(), response.body() != null ? response.body().getResults() : null, listGenres, true);
                                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rv.setAdapter(adapter);
                            } else {
                                Toast.makeText(getActivity(), "Get List Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            searchView.clearFocus();
            searchView.closeSearch();
    }
}
