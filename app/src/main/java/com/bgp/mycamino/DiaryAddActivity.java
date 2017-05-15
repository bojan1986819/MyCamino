package com.bgp.mycamino;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bgp.mycamino.db.DiaryDataSource;
import com.bgp.mycamino.db.DiaryOpenHelper;

import java.text.SimpleDateFormat;

public class DiaryAddActivity extends Activity {
    DiaryOpenHelper dbOpenHelper = null;
    DiaryDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (heigth * .9));

        dbOpenHelper = new DiaryOpenHelper(this);
        mDataSource = new DiaryDataSource(this);
        mDataSource.open();

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        String dateString = sdf.format(date);
        TextView tvDate=(TextView)findViewById(R.id.tvDate);
        tvDate.setText(dateString);

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
    }

    public void Save(View v) {
        EditText text = (EditText) findViewById(R.id.etText);
        TextView topic = (TextView) findViewById(R.id.etTopic);
        TextView date = (TextView) findViewById(R.id.tvDate);

        boolean isInserted = dbOpenHelper.insertData(date.getText().toString(),
                text.getText().toString(),
                topic.getText().toString());
        if (isInserted = true)
            Toast.makeText(DiaryAddActivity.this, "Entry added to you Diary", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(DiaryAddActivity.this, "Entry did not add to your Diary", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void Close(View v){
        finish();
    }
}
