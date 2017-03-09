package a3t.groupartapp.comp3717.artapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Ryan on 2017-03-06.
 */

public class ArtDataProvider extends ContentProvider {
    private static final UriMatcher uriMatcher;
    public static final Uri ART_URI;
    public static final Uri ART_PHOTO_URI;
    public static final Uri ART_COMMENT_URI;
    private static final int ART_URI_INDEX = 1;
    private static final int ART_PHOTO_URI_INDEX = 2;
    private static final int ART_COMMENT_URI_INDEX = 3;
    private static final String URI_PREFIX = "content://";

    private static final String PACKAGE_NAME ;
    private static final String ART_TABLE = "Art";
    public static final String ART_ID= "_id";
    public static final String ART_NAME = "Name";
    public static final String ART_ADDRESS = "Address";
    public static final String ART_DESCRIPTION = "Description";
    public static final String ART_SUMMARY = "Summary";
    public static final String ART_LONGITUDE = "Longitude";
    public static final String ART_LATITUDE = "Latitude";
    public static final String ART_COLLECTED = "Collected";

    public static final String COMMENT_TABLE ="Comment";
    public static final String COMMENT_ID = "_id";
    public static final String COMMENT_ART_ID = "ArtId";
    public static final String COMMENT_TEXT = "Text";

    public static final String PHOTO_TABLE ="Photo";
    public static final String PHOTO_ID = "_id";
    public static final String PHOTO_ART_ID = "ArtId";
    public static final String PHOTO_FILE = "File";

    /*
    private static final String COLLECTION_TABLE = "Collection";
    private static final String COLLECTION_ID = "_id";
    private static final String COLLECTED_ART_ID = "ArtId";
    */

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ArtDataProvider.class.getPackage().getName(),ART_TABLE ,ART_URI_INDEX);
        uriMatcher.addURI(ArtDataProvider.class.getPackage().getName(),PHOTO_TABLE,ART_PHOTO_URI_INDEX);
        uriMatcher.addURI(ArtDataProvider.class.getPackage().getName(),COMMENT_TABLE,ART_COMMENT_URI_INDEX);
    }

    static
    {
        PACKAGE_NAME = ArtDataProvider.class.getPackage().getName();
        Log.d("PACKAGE_NAME", PACKAGE_NAME);
        ART_URI = Uri.parse(URI_PREFIX
                + PACKAGE_NAME
                + "/"
                + ART_TABLE);
        ART_PHOTO_URI = Uri.parse(URI_PREFIX
                + PACKAGE_NAME
                + "/"
                + PHOTO_TABLE);
        ART_COMMENT_URI = Uri.parse(URI_PREFIX
                + PACKAGE_NAME
                + "/"
                + COMMENT_TABLE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection, String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        final Cursor cursor;
        final DataHelper dataHelper;

        dataHelper = DataHelper.getInstance(getContext());

        switch (uriMatcher.match(uri)) {
            case ART_URI_INDEX:
                cursor = dataHelper.query(ART_TABLE,projection,selection,selectionArgs,sortOrder);
                break;
            case ART_PHOTO_URI_INDEX:
                cursor = dataHelper.query(PHOTO_TABLE,projection,selection,selectionArgs,sortOrder);
                break;
            case ART_COMMENT_URI_INDEX:
                cursor = dataHelper.query(COMMENT_TABLE,projection,selection,selectionArgs,sortOrder);
                break;
            default: {
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }

        return (cursor);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final String VND = "vnd.android.cursor.dir/vnd.";
        final String type;

        switch(uriMatcher.match(uri)) {
            case ART_URI_INDEX:
                type = VND + PACKAGE_NAME + "." + ART_TABLE;
                break;
            case ART_PHOTO_URI_INDEX:
                type = VND + PACKAGE_NAME + "." + PHOTO_TABLE;
                break;
            case ART_COMMENT_URI_INDEX:
                type = VND + PACKAGE_NAME + "." + COMMENT_TABLE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri,
                      String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
