package dicoding2.moviecatalog.favorite.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import dicoding2.moviecatalog.favorite.R;
import dicoding2.moviecatalog.favorite.adapter.FavoriteAdapter;

import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FavoriteAdapter favoriteAdapter;
    ListView listView;
    private int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        favoriteAdapter = new FavoriteAdapter(this, null, true);
        listView.setAdapter(favoriteAdapter);
        getSupportLoaderManager().initLoader(ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        favoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favoriteAdapter.swapCursor(null);
    }
}
