package com.renesis.tech.androidassignment.quizgame.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.paperdb.Paper;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utils {




    public static boolean isInternetAvailable_for_popup(Activity context) {
        Paper.init(context);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


            return isConnected;


    }



}
