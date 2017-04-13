package a3t.groupartapp.comp3717.artapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;


public class ArtDetailsActivity extends AppCompatActivity {
    private Cursor artCursor;
    private Cursor imageCursor;
    private Cursor commentCursor;
    private String artId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.art_details_mod);

        //This image resourceID can be passed from previous intent.
        Intent intent = getIntent();
        artId = intent.getStringExtra("ArtId");

//        final Drawable ic_pluscircle = new IconicsDrawable(this)
//                .icon(FontAwesome.Icon.faw_plus_circle)
//                .color(Color.RED)
//                .sizeDp(24);

        //For Whatever reason I'm getting a java.lang.IllegalStateException in
        //here. It doesn't affect the app but FYI.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_discuss);

        new LoadArtDetail().execute(Integer.parseInt(artId));


    }

    public void openMap(View view) {
        WebView webView = (WebView) findViewById(R.id.map);
        artCursor.moveToFirst();
       // String stringURL = "https://www.google.ca/maps/place/@"+artCursor.getString(3)+","+artCursor.getString(2)+"z";
        //The below is harcoded, the idea is to use google maps to take the user to the piece of art they just found :)
        String stringURL = "https://www.google.ca/maps/place/Harry+Jerome+Sports+Centre/@49.2890585,-122.9426928,17z/data=!3m1!4b1!4m5!3m4!1s0x548679f3936b0a97:0xa3725c9fdb431a03!8m2!3d49.289055!4d-122.9405041";
        Log.d("URL", stringURL);
        webView.loadUrl(stringURL);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        artCursor.close();
        commentCursor.close();
        imageCursor.close();
        super.onDestroy();
    }

    private void updateUi() {
        final String artName;
        String masterCommentsString = "";
        while(commentCursor.moveToNext()){
            masterCommentsString = masterCommentsString.concat(commentCursor.getString(0) + "\n");
        }
        artCursor.moveToFirst();

        //Retrieving the views in order to update them with Art Obj data
        //EditText commentField = (EditText) findViewById(R.id.comment);
        TextView infoText = (TextView) findViewById(R.id.infoText);
        TextView addressText = (TextView) findViewById(R.id.addressText);
//        ImageView imageView  = (ImageView) findViewById(R.id.imageView);

        infoText.setText(artCursor.getString(5));
        addressText.setText(artCursor.getString(4));
        //In here we pass the values retrieved from previous intent and upload
        //them in this activity screen (in their respective places). Note the imgID
        //resource is a placeholder that needs to be modified.
//        int imgId = R.drawable.alphabetball1;
//        imageView.setImageResource(imgId);

//        nameField.setText(artCursor.getString(1));
//        comments.setText(masterCommentsString);
        artName = artCursor.getString(1);


        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(artName);

        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView;
        String imageFileName = "";
        try {
            imageCursor.moveToFirst();
            imageFileName = imageCursor.getString(0);
            int index = imageFileName.indexOf(".");
            imageFileName = imageFileName.substring(0, index);
            //Log.d("IMAGE NAME", imageFileName);
        } catch (Exception ex) {

        }
        int imgId;
        if (imageFileName.isEmpty()) {
            imgId = R.drawable.public_art;
        } else {
            imgId = getResources().getIdentifier(imageFileName, "drawable", getPackageName());
        }
        imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(imgId).centerCrop().into(imageView);
    }

    private class LoadArtDetail extends AsyncTask<Integer, Void, Long> {

        @Override
        protected Long doInBackground(Integer... params) {
            final ContentResolver contentResolver;
            contentResolver = getContentResolver();
            artCursor = contentResolver.query(
                    ArtDataProvider.ART_URI,
                    new String[] {  //0
                            ArtDataProvider.ART_ID,
                            //1
                            ArtDataProvider.ART_NAME,
                            //2
                            ArtDataProvider.ART_LONGITUDE,
                            //3
                            ArtDataProvider.ART_LATITUDE,
                            //4
                            ArtDataProvider.ART_ADDRESS,
                            //5
                            ArtDataProvider.ART_DESCRIPTION},
                    ArtDataProvider.ART_ID + "=?",
                    new String[]{artId},
                    null,null);
            imageCursor = contentResolver.query(
                    ArtDataProvider.ART_PHOTO_URI,
                    new String[]{ArtDataProvider.PHOTO_FILE},
                    ArtDataProvider.ART_ID + "=?",
                    new String[]{artId},
                    null,
                    null);
            commentCursor = contentResolver.query(
                    ArtDataProvider.ART_COMMENT_URI,
                    new String[]{ArtDataProvider.COMMENT_TEXT},
                    ArtDataProvider.COMMENT_ART_ID + "=?",
                    new String[]{artId},
                    null,
                    null);
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            updateUi();
        }
    }
}
