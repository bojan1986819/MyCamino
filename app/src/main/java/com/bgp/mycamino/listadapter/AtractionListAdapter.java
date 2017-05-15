package com.bgp.mycamino.listadapter;

//itt adom meg hogyan nézzen ki az itemek kilistázása miután rákattintok az egyikre

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bgp.mycamino.R;
import com.bgp.mycamino.db.DBItem;

import java.util.List;

public class AtractionListAdapter extends ArrayAdapter<DBItem> {
    Context context;
    List<DBItem> entries;


    public AtractionListAdapter(Context context, List<DBItem> entries){
        super(context, android.R.id.content, entries);
        this.context =context;
        this.entries=entries;
    }



    @Override
    // itt adom meg hogy a main activitinél mit mutasson a lista elemeinél, a stage_listitem_entry.xml-el együtt kell szerkeszteni
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.atraction_listitem_entry, null);

        DBItem entry = entries.get(position);

        TextView tv = (TextView) view.findViewById(R.id.nameText);
        tv.setText(entry.getSightseename());


        tv = (TextView) view.findViewById(R.id.addressText);
        tv.setText(entry.getAddress());



        return view;
    }

}
