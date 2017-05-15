package com.bgp.mycamino.utilities;


import android.content.Context;
import android.widget.Toast;

public class MessageUtility {

    public static void displayToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}