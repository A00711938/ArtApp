package a3t.groupartapp.comp3717.artapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tanha on 2017-02-12.
 */

public class DataHelper {
    private Context context;
    private List<Art> arts;

    public DataHelper(Context context) {
        this.context = context;
        arts = new ArrayList<>();
    }

    private void readFromFile() {
        String line = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader( context.getResources().openRawResource(R.raw.mockdata)));
            while ((line = bufferedReader.readLine()) != null) {
                Scanner scanner = new Scanner(line).useDelimiter("\\|");
                String name = scanner.next();
                Art newArt = new Art(name);
                String address = scanner.next();
                newArt.setAddress(address);
                newArt.rate(scanner.nextInt());
                while (scanner.hasNext()) {
                    String temp;
                    if ((temp = scanner.next()).contains("jpg")) {
                        temp = temp.substring(0,temp.indexOf("."));
                        newArt.addImage(temp);
                    } else {
                        newArt.addComment(temp);
                    }
                }
                arts.add(newArt);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void init(){
        readFromFile();
    }

    public List<Art> getArts() {
        return arts;
    }
}
