package com.example.jesse.barscan;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.widget.Button;
import android.widget.TextView;

public class DBDisplay extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    Button fetchButton;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbdisplay);
        fetchButton = (Button) findViewById(R.id.fetch);
        tv = (TextView) findViewById(R.id.textView);
    }

    public void fetchInfo(View view) {

        String query = "SELECT * FROM " + "test";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String string = "";

        while (cursor.moveToNext()) {

            string += (cursor.getString(1)) + " ";

        }
        cursor.close();
        db.close();
        tv.setText(string);

    }
}




