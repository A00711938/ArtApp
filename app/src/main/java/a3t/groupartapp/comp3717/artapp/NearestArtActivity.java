package a3t.groupartapp.comp3717.artapp;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;
import java.util.TreeMap;


public class NearestArtActivity extends AppCompatActivity {

    protected Location distance;

    protected float results[] = new float[10];

    protected double meter;

    private Cursor artCursor;

    private String name = " ";

    private Map<String,Place> myPlace = new TreeMap<String,Place>();

    private LocationManager locationManager;
    private LocationListener locationListener;

    private double currLongitude = 0;
    private double currLatitude = 0;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_art);

        new NearestArtActivity.LoadArtDetail().execute(0);

        distance = new Location("Test Location");

       // distance.distanceBetween(37.4219, -122.0879,49.2118,-122.9272, results);
        new NearestArtActivity.LoadArtDetail().execute(0);

        button = (Button) findViewById(R.id.check);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currLongitude = location.getLongitude();
                currLatitude = location.getLatitude();
                Place.CurrLatitude = currLatitude;
                Place.CurrLongitude = currLongitude;
                calculateDistance();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                },10);
            }
            return;
        }else {
            configureButton();
        }

        Log.d("Longitude: " , Double.toString(currLongitude));

    }


    private void saveAllPlace() {

        //while(!artCursor.isLast()) {
        artCursor.moveToFirst();
        myPlace.put(artCursor.getString(0),
                new Place(artCursor.getString(1),
                        Float.parseFloat(artCursor.getString(2)),
                        Float.parseFloat(artCursor.getString(3))
                ));

       // artCursor.moveToNext();

       while(artCursor.moveToNext()) {
           myPlace.put(artCursor.getString(0),
                   new Place(artCursor.getString(1),
                           Double.parseDouble(artCursor.getString(2)),
                           Double.parseDouble(artCursor.getString(3))
                   ));
           //Log.d("id: : " ,artCursor.getString(0));
           //}
       }
       // Log.d("size: " ,Integer.toString(g));

        Log.d("Longitude: " , "heheheh");
        for(Map.Entry m:myPlace.entrySet()){
            String temp = (String)m.getKey();
        }

    }


    private class LoadArtDetail extends AsyncTask<Integer, Void, Long> {

        @Override
        protected Long doInBackground(Integer... params) {
            final ContentResolver contentResolver;
            contentResolver = getContentResolver();
            artCursor = contentResolver.query(
                    ArtDataProvider.ART_URI,
                    new String[] {ArtDataProvider.ART_ID,ArtDataProvider.ART_NAME,ArtDataProvider.ART_LONGITUDE,ArtDataProvider.ART_LATITUDE},
                    null,
                    null,
                    null,null);
            /*
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

            */
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            saveAllPlace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void calculateDistance() {
         double longitude = 0;
         double latitude = 0;
        for(Map.Entry m:myPlace.entrySet()){
            longitude = ((Place)m.getValue()).getLongitude();
            latitude = ((Place)m.getValue()).getLatitude();
            distance = new Location("Test Location");
            distance.distanceBetween(latitude,longitude,currLatitude,currLongitude,results);
            meter = results[0]/1000;
            ((Place)m.getValue()).setDistance(meter);
        }
        findClosest();
    }

    private void findClosest() {
        String[] index = {"-1","-1","-1"};
        double min=99999999;
        int count = 0;
        while(count<3) {
            for(Map.Entry m:myPlace.entrySet()){
                String key = (String)m.getKey();
                if(key!=index[0]&&key!=index[1]&&key!=index[2]) {
                    if(((Place)m.getValue()).getDistance() < min) {
                        min = ((Place)m.getValue()).getDistance();
                        index[count] = key;
                    }
                }
            }
            count++;
            min=99999999;
        }

        TextView myView1 =(TextView)findViewById(R.id.textView2);
        TextView myView2 =(TextView)findViewById(R.id.textView3) ;
        TextView myView3 = (TextView)findViewById(R.id.textView4);
        double distance = (myPlace.get(index[0])).getDistance();
        String name = (myPlace.get(index[0])).getName();
        myView1.setText(name+ " : "+Double.toString(distance) + " meters");
        distance = (myPlace.get(index[1])).getDistance();
        name = (myPlace.get(index[1])).getName();
        myView2.setText(name+ " : "+Double.toString(distance) + " meters");
        distance = (myPlace.get(index[2])).getDistance();
        name = (myPlace.get(index[2])).getName();
        myView3.setText(name+ " : "+Double.toString(distance) + " meters");
    }

    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, locationListener);
                Log.d("Longitude: " , Double.toString(currLongitude));
            }
        });
    }
}
