package com.bgp.mycamino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bgp.mycamino.db.DiaryDataSource;
import com.bgp.mycamino.db.DiaryEntry;
import com.bgp.mycamino.db.DiaryOpenHelper;

import java.util.List;

public class DiaryEntryActivity extends Activity {
    DiaryOpenHelper dbOpenHelper=null;
    DiaryDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int heigth=dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (heigth * .9));

        dbOpenHelper = new DiaryOpenHelper(this);
        mDataSource = new DiaryDataSource(this);
        mDataSource.open();

        Intent intent=getIntent();
        int ID=intent.getIntExtra("ID", 0);
        String text=intent.getStringExtra("text");
        String topic=intent.getStringExtra("topic");
        String date=intent.getStringExtra("date");

        mDataSource.showEntry("ID ="+ID);

        TextView tv=(TextView)findViewById(R.id.tvTopic);
        tv.setText(topic);

        tv=(TextView)findViewById(R.id.tvDate);
        tv.setText(date);

        tv=(TextView)findViewById(R.id.tvText);
        tv.setText(text);

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

    public void EditEntry(View v){
        Intent getintent=getIntent();
        int ID=getintent.getIntExtra("ID", 0);
        String text=getintent.getStringExtra("text");
        String topic=getintent.getStringExtra("topic");
        String date=getintent.getStringExtra("date");

        Intent intent=new Intent(this,DiaryEditActivity.class);
        intent.putExtra("ID", ID);
        intent.putExtra("text", text);
        intent.putExtra("date", date);
        intent.putExtra("topic", topic);

        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        Toast.makeText(getApplicationContext(), "You can edit Date and Subject too", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void DeleteEntry(View v){
        final AlertDialog alertDialog = new AlertDialog.Builder(DiaryEntryActivity.this).create(); //Read Update
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Do you really want to delete this entry?");

        alertDialog.setButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                int id = intent.getIntExtra("ID", 0);
                String ID = String.valueOf(id);
                Integer isDeleted = dbOpenHelper.deleteData(ID);
                if (isDeleted > 0)
                    Toast.makeText(DiaryEntryActivity.this, "Diary entry deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DiaryEntryActivity.this, "Diary entry deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
                alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();
                            }
                        }
                );

        alertDialog.show();


    }

    public void Close(View v){
        finish();
    }

}
