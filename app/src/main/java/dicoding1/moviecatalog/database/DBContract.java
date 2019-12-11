package dicoding1.moviecatalog.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static dicoding1.moviecatalog.utilities.Static.*;

public class DBContract {
    static final String AUTHORITY = "dicoding1.moviecatalog";
    private static final String TASK = "content";

    public static final class Fav implements BaseColumns {
        static final String DATABASE_TABLE = "favorite";
        public static final String FIELD_ID = ID;
        public static final String FIELD_TITTLE = TITTLE;
        public static final String FIELD_GENRE = GENRE;
        public static final String FIELD_POSTER_IMAGE = POSTER_IMAGE;
        public static final String FIELD_LANGUAGE = LANGUAGE;
        public static final String FIELD_RELEASE_DATE = RELEASE_DATE;
        public static final String FIELD_VOTE_AVERAGE = VOTE_AVERAGE;
        public static final String FIELD_VOTE_COUNT = VOTE_COUNT;
        public static final String FIELD_POPULARITY = POPULARITY;
        public static final String FIELD_OVERVIEW = OVERVIEW;

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(TASK)
                .authority(AUTHORITY)
                .appendPath(DATABASE_TABLE)
                .build();

        public static String getColumnString(Cursor cursor, String columnName) {
            return cursor.getString(cursor.getColumnIndex(columnName));
        }

        public static int getColumnInt(Cursor cursor, String columnName) {
            return cursor.getInt(cursor.getColumnIndex(columnName));
        }
    }
}
