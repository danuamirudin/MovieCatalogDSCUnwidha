package dicoding2.moviecatalog.favorite.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import dicoding2.moviecatalog.favorite.R;
import dicoding2.moviecatalog.favorite.utilities.Static;

public class DetailActivity extends AppCompatActivity {

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
