package com.renesis.tech.androidassignment.quizgame.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utilities {
    static ProgressDialog dialog;
    private Context context;


    public Utilities(Context context) {
        this.context = context;
    }


    public static void showProgressDialog(Context ctx, String msg) {
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public static void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }

    }

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();


    }

    public static int getInt(Context context, String key) {

        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        int val = sharedPref.getInt(key, 0);
        return val;

    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static String getString(Context context, String key, String defaultvalue) {

        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        String val = sharedPref.getString(key, defaultvalue);

        return val;

    }

    public static void saveBoolean(Context context, String key, Boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }

    public static Boolean getBoolean(Context context, String key, Boolean defaultvalue) {

        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        Boolean val = sharedPref.getBoolean(key, defaultvalue);

        return val;

    }

    public static void deleteKey(Context context, String key) {

        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.remove(key);
        editor.apply();

    }

    public static void clearSharedPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("SharedStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();


    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static String changeDateFormate(String date, String from, String to) {

        String finalDate = date;

//        SimpleDateFormat date_formate = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat date_formate = new SimpleDateFormat(from);

        Date datee = null;
        try {
            datee = date_formate.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
        SimpleDateFormat df = new SimpleDateFormat(to);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datee);
        finalDate = df.format(calendar.getTime());

        return finalDate;

    }

    public static String differenceBetweenDated(String dates_formate, String dateEnd, String dateStr) {

        String days = "";

        SimpleDateFormat sdf = new SimpleDateFormat(dates_formate);

        try {
            Date dateS = sdf.parse(dateStr);
            Date dateE = sdf.parse(dateEnd);

            long diff = dateE.getTime() - dateS.getTime();


            days = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return days;

    }

    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());

        return currentDate;

    }

    public static String formattTwoDecimal(Context context, int number) {

        if (number == 0) {

            return String.valueOf("AED" + 0.000);
        } else {

            String qa = String.valueOf(number);
            String nocomma = qa.replace(",", "");
            Float f = Float.parseFloat(nocomma);
            String.format("%.2f", f);
            DecimalFormat formatter = new DecimalFormat("#,###,###.00#");
            String yourFormattedString = formatter.format(f);

            return String.valueOf("AED" + yourFormattedString);
        }

    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {

                        return true;
                    }
        }
        return false;
    }

    public static Integer[] randonnum(int total) {
        Integer[] arr = new Integer[total];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        System.out.println(Arrays.toString(arr));
        return arr;
    }
}
