package com.bgp.mycamino;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by bojan on 2016. 02. 04..
 */
public class IconLegend extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_iconlegend);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int heigth=dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(heigth*.9));
    }
}
