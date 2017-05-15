package com.bgp.mycamino;


import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bgp.mycamino.db.DiaryEntry;
import com.bgp.mycamino.db.DiaryOpenHelper;

import java.util.List;


public class DiaryActivity extends Activity{
    DiaryOpenHelper myDb;
    EditText entryDate,text,topic,cityName,image, editTextId;
    Button btnAddData, btnviewAll, btnviewUpdate, btnviewDelete;

    private List<DiaryEntry> mEntries;

    @Override
    protected void onCreate(Bundle savedInstaneState){
        super.onCreate(savedInstaneState);
        setContentView(R.layout.activity_diary);
        myDb=new DiaryOpenHelper(this);


        entryDate=(EditText)findViewById(R.id.editText_entrydate);
        text=(EditText)findViewById(R.id.editText_text);
        topic=(EditText)findViewById(R.id.editText_topic);
        cityName=(EditText)findViewById(R.id.editText_cityname);
        image=(EditText)findViewById(R.id.editText_image);
        editTextId=(EditText)findViewById(R.id.editText_ID);
        btnAddData=(Button)findViewById(R.id.button_add);
        btnviewAll=(Button)findViewById(R.id.button_viewAll);
        btnviewUpdate=(Button)findViewById(R.id.button_update);
        btnviewDelete=(Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnviewDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer isDeleted = myDb.deleteData(editTextId.getText().toString());
                        if (isDeleted > 0)
                            Toast.makeText(DiaryActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DiaryActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editTextId.getText().toString(),
                                entryDate.getText().toString(),
                                text.getText().toString(),
                                topic.getText().toString());
                        if (isUpdated = true)
                            Toast.makeText(DiaryActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DiaryActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(entryDate.getText().toString(),
                                text.getText().toString(),
                                topic.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(DiaryActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DiaryActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res=myDb.getAllData();
                        if(res.getCount()==0){
                            // show message
                            showMessage("Error","No entry found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID :"+res.getString(0)+"\n");
                            buffer.append("ENTRYDATE :"+res.getString(1)+"\n");
                            buffer.append("TEXT :"+res.getString(2)+"\n");
                            buffer.append("TOPIC :"+res.getString(3)+"\n");
                            buffer.append("CITYNAME :"+res.getString(4)+"\n");
                            buffer.append("IMAGE :"+res.getString(5)+"\n\n");
                        }

                        // Showw all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
