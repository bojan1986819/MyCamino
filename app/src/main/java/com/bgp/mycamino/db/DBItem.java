package com.bgp.mycamino.db;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


import java.text.NumberFormat;


public class DBItem implements Parcelable {

//  table old test
    private long id;
    private int part;
    private String name;
    private String city;
    private String type;
    private String description;
    private String link;
    private String email;
    private String facilities;
    private double price;
    private String image;
    private double latitude = 36.778261;
    private double longitude = -119.417932;
    private String markerText = "";
    private String notes;

//  table albergues
    private String albergueName;
    private int albergueType;
    private String address;
    private String tel;
//  private String email;
//  private String link;
    private int credential;
    private int numbeds;
    private int bedcost;
    private int kitchen;
    private int costwithbfast;
    private int costwithmeal;
    private int bfastcost;
    private int mealcost;
    private int picnicpack;
    private int whasm;
    private int drym;
    private int vendmachine;
    private String opentime;
    private String openseason;
    private int reservation;
    private long cityid;
//    private double latitude;
//    private double longitude;
    private int internet;
    private int internetcost;
    private int wifi;
    private int bike;

// table cities
    private String cityname;
//    private String description;
    private double distfromlast;
    private double distfromaltlast;
    private double distfromsjpd;
    private double disttoso;
    private int altitude;
//    private String image;
    private int water;
    private int atmbank;
    private int restaurant;
    private int cafe;
    private int grocery;
    private int hospital;
    private int pharmacy;
    private int post;
    private int taxi;
    private int bus;
    private int train;
    private int airport;
    private int church;
    private int pilgrimof;
    private int munalb;
    private int paralb;
    private int privalb;
    private int hotel;
    private int camp;
//    private double latitude;
//    private double longitude;
    private String stagesid;
    private long citycode;
    private String wikilink;
    private String fbplaceid;


    // table stages
    private String stagename;
    private String daynum;
    private int kmtotal;
//    private String description;
//    private String image;
    private String altimage;
    private String startcity;
    private String endcity;
    private int kmpart;

//table sightsee
    private String sightseename;
//    private int cityid;
//    private String address;
//    private String description;
//    private String image;
//    private double latitude;
//    private double longitude;
    private String source;

    private static String LOGTAG="MYCAMINO";

    public DBItem(){

    }

    public DBItem(Parcel in){
        Log.i(LOGTAG,"Parcel constructor");

        id=in.readLong();
        part=in.readInt();
        name=in.readString();
        city=in.readString();
        type=in.readString();
        description=in.readString();
        link=in.readString();
        email=in.readString();
        facilities=in.readString();
        price=in.readDouble();
        image=in.readString();
        latitude=in.readDouble();
        longitude=in.readDouble();
        markerText=in.readString();
        notes=in.readString();

        albergueName=in.readString();
        albergueType=in.readInt();
        address=in.readString();
        tel=in.readString();
        credential=in.readInt();
        numbeds=in.readInt();
        bedcost=in.readInt();
        kitchen=in.readInt();
        costwithbfast=in.readInt();
        costwithmeal=in.readInt();
        bfastcost=in.readInt();
        mealcost=in.readInt();
        picnicpack=in.readInt();
        whasm=in.readInt();
        drym=in.readInt();
        vendmachine=in.readInt();
        opentime=in.readString();
        openseason=in.readString();
        reservation=in.readInt();
        cityid=in.readLong();
        internet=in.readInt();
        internetcost=in.readInt();
        wifi=in.readInt();
        bike=in.readInt();
        cityname=in.readString();
        distfromlast=in.readDouble();
        distfromaltlast=in.readDouble();
        distfromsjpd=in.readDouble();
        disttoso=in.readDouble();
        altitude=in.readInt();
        water=in.readInt();
        atmbank=in.readInt();
        restaurant=in.readInt();
        cafe=in.readInt();
        grocery=in.readInt();
        hospital=in.readInt();
        pharmacy=in.readInt();
        post=in.readInt();
        taxi=in.readInt();
        bus=in.readInt();
        train=in.readInt();
        airport=in.readInt();
        church=in.readInt();
        pilgrimof=in.readInt();
        munalb=in.readInt();
        paralb=in.readInt();
        privalb=in.readInt();
        hotel=in.readInt();
        camp=in.readInt();
        stagesid=in.readString();
        stagename=in.readString();
        daynum=in.readString();
        kmtotal=in.readInt();
        altimage=in.readString();
        sightseename=in.readString();
        startcity=in.readString();
        endcity=in.readString();
        kmpart=in.readInt();
        citycode=in.readLong();
        wikilink=in.readString();
        source=in.readString();
        fbplaceid=in.readString();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getPart() {
        return part;
    }
    public void setPart(int part) {
        this.part = part;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFacilities() {
        return facilities;
    }
    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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
    public String getMarkerText() {
        return markerText;
    }
    public void setMarkerText(String markerText) {
        this.markerText = markerText;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {this.notes = notes;}

    public String getAlbergueName() { return albergueName; }
    public int getAlbergueType() { return albergueType; }
    public String getAddress() { return address; }
    public String getTel() { return tel; }
    public int getCredential() { return credential; }
    public int getNumbeds() { return numbeds; }
    public int getBedcost() { return bedcost; }
    public int getKitchen() { return kitchen; }
    public int getCostwithbfast() { return costwithbfast; }
    public int getCostwithmeal() { return costwithmeal; }
    public int getBfastcost() { return bfastcost; }
    public int getMealcost() { return mealcost; }
    public int getPicnicpack() { return picnicpack; }
    public int getWhasm() { return whasm; }
    public int getDrym() { return drym; }
    public int getVendmachine() { return vendmachine; }
    public String getOpentime() { return opentime; }
    public String getOpenseason() { return openseason; }
    public int getReservation() { return reservation; }
    public long getCityid() { return cityid; }
    public int getInternet() { return internet; }
    public int getInternetcost() { return internetcost; }
    public int getWifi() { return wifi; }
    public int getBike() { return bike; }
    public String getCityname() { return cityname; }
    public double getDistfromlast() { return distfromlast; }
    public double getDistfromaltlast() { return distfromaltlast; }
    public double getDistfromsjpd() { return distfromsjpd; }
    public double getDisttoso() { return disttoso; }
    public int getAltitude() { return altitude; }
    public int getWater() { return water; }
    public int getAtmbank() { return atmbank; }
    public int getRestaurant() { return restaurant; }
    public int getCafe() { return cafe; }
    public int getGrocery() { return grocery; }
    public int getHospital() { return hospital; }
    public int getPharmacy() { return pharmacy; }
    public int getPost() { return post; }
    public int getTaxi() { return taxi; }
    public int getBus() { return bus; }
    public int getTrain() { return train; }
    public int getAirport() { return airport; }
    public int getChurch() { return church; }
    public int getPilgrimof() { return pilgrimof; }
    public int getMunalb() { return munalb; }
    public int getParalb() { return paralb; }
    public int getPrivalb() { return privalb; }
    public int getHotel() { return hotel; }
    public int getCamp() { return camp; }
    public String getStagesid() { return stagesid; }
    public String getStagename() { return stagename; }
    public String getDaynum() { return daynum; }
    public int getKmtotal() { return kmtotal; }
    public String getAltimage() { return altimage; }
    public String getSightseename() { return sightseename; }
    public int getKmpart() { return kmpart; }
    public String getStartcity() { return startcity; }
    public String getEndcity() { return endcity; }
    public long getCitycode(){return citycode; }
    public String getWikilink(){return wikilink; }
    public String getSource(){return source; }
    public String getFbplaceid(){return fbplaceid; }


    public void setAlbergueName(String albergueName) { this.albergueName = albergueName; }
    public void setAlbergueType(int albergueType) { this.albergueType = albergueType; }
    public void setAddress(String address) { this.address = address; }
    public void setTel(String tel) { this.tel = tel; }
    public void setCredential(int credential) { this.credential = credential; }
    public void setNumbeds(int numbeds) { this.numbeds = numbeds; }
    public void setBedcost(int bedcost) { this.bedcost = bedcost; }
    public void setKitchen(int kitchen) { this.kitchen = kitchen; }
    public void setCostwithbfast(int costwithbfast) { this.costwithbfast = costwithbfast; }
    public void setCostwithmeal(int costwithmeal) { this.costwithmeal = costwithmeal; }
    public void setBfastcost(int bfastcost) { this.bfastcost = bfastcost; }
    public void setMealcost(int mealcost) { this.mealcost = mealcost; }
    public void setPicnicpack(int picnicpack) { this.picnicpack = picnicpack; }
    public void setWhasm(int whasm) { this.whasm = whasm; }
    public void setDrym(int drym) { this.drym = drym; }
    public void setVendmachine(int vendmachine) { this.vendmachine = vendmachine; }
    public void setOpentime(String opentime) { this.opentime = opentime; }
    public void setOpenseason(String openseason) { this.openseason = openseason; }
    public void setReservation(int reservation) { this.reservation = reservation; }
    public void setCityid(long cityid) { this.cityid = cityid; }
    public void setInternet(int internet) { this.internet = internet; }
    public void setInternetcost(int internetcost) { this.internetcost = internetcost; }
    public void setWifi(int wifi) { this.wifi = wifi; }
    public void setBike(int bike) { this.bike = bike; }
    public void setCityname(String cityname) { this.cityname = cityname; }
    public void setDistfromlast(double distfromlast) { this.distfromlast = distfromlast; }
    public void setDistfromaltlast(double distfromaltlast) { this.distfromaltlast = distfromaltlast; }
    public void setDistfromsjpd(double distfromsjpd) { this.distfromsjpd = distfromsjpd; }
    public void setDisttoso(double disttoso) { this.disttoso = disttoso; }
    public void setAltitude(int altitude) { this.altitude = altitude; }
    public void setWater(int water) { this.water = water; }
    public void setAtmbank(int atmbank) { this.atmbank = atmbank; }
    public void setRestaurant(int restaurant) { this.restaurant = restaurant; }
    public void setCafe(int cafe) { this.cafe = cafe; }
    public void setGrocery(int grocery) { this.grocery = grocery; }
    public void setHospital(int hospital) { this.hospital = hospital; }
    public void setPharmacy(int pharmacy) { this.pharmacy = pharmacy; }
    public void setPost(int post) { this.post = post; }
    public void setTaxi(int taxi) { this.taxi = taxi; }
    public void setBus(int bus) { this.bus = bus; }
    public void setTrain(int train) { this.train = train; }
    public void setAirport(int airport) { this.airport = airport; }
    public void setChurch(int church) { this.church = church; }
    public void setPilgrimof(int pilgrimof) { this.pilgrimof = pilgrimof; }
    public void setMunalb(int munalb) { this.munalb = munalb; }
    public void setParalb(int paralb) { this.paralb = paralb; }
    public void setPrivalb(int privalb) { this.privalb = privalb; }
    public void setHotel(int hotel) { this.hotel = hotel; }
    public void setCamp(int camp) { this.camp = camp; }
    public void setStagesid(String stagesid) { this.stagesid = stagesid; }
    public void setStagename(String stagename) { this.stagename = stagename; }
    public void setDaynum(String daynum) { this.daynum = daynum; }
    public void setKmtotal(int kmtotal) { this.kmtotal = kmtotal; }
    public void setAltimage(String altimage) { this.altimage = altimage; }
    public void setSightseename(String sightseename) { this.sightseename = sightseename; }
    public void setKmpart(int kmpart) { this.kmpart = kmpart; }
    public void setStartcity(String startcity) { this.startcity = startcity; }
    public void setEndcity(String endcity) { this.endcity = endcity; }
    public void setCitycode(long citycode){this.citycode=citycode; }
    public void setWikilink(String wikilink){this.wikilink=wikilink;}
    public void setSource(String source){this.source=source;}
    public void setFbplaceid(String fbplaceid){this.fbplaceid=fbplaceid; }



    @Override
    public String toString(){
        NumberFormat nf= NumberFormat.getCurrencyInstance();
        return name+"\n("+nf.format(price)+") ";

    }

    public LatLng getLatLng(){
        LatLng latLng=new LatLng(latitude,longitude);
        return latLng;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(id);
        dest.writeInt(part);
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(email);
        dest.writeString(facilities);
        dest.writeDouble(price);
        dest.writeString(image);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(markerText);
        dest.writeString(notes);

        dest.writeString(albergueName);
        dest.writeInt(albergueType);
        dest.writeString(address);
        dest.writeString(tel);
        dest.writeInt(credential);
        dest.writeInt(numbeds);
        dest.writeInt(bedcost);
        dest.writeInt(kitchen);
        dest.writeInt(costwithbfast);
        dest.writeInt(costwithmeal);
        dest.writeInt(bfastcost);
        dest.writeInt(mealcost);
        dest.writeInt(picnicpack);
        dest.writeInt(whasm);
        dest.writeInt(drym);
        dest.writeInt(vendmachine);
        dest.writeString(opentime);
        dest.writeString(openseason);
        dest.writeInt(reservation);
        dest.writeLong(cityid);
        dest.writeInt(internet);
        dest.writeInt(internetcost);
        dest.writeInt(wifi);
        dest.writeInt(bike);
        dest.writeString(cityname);
        dest.writeDouble(distfromlast);
        dest.writeDouble(distfromaltlast);
        dest.writeDouble(distfromsjpd);
        dest.writeDouble(disttoso);
        dest.writeInt(altitude);
        dest.writeInt(water);
        dest.writeInt(atmbank);
        dest.writeInt(restaurant);
        dest.writeInt(cafe);
        dest.writeInt(grocery);
        dest.writeInt(hospital);
        dest.writeInt(pharmacy);
        dest.writeInt(post);
        dest.writeInt(taxi);
        dest.writeInt(bus);
        dest.writeInt(train);
        dest.writeInt(airport);
        dest.writeInt(church);
        dest.writeInt(pilgrimof);
        dest.writeInt(munalb);
        dest.writeInt(paralb);
        dest.writeInt(privalb);
        dest.writeInt(hotel);
        dest.writeInt(camp);
        dest.writeString(stagesid);
        dest.writeString(stagename);
        dest.writeString(daynum);
        dest.writeInt(kmtotal);
        dest.writeString(altimage);
        dest.writeString(sightseename);
        dest.writeInt(kmpart);
        dest.writeString(startcity);
        dest.writeString(endcity);
        dest.writeLong(citycode);
        dest.writeString(wikilink);
        dest.writeString(source);
        dest.writeString(fbplaceid);
    }

    public static final Parcelable.Creator<DBItem> CREATOR=
            new Parcelable.Creator<DBItem>(){
                @Override
                public DBItem createFromParcel(Parcel source){
                    return new DBItem(source);
                }

                @Override
                public DBItem[] newArray(int size){
                    return new DBItem[size];
                }
            };




}
