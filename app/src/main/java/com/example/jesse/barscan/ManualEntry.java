package com.example.jesse.barscan;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManualEntry extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText etDOB;
    EditText etZip;
    Spinner spinner;
    Button save;
    String inputGender;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    DBHelper helper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);
        inputGender = "Other";
        save = (Button)findViewById(R.id.saveButton);
        etDOB = (EditText)findViewById(R.id.dob);
        etZip = (EditText)findViewById(R.id.zip);
        spinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        String input = parent.getItemAtPosition(pos).toString();
        inputGender = "" + input;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void saveClick(View view){

        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("dateVar", reportDate);
        row.put("dobVar", etDOB.getText().toString());
        row.put("zipVar", etZip.getText().toString());
        row.put("genderVar", inputGender);
        db.insert("test", null, row);
        db.close();
    }
}
