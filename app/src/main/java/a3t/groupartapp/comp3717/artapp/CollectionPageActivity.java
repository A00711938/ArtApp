package a3t.groupartapp.comp3717.artapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CollectionPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.art_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //On this method I call the dataHelper and initialize it so
        //that an array list is created from the mockdata.txt
        DataHelper dataHelper = new DataHelper(this);
        dataHelper.init();

        //The following creates an array Adapter that will manage list items.
        //The idea is that we customize the array adapter so that a list view is
        // presented with a mini image of the art piece collected, the name, and maybe something else.
        ArrayAdapter<Art> itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.artName, dataHelper.getArts());

        //In here, we save the list view data.
        ListView listView = (ListView) findViewById(R.id.list);

        //In here, we set the adapter in the list view to automatically manage
        //the information we pull from the Arts class.
        listView.setAdapter(itemsAdapter);
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
