package dicoding1.moviecatalog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movfav.db";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FAVORITE = "CREATE TABLE " + DBContract.Fav.DATABASE_TABLE + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.Fav.FIELD_ID + " INTEGER NOT NULL," +
                DBContract.Fav.FIELD_TITTLE + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_GENRE + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_POSTER_IMAGE + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_LANGUAGE + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_VOTE_AVERAGE + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_RELEASE_DATE + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_VOTE_COUNT + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_POPULARITY + " TEXT NOT NULL, " +
                DBContract.Fav.FIELD_OVERVIEW + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
