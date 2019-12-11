package dicoding1.moviecatalog.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import static dicoding1.moviecatalog.database.DBContract.AUTHORITY;
import static dicoding1.moviecatalog.database.DBContract.Fav.DATABASE_TABLE;

public class FavoriteProvider extends ContentProvider {

    static final int FAVORITE = 1;
    static final int FAVORITE_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DATABASE_TABLE, FAVORITE);
        URI_MATCHER.addURI(AUTHORITY,DATABASE_TABLE+ "/#", FAVORITE_ID);
    }

    private FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)) {
            case FAVORITE:
                cursor = favoriteHelper.queryProvider(projection, selection, selectionArgs,sortOrder);
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
        }

        if (cursor != null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) { return null; }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;
        switch (URI_MATCHER.match(uri)) {
            case FAVORITE:
                id = favoriteHelper.insertProvider(contentValues);
                if (id > 0) {
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                id = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return Uri.parse(DBContract.Fav.CONTENT_URI + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id;
        switch (URI_MATCHER.match(uri)) {
            case FAVORITE_ID:
                id = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                if (id > 0) {
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                }
                break;
            default:
                id = 0;
        }
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id;
        switch (URI_MATCHER.match(uri)) {
            case FAVORITE_ID:
                id = favoriteHelper.updateProvider(uri.getLastPathSegment(), values);
                if (id > 0) {
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                }
                break;

            default:
                id = 0;
        }
        return id;
    }
}
