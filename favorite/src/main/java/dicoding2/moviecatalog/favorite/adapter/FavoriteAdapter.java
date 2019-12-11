package dicoding2.moviecatalog.favorite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dicoding2.moviecatalog.favorite.R;
import dicoding2.moviecatalog.favorite.ui.DetailActivity;

import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_GENRE;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_ID;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_LANGUAGE;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_OVERVIEW;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_POPULARITY;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_POSTER_IMAGE;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_RELEASE_DATE;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_TITTLE;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_VOTE_AVERAGE;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.FIELD_VOTE_COUNT;
import static dicoding2.moviecatalog.favorite.database.DBContract.Fav.getColumnString;
import static dicoding2.moviecatalog.favorite.utilities.Static.GENRE;
import static dicoding2.moviecatalog.favorite.utilities.Static.ID;
import static dicoding2.moviecatalog.favorite.utilities.Static.LANGUAGE;
import static dicoding2.moviecatalog.favorite.utilities.Static.OVERVIEW;
import static dicoding2.moviecatalog.favorite.utilities.Static.POPULARITY;
import static dicoding2.moviecatalog.favorite.utilities.Static.POSTER_IMAGE;
import static dicoding2.moviecatalog.favorite.utilities.Static.RELEASE_DATE;
import static dicoding2.moviecatalog.favorite.utilities.Static.TITTLE;
import static dicoding2.moviecatalog.favorite.utilities.Static.VOTE_AVERAGE;
import static dicoding2.moviecatalog.favorite.utilities.Static.VOTE_COUNT;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoQueue) {
        super(context, c, autoQueue);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            ImageView image;
            TextView title, description, rating, categories, top_text;
            CardView cardView;
            
            image = view.findViewById(R.id.media_image);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            rating = view.findViewById(R.id.rating);
            categories = view.findViewById(R.id.categories);
            top_text = view.findViewById(R.id.top_text);
            cardView = view.findViewById(R.id.cardview_item);
            
            final String descriptionStr = getColumnString(cursor, FIELD_OVERVIEW);  
            title.setText(getColumnString(cursor, FIELD_TITTLE));
            if (descriptionStr.equalsIgnoreCase("") || descriptionStr.isEmpty())
                description.setText(context.getString(R.string.no_movie));
            else
                description.setText(descriptionStr);
            rating.setText(getColumnString(cursor, FIELD_VOTE_AVERAGE));
            top_text.setText(context.getString(R.string.release_at) + " " + getColumnString(cursor, FIELD_RELEASE_DATE));
            categories.setText(getColumnString(cursor, FIELD_GENRE));

            Picasso.get().load(getColumnString(cursor, FIELD_POSTER_IMAGE)).into(image);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(ID, getColumnString(cursor, FIELD_ID));
                    intent.putExtra(POSTER_IMAGE, getColumnString(cursor, FIELD_POSTER_IMAGE));
                    intent.putExtra(TITTLE, getColumnString(cursor, FIELD_TITTLE));
                    intent.putExtra(OVERVIEW, (descriptionStr.equalsIgnoreCase("") || descriptionStr.isEmpty()) ? context.getString(R.string.no_movie) : descriptionStr);
                    intent.putExtra(RELEASE_DATE, getColumnString(cursor, FIELD_RELEASE_DATE));
                    intent.putExtra(GENRE, getColumnString(cursor, FIELD_GENRE));
                    intent.putExtra(LANGUAGE, getColumnString(cursor, FIELD_LANGUAGE));
                    intent.putExtra(VOTE_COUNT, getColumnString(cursor, FIELD_VOTE_COUNT));
                    intent.putExtra(VOTE_AVERAGE, getColumnString(cursor, FIELD_VOTE_AVERAGE));
                    intent.putExtra(POPULARITY, getColumnString(cursor, FIELD_POPULARITY));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
