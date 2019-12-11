package dicoding1.moviecatalog.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.database.DBContract;
import dicoding1.moviecatalog.utilities.Static;
import dicoding1.moviecatalog.utilities.mAppCompatActivity;
import dicoding1.moviecatalog.widget.MovieWidget;

import static dicoding1.moviecatalog.database.DBContract.Fav.CONTENT_URI;

public class DetailActivity extends mAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView title = findViewById(R.id.tittle);
        TextView rating = findViewById(R.id.rating);
        TextView genres = findViewById(R.id.genres);
        ImageView imageView = findViewById(R.id.media_image);
        TextView language = findViewById(R.id.language);
        TextView release_date = findViewById(R.id.release_date);
        TextView vote_count = findViewById(R.id.vote_count);
        TextView popularity = findViewById(R.id.popularity);
        TextView overview = findViewById(R.id.overview);

        final ShineButton favBtn = findViewById(R.id.favorite_button);

        title.setText(getIntent().getStringExtra(Static.TITTLE));
        rating.setText(getIntent().getStringExtra(Static.VOTE_AVERAGE));
        genres.setText(getIntent().getStringExtra(Static.GENRE));
        Picasso.get()
                .load(getIntent().getStringExtra(Static.POSTER_IMAGE))
                .into(imageView);
        language.setText((new Locale(getIntent().getStringExtra(Static.LANGUAGE))).getDisplayLanguage());
        release_date.setText(getIntent().getStringExtra(Static.RELEASE_DATE));
        vote_count.setText(getIntent().getStringExtra(Static.VOTE_COUNT));
        popularity.setText(getIntent().getStringExtra(Static.POPULARITY));
        overview.setText(getIntent().getStringExtra(Static.OVERVIEW));

        if (isFavorite()) {
            favBtn.setChecked(true);
        } else {
            favBtn.setChecked(false);
        }

        favBtn.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBContract.Fav.FIELD_ID, getIntent().getStringExtra(Static.ID));
                    contentValues.put(DBContract.Fav.FIELD_TITTLE, getIntent().getStringExtra(Static.TITTLE));
                    contentValues.put(DBContract.Fav.FIELD_GENRE, getIntent().getStringExtra(Static.GENRE));
                    contentValues.put(DBContract.Fav.FIELD_POSTER_IMAGE, getIntent().getStringExtra(Static.POSTER_IMAGE));
                    contentValues.put(DBContract.Fav.FIELD_LANGUAGE, getIntent().getStringExtra(Static.LANGUAGE));
                    contentValues.put(DBContract.Fav.FIELD_VOTE_AVERAGE, getIntent().getStringExtra(Static.VOTE_AVERAGE));
                    contentValues.put(DBContract.Fav.FIELD_RELEASE_DATE, getIntent().getStringExtra(Static.RELEASE_DATE));
                    contentValues.put(DBContract.Fav.FIELD_VOTE_COUNT, getIntent().getStringExtra(Static.VOTE_COUNT));
                    contentValues.put(DBContract.Fav.FIELD_POPULARITY, getIntent().getStringExtra(Static.POPULARITY));
                    contentValues.put(DBContract.Fav.FIELD_OVERVIEW, getIntent().getStringExtra(Static.OVERVIEW));

                    getContentResolver().insert(CONTENT_URI, contentValues);
                    updateWidget();
                    Log.i("uri", "Save to Favorite : " + getIntent().getStringExtra(Static.TITTLE));
                } else {
                    favBtn.setEnabled(false);
                    Uri uri = Uri.parse(CONTENT_URI + "/" + String.valueOf(Integer.parseInt(getIntent().getStringExtra(Static.ID))));
                    getContentResolver().delete(uri, null, null);
                    favBtn.setEnabled(true);
                    updateWidget();
                }

            }
        });
    }

    private void updateWidget() {
        Context context = this;
        Intent intent = new Intent(context, MovieWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, MovieWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        sendBroadcast(intent);
    }

    private boolean isFavorite() {
        boolean favorite = false;
        Uri uri = Uri.parse(CONTENT_URI.toString());
        Cursor cursor = getContentResolver().query(uri,
                new String[]{DBContract.Fav.FIELD_ID},
                DBContract.Fav.FIELD_ID + " = '" + getIntent().getStringExtra(Static.ID) + "'", null, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                favorite = true;
            }
            cursor.close();
        }
        return favorite;
    }


    public void onBackPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
