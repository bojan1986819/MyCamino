package com.bgp.mycamino;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bgp.mycamino.db.DiaryDataSource;
import com.bgp.mycamino.db.DiaryOpenHelper;
import com.bgp.mycamino.utilities.DatePicker;
import com.github.clans.fab.FloatingActionMenu;

public class DiaryEditActivity extends Activity {
    DiaryOpenHelper dbOpenHelper = null;
    DiaryDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (heigth * .9));

        dbOpenHelper = new DiaryOpenHelper(this);
        mDataSource = new DiaryDataSource(this);
        mDataSource.open();

        Intent intent = getIntent();
        int ID = intent.getIntExtra("ID", 0);
        String text = intent.getStringExtra("text");
        String topic = intent.getStringExtra("topic");
        String date = intent.getStringExtra("date");

        mDataSource.showEntry("ID =" + ID);

        TextView tv = (TextView) findViewById(R.id.tvTopic);
        tv.setText(topic);

        tv = (TextView) findViewById(R.id.tvDate);
        tv.setText(date);

        tv = (EditText) findViewById(R.id.tvText);
        tv.setText(text);

        EditText et = (EditText) findViewById(R.id.tvDate);
        DatePicker fromDate = new DatePicker(this, et);

        et=(EditText)findViewById(R.id.tvText);
        et.requestFocus();

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
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        String ID = String.valueOf(id);

        EditText textO = (EditText) findViewById(R.id.tvText);
        TextView topicO = (TextView) findViewById(R.id.tvTopic);
        TextView dateO = (TextView) findViewById(R.id.tvDate);

        boolean isUpdated = dbOpenHelper.updateData(ID.toString(),
                dateO.getText().toString(),
                textO.getText().toString(),
                topicO.getText().toString());
        if (isUpdated = true)
            Toast.makeText(DiaryEditActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(DiaryEditActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();

        Intent intentput = new Intent(this, DiaryEntryActivity.class);
        intentput.putExtra("ID", id);
        intentput.putExtra("text", textO.getText().toString());
        intentput.putExtra("date", dateO.getText().toString());
        intentput.putExtra("topic", topicO.getText().toString());

        startActivity(intentput);
        overridePendingTransition(R.anim.left_out, R.anim.right_in);
        finish();
    }
    public void Close(View v){
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        String date = intent.getStringExtra("date");
        String ID = String.valueOf(id);

        EditText textO = (EditText) findViewById(R.id.tvText);
        TextView topicO = (TextView) findViewById(R.id.tvTopic);

        Intent intentput = new Intent(this, DiaryEntryActivity.class);
        intentput.putExtra("ID", id);
        intentput.putExtra("text", textO.getText().toString());
        intentput.putExtra("date", date);
        intentput.putExtra("topic", topicO.getText().toString());

        startActivity(intentput);
        overridePendingTransition(R.anim.left_out, R.anim.right_in);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        String date = intent.getStringExtra("date");
        String ID = String.valueOf(id);

        EditText textO = (EditText) findViewById(R.id.tvText);
        TextView topicO = (TextView) findViewById(R.id.tvTopic);

        Intent intentput = new Intent(this, DiaryEntryActivity.class);
        intentput.putExtra("ID", id);
        intentput.putExtra("text", textO.getText().toString());
        intentput.putExtra("date", date);
        intentput.putExtra("topic", topicO.getText().toString());

        startActivity(intentput);
        overridePendingTransition(R.anim.left_out, R.anim.right_in);
        finish();
    }

}
