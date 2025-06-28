package com.covalsys.ttss_barcode.utils;

import android.util.Log;

import com.covalsys.ttss_barcode.BuildConfig;

/**
 * Created by CBS on 09-07-2020.
 */
class Logs {
    private static final boolean ENABLE_LOGS = BuildConfig.DEBUG;
    public static boolean LOGGING_ENABLED = true;

    private Logs() {
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, Exception e) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, OutOfMemoryError e) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static boolean getIsLogsEnabled() {
        return ENABLE_LOGS;
    }

    @SuppressWarnings("unused")
    public static void reportException(Exception e) {
        if (ENABLE_LOGS) {
            Log.e("Exception", e.toString(), e);
        }
    }


    public static void LOGE(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.e("Exception", message);
        }
    }

    public static void LOGE(String message) {
        if (LOGGING_ENABLED) {
            Log.e("Exception", message);
        }
    }

    public static void LOGD(String message) {
        if (LOGGING_ENABLED) {
            Log.d("Exception", message);
        }
    }
}
