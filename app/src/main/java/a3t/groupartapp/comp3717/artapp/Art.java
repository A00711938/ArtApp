package a3t.groupartapp.comp3717.artapp;

import java.util.ArrayList;
import java.util.List;

import static a3t.groupartapp.comp3717.artapp.R.id.comment;

/**
 * Art class stores many components that are described in the
 * new west metadata. This class can be used to store all the
 * meta data from the city of new west - Public Art
 *
 * Created by Ryan on 2017-02-12.
 */

public class Art {
    private String name;
    private String address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address= address;

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

    public String toString(){
        return name;
    }
}
