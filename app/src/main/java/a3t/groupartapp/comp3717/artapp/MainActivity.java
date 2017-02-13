package a3t.groupartapp.comp3717.artapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int one = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Test DataHelper
        DataHelper helper = new DataHelper(getApplicationContext());
        helper.init();
        List<Art> arts = helper.getArts();
        if (arts != null) {
            for (Art art : arts) {
                Log.d("Art name:", art.getName());
                Log.d("Art address: ", art.getAddress());
                Log.d("Art rate:", Integer.toString(art.getRate()));
                for (String image: art.getImages()) {
                    Log.d("Art image:", image);
                }
                for (String comment: art.getComment()) {
                    Log.d("Art comment:", comment);
                }
            }

        }
    }

    public void startApp(View view){
        Intent i = new Intent(this, WelcomeScreenActivity.class);
        startActivity(i);
    }
}
