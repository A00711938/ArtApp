package a3t.groupartapp.comp3717.artapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CollectionPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void goToArtDetails(View view){
        String name = "Alphabet Ball";

        Intent i = new Intent(this, ArtDetailsActivity.class);
        i.putExtra("position",name);
        startActivity(i);
    }

    public void goToArtDetails1(View view){

        String name ="Kiwanis Mosaic";
        Intent i = new Intent(this, ArtDetailsActivity.class);
        i.putExtra("position",name);
        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
