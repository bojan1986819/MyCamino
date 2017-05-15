package com.bgp.mycamino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bgp.mycamino.db.DBDataSource;
import com.bgp.mycamino.db.DBItem;
import com.bgp.mycamino.db.DBOpenHelper;
import com.bgp.mycamino.db.DiaryOpenHelper;
import com.bgp.mycamino.db.RouteDataSource;
import com.bgp.mycamino.db.RouteOpenHelper;
import com.bgp.mycamino.listadapter.DBListAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOGTAG;
    DBOpenHelper dbOpenHelper = null;
    DiaryOpenHelper myDb;
    RouteOpenHelper rDB;



    static {
        LOGTAG = "MYCAMINO";
    }

    private List<DBItem> mEntries;
    DBDataSource mDataSource;
    RouteDataSource rDataSource;

    ListView list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MyCamino - Francés");

        list1 = (ListView) findViewById(R.id.list);


        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);

        menu.setClosedOnTouchOutside(true);


        dbOpenHelper=new DBOpenHelper(this);
        myDb=new DiaryOpenHelper(this);
        rDB=new RouteOpenHelper(this);

//        dbOpenHelper.createDatabase();
        rDB.createDatabase();

        mDataSource = new DBDataSource(this);
        mDataSource.open();

        rDataSource=new RouteDataSource(this);
        rDataSource.open();

        mEntries = mDataSource.findStagesFiltered();

        refreshDisplay();

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DBItem entry = mEntries.get(position);

                Intent intent = new Intent(MainActivity.this, StagePartDetail.class);
                intent.putExtra("daynum", entry.getId());
                intent.putExtra("stagename", entry.getStagename());
                intent.putExtra("kmpart", entry.getKmpart());
                intent.putExtra("imagename", entry.getImage());
                intent.putExtra("desc", entry.getDescription());
                intent.putExtra("altimagename", entry.getAltimage());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
        rDataSource.close();
    }

    public void refreshDisplay() {
        ArrayAdapter<DBItem> adapter = new DBListAdapter(this, mEntries);
        ListView listView= (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        setListViewHeightBasedOnChildren(listView);
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
        Integer type=0;
        i.putExtra("type", type);
        startActivity(i);
    }

    public void openJournal(View v){

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, DiaryListActivity.class);
        startActivity(i);

    }
    public void openIconLegend(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);

        Intent i = new Intent(this, IconLegend.class);
        startActivity(i);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
