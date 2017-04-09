package a3t.groupartapp.comp3717.artapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


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

        //For Whatever reason I'm getting a java.lang.IllegalStateException in
        //here. It doesn't affect the app but FYI.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new LoadArtDetail().execute(Integer.parseInt(artId));

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
        String masterCommentsString = "";
        while(commentCursor.moveToNext()){
            masterCommentsString = masterCommentsString.concat(commentCursor.getString(0) + "\n");
        }

        //Retrieving the views in order to update them with Art Obj data
        //EditText commentField = (EditText) findViewById(R.id.comment);
//        TextView nameField = (TextView) findViewById(R.id.name);
//        TextView comments = (TextView) findViewById(R.id.comments);
//        ImageView imageView  = (ImageView) findViewById(R.id.imageView);

        //In here we pass the values retrieved from previous intent and upload
        //them in this activity screen (in their respective places). Note the imgID
        //resource is a placeholder that needs to be modified.
//        int imgId = R.drawable.alphabetball1;
//        imageView.setImageResource(imgId);
        artCursor.moveToFirst();
//        nameField.setText(artCursor.getString(1));
//        comments.setText(masterCommentsString);

        final String cheeseName = artCursor.getString(1);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

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
            Log.d("IMAGE NAME", imageFileName);
        } catch (Exception ex) {

        }
        int imgId;
        if (imageFileName.isEmpty()) {
            imgId = R.drawable.sample;
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
                    new String[] {ArtDataProvider.ART_ID,ArtDataProvider.ART_NAME},
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
