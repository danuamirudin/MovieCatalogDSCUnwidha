package dicoding1.moviecatalog.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.database.DBContract;
import dicoding1.moviecatalog.model.FavoriteMovie;

import static dicoding1.moviecatalog.utilities.Static.GENRE;
import static dicoding1.moviecatalog.utilities.Static.ID;
import static dicoding1.moviecatalog.utilities.Static.LANGUAGE;
import static dicoding1.moviecatalog.utilities.Static.OVERVIEW;
import static dicoding1.moviecatalog.utilities.Static.POPULARITY;
import static dicoding1.moviecatalog.utilities.Static.POSTER_IMAGE;
import static dicoding1.moviecatalog.utilities.Static.RELEASE_DATE;
import static dicoding1.moviecatalog.utilities.Static.TITTLE;
import static dicoding1.moviecatalog.utilities.Static.VOTE_AVERAGE;
import static dicoding1.moviecatalog.utilities.Static.VOTE_COUNT;

public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private Cursor cursor;

    MovieRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    private FavoriteMovie getFavorite(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
      return new FavoriteMovie(cursor);
    }


    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                DBContract.Fav.CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                DBContract.Fav.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        FavoriteMovie favoriteMovie = getFavorite(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item_xml);

        Log.d("Special",favoriteMovie.getTitle());

        try {
            Bitmap image = Picasso.get().load(favoriteMovie.getPosterPath()).get();
            remoteViews.setImageViewBitmap(R.id.img_widget, image);
            remoteViews.setTextViewText(R.id.tv_movie_title, favoriteMovie.getTitle());
            Log.d("Special","Widget done");
        }catch (IOException e) {
            Log.e("Special",e.getLocalizedMessage());
        }
        Bundle extras = new Bundle();
        extras.putString(ID, favoriteMovie.getId());
        extras.putString(POSTER_IMAGE, favoriteMovie.getPosterPath());
        extras.putString(TITTLE, favoriteMovie.getTitle());
        extras.putString(OVERVIEW, favoriteMovie.getOverview());
        extras.putString(RELEASE_DATE, favoriteMovie.getReleaseDate());
        extras.putString(GENRE, favoriteMovie.getGenreStr());
        extras.putString(LANGUAGE, favoriteMovie.getOriginalLanguage());
        extras.putString(VOTE_COUNT, favoriteMovie.getVoteCount());
        extras.putString(VOTE_AVERAGE, favoriteMovie.getVoteAverage());
        extras.putString(POPULARITY, favoriteMovie.getPopularity());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
