package com.bgp.mycamino;


import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class GPSLicenseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_license);

        getActionBar().setDisplayHomeAsUpEnabled(true);

//		display license terms here

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

