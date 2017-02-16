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

import java.util.List;

import static a3t.groupartapp.comp3717.artapp.R.drawable.kiwanis1;

public class ArtDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = null;
        List<String> comment = null;
        List<String> img = null;
        Intent intent = getIntent();
        String positionName = intent.getStringExtra("position");
        DataHelper helper = new DataHelper(getApplicationContext());
        helper.init();
        List<Art> arts = helper.getArts();
        for(Art art : arts) {
            name = art.getName();
            if(name.equalsIgnoreCase(positionName)) {
                comment = art.getComment();
                img =  art.getImages();
                break;
            }
        }

        EditText commentField = (EditText) findViewById(R.id.comment);
        TextView nameField = (TextView) findViewById(R.id.name);
        ImageView imageView  = (ImageView) findViewById(R.id.imageView);
        Log.d("Image name:", img.get(0));

        int resId = getResources().getIdentifier(img.get(0), "drawable", getPackageName());
        //int resId = R.drawable.kiwanis1;
        Log.d("Resource id", "" + resId);

        imageView.setImageResource(resId);

            commentField.setText(comment.get(0));
            nameField.setText(name);
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
