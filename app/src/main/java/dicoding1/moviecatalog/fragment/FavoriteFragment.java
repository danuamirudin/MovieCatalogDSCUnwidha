package dicoding1.moviecatalog.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.adapter.FavoriteAdapter;

import static dicoding1.moviecatalog.database.DBContract.Fav.CONTENT_URI;

@SuppressLint("ValidFragment")
public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private Cursor listFavorite;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favorite_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadNoteAsync().execute();
            }
        });

        adapter = new FavoriteAdapter(getContext());
        adapter.setListFavorite(listFavorite);
        recyclerView.setAdapter(adapter);
        new LoadNoteAsync().execute();
    }

    private void postAction(Cursor listFavorite) {
        this.listFavorite = listFavorite;
        adapter.setListFavorite(this.listFavorite);
        adapter.notifyDataSetChanged();

        if (this.listFavorite.getCount() == 0) {
            recyclerView.removeAllViews();
            Toast.makeText(getActivity(), "Tidak Ada Favorite", Toast.LENGTH_LONG).show();
        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getActivity()).getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            postAction(favorite);
        }
    }
}
