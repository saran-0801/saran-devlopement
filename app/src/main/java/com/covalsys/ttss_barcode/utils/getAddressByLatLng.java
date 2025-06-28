package com.covalsys.ttss_barcode.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by CBS on 15-07-2020.
 */
public class getAddressByLatLng {
    static String address;
    static LatLng latLng = null;

    public static String getAddress(double lat, double lng, Context context){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);

            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "no network", Toast.LENGTH_SHORT).show();
        }
        return address;
    }


    public static LatLng getLatLngByAddress(String strAddress,Context context){
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            //check for null
            if (address == null) {
                return latLng;
            }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.e("latLng", String.valueOf(latLng));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }
}
