package a3t.groupartapp.comp3717.artapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.os.Build.VERSION_CODES.M;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void goToNearestArt(View view) {
        Intent i = new Intent(this, NearestArtActivity.class);
        startActivity(i);
    }

    public void goToMyCollection(View view){
        Intent i = new Intent(this, CollectionPageActivity.class);
        startActivity(i);
    }
}
