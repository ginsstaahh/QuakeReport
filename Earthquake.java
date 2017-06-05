package com.example.android.quakereport;

/**
 * Created by GIN-OFFICE on 6/03/17.
 */

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url){
        // set all private variables to the values given by the constructor
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    //Methods to return the values of the private variables of this class
    public double getMagnitude(){return mMagnitude;}
    public String getLocation(){return mLocation;}
    public long getTimeInMilliseconds(){return mTimeInMilliseconds;}
    public String getUrl(){return mUrl;}
}