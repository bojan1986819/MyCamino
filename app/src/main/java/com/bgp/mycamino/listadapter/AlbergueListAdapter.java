package com.bgp.mycamino.listadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgp.mycamino.R;
import com.bgp.mycamino.db.DBItem;

import java.util.List;

public class AlbergueListAdapter extends ArrayAdapter<DBItem> {
    Context context;
    List<DBItem> entries;


    public AlbergueListAdapter(Context context, List<DBItem> entries){
        super(context, android.R.id.content, entries);
        this.context =context;
        this.entries=entries;
    }

    @Override
    // albergue_list_item entry-hez adom meg hogy mi legyen a listában
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.albergue_listitem_entry, null);

        final DBItem entry = entries.get(position);

        TextView tv = (TextView) view.findViewById(R.id.nameText);
        tv.setText(entry.getAlbergueName());


        tv = (TextView) view.findViewById(R.id.emailText);
        tv.setText(entry.getEmail());
        tv.setTextColor(Color.BLUE);
        Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        tv = (TextView) view.findViewById(R.id.linkText);
        tv.setText(Html.fromHtml("<a href=http://" + entry.getLink() + ">" + entry.getLink() + "</a> "));
        tv.setMovementMethod(LinkMovementMethod.getInstance());


        tv = (TextView) view.findViewById(R.id.telText);
        tv.setText(entry.getTel());
        tv.setTextColor(Color.BLUE);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + entry.getTel()));
                context.startActivity(callIntent);
            }
        });

        tv = (TextView) view.findViewById(R.id.addressText);
        tv.setText(entry.getAddress());

        tv = (TextView) view.findViewById(R.id.seasonText);
        tv.setText(entry.getOpenseason());

        tv = (TextView) view.findViewById(R.id.timeText);
        tv.setText(entry.getOpentime());

        tv = (TextView) view.findViewById(R.id.bednrText);
        tv.setText(String.valueOf(entry.getNumbeds()));

        tv = (TextView) view.findViewById(R.id.priceText);
        tv.setText(String.valueOf(entry.getBedcost()));

        tv = (TextView) view.findViewById(R.id.bfText);
        int bfincl = entry.getCostwithbfast();
        int bfcost= entry.getBfastcost();

        if(bfincl==1){
            tv.setText("incl.");
        } else {
            if(bfcost==0) {
                tv.setText("");
            } else {
                tv.setText(String.valueOf(entry.getBfastcost()));
            }
        }

        tv = (TextView) view.findViewById(R.id.mealText);
        int mealincl = entry.getCostwithmeal();
        int mealcost= entry.getMealcost();

        if(mealincl==1){
            tv.setText("incl.");
        } else {
            if(mealcost==0) {
                tv.setText("");
            } else {
                tv.setText(String.valueOf(entry.getMealcost()));
            }
        }

        ImageView iv =(ImageView)view.findViewById(R.id.kitchenImg);
        int kit = entry.getKitchen();
        if (kit == 1) {
            iv.setImageResource(R.drawable.albic_kitchen);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.wmImg);
        int wm = entry.getWhasm();
        if (wm > 0) {
            iv.setImageResource(R.drawable.albic_wm);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.dryerImg);
        int dry = entry.getDrym();
        if (dry > 0) {
            iv.setImageResource(R.drawable.albic_dryer);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.internetImg);
        int inter = entry.getInternet();
        if (inter == 1) {
            iv.setImageResource(R.drawable.albic_internet);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.wifiImg);
        int wifi = entry.getWifi();
        if (wifi == 1) {
            iv.setImageResource(R.drawable.albic_wifi);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.vendingmImg);
        int vend = entry.getVendmachine();
        if (vend == 1) {
            iv.setImageResource(R.drawable.albic_vendm);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.bikeImg);
        int bike = entry.getBike();
        if (bike == 1) {
            iv.setImageResource(R.drawable.albic_bike);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.credImg);
        int cred = entry.getCredential();
        if (cred == 1) {
            iv.setImageResource(R.drawable.cityic_pilgromof);
        } else {
            iv.setVisibility(View.GONE);
        }

        iv =(ImageView)view.findViewById(R.id.bfImg);
        if (bfcost > 0) {
            iv.setImageResource(R.drawable.albic_bf);
        } else {
            if(bfincl==1) {
                iv.setImageResource(R.drawable.albic_bf);
            } else {
                iv.setVisibility(View.GONE);
            }
        }

        iv =(ImageView)view.findViewById(R.id.mealImg);
        if (mealcost > 0) {
            iv.setImageResource(R.drawable.albic_meal);
        } else {
            if(mealincl==1) {
                iv.setImageResource(R.drawable.albic_meal);
            } else {
                iv.setVisibility(View.GONE);
            }
        }

        iv =(ImageView)view.findViewById(R.id.albtypeImg);
        int albtyp = entry.getAlbergueType();
        if (albtyp == 0) {
            iv.setImageResource(R.drawable.cityic_pilgromof);
        } else {
            if(albtyp==1){
                iv.setImageResource(R.drawable.cityic_mbed);
            } else {
                if (albtyp == 3){
                    iv.setImageResource(R.drawable.cityic_hbed);
                } else {
                    iv.setImageResource(R.drawable.cityic_pbed);
                }
            }
        }

                return view;
    }

}
