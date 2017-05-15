package com.bgp.mycamino;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgp.mycamino.db.DBDataSource;
import com.bgp.mycamino.db.DBItem;
import com.bgp.mycamino.db.DBOpenHelper;
import com.bgp.mycamino.listadapter.CityListAdapter;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CityDetailActivity extends Activity {
    public static final String LOGTAG;
    DBOpenHelper dbOpenHelper = null;

    static {
        LOGTAG = "MYCAMINO";
    }

    private List<DBItem> mEntries;

    DBDataSource mDataSource;

    private ShareButton shareButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        dbOpenHelper=new DBOpenHelper(this);

//        dbOpenHelper.createDatabase();

        mDataSource = new DBDataSource(this);
        mDataSource.open();

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);

        menu.setClosedOnTouchOutside(true);


//        Intent intent = getIntent();
//        Long city = intent.getLongExtra("city",0);
//        Long cityid = intent.getLongExtra("city",0);
//
//        Snackbar.make(findViewById(android.R.id.content), "Cityid: " + cityid, Snackbar.LENGTH_LONG)
//
//                .setActionTextColor(Color.RED)
//                .show();


        displayCityDetails();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
        displayCityDetails();
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


    public void openMap(View v){

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);
        Intent i = new Intent(this, MapsActivity.class);
        Intent intentget=getIntent();
        Long cityid=intentget.getLongExtra("city", 0);
        Double citylat=intentget.getDoubleExtra("citylat", 0);
        Double citylong=intentget.getDoubleExtra("citylong", 0);
        Long daynum = intentget.getLongExtra("daynum", 0);
        Integer type=4;
        i.putExtra("stagepart", daynum);
        i.putExtra("part",cityid);
        i.putExtra("citylat",citylat);
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

    // ez kell ahhoz, hogy képeket be tudjak hívni
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    private void displayCityDetails() {

        //itt adom meg, hogy mit vigyen át a descreiptionbe felül

        Intent intent = getIntent();
        String cityimage = intent.getStringExtra("cityimage");
        String desc = intent.getStringExtra("desc");
        String cityname = intent.getStringExtra("cityname");
        String source = intent.getStringExtra("source");

        TextView tv = (TextView) findViewById(R.id.descText);
        tv.setText(Html.fromHtml(desc));

        tv=(TextView) findViewById(R.id.nameText);
        tv.setText(cityname);

        tv=(TextView)findViewById(R.id.sourceText);
        if(!source.equals("")){
            tv.setText(Html.fromHtml("<a href=" + source + ">Source</a> "));
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }

        ImageView iv = (ImageView) findViewById(R.id.cityImage);
        int image = getImageId(this, cityimage);
//        iv.setImageResource(getImageId(this, cityimage));
        Picasso.with(this).load(image).fit().centerCrop().into(iv);

    }

    public void imageClick(View v){
        Intent intent2 = getIntent();
        String cityimage = intent2.getStringExtra("cityimage");
        ImageView iv = (ImageView) findViewById(R.id.cityImage);
        iv.setImageResource(getImageId(this, cityimage));

        Intent intent = new Intent(this, ImageViewer.class);
        intent.putExtra("selected_image", intent2.getStringExtra("cityimage"));
        startActivity(intent);
    }

    public void openAlbergues(View v){
        Intent intentget=getIntent();
        Long cityid=intentget.getLongExtra("city", 0);
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

        Toast.makeText(getApplicationContext(),"Please wait while list is being loaded",Toast.LENGTH_LONG).show();

        startActivity(intent);
    }

    public void openAtractions(View v){
        Intent intentget=getIntent();
        Long cityid=intentget.getLongExtra("city", 0);
        Double citylat=intentget.getDoubleExtra("citylat", 0);
        Double citylong=intentget.getDoubleExtra("citylong",0);
        String cityname=intentget.getStringExtra("cityname");
        Long daynum = intentget.getLongExtra("daynum", 0);
        String fbplaceid=intentget.getStringExtra("fbplaceid");
        Intent intent = new Intent(this, AtractionsActivity.class);
        intent.putExtra("daynum",daynum);
        intent.putExtra("cityid", cityid);
        intent.putExtra("citylat", citylat);
        intent.putExtra("citylong", citylong);
        intent.putExtra("cityname", cityname);
        intent.putExtra("source","");
        intent.putExtra("imagename", "empty");
        intent.putExtra("atrname", "Chose an atraction from the list below");
        intent.putExtra("desc", "");
        intent.putExtra("fbplaceid", fbplaceid);
        intent.putExtra("cityimage", intentget.getStringExtra("cityimage"));

        startActivity(intent);
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
