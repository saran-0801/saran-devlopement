package com.covalsys.ttss_barcode.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {

    public static final String format_dd_mm_yy_HH_mm = "dd/MM/yy HH:mm";
    public static final String gate_pass_time_format = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String format_dd_mm_yy = "dd/MM/yy";

    public static DateFormat dateFormat(){
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf;
    }

    public static Date dateFormat2(String string) throws ParseException {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date date = sdf.parse(string);
        return date;

    }

    public static DateFormat dateSysFormat(){
        String myFormat = "yyyyMMdd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf;
    }

    public static String currentTime(){
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        return currentTime;
    }

    public static String currentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String currentDateNo(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String currentDate_(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String currentDateYYYY(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String currentDateTimeYYYY(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.US);
        String formattedDate = df.format(c);

        return formattedDate;
    }


    public static String convertDateFormat(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }


    public static String convertDateFormat2(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat3(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }


    public static String convertDateFormat4(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss a", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat5(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat6(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat7(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat8(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }


    public static String convertTimeFormat(String mTime){
        String mNewDate ="";
        try {
            SimpleDateFormat HHmmFormat = new SimpleDateFormat("HH:mm", Locale.US); // 13:55
            Date date = HHmmFormat.parse(mTime);
            SimpleDateFormat hhmmFormat = new SimpleDateFormat("hh:mm a", Locale.US);// 01:55
            mNewDate = hhmmFormat.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }


    public static String convertDateFormat9(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("MMM,yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat10(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat11(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat12(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convertDateFormat13(String mDate){
        String mNewDate ="";
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = sdfSource.parse(mDate);
            SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
            mNewDate = sdfDestination.format(date);
        }catch (ParseException e){
            System.out.println("Parse Exception : " + e);
        }
        return  mNewDate;
    }

    public static String convert_local_to_GMT(String datestring, String inputPattern, String outputPattern) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        TimeZone tz = TimeZone.getTimeZone("GMT");
        outputFormat.setTimeZone(tz);

        Date date = null;
        String formated_date_str = null;

        try {
            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formated_date_str;
    }

    public static String format_date(String source_format, String expected_format, String datestring) {
        String inputPattern = source_format;
        String outputPattern = expected_format;
        Date date = null;
        String formated_date_str = null;
        SimpleDateFormat inputFormat = null;
        SimpleDateFormat outputFormat = null;

        try {
            inputFormat = new SimpleDateFormat(inputPattern);
        } catch (Exception e) {
            e.printStackTrace();
            formated_date_str = "error in source time format";
            return formated_date_str;
        }
        try {
            outputFormat = new SimpleDateFormat(outputPattern);
        } catch (Exception e) {
            e.printStackTrace();
            formated_date_str = "error in expected time format";
            return formated_date_str;
        }

        try {

            date = inputFormat.parse(datestring);
            formated_date_str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
            formated_date_str = "invalid input date";
            return formated_date_str;
        } catch (Exception e) {
            e.printStackTrace();
            formated_date_str = e.getLocalizedMessage();
        } finally {
            return formated_date_str;
        }

    }

}
