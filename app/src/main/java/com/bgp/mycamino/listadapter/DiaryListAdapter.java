package com.bgp.mycamino.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bgp.mycamino.R;
import com.bgp.mycamino.db.DiaryEntry;

import java.util.List;


public class DiaryListAdapter extends ArrayAdapter<DiaryEntry> {
    Context context;
    List<DiaryEntry> entries;


    public DiaryListAdapter(Context context, List<DiaryEntry> entries){
        super(context, android.R.id.content, entries);
        this.context =context;
        this.entries=entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.diary_listitem_entry, null);

        DiaryEntry entry = entries.get(position);

        TextView tv = (TextView) view.findViewById(R.id.tvDate);
        tv.setText(entry.getEntryDate());

        tv = (TextView) view.findViewById(R.id.tvText);
        tv.setText(entry.getText());

        tv = (TextView) view.findViewById(R.id.tvTopic);
        tv.setText(entry.getTopic());

        return view;
    }
}
