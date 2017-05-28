/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jesse.barscan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import java.text.ParseException;


public class MainActivity extends Activity implements View.OnClickListener {


    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView name;
    private TextView address;
    private TextView cityStateZip;
    private TextView gender;
    private TextView dob;
    private TextView over21;
    private TableLayout tbl;
    private static int minAge = 21;
    private static int maxAge = 200;
    private Button manual;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = (TextView)findViewById(R.id.status_message);
        name = (TextView)findViewById(R.id.name);
        cityStateZip = (TextView)findViewById(R.id.cityStateZip);
        address = (TextView)findViewById(R.id.address);
        gender = (TextView)findViewById(R.id.gender);
        dob = (TextView)findViewById(R.id.dob);
        over21 = (TextView)findViewById(R.id.over21);
        tbl = (TableLayout)findViewById(R.id.tableLayout);
        manual = (Button)findViewById(R.id.manual);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_barcode).setOnClickListener(this);

        //generates toast upon initial load
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("This is a custom toast");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    public void toOptions(View v){
        Intent intent = new Intent(this, OptionsMenu.class);
        startActivity(intent);
    }

    public void manualEntry(View v){
        Intent intent = new Intent(this, ManualEntry.class);
        startActivity(intent);
    }

    //allows user to change age threshold
    public static void setMinAge(int age){
        minAge = age;
    }

    public static void setMaxAge(int age){
        maxAge = age;
    }

   //class that calculates barcode data upon closing BarcodeCaptureActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {

                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Barcode.DriverLicense sample = barcode.driverLicense;

                    try {
                        int age = DateDifference.generateAge(sample.birthDate);
                        if (age < minAge) {
                            tbl.setBackgroundColor(Color.parseColor("#980517"));
                            over21.setText("UNDER AGE");
                        }
                        else {
                            tbl.setBackgroundColor(Color.parseColor("#617C17"));
                            over21.setText("OKAY");
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String cityContent = sample.addressCity + ", " + sample.addressState
                            + " " + (sample.addressZip).substring(0,5);
                    address.setText(sample.addressStreet);
                    cityStateZip.setText(cityContent);

                    String genderString;
                    if((sample.gender).equals("1"))
                        genderString = "Male";
                    else if((sample.gender).equals("2"))
                        genderString = "Female";
                    else genderString = "Other";
                    gender.setText(genderString);

                    dob.setText((sample.birthDate).substring(0,2) + "/" + (sample.birthDate).substring(2,4)
                            + "/" + (sample.birthDate).substring(4,8));
                    String nameString = new String(sample.firstName + " " + sample.middleName +
                        " " + sample.lastName);

                    name.setText(nameString);

                    //insert collected data into SQLlite database


                    //db.execSQL("create table test (dateVar text primary key, dobVar text, zipVar text, genderVar text)");

                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
