package dicoding1.moviecatalog.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import dicoding1.moviecatalog.database.DBContract;

import static dicoding1.moviecatalog.database.DBContract.Fav.getColumnString;

public class FavoriteMovie implements Parcelable {
    private String overview;
    private String originalLanguage;
    private String title;
    private String genreStr;
    private String posterPath;
    private String releaseDate;
    private String voteAverage;
    private String popularity;
    private String id;
    private String voteCount;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenreStr() {
        return genreStr;
    }

    public void setGenreStr(String genreStr) {
        this.genreStr = genreStr;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.genreStr);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.voteAverage);
        dest.writeString(this.popularity);
        dest.writeString(this.id);
        dest.writeString(this.voteCount);
    }

    public FavoriteMovie() { }

    public FavoriteMovie(Cursor cursor) {
        this.overview = getColumnString(cursor, DBContract.Fav.FIELD_OVERVIEW );
        this.originalLanguage = getColumnString(cursor, DBContract.Fav.FIELD_LANGUAGE );
        this.title = getColumnString(cursor, DBContract.Fav.FIELD_TITTLE );
        this.genreStr = getColumnString(cursor, DBContract.Fav.FIELD_GENRE );
        this.posterPath = getColumnString(cursor, DBContract.Fav.FIELD_POSTER_IMAGE );
        this.releaseDate = getColumnString(cursor, DBContract.Fav.FIELD_RELEASE_DATE );
        this.voteAverage = getColumnString(cursor, DBContract.Fav.FIELD_VOTE_AVERAGE );
        this.popularity = getColumnString(cursor, DBContract.Fav.FIELD_POPULARITY );
        this.id = getColumnString(cursor, DBContract.Fav.FIELD_ID );
        this.voteCount = getColumnString(cursor, DBContract.Fav.FIELD_VOTE_COUNT );
    }

    private FavoriteMovie(Parcel in) {
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.genreStr = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readString();
        this.popularity = in.readString();
        this.id = in.readString();
        this.voteCount = in.readString();
    }

    public static final Parcelable.Creator<FavoriteMovie> CREATOR = new Parcelable.Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel source) {
            return new FavoriteMovie(source);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };
}
