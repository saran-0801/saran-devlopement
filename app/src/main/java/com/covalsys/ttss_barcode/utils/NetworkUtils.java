package com.covalsys.ttss_barcode.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by CBS on 09-07-2020.
 */
public final class NetworkUtils {

    private NetworkUtils() {
        // This class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static boolean isWifiNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  wifiCheck = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        Log.e("TAG_1", wifiCheck.isConnected() +"");
        Log.e("TAG_2", wifiCheck.isConnectedOrConnecting() +"");
        Log.e("TAG_3", wifiCheck.isAvailable() +"");
        Log.e("TAG_4", wifiCheck.isFailover() +"");
        Log.e("TAG_5", wifiCheck.isRoaming() +"");

        if (wifiCheck.isConnectedOrConnecting()) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static String getAddressLog(Context context, int port) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        @SuppressLint("DefaultLocale")
        final String formattedIpAddress = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        return "Open http://" + formattedIpAddress + ":" + port + " in your browser";
    }

}
