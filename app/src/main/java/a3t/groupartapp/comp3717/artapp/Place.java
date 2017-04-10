package a3t.groupartapp.comp3717.artapp;

/**
 * Created by forev on 3/23/2017.
 */

public class Place {
    private String name = "";
    private double longitude = 0;
    private double latitude = 0;
    private double distance = 0;
    public static double CurrLongitude = 0;
    public static double CurrLatitude = 0;

    public Place( String name, double longitude,double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }



}
