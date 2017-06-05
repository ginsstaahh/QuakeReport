package com.example.android.quakereport;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.start;

/**
 * Created by GIN-OFFICE on 6/03/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";

    //Constructor using an ArrayList of earthquake data
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /*
    * Returns a list item view that displays information about the earthquake at the given position
    * in the list of earthquakes.
    * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if there is an existing list item view (called convertView) that we can reuse,
        //otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        //Find the earthquake at the given position in the list of earthquakes
        final Earthquake currentEarthquake = this.getItem(position);

        //Format the magnitude value to always show values with one decimal place
        double magnitude = currentEarthquake.getMagnitude();
        String formattedMagnitude = formatMagnitude(magnitude);

        //Find the TextViews using their ID's and set the text to the given data
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeView.setText(formattedMagnitude);

        //Set the proper background color on the magnitude circle.
        //Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        //Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        //Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //Need to split the location string into two parts if the string says that the earthquake
        //occured a distance away from a city.  Create two strings, one for the closest city (primaryLocation)
        //and another for the distance away from it (locationOffset)
        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation, locationOffset;

        //if the original string contains the word "of", then we know that there is a distance offset
        if (originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        //Find the TextViews using their ID's and set the text to the given data
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        //Create new date object from the time in milliseconds
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        //Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);

        //Find the TextViews using their ID's and set the text to the given data
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(formattedDate);

        //format the time string (i.e. "4:30 PM")
        String formattedTime = formatTime(dateObject);

        //Find the TextViews using their ID's and set the text to the given data
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(formattedTime);

        //Override the onClick method so that when the view is pressed, an intent is sent to the
        //browser to direct the user to the website with more information of that particular earthquake
        listItemView.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View V){
               Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquake.getUrl()));
               getContext().startActivity(websiteIntent);
           }
        });

        return listItemView;
    }

    //Return the formatted magnitude string showing only one decimal place
    private String formatMagnitude(double magnitude){
        return new DecimalFormat("0.0").format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeFloor = (int) Math.floor(magnitude);
        int magnitudeColorResourceId;

        switch(magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                //any earthquake with magnitude higher than 10 will use the R.color.magnitude10plus color resource
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
        return magnitudeColorResourceId;
    }

    //return the formatted date string (i.e. "Mar 3, 1984") from a Date object
    private String formatDate(Date dateObject) {
        return new SimpleDateFormat("LLL dd, yyyy").format(dateObject);
    }

    //return the formatted time string (i.e. "4:30 PM") from a Date object
    private String formatTime(Date dateObject) {
        return new SimpleDateFormat("h:mm a").format(dateObject);
    }
}
