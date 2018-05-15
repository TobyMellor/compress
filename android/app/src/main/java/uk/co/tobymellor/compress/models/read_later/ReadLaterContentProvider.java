package uk.co.tobymellor.compress.models.read_later;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ReadLaterContentProvider extends ContentProvider {
    public static final int ARTICLE = 100;
    public static final int ARTICLE_WITH_ID = 101;
    private static final UriMatcher readLaterUriMatcher = buildUriMatcher();
    public static ReadLaterDBHelper readLaterDBHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority   = ReadLaterContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ReadLaterContract.PATH_READ_LATER, ARTICLE);
        matcher.addURI(authority, ReadLaterContract.PATH_READ_LATER + "/#", ARTICLE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        readLaterDBHelper = new ReadLaterDBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (readLaterUriMatcher.match(uri)) {
            case ARTICLE: {
                cursor = readLaterDBHelper.getReadableDatabase().query(
                        ReadLaterContract.ReadLaterTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;
            }

            default:
                throw new UnsupportedOperationException("Not supported: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (readLaterUriMatcher.match(uri)) {
            case ARTICLE:
                return ReadLaterContract.ReadLaterTable.CONTENT_TYPE_DIR;
            case ARTICLE_WITH_ID:
                return ReadLaterContract.ReadLaterTable.CONTENT_TYPE_ITEM;
            default:
                throw new UnsupportedOperationException("Not supported: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase database = readLaterDBHelper.getWritableDatabase();

        switch (readLaterUriMatcher.match(uri)) {
            case ARTICLE:
                final long _id = database.insert(ReadLaterContract.ReadLaterTable.TABLE_NAME, null, values);

                if (_id != -1) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return ReadLaterContract.ReadLaterTable.buildReadLaterUriWithId(_id);
            default:
                return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (readLaterUriMatcher.match(uri)) {
            case ARTICLE_WITH_ID:
                readLaterDBHelper.getWritableDatabase().delete(
                        ReadLaterContract.ReadLaterTable.TABLE_NAME,
                        ReadLaterContract.ReadLaterTable.COLUMN_ARTICLE_ID + " = ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))}
                );

                break;
            default:
                throw new UnsupportedOperationException("Not supported: " + uri);
        }

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
