package com.nagirescue;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by cipher1729 on 10/10/2015.
 */
public class AnimalServicesFragment extends Fragment {
    public static View rootView;
    public static double latitude=-1;
    public static double longitude=-1;
    LocationListener locationListener;
    LocationManager locationManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutId= R.layout.animalservicelayout;
        rootView = inflater.inflate(layoutId, container, false);
        //get latitude and longitude

        setOnClickListeners();
        return rootView;
    }

    private void setOnClickListeners() {
        locationManager = (LocationManager)(getActivity().getSystemService(Context.LOCATION_SERVICE));
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        //search gps
        ((Button)rootView.findViewById(R.id.gpsBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                PlacesTask placesTask= new PlacesTask();
                placesTask.execute(getActivity());
            }
        });


        //search address
        ((Button)rootView.findViewById(R.id.addressBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            }
        });




    }

}
