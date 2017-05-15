package com.bgp.mycamino;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ImageViewer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_image_viewer);

        Intent intent = getIntent();
        String altimagename = intent.getStringExtra("selected_image");


        String imageUrl = "file:///android_res/drawable/";
        WebView wv = (WebView) findViewById(R.id.yourwebview);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.loadDataWithBaseURL(imageUrl, "<img src='"+altimagename+".jpg' />", "text/html", "utf-8", null);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);


    }

    public void closeImage(View v){
        finish();
    }
}
