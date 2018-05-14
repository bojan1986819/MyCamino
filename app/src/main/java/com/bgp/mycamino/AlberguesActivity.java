package com.bgp.mycamino;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bgp.mycamino.db.DBDataSource;
import com.bgp.mycamino.db.DBItem;
import com.bgp.mycamino.db.DBOpenHelper;
import com.bgp.mycamino.listadapter.AlbergueListAdapter;
import com.bgp.mycamino.listadapter.CityListAdapter;
import com.bgp.mycamino.utilities.GooglePlayServiceUtility;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.List;

public class AlberguesActivity extends ListActivity implements OnMapReadyCallback {

    DBItem mEntry;
    boolean mShowMap;
    GoogleMap mMap;
    private List<DBItem> mEntries;
    DBDataSource mDataSource;
    DBOpenHelper dbOpenHelper = null;
    private static final int PERMS_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_albergues);
        dbOpenHelper=new DBOpenHelper(this);

//        dbOpenHelper.createDatabase();

        mDataSource = new DBDataSource(this);
        mDataSource.open();


        mShowMap = GooglePlayServiceUtility.isPlayServiceAvailable(this) && initMap();

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);

        menu.setClosedOnTouchOutside(true);

        Intent intent=getIntent();
        Long cityid=intent.getLongExtra("cityid",0);
        mEntries=mDataSource.findAlberguesFiltered("CITYID = " + cityid);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        displayAlbergueDetails();
    }

    private boolean initMap() {
        if (mMap == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return (mMap != null);
    }

    @Override
    public void onBackPressed() {
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        if(menu.isOpened()) {
            menu.close(true);
        }else{
            finish();
        }
    }


    public void openMap(View v){

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);
        Intent i = new Intent(this, MapsActivity.class);
        Intent intent=getIntent();

        Long cityid=intent.getLongExtra("cityid", 0);
        Double citylat=intent.getDoubleExtra("citylat", 0);
        Double citylong=intent.getDoubleExtra("citylong", 0);
        String cityname=intent.getStringExtra("cityname");
        Long daynum = intent.getLongExtra("daynum", 0);
        Integer type=2;
        i.putExtra("stagepart",daynum);
        i.putExtra("part", cityid);
        i.putExtra("citylat", citylat);
        i.putExtra("citylong",citylong);
        i.putExtra("type",type);
        startActivity(i);
    }

    public void openJournal(View v){

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, DiaryListActivity.class);
        startActivity(i);

    }

    public void showMyLocation(View v) {

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            Toast.makeText(getApplicationContext(), "Please wait for Location", Toast.LENGTH_LONG).show();
        } else {
            String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions,PERMS_REQUEST_CODE);
            Snackbar.make(findViewById(android.R.id.content), "Permission is needed to be able to show your current location on the map", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
        }
        // Check if we were successful in obtaining the map.
        if (mMap != null) {


            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location arg0) {
                    // TODO Auto-generated method stub

                    mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    LatLng latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                }
            });
        }
    }

    private void displayAlbergueDetails() {

        ArrayAdapter<DBItem> adapter = new AlbergueListAdapter(this, mEntries);
        setListAdapter(adapter);

        Intent intent=getIntent();
        Long cityid=intent.getLongExtra("cityid", 0);
        Double citylat=intent.getDoubleExtra("citylat", 0);
        Double citylong=intent.getDoubleExtra("citylong",0);
        String cityname=intent.getStringExtra("cityname");

        TextView tv =(TextView)findViewById(R.id.cityNameText);
        tv.setText(cityname+ " albergues");


            mEntries=mDataSource.findAlberguesFiltered("CITYID = " + cityid);

            for (int i =0; i<mEntries.size(); i++){
                int albtyp= mEntries.get(i).getAlbergueType();


                if (albtyp == 0) {
                    mMap.addMarker(new MarkerOptions()
                                    .position(mEntries.get(i).getLatLng())
                                    .title(String.valueOf(mEntries.get(i).getAlbergueName()))
                                    .anchor(.5f, .5f)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapic_pilgrimof))
                    );
                } else {
                    if(albtyp==1){
                        mMap.addMarker(new MarkerOptions()
                                        .position(mEntries.get(i).getLatLng())
                                        .title(String.valueOf(mEntries.get(i).getAlbergueName()))
                                        .anchor(.5f, .5f)
                                        .snippet("Beds: " + String.valueOf(mEntries.get(i).getNumbeds()) + " Cost: " + String.valueOf(mEntries.get(i).getBedcost()))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapic_mbed))
                        );
                    } else {
                        if (albtyp == 3){
                            mMap.addMarker(new MarkerOptions()
                                            .position(mEntries.get(i).getLatLng())
                                            .title(String.valueOf(mEntries.get(i).getAlbergueName()))
                                            .anchor(.5f, .5f)
                                            .snippet("Beds: " + String.valueOf(mEntries.get(i).getNumbeds()) + " Cost: " + String.valueOf(mEntries.get(i).getBedcost()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapic_hbed))
                            );
                        } else {
                            mMap.addMarker(new MarkerOptions()
                                            .position(mEntries.get(i).getLatLng())
                                            .title(String.valueOf(mEntries.get(i).getAlbergueName()))
                                            .anchor(.5f, .5f)
                                            .snippet("Beds: " + String.valueOf(mEntries.get(i).getNumbeds()) + " Cost: " + String.valueOf(mEntries.get(i).getBedcost()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapic_pbed))
                            );
                        }
                    }
                }

            }

        LatLng latLng=new LatLng(citylat,citylong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        mMap.clear();
        Intent intent=getIntent();
        Long cityid=intent.getLongExtra("cityid",0);
        mEntries=mDataSource.findAlberguesFiltered("CITYID = " + cityid);

        mEntry = mEntries.get(position);

//        mMap.addMarker(new MarkerOptions()
//                        .position(mEntry.getLatLng())
//                        .title(String.valueOf(mEntry.getAlbergueName()))
//                        .anchor(.5f, .5f)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_starmarker))
//        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mEntry.getLatLng(), 17));
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 1000, null);

    }

    public void openAtractions(View v){
        Intent intentget=getIntent();
        Long cityid=intentget.getLongExtra("cityid", 0);
        Double citylat=intentget.getDoubleExtra("citylat", 0);
        Double citylong=intentget.getDoubleExtra("citylong", 0);
        String cityname=intentget.getStringExtra("cityname");
        Long daynum = intentget.getLongExtra("daynum", 0);
        String fbplaceid=intentget.getStringExtra("fbplaceid");
        Intent intent = new Intent(this, AtractionsActivity.class);
        intent.putExtra("daynum",daynum);
        intent.putExtra("cityid", cityid);
        intent.putExtra("citylat", citylat);
        intent.putExtra("citylong", citylong);
        intent.putExtra("cityname", cityname);
        intent.putExtra("source","");
        intent.putExtra("imagename", "empty");
        intent.putExtra("atrname", "Chose an atraction from the list below");
        intent.putExtra("desc", "");
        intent.putExtra("fbplaceid", fbplaceid);
        intent.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        this.finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void backToCity(View v){
        this.finish();
    }

    public void openIconLegend(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, IconLegend.class);
        startActivity(i);
    }

    public void shareOnFB(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent intentget=getIntent();
        String cityname=intentget.getStringExtra("cityname");
        String fbplaceid=intentget.getStringExtra("fbplaceid");


        Intent i = new Intent(this, FacebookActivity.class);
        i.putExtra("cityname", cityname);
        i.putExtra("fbplaceid", fbplaceid);
        i.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        startActivity(i);
    }

    public void albergueShareOnFB(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        LinearLayout vwParentRow = (LinearLayout) v.getParent().getParent();
        RelativeLayout rlRow = (RelativeLayout) vwParentRow.getChildAt(0);
        TextView textView = (TextView)rlRow.getChildAt(0);
        String albname = textView.getText().toString();
        Intent intentget=getIntent();
        String cityname=intentget.getStringExtra("cityname");
        String fbplaceid=intentget.getStringExtra("fbplaceid");
//                Toast.makeText(getApplicationContext(), albname, Toast.LENGTH_LONG).show();


        Intent i = new Intent(this, FacebookActivity.class);
        i.putExtra("cityname", cityname);
        i.putExtra("alberguename", albname);
        i.putExtra("fbplaceid", fbplaceid);
        i.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        startActivity(i);
    }
}
