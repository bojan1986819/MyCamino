package com.bgp.mycamino;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bgp.mycamino.db.DiaryDataSource;
import com.bgp.mycamino.db.DiaryEntry;
import com.bgp.mycamino.db.DiaryOpenHelper;
import com.bgp.mycamino.listadapter.DiaryListAdapter;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DiaryListActivity extends ListActivity {
    DiaryOpenHelper dbOpenHelper=null;
    private List<DiaryEntry> mEntries;

    DiaryDataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);

        dbOpenHelper = new DiaryOpenHelper(this);
        mDataSource = new DiaryDataSource(this);
        mDataSource.open();

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.setClosedOnTouchOutside(true);

        mEntries=mDataSource.listAllEntries();

        displayDiaryEntries();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
        mEntries=mDataSource.listAllEntries();
        ArrayAdapter<DiaryEntry> adapter = new DiaryListAdapter(this, mEntries);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
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

    private void displayDiaryEntries(){
        ArrayAdapter<DiaryEntry> adapter = new DiaryListAdapter(this, mEntries);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DiaryEntry entry = mEntries.get(position);

        int ID = entry.getID();
        String text=entry.getText();
        String date=entry.getEntryDate();
        String topic=entry.getTopic();

        Intent intent=new Intent(this,DiaryEntryActivity.class);
        intent.putExtra("ID", ID);
        intent.putExtra("text", text);
        intent.putExtra("date", date);
        intent.putExtra("topic", topic);

        startActivity(intent);
    }

    public void AddEntry(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);
        Intent intent=new Intent(this,DiaryAddActivity.class);
        startActivity(intent);
    }

    public void ExportDiary(View v){
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu);
        menu.close(true);
        Cursor cursor=dbOpenHelper.getAllData();
        exportToExcel(cursor);
        Toast.makeText(getApplicationContext(), "Your diary exported successfully to SDCard/MyDiary/MyDiary.xls", Toast.LENGTH_LONG).show();
    }


    private void exportToExcel(Cursor cursor) {
        /*jxl library van használva ehhez*/
            final String fileName = "MyDiary.xls";

            //Saving file in external storage
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/MyDiary");

            //create directory if not exist
            if(!directory.isDirectory()){
                directory.mkdirs();
            }

            //file path
            File file = new File(directory, fileName);

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;

            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("MyDiary", 0);

                try {
                    sheet.addCell(new Label(0, 0, "Date")); // column and row
                    sheet.addCell(new Label(1, 0, "Subject"));
                    sheet.addCell(new Label(2, 0, "Description"));
                    if (cursor.moveToFirst()) {
                        do {
                            String date = cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_ID));
                            String title = cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_TOPIC));
                            String desc = cursor.getString(cursor.getColumnIndex(DiaryOpenHelper.COLUMN_DIARY_TEXT));

                            int i = cursor.getPosition() + 1;
                            sheet.addCell(new Label(0, i, date));
                            sheet.addCell(new Label(1, i, title));
                            sheet.addCell(new Label(2, i, desc));
                        } while (cursor.moveToNext());
                    }
                    //closing cursor
                    cursor.close();
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                workbook.write();
                try {
                    workbook.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
