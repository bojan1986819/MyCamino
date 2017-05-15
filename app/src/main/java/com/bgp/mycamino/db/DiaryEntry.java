package com.bgp.mycamino.db;


import android.os.Parcel;
import android.os.Parcelable;

public class DiaryEntry implements Parcelable{
    private int id;
    private String entrydate;
    private String text;
    private String topic;
    private String cityname;
    private String image;

    public DiaryEntry(){

    }

    public DiaryEntry(Parcel in){
        id=in.readInt();
        entrydate=in.readString();
        text=in.readString();
        topic=in.readString();
        cityname=in.readString();
        image=in.readString();
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setEntryDate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntryDate() {
        return entrydate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(entrydate);
        dest.writeString(text);
        dest.writeString(topic);
        dest.writeString(cityname);
        dest.writeString(image);
    }

    public static final Parcelable.Creator<DiaryEntry> CREATOR=
            new Parcelable.Creator<DiaryEntry>(){
                @Override
                public DiaryEntry createFromParcel(Parcel source){
                    return new DiaryEntry(source);
                }

                @Override
                public DiaryEntry[] newArray(int size){
                    return new DiaryEntry[size];
                }
            };
}
