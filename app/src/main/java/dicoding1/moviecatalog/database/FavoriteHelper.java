package dicoding1.moviecatalog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import dicoding1.moviecatalog.model.FavoriteMovie;

import static dicoding1.moviecatalog.utilities.Static.ID;

public class FavoriteHelper {

    private static String DATABASE_TABLE = DBContract.Fav.DATABASE_TABLE;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;

    FavoriteHelper(Context context){
        this.context = context;
    }

    void open() throws SQLException {
        DB dataBaseHelper = new DB(context);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    public ArrayList<FavoriteMovie> query() {
        ArrayList<FavoriteMovie> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, ID + " DESC"
                , null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                FavoriteMovie favoriteMovie = new FavoriteMovie();
                favoriteMovie.setId(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_ID)));
                favoriteMovie.setPosterPath(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_POSTER_IMAGE)));
                favoriteMovie.setTitle(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_TITTLE)));
                favoriteMovie.setGenreStr(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_GENRE)));
                favoriteMovie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_LANGUAGE)));
                favoriteMovie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_RELEASE_DATE)));
                favoriteMovie.setVoteCount(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_VOTE_COUNT)));
                favoriteMovie.setVoteAverage(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_VOTE_AVERAGE)));
                favoriteMovie.setPopularity(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_POPULARITY)));
                favoriteMovie.setOverview(cursor.getString(cursor.getColumnIndex(DBContract.Fav.FIELD_OVERVIEW)));

                arrayList.add(favoriteMovie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    Cursor queryByIdProvider(String id){
        return sqLiteDatabase.query(DATABASE_TABLE,null
                ,ID + " = ?"
                , new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    Cursor queryProvider(@Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder){
        return sqLiteDatabase.query(DATABASE_TABLE
                ,projection
                ,selection
                ,selectionArgs
                ,null
                ,null
                ,sortOrder);
    }

    long insertProvider(ContentValues values){
        return sqLiteDatabase.insert(DATABASE_TABLE,null, values);
    }

    int updateProvider(String id, ContentValues values){
        return sqLiteDatabase.update(DATABASE_TABLE,values,ID +" = ?",new String[]{id} );
    }

    int deleteProvider(String id){
        Log.d("Special", "Delete in DB _id = " + id);
        return sqLiteDatabase.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }
}
