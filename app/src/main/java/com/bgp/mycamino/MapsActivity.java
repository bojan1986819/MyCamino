package com.bgp.mycamino;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.bgp.mycamino.db.DBDataSource;
import com.bgp.mycamino.db.DBItem;
import com.bgp.mycamino.db.RouteDataSource;
import com.bgp.mycamino.db.RouteItem;
import com.bgp.mycamino.utilities.GooglePlayServiceUtility;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean mShowMap;
    private List<DBItem> mEntries;
    DBDataSource mDataSource;
    RouteDataSource rDataSource;
    CameraUpdate cu;
    ArrayList<LatLng> arrayPoints = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int heigth=dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (heigth * .9));

        arrayPoints = new ArrayList<LatLng>();

        mDataSource = new DBDataSource(this);
        mDataSource.open();
        rDataSource= new RouteDataSource(this);
        rDataSource.open();

        mShowMap = GooglePlayServiceUtility.isPlayServiceAvailable(this) && initMap();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        showMarkers();

        drawMarkerLine();

        fixZoom();
    }

    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return (mMap != null);
    }



    private void showMarkers() {

        Intent intent = getIntent();


        Integer type = intent.getIntExtra("type", 0);

        if (type == 1) {
//            stage lapról
            Long part = intent.getLongExtra("part", 0);
            String extras = String.valueOf(part);

            mEntries = mDataSource.findCitiesFiltered("STAGESID = " + extras);

            for (int i = 0; i < mEntries.size(); i++) {


                mMap.addMarker(new MarkerOptions()
                                .position(mEntries.get(i).getLatLng())
                                .title(String.valueOf(mEntries.get(i).getCityname()))
                                .anchor(.5f, .5f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                );
            }
        } else if(type==2){
//            albergés lap
            Long extras = intent.getLongExtra("part", 0);

            mEntries = mDataSource.findAlberguesFiltered("CITYID = " + extras);

            for (int i = 0; i < mEntries.size(); i++) {


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
        } else if (type==3){
//            látnivalós lapról
            Long extras = intent.getLongExtra("part",0);
            mEntries = mDataSource.findSightseeFiltered("CITYID = " + extras);

            for (int i = 0; i < mEntries.size(); i++) {


                mMap.addMarker(new MarkerOptions()
                                .position(mEntries.get(i).getLatLng())
                                .title(String.valueOf(mEntries.get(i).getSightseename()))
                                .anchor(.5f, .5f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapic_atract))
                );
            }
        } else if (type==4){
//            város lapról
            Long extras = intent.getLongExtra("part",0);
            mEntries = mDataSource.findSightseeFiltered("CITYID = " + extras);

            for (int i = 0; i < mEntries.size(); i++) {


                mMap.addMarker(new MarkerOptions()
                                .position(mEntries.get(i).getLatLng())
                                .title(String.valueOf(mEntries.get(i).getSightseename()))
                                .anchor(.5f, .5f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapic_atract))
                );
            }

            mEntries = mDataSource.findAlberguesFiltered("CITYID = " + extras);

            for (int i = 0; i < mEntries.size(); i++) {


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

        } else if(type==0) {
//            főoldalról
            mEntries = mDataSource.findCitiesFiltered("CAMPSITE = 1");

            for (int i = 0; i < mEntries.size(); i++) {


                mMap.addMarker(new MarkerOptions()
                                .position(mEntries.get(i).getLatLng())
                                .title(String.valueOf(mEntries.get(i).getCityname()))
                                .anchor(.5f, .5f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                );
            }

        }
    }

    private void fixZoom() {

        Intent intent = getIntent();

        Long part = intent.getLongExtra("part", 0);
        String extras=String.valueOf(part);
        Integer type = intent.getIntExtra("type", 0);
        Double citylat=intent.getDoubleExtra("citylat", 0);
        Double citylong=intent.getDoubleExtra("citylong", 0);

        if(type==1) {
            LatLngBounds.Builder bc = new LatLngBounds.Builder();
            mEntries = mDataSource.findCitiesFiltered("STAGESID = " + extras);


            for (DBItem m : mEntries) {
                bc.include(m.getLatLng());
            }

            LatLngBounds bounds = bc.build();

            cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);



            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(cu);
                }
            });
        } else if(type==2){
            LatLng latLng=new LatLng(citylat,citylong);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } else if(type==3){
            LatLng latLng=new LatLng(citylat,citylong);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } else if(type==4){
            LatLng latLng=new LatLng(citylat,citylong);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } else if(type==0) {
            LatLngBounds.Builder bc = new LatLngBounds.Builder();
            mEntries = mDataSource.findCitiesFiltered("WATER = 1");


            for (DBItem m : mEntries) {
                bc.include(m.getLatLng());
            }

            LatLngBounds bounds = bc.build();

            cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(cu);
                }
            });
        }
    }

    private void drawMarkerLine(){

        Intent intent = getIntent();
        Integer type = intent.getIntExtra("type", 0);

        if(type==0){
//            List<RouteItem> rEntries = rDataSource.showRoute();
//
//            for (RouteItem m : rEntries){
//                PolylineOptions options = new PolylineOptions();
//
//                arrayPoints.add(m.getLatLng());
//                options.addAll(arrayPoints);
//
//                mMap.addPolyline(options);
//            }
        }
        else {
            Long part = intent.getLongExtra("stagepart", 0);
            String extras = String.valueOf(part);

            List<RouteItem> rEntries = rDataSource.showRouteFiltered("STAGEID= " + extras);

            for (RouteItem m : rEntries) {
                PolylineOptions options = new PolylineOptions();
                options.color(Color.BLUE);
                arrayPoints.add(m.getLatLng());
                options.addAll(arrayPoints);

                mMap.addPolyline(options);
            }
        }
    }

/*
    private void drawRouteOverlay(){
        List<RouteItem> K = rDataSource.getAllDatabaseObject();
        for (RouteItem cn : K) {
            point = new Barcode.GeoPoint((int)(cn.getLatitude()), (int)(cn.getLongitude()));
            overlayitem = new RouteItem(point, cn.getname(), cn.getinfo());
            itemizedOverlay.addOverlay(RouteItem);
            mapOverlays.add(itemizedOverlay);
        }
    }
*/

    public void showMyLocation(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            Toast.makeText(getApplicationContext(), "Please wait for Location", Toast.LENGTH_LONG).show();
        } else {
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

}



