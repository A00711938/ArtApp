package a3t.groupartapp.comp3717.artapp;

import java.util.ArrayList;
import java.util.List;

import static a3t.groupartapp.comp3717.artapp.R.id.comment;

/**
 * Created by Ryan on 2017-02-12.
 */

public class Art {
    private String name;
    private List<String> images;
    private int rate;
    private List<String> comments;

    public Art(String name) {
        this.name = name;
        comments = new ArrayList<>();
        images = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void rate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
    public void addComment(String comment) {
        comments.add(comment);

    }

    public List<String> getComment() {
        return comments;
    }

    public void addImage(String imageName) {
        images.add(imageName);
    }

    public List<String> getImages() {
        return images;
    }
}
