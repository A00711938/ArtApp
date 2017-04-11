package a3t.groupartapp.comp3717.artapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CollectionPageActivity extends ListFragment {
    //On this method I call the dataHelper and initialize it so
    //that an array list is created from the mockdata.txt
    //private DataHelper dataHelper = new DataHelper(this);
    //private List<Art> artObjs;
    protected Cursor cursor;
    ArrayList<String> test;

    // Store instance variables
    private String title;
    private int page;
    //private ArtDataProvider cursor;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.art_list);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
//    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.art_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated (Bundle SavedInstanceState){
        super.onActivityCreated(SavedInstanceState);

        new LoadArtDetail().execute(0);
        //Initializes data helper and creates Arts objects using the
        // Arts class. Data is saved as a List.
        //dataHelper.init();

        //Storing all the arts objs in a List Array.
        //artObjs = dataHelper.getArts();

        //The following creates an array Adapter that will manage list items.
        //The idea is that we customize the array adapter so that a list view is
        // presented with a mini image of the art piece collected, the name, and maybe something else.
        //ArrayAdapter<Art> itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.artName, artObjs);

        //In here, we save the list view data.
//        final ListView listView = (ListView) findViewById(R.id.list);

        //In here, we set the adapter in the list view to automatically manage
        //the information we pull from the Arts class.
//        adapter = new SimpleCursorAdapter(
//                getBaseContext(),
//                R.layout.list_item,
//                null,
//                new String[] {"Name"},
//                new int[]{R.id.artName},
//                0);
//
//        android.app.LoaderManager manager = getLoaderManager();
//        manager.initLoader(0, null, new CollectionLoaderCallbacks());
//
//        listView.setAdapter(adapter);

        //Set an onClickListener. When an item on the adapter is clicked, the objects details are passed to the
        //next screen for the ArtDetailsActivity Page to use.
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Art artSelection;
//                final String artId;

                //Grabs the item clicked and saves the object in a variable.
                //selectedArtId = listView.getItemAtPosition(position);

//                Cursor cursor = ((SimpleCursorAdapter) listView.getAdapter()).getCursor();
//                cursor.moveToPosition(position);
//                artId = cursor.getString(0);
//                Log.d("ArtId", artId);

                //Testing. It works. Clicked item name is displayed correctly on screen.
                //Log.i("CLicked Art Piece: ", artSelection.getName());

//                Intent i = new Intent(CollectionPageActivity.this, ArtDetailsActivity.class);
//                i.putExtra("ArtId", artId);
//                //i.putStringArrayListExtra("comments", (ArrayList<String>)artSelection.getComment());
//                startActivity(i);
//            }
//        });
    }

    public static CollectionPageActivity newInstance(String text) {

        CollectionPageActivity c = new CollectionPageActivity();
        Bundle b = new Bundle();
        b.putString("msg", text);

        c.setArguments(b);

        return c;
    }

    public void createAdapter(){
        test = new ArrayList<>();
//        test.add("one");
//        test.add("two");
        cursor.moveToFirst();
        test.add(cursor.getString(1));
        Log.d("NAME: ", test.get(0));

        while(cursor.moveToNext()){
            test.add(cursor.getString(1));
        }

        Log.d("SIZE", ""+test.size());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, test);

        setListAdapter(adapter);
        //getListView().setOnItemClickListener(this);
    }

    /**
     * The AsyncTask Queries the DB and returns an array of DB objects
     * that are stored inside the artCursor Variable.
     */
    private class LoadArtDetail extends AsyncTask<Integer, Void, Long> {

        @Override
        protected Long doInBackground(Integer... params) {
            final ContentResolver contentResolver;
            contentResolver = getActivity().getContentResolver();
            cursor = contentResolver.query(
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
            createAdapter();
        }
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

//    public void goToArtDetails1(View view){
//
//        String name ="Kiwanis Mosaic";
//        Intent i = new Intent(this, ArtDetailsActivity.class);
//        i.putExtra("position",name);
//        startActivity(i);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        if (item.getItemId() == android.R.id.home ) {
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private class CollectionLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
//
//        @Override
//        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//            final Uri uri;
//            final CursorLoader loader;
//            final String SELECTION;
//
//            SELECTION = ArtDataProvider.ART_COLLECTED + "=?";
//
//            uri = ArtDataProvider.ART_URI;
//            loader = new CursorLoader(getApplicationContext(),
//                    uri,
//                    new String[] {ArtDataProvider.ART_ID, ArtDataProvider.ART_NAME},
//                    SELECTION,
//                    new String[]{"1"},
//                    null);
//            return loader;
//        }
//
//        @Override
//        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//            adapter.swapCursor(data);
//        }
//
//        @Override
//        public void onLoaderReset(Loader<Cursor> loader) {
//            adapter.swapCursor(null);
//        }
//    }
}
