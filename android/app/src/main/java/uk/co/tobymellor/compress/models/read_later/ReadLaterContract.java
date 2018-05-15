package uk.co.tobymellor.compress.models.read_later;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ReadLaterContract {
    public static final String CONTENT_AUTHORITY = "uk.co.tobymellor.compress";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_READ_LATER = "read_later";

    public static class ReadLaterTable implements BaseColumns {
        public static final String TABLE_NAME = "READ_LATER_TABLE";
        public static final String COLUMN_ARTICLE_ID = "ARTICLE_ID";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_READ_LATER).build();
        public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_READ_LATER;
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_READ_LATER;

        public static Uri buildReadLaterUriWithId(long Id) {
            return ContentUris.withAppendedId(CONTENT_URI, Id);
        }
    }

}
