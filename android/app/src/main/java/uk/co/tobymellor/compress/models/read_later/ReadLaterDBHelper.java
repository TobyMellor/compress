package uk.co.tobymellor.compress.models.read_later;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReadLaterDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "article.db";

    private static final String CREATE_READ_LATER =
            "CREATE TABLE " + ReadLaterContract.ReadLaterTable.TABLE_NAME + " (" +
                    ReadLaterContract.ReadLaterTable._ID + " INTEGER PRIMARY KEY," +
                    ReadLaterContract.ReadLaterTable.COLUMN_ARTICLE_ID + " INTEGER UNIQUE)";

    private static final String DELETE_READ_LATER =
            "DROP TABLE IF EXISTS " + ReadLaterContract.ReadLaterTable.TABLE_NAME;

    public ReadLaterDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_READ_LATER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_READ_LATER);
        onCreate(db);
    }
}