package a3t.groupartapp.comp3717.artapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DataHelper dataHelper;
        final String jsonArts;
        final JSONArray artArray;
        final ArrayList<ContentValues> bulkArts;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Test DataHelper
        //DataHelper helper = getApplicationContext());
        dataHelper = DataHelper.getInstance(this);
        //Log.d("DATABASE EMPTY", "" + dataHelper.isDbEmpty());

        if (dataHelper.isDbEmpty()) {
            jsonArts = loadJSONFromAsset();

            bulkArts = new ArrayList<>();
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
                    bulkArts.add(artValues);
                    //Log.d("JSON", art.toString());
                }
                Log.d("ContentValues[]", bulkArts.toArray(new ContentValues[0]).toString());
                dataHelper.insertArts(bulkArts.toArray(new ContentValues[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                dataHelper.close();
            }
        }
    }

    public void startApp(View view){
        Intent i = new Intent(this, WelcomeScreenActivity.class);
        startActivity(i);
    }

    public String loadJSONFromAsset() {
        String json = null;
        final int size;
        byte[] buffer;
        InputStream is;
        try {
            is = getAssets().open("public_art.json");
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
