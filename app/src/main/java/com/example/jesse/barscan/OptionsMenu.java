package com.example.jesse.barscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.example.jesse.barscan.MainActivity.setMinAge;
import static com.example.jesse.barscan.MainActivity.setMaxAge;

import com.google.android.gms.vision.text.Text;

public class OptionsMenu extends AppCompatActivity {
    Button maxSet;
    Button minSet;
    Button dbFetch;
    TextView tvMin;
    TextView tvMax;
    EditText etMin;
    EditText etMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu_layout);

        maxSet = (Button)findViewById(R.id.minButton);
        minSet = (Button)findViewById(R.id.maxButton);
        tvMin = (TextView)findViewById(R.id.minAge);
        tvMax = (TextView)findViewById(R.id.maxAge);
        etMin = (EditText)findViewById(R.id.et1);
        etMax = (EditText)findViewById(R.id.et2);
        dbFetch = (Button)findViewById(R.id.fetch);
    }

    public void setMinThreshold(View view){
        int temp = Integer.parseInt(etMin.getText().toString());
        setMinAge(temp);
    }

    public void setMaxThreshold(View view){
        int temp = Integer.parseInt(etMax.getText().toString());
        setMaxAge(temp);
    }

    public void fetchInfo(View view){
        Intent intent = new Intent(this, DBDisplay.class);
        startActivity(intent);
    }
}
