package a3t.groupartapp.comp3717.artapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static a3t.groupartapp.comp3717.artapp.R.id.textView;

public class NearestArtActivity extends AppCompatActivity {

    protected Location distance;

    protected float results[] = new float[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_art);

        distance = new Location("Test Location");

        TextView updateDist = (TextView)findViewById(R.id.testNum);

        distance.distanceBetween(37.4219, -122.0879,49.2118,-122.9272, results);

        updateDist.setText(""+results[0]/1000+" mts");
    }
}
