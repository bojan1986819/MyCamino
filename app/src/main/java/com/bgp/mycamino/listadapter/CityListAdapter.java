package com.bgp.mycamino.listadapter;

import java.util.List;

import com.bgp.mycamino.R;
import com.bgp.mycamino.db.DBItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CityListAdapter extends ArrayAdapter<DBItem> {
    Context context;
    List<DBItem> entries;


    public CityListAdapter(Context context, List<DBItem> entries){
        super(context, android.R.id.content, entries);
        this.context =context;
        this.entries=entries;
    }



    @Override
    // city_list_item entry-hez adom meg hogy mi legyen a listában
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.city_listitem_entry, null);

        DBItem entry = entries.get(position);

        TextView tv = (TextView) view.findViewById(R.id.cityNameText);
        tv.setText(entry.getCityname());

        tv = (TextView) view.findViewById(R.id.distText);
        tv.setText(String.valueOf(Math.round(entry.getDistfromlast()))+"km");

        tv = (TextView) view.findViewById(R.id.distSoText);
        tv.setText(String.valueOf(Math.round(entry.getDisttoso()))+"km");

        ImageView iv =(ImageView)view.findViewById(R.id.mAlbergueIcon);
        int mAlb = entry.getMunalb();
        if (mAlb == 1) {
            iv.setImageResource(R.drawable.cityic_mbed);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.pAlbergueIcon);
        int pAlb = entry.getParalb();
        if (pAlb == 1) {
            iv.setImageResource(R.drawable.cityic_pbed);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.hAlbergueIcon);
        int hAlb = entry.getPrivalb();
        if (hAlb == 1) {
            iv.setImageResource(R.drawable.cityic_hbed);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.restaurantIcon);
        int rest = Math.round(entry.getRestaurant());
        if (rest == 1) {
            iv.setImageResource(R.drawable.cityic_rest);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.hospitalIcon);
        int hosp = entry.getHospital();
        if (hosp == 1) {
            iv.setImageResource(R.drawable.cityic_hospital);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.bankIcon);
        int bank = entry.getAtmbank();
        if (bank == 1) {
            iv.setImageResource(R.drawable.cityic_atm);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.busIcon);
        int bus = entry.getBus();
        if (bus == 1) {
            iv.setImageResource(R.drawable.cityic_bus);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.cafeIcon);
        int cafe = entry.getCafe();
        if (cafe == 1) {
            iv.setImageResource(R.drawable.cityic_cafe);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.pharIcon);
        int phar = entry.getPharmacy();
        if (phar == 1) {
            iv.setImageResource(R.drawable.cityic_pharm);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.pilgrimOfIcon);
        int pilo = entry.getPilgrimof();
        if (pilo == 1) {
            iv.setImageResource(R.drawable.cityic_pilgromof);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.postIcon);
        int post = entry.getPost();
        if (post == 1) {
            iv.setImageResource(R.drawable.cityic_post);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.shopIcon);
        int shop = entry.getGrocery();
        if (shop == 1) {
            iv.setImageResource(R.drawable.cityic_shop);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.waterIcon);
        int water = entry.getWater();
        if (water == 1) {
            iv.setImageResource(R.drawable.cityic_water);
        } else {
            iv.setVisibility(View.GONE);
        }


        return view;
    }

}
