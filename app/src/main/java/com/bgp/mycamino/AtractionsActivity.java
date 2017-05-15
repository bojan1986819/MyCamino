package com.bgp.mycamino;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgp.mycamino.db.DBDataSource;
import com.bgp.mycamino.db.DBItem;
import com.bgp.mycamino.db.DBOpenHelper;
import com.bgp.mycamino.listadapter.AtractionListAdapter;
import com.bgp.mycamino.listadapter.CityListAdapter;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AtractionsActivity extends ListActivity{
    public static final String LOGTAG;
    DBOpenHelper dbOpenHelper = null;
    DBItem entry;

    static {
        LOGTAG = "MYCAMINO";
    }

    private List<DBItem> mEntries;

    DBDataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atractions);

        dbOpenHelper=new DBOpenHelper(this);

//        dbOpenHelper.createDatabase();

        mDataSource = new DBDataSource(this);
        mDataSource.open();

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);

        menu.setClosedOnTouchOutside(true);

        Intent intent = getIntent();
        Long cityid = intent.getLongExtra("cityid",0);
        mEntries = mDataSource.findSightseeFiltered("CITYID = "+cityid);

//               Snackbar.make(findViewById(android.R.id.content), "Cityid: " + cityid, Snackbar.LENGTH_LONG)
//
//                .setActionTextColor(Color.RED)
//                .show();

        String atrname = intent.getStringExtra("atrname");
        String imagename = intent.getStringExtra("imagename");
        String desc=intent.getStringExtra("desc");
        String source=intent.getStringExtra("source");

        TextView tv = (TextView) findViewById(R.id.nameText);
        tv.setText(atrname);

        tv = (TextView) findViewById(R.id.descText);
        tv.setText(Html.fromHtml(desc));

        tv=(TextView)findViewById(R.id.sourceText);
        if(!source.equals("")){
            tv.setText(Html.fromHtml("<a href=" + source + ">Source</a> "));
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }

        ImageView iv = (ImageView) findViewById(R.id.atrImage);
        int image = getImageId(this, imagename);
//        iv.setImageResource(getImageId(this, imagename));
        Picasso.with(this).load(image).fit().centerCrop().into(iv);

        refreshDisplay();
    }



    public void refreshDisplay() {
        ArrayAdapter<DBItem> adapter = new AtractionListAdapter(this, mEntries);
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
        Intent intentget=getIntent();
        Long cityid=intentget.getLongExtra("cityid", 0);
        Double citylat=intentget.getDoubleExtra("citylat", 0);
        Double citylong=intentget.getDoubleExtra("citylong", 0);
        String cityname=intentget.getStringExtra("cityname");
        Long daynum = intentget.getLongExtra("daynum", 0);
        String fbplaceid=intentget.getStringExtra("fbplaceid");

        DBItem entry = mEntries.get(position);

        String atrname = entry.getSightseename();
        String imagename = entry.getImage();
        String desc=entry.getDescription();

        Intent intent=new Intent(this,AtractionsActivity.class);
        intent.putExtra("imagename", imagename);
        intent.putExtra("atrname", atrname);
        intent.putExtra("desc", desc);
        intent.putExtra("cityid", cityid);
        intent.putExtra("citylat", citylat);
        intent.putExtra("citylong", citylong);
        intent.putExtra("cityname", cityname);
        intent.putExtra("source",entry.getSource());
        intent.putExtra("daynum",daynum);
        intent.putExtra("fbplaceid", fbplaceid);
        intent.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        this.finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }




    public void openMap(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);
        Intent i = new Intent(this, MapsActivity.class);
        Intent intent=getIntent();
        Long cityid=intent.getLongExtra("cityid", 0);
        Double citylat=intent.getDoubleExtra("citylat", 0);
        Double citylong=intent.getDoubleExtra("citylong", 0);
        String cityname=intent.getStringExtra("cityname");
        Long daynum = intent.getLongExtra("daynum", 0);
        Integer type=3;
        i.putExtra("stagepart",daynum);
        i.putExtra("part", cityid);
        i.putExtra("citylat", citylat);
        i.putExtra("citylong",citylong);
        i.putExtra("type",type);
        startActivity(i);
    }

    public void openJournal(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, DiaryListActivity.class);
        startActivity(i);
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public void imageClick(View v){
        Intent intent2 = getIntent();
        String imagename = intent2.getStringExtra("imagename");

        Intent intent = new Intent(this, ImageViewer.class);
        intent.putExtra("selected_image", imagename);
        startActivity(intent);
    }

    public void openAlbergues(View v){
        Intent intentget=getIntent();
        Long cityid=intentget.getLongExtra("cityid", 0);
        Double citylat=intentget.getDoubleExtra("citylat", 0);
        Double citylong=intentget.getDoubleExtra("citylong",0);
        String cityname=intentget.getStringExtra("cityname");
        Long daynum = intentget.getLongExtra("daynum", 0);
        String fbplaceid=intentget.getStringExtra("fbplaceid");
        Intent intent = new Intent(this, AlberguesActivity.class);
        intent.putExtra("daynum",daynum);
        intent.putExtra("cityid", cityid);
        intent.putExtra("citylat", citylat);
        intent.putExtra("citylong", citylong);
        intent.putExtra("cityname", cityname);
        intent.putExtra("fbplaceid", fbplaceid);
        intent.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        this.finish();
        overridePendingTransition(0, 0);
        Toast.makeText(getApplicationContext(), "Please wait while list is being loaded", Toast.LENGTH_LONG).show();
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public void backToCity(View v){
        this.finish();
    }

    public void openIconLegend(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, IconLegend.class);
        startActivity(i);
    }

    public void shareOnFB(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent intentget=getIntent();
        String cityname=intentget.getStringExtra("cityname");
        String fbplaceid=intentget.getStringExtra("fbplaceid");


        Intent i = new Intent(this, FacebookActivity.class);
        i.putExtra("cityname", cityname);
        i.putExtra("fbplaceid", fbplaceid);
        i.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        startActivity(i);
    }
}

