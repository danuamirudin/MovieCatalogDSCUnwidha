package dicoding1.moviecatalog.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenreMovieListModel {
    @SerializedName("genres")
    private ArrayList<ResultGenres> results;

    public ArrayList<ResultGenres> getResults() {
        return results;
    }
}
