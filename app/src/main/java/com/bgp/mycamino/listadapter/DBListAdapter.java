package com.bgp.mycamino.listadapter;

//itt adom meg hogyan nézzen ki az itemek kilistázása miután rákattintok az egyikre

import java.util.List;

import com.bgp.mycamino.R;
import com.bgp.mycamino.db.DBItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DBListAdapter extends ArrayAdapter<DBItem> {
    Context context;
    List<DBItem> entries;


    public DBListAdapter(Context context, List<DBItem> entries){
        super(context, android.R.id.content, entries);
        this.context =context;
        this.entries=entries;
    }



    @Override
    // itt adom meg hogy a main activitinél mit mutasson a lista elemeinél, a stage_listitem_entry.xml-el együtt kell szerkeszteni
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.stage_listitem_entry, null);

        DBItem entry = entries.get(position);

        TextView tv = (TextView) view.findViewById(R.id.nameText);
        tv.setText(entry.getStagename());


//        tv = (TextView) view.findViewById(R.id.nameText2);
//        tv.setText(entry.getEndcity());

        tv = (TextView) view.findViewById(R.id.partText);
        tv.setText(String.valueOf(entry.getDaynum()));

        tv = (TextView) view.findViewById(R.id.kmText);
        //NumberFormat nf = NumberFormat.getCurrencyInstance();
        //tv.setText(nf.format(entry.getKmtotal()));
        tv.setText(String.valueOf(entry.getKmpart())+" KM");

        tv = (TextView) view.findViewById(R.id.kmText2);
        tv.setText(String.valueOf(entry.getKmtotal())+" KM");


        // ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
        // int imageResource = context.getResources().getIdentifier(
        //         albergue.getImage(), "drawable", context.getPackageName());
        // if (imageResource != 0) {
        //     iv.setImageResource(imageResource);
        // }

        return view;
    }

}
