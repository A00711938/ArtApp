package a3t.groupartapp.comp3717.artapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DataHelper dataHelper;
        final String jsonArts;
        final JSONArray artArray;
        final String jsonPhotos;
        final JSONArray photoArray;
        ArrayList<ContentValues> bulkValues;

        //The layout inflater allows us to use the font awesome library through the android-iconics library.
        //https://github.com/mikepenz/Android-Iconics
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test DataHelper
        //DataHelper helper = getApplicationContext());
        dataHelper = DataHelper.getInstance(this);
        //Log.d("DATABASE EMPTY", "" + dataHelper.isDbEmpty());

        if (dataHelper.isDbEmpty()) {
            jsonArts = loadJSONFromAsset("public_art.json");
            jsonPhotos = loadJSONFromAsset("art_photos.json");

            bulkValues = new ArrayList<>();
            try {
                artArray = new JSONArray(jsonArts);
                int i = 0;
                JSONObject art;
                while (!artArray.isNull(i)) {
                    ContentValues artValues = new ContentValues();
                    art = artArray.getJSONObject(i++);
                    artValues.put(ArtDataProvider.ART_NAME,art.getString(ArtDataProvider.ART_NAME));
                    artValues.put(ArtDataProvider.ART_ADDRESS,art.getString(ArtDataProvider.ART_ADDRESS));
                    artValues.put(ArtDataProvider.ART_DESCRIPTION,art.getString(ArtDataProvider.ART_DESCRIPTION));
                    artValues.put(ArtDataProvider.ART_SUMMARY,art.getString(ArtDataProvider.ART_SUMMARY));
                    artValues.put(ArtDataProvider.ART_LONGITUDE, art.getString("X"));
                    artValues.put(ArtDataProvider.ART_LATITUDE, art.getString("Y"));
                    artValues.put(ArtDataProvider.ART_COLLECTED, 1);
                    bulkValues.add(artValues);
                    //Log.d("JSON", art.toString());
                }
                //Log.d("ContentValues[]", bulkArts.toArray(new ContentValues[0]).toString());
                dataHelper.insertArts(bulkValues.toArray(new ContentValues[0]));

                i = 0;
                photoArray = new JSONArray(jsonPhotos);
                //Log.d("Read Photo", photoArray.toString());

                bulkValues = new ArrayList<>();
                JSONObject photo;
                while (!photoArray.isNull(i)) {
                    ContentValues photoValues = new ContentValues();
                    photo = photoArray.getJSONObject(i++);
                   // Log.d("Read Photo", photo.toString());
                    photoValues.put(ArtDataProvider.ART_NAME, photo.getString(ArtDataProvider.ART_NAME));
                    photoValues.put(ArtDataProvider.PHOTO_FILE, photo.getString(ArtDataProvider.PHOTO_FILE));

                    bulkValues.add(photoValues);
                }

                dataHelper.insertArtPhotos(bulkValues.toArray(new ContentValues[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                dataHelper.close();
            }
        }

        startRippleAnimation();
    }

    public void startApp(View view){
        Intent i = new Intent(this, WelcomeScreenActivity.class);
        startActivity(i);
    }

    public void startRippleAnimation(){
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
    }

    public String loadJSONFromAsset(String jsonFile) {
        String json = null;
        final int size;
        byte[] buffer;
        InputStream is;
        try {
            is = getAssets().open(jsonFile);
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
