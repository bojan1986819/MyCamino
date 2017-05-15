package com.bgp.mycamino.db;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class RouteItem implements Parcelable {
    private long id;
    private double latitude;
    private double longitude;
    private double elevation;
    private String stagid;

    public RouteItem(){

    }

    public RouteItem(Parcel in) {
        Log.i("Route", "Parcel constructor");

        id = in.readLong();
        latitude = in.readDouble();
        longitude = in.readDouble();
        elevation = in.readDouble();
        stagid = in.readString();
    }

    public RouteItem(double lat, double lng){
        this.latitude=lat;
        this.longitude=lng;
    }

    public long getID() {
        return id;
    }
    public void setID(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getStagid() {
        return stagid;
    }
    public void setStagid(String longitude) {
        this.stagid = stagid;
    }

    public LatLng getLatLng(){
        LatLng latLng=new LatLng(latitude,longitude);
        return latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(elevation);
        dest.writeString(stagid);
    }

    public static final Parcelable.Creator<RouteItem> CREATOR=
            new Parcelable.Creator<RouteItem>(){
                @Override
                public RouteItem createFromParcel(Parcel source){
                    return new RouteItem(source);
                }

                @Override
                public RouteItem[] newArray(int size){
                    return new RouteItem[size];
                }
            };
}
