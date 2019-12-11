package dicoding1.moviecatalog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.model.FavoriteMovie;
import dicoding1.moviecatalog.ui.DetailActivity;

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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private Context context;
    private Cursor listFavorite;

    public FavoriteAdapter(Context context) { this.context = context; }

    public void setListFavorite(Cursor listFavorite) {
        this.listFavorite = listFavorite;
    }

    @NonNull
    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie,
                parent, false);
        Log.d("Info", "List Adapter Called");
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final FavoriteMovie favorite = getItem(position);

        final String description = favorite.getOverview();
        holder.title.setText(favorite.getTitle());
        if (description.equalsIgnoreCase("") || description.isEmpty())
            holder.description.setText(context.getString(R.string.no_movie));
        else
            holder.description.setText(description);
        holder.rating.setText(favorite.getVoteAverage());
        holder.top_text.setText(context.getString(R.string.release_at) + " " + favorite.getReleaseDate());
        holder.categories.setText(favorite.getGenreStr());

        Picasso.get().load(favorite.getPosterPath()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(ID, favorite.getId());
                intent.putExtra(POSTER_IMAGE, favorite.getPosterPath());
                intent.putExtra(TITTLE, favorite.getTitle());
                intent.putExtra(OVERVIEW, (description.equalsIgnoreCase("") || description.isEmpty()) ? context.getString(R.string.no_movie) : description);
                intent.putExtra(RELEASE_DATE, favorite.getReleaseDate());
                intent.putExtra(GENRE, favorite.getGenreStr());
                intent.putExtra(LANGUAGE, favorite.getOriginalLanguage());
                intent.putExtra(VOTE_COUNT, favorite.getVoteCount());
                intent.putExtra(VOTE_AVERAGE, favorite.getVoteAverage());
                intent.putExtra(POPULARITY, favorite.getPopularity());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    private FavoriteMovie getItem(int position){
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new FavoriteMovie(listFavorite);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description, rating, categories, top_text;
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.media_image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            rating = itemView.findViewById(R.id.rating);
            categories = itemView.findViewById(R.id.categories);
            top_text = itemView.findViewById(R.id.top_text);
            cardView = itemView.findViewById(R.id.cardview_item);
        }
    }
}
