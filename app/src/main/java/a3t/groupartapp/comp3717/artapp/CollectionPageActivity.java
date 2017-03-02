package a3t.groupartapp.comp3717.artapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CollectionPageActivity extends AppCompatActivity {
    //On this method I call the dataHelper and initialize it so
    //that an array list is created from the mockdata.txt
    private DataHelper dataHelper = new DataHelper(this);
    private List<Art> artObjs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.art_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initializes data helper and creates Arts objects using the
        // Arts class. Data is saved as a List.
        dataHelper.init();

        //Storing all the arts objs in a List Array.
        artObjs = dataHelper.getArts();

        //The following creates an array Adapter that will manage list items.
        //The idea is that we customize the array adapter so that a list view is
        // presented with a mini image of the art piece collected, the name, and maybe something else.
        ArrayAdapter<Art> itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.artName, artObjs);

        //In here, we save the list view data.
        final ListView listView = (ListView) findViewById(R.id.list);

        //In here, we set the adapter in the list view to automatically manage
        //the information we pull from the Arts class.
        listView.setAdapter(itemsAdapter);

        //Set an onClickListener. When an item on the adapter is clicked, the objects details are passed to the
        //next screen for the ArtDetailsActivity Page to use.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Art artSelection;

                //Grabs the item clicked and saves the object in a variable.
                artSelection = (Art) listView.getItemAtPosition(position);

                //Testing. It works. Clicked item name is displayed correctly on screen.
                Log.i("CLicked Art Piece: ", artSelection.getName());

                Intent i = new Intent(CollectionPageActivity.this, ArtDetailsActivity.class);
                i.putExtra("name", artSelection.getName());
                i.putStringArrayListExtra("comments", (ArrayList<String>)artSelection.getComment());
                startActivity(i);
            }
        });
    }

//    public void goToArtDetails(View view){
//        //Attempt to add something that passes the clicked art piece
//        //to the String below.
//        String name = "Alphabet Ball";
//
//        Intent i = new Intent(this, ArtDetailsActivity.class);
//        i.putExtra("position",name);
//        startActivity(i);
//    }

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
