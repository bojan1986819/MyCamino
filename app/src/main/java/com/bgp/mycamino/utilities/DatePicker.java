package com.bgp.mycamino.utilities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class DatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText _editText;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;

    public DatePicker(Context context, EditText editTextViewID)
    {
        this._editText = editTextViewID;
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onClick(View v) {
        long date = System.currentTimeMillis();

        SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfM = new SimpleDateFormat("MM");
        SimpleDateFormat sdfD = new SimpleDateFormat("d");

        Integer dateY = Integer.parseInt(sdfY.format(date));
        Integer dateM = Integer.parseInt(sdfM.format(date));
        Integer dateD = Integer.parseInt(sdfD.format(date));
        DatePickerDialog dialog =  new DatePickerDialog(_context, this, dateY, dateM-1, dateD);
        dialog.show();
    }

    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month+1).append("/").append(_birthYear).append(" "));
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        updateDisplay();
    }
}
