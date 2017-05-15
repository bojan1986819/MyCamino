package com.bgp.mycamino;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bgp.mycamino.db.DBDataSource;
import com.bgp.mycamino.db.DBItem;
import com.bgp.mycamino.db.DBOpenHelper;
import com.bgp.mycamino.listadapter.CityListAdapter;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;


public class StagePartDetail extends ListActivity{
    public static final String LOGTAG;
    DBOpenHelper dbOpenHelper = null;

    static {
        LOGTAG = "MYCAMINO";
    }

    private List<DBItem> mEntries;

    DBDataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);

        dbOpenHelper=new DBOpenHelper(this);

//        dbOpenHelper.createDatabase();

        mDataSource = new DBDataSource(this);
        mDataSource.open();

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);

        menu.setClosedOnTouchOutside(true);


        Intent intent = getIntent();
        int position= intent.getIntExtra("part", 0);


        Long daynum = intent.getLongExtra("daynum",0);
        String stagename = intent.getStringExtra("stagename");
        int kmpart = intent.getIntExtra("kmpart",0);
        mEntries = mDataSource.findCitiesFiltered("STAGESID = " + daynum);


        refreshDisplay();
        displayStageDetails();

    }



    public void refreshDisplay() {
        ArrayAdapter<DBItem> adapter = new CityListAdapter(this, mEntries);
        setListAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
        refreshDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
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


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DBItem entry = mEntries.get(position);
        Intent intentget=getIntent();
        Long daynum = intentget.getLongExtra("daynum", 0);

        Intent intent = new Intent(this, CityDetailActivity.class);
        intent.putExtra("daynum",daynum);
        intent.putExtra("cityname", entry.getCityname());
        intent.putExtra("desc", entry.getDescription());
        intent.putExtra("cityimage", entry.getImage());
        intent.putExtra("city", entry.getCitycode());
        intent.putExtra("citylat",entry.getLatitude());
        intent.putExtra("citylong",entry.getLongitude());
        intent.putExtra("source",entry.getWikilink());
        intent.putExtra("fbplaceid",entry.getFbplaceid());

        startActivity(intent);

    }




    public void openMap(View v){

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);
        Intent i = new Intent(this, MapsActivity.class);
        Intent intent=getIntent();
        Long daynum = intent.getLongExtra("daynum", 0);
        Integer type=1;
        i.putExtra("stagepart", daynum);
        i.putExtra("part", daynum);
        i.putExtra("type",type);
        startActivity(i);
    }

    public void openJournal(View v){

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, DiaryListActivity.class);
        startActivity(i);

    }

    // ez kell ahhoz, hogy képeket be tudjak hívni
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    private void displayStageDetails() {

        //itt adom meg, hogy mit vigyen át a descreiptionbe felül

        Intent intent = getIntent();
        String stagename = intent.getStringExtra("stagename");
        String imagename = intent.getStringExtra("imagename");
        String altimagename = intent.getStringExtra("altimagename");
        String desc = intent.getStringExtra("desc");
        int kmpart = intent.getIntExtra("kmpart", 0);

        TextView tv = (TextView) findViewById(R.id.nameText);
        tv.setText(stagename);


        tv = (TextView)findViewById(R.id.kmText);
        tv.setText(String.valueOf(kmpart + " KM"));


        tv = (TextView) findViewById(R.id.descText);
        tv.setText(Html.fromHtml("<html><body><font size=5 color=red>"+desc+"</font></body></html>"));

        ImageView iv = (ImageView) findViewById(R.id.altImage);
        iv.setImageResource(getImageId(this, altimagename));

    }

    public void imageClick(View v){
        Intent intent2 = getIntent();
        String altimagename = intent2.getStringExtra("altimagename");
        ImageView iv = (ImageView) findViewById(R.id.altImage);
        iv.setImageResource(getImageId(this, altimagename));

        Intent intent = new Intent(this, ImageViewer.class);
        intent.putExtra("selected_image", intent2.getStringExtra("altimagename"));
        startActivity(intent);
    }
    public void openIconLegend(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, IconLegend.class);
        startActivity(i);
    }
}

