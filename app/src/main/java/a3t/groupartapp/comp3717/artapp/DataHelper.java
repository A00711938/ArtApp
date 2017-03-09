package a3t.groupartapp.comp3717.artapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * This class reads a file (tokenized with the | delimiter) and
 * places the information in an Art object. DataHelper creates and List
 * Array of art objects from the file.
 *
 * Created by Hai Hua (Ryan) Tan on 2017-02-12.
 */

public class DataHelper extends SQLiteOpenHelper{
    private static final int SCHEMA_VERSION = 1;
    private static final String DB_NAME = "public_art.db";
    private static final String ART_TABLE = "Art";
    private static final String ART_ID= "_id";
    private static final String ART_NAME = "Name";
    private static final String ART_ADDRESS = "Address";
    private static final String ART_DESCRIPTION = "Description";
    private static final String ART_SUMMARY = "Summary";
    private static final String ART_LONGITUDE = "Longitude";
    private static final String ART_LATITUDE = "Latitude";
    private static final String ART_COLLECTED = "Collected";

    private static final String COMMENT_TABLE ="Comment";
    private static final String COMMENT_ID = "_id";
    private static final String COMMENT_ART_ID = "ArtId";
    private static final String COMMENT_TEXT = "Text";

    private static final String PHOTO_TABLE ="Photo";
    private static final String PHOTO_ID = "_id";
    private static final String PHOTO_ART_ID = "ArtId";
    private static final String PHOTO_FILE = "File";

    /*
    private static final String COLLECTION_TABLE = "Collection";
    private static final String COLLECTION_ID = "_id";
    private static final String COLLECTED_ART_ID = "ArtId";
    */

    private static DataHelper instance;

    private Context context;
    private DataHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
    }

    public synchronized static DataHelper getInstance(final Context context)
    {
        if(instance == null)
        {
            instance = new DataHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onConfigure(final SQLiteDatabase db) {
        super.onConfigure(db);

        setWriteAheadLoggingEnabled(true);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_ART_TABLE;
        final String CREATE_ART_COMMENT_TABLE;
        final String CREATE_ART_PHOTO_TABLE;
        final String CREATE_ART_COLLECT_TABLE;

        CREATE_ART_TABLE = "CREATE TABLE IF NOT EXISTS "
                + ART_TABLE
                + "(" + ART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT "
                + ", " + ART_NAME + " TEXT NOT NULL"
                + ", " + ART_ADDRESS + " TEXT"
                + ", " + ART_DESCRIPTION + " TEXT"
                + ", " + ART_SUMMARY + " TEXT "
                + ", " + ART_LONGITUDE + " REAL "
                + ", " + ART_LATITUDE + " REAL "
                + ", " + ART_COLLECTED + " INTEGER NOT NULL DEFAULT 0);";

        CREATE_ART_COMMENT_TABLE = "CREATE TABLE IF NOT EXISTS "
                + COMMENT_TABLE
                + "(" + COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT "
                + ", " + COMMENT_ART_ID + " INTEGER "
                + ", " + COMMENT_TEXT + " TEXT"
                + ", FOREIGN KEY (" + COMMENT_ART_ID
                + ") REFERENCES " + ART_TABLE + "(" + ART_ID + "));";

        CREATE_ART_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS "
                + PHOTO_TABLE
                + "(" + PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT "
                + ", " + PHOTO_ART_ID + " INTEGER "
                + ", " + PHOTO_FILE + " TEXT "
                + ", FOREIGN KEY (" + PHOTO_ART_ID + ") REFERENCES " + ART_TABLE + "(" + ART_ID + "));";

        /*
        CREATE_ART_COLLECT_TABLE = "CREATE TABLE IF NOT EXISTS "
                + COLLECTION_TABLE
                + "(" +  COLLECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT "
                + ", " + COLLECTED_ART_ID + " INTEGER NOT NULL "
                + ", FOREIGN KEY (" + COLLECTED_ART_ID + ") REFERENCES " + ART_TABLE + "(" + ART_ID + "));";

        */
        db.execSQL(CREATE_ART_TABLE);
        db.execSQL(CREATE_ART_COMMENT_TABLE);
        db.execSQL(CREATE_ART_PHOTO_TABLE);
       // db.execSQL(CREATE_ART_COLLECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Cursor getAllArts() {
        final Cursor cursor;
        final SQLiteDatabase db;

        db = getReadableDatabase();
        cursor = db.query(ART_TABLE,
                new String[]{ART_ID, ART_NAME},
                null,
                null,
                null,
                null,
                "ORDER BY " + ART_NAME);

        return  cursor;
    }

    public boolean isDbEmpty() {
        final SQLiteDatabase db;
        final long numEntries;
        db = getReadableDatabase();
        numEntries = DatabaseUtils.queryNumEntries(db, ART_TABLE);
        db.close();
        return numEntries == 0;
    }

    public void insertArts(ContentValues values[]) {
        final SQLiteDatabase db;

        // Log.d("INSERT ART", "begin");
        db = getWritableDatabase();
        db.beginTransaction();
        try{
            for (ContentValues art : values) {
                //Log.d("ART_TO_INSERT",art.toString());
                db.insert(ART_TABLE,null,art);
            }
            db.setTransactionSuccessful();
        } catch (Exception ex){
            ex.printStackTrace();

        } finally {
            db.endTransaction();
            db.close();
        }
    }
    Cursor getAllCollectedArts() {
        final SQLiteDatabase db;
        final Cursor cursor;

        db = getReadableDatabase();
        cursor = db.query(ART_TABLE,
                new String[]{ART_ID, ART_NAME},
                "WHERE " + ART_COLLECTED + "=?",
                new String[]{"1"},
                null,
                null,
                null);
        return cursor;
    }

    public Cursor query(String table,
                        String[] projection, String selection,
                        String[] selectionArgs,
                        String sortOrder){
        final SQLiteDatabase db;
        final Cursor cursor;

        db = getReadableDatabase();
        cursor = db.query(table, projection,selection,selectionArgs,sortOrder,null, null);
        return cursor;
    }
}
