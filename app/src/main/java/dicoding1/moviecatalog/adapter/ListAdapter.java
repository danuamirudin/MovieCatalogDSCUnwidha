package dicoding1.moviecatalog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import dicoding1.moviecatalog.R;
import dicoding1.moviecatalog.model.ResultGenres;
import dicoding1.moviecatalog.model.ResultMovie;
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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private Context context;
    private List<ResultMovie> listPopuler;
    private ArrayList<Integer> listGenreId = new ArrayList<>();
    private ArrayList<String> listGenreName = new ArrayList<>();
    private Boolean upcoming;
    private String genres;

    public ListAdapter(Context context, List<ResultMovie> listPopuler, ArrayList<ResultGenres> listGenres, Boolean upcoming) {
        this.context = context;
        this.listPopuler = listPopuler;
        for (int i = 0; i < listGenres.size(); i++) {
            listGenreId.add(listGenres.get(i).getId());
            listGenreName.add(listGenres.get(i).getName());
        }
        this.upcoming = upcoming;
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie,
                parent, false);
        Log.d("Info", "List Adapter Called");
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final StringBuilder genre = new StringBuilder();
        final String description = listPopuler.get(position).getOverview();
        holder.title.setText(listPopuler.get(position).getTitle());
        if (description.equalsIgnoreCase("") || description.isEmpty())
            holder.description.setText(context.getString(R.string.no_movie));
        else
            holder.description.setText(description);
        holder.rating.setText(listPopuler.get(position).getVoteAverage());
        holder.top_text.setText(context.getString(R.string.release_at) + " " + listPopuler.get(position).getReleaseDate());
        for (String item : listPopuler.get(position).getGenreIds()) {
            genre.append(findGenres(Integer.parseInt(item))).append(", ");
        }
        genres = (genre.length() > 2) ? genre.toString().substring(0, genre.toString().length() - 2) : genre.toString();
        holder.categories.setText(genres);
        if (genre.length() <= 2) holder.categories.setVisibility(View.INVISIBLE);
        if (!upcoming) holder.top_text.setVisibility(View.GONE);

        Picasso.get().load("https://image.tmdb.org/t/p/w780" + listPopuler.get(position).getPosterPath()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(ID, listPopuler.get(position).getId());
                intent.putExtra(POSTER_IMAGE, "https://image.tmdb.org/t/p/w780" + listPopuler.get(position).getPosterPath());
                intent.putExtra(TITTLE, listPopuler.get(position).getTitle());
                intent.putExtra(OVERVIEW, (description.equalsIgnoreCase("") || description.isEmpty()) ? context.getString(R.string.no_movie) : description);
                intent.putExtra(RELEASE_DATE, listPopuler.get(position).getReleaseDate());
                intent.putExtra(GENRE, ((genre.length() > 2) ? genres : "-"));
                intent.putExtra(LANGUAGE, listPopuler.get(position).getOriginalLanguage());
                intent.putExtra(VOTE_COUNT, listPopuler.get(position).getVoteCount());
                intent.putExtra(VOTE_AVERAGE, listPopuler.get(position).getVoteAverage());
                intent.putExtra(POPULARITY, listPopuler.get(position).getPopularity());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    private String findGenres(int i) {
        return listGenreName.get(listGenreId.indexOf(i));
    }

    @Override
    public int getItemCount() {
        return listPopuler.size();
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
