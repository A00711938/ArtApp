package a3t.groupartapp.comp3717.artapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static a3t.groupartapp.comp3717.artapp.R.drawable.kiwanis1;
import static a3t.groupartapp.comp3717.artapp.R.id.imageView;
import static android.text.TextUtils.concat;

public class ArtDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This image resourceID can be passed from previous intent.
        int imgId = R.drawable.alphabetball1;

        //Retrieving the previous intent.
        Intent intent = getIntent();

        //Retrieving Intent putExtra Values and saving them in their respective
        //variables for further use.
        String objName = intent.getStringExtra("name");
        ArrayList<String> objComments = intent.getStringArrayListExtra("comments");

        //MasterCommentsString pulls all the comments related to the item and arranges
        //them so they are displayed in a nicer format (comment, space, comment, space, etc).
        String masterCommentsString = "";
        for(int i = 0; i < objComments.size(); i++){
            masterCommentsString = masterCommentsString.concat(objComments.get(i) + "\n");
        }

        //Retrieving the views in order to update them with Art Obj data
        //EditText commentField = (EditText) findViewById(R.id.comment);
        TextView nameField = (TextView) findViewById(R.id.name);
        TextView comments = (TextView) findViewById(R.id.comments);
        ImageView imageView  = (ImageView) findViewById(R.id.imageView);

        //In here we pass the values retrieved from previous intent and upload
        //them in this activity screen (in their respective places). Note the imgID
        //resource is a placeholder that needs to be modified.
        imageView.setImageResource(imgId);
        nameField.setText(objName);
        comments.setText(masterCommentsString);
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
