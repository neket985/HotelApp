package com.example.nikitos.hotelapp;

import com.google.gson.*;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView searchbtn;
    EditText search_str;
    Spinner spin_farang;
    Button sendForm;

    AutoCompleteTextView AutoCompleteCities;
    String[] citiesWithid = { "Moscow :1", "France :2", "Germany :3"};
    String[] cities;
    String[] cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = new String[citiesWithid.length];
        cityId = new String[citiesWithid.length];
        for(int i=0;i<citiesWithid.length;++i){
            cities[i] = citiesWithid[i].substring(0, citiesWithid[i].indexOf(':')-1);
            cityId[i] = citiesWithid[i].substring(citiesWithid[i].indexOf(':')+1);
        }

        AutoCompleteCities = (AutoCompleteTextView) findViewById(R.id.Cities);
        tv = (TextView)findViewById(R.id.tv);
        searchbtn = (ImageView)findViewById(R.id.serchbtn);
        search_str = (EditText)findViewById(R.id.search_string);
        spin_farang = (Spinner)findViewById(R.id.spin_farang);
        sendForm = (Button)findViewById(R.id.send_form);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cities);
        AutoCompleteCities.setAdapter(adapter1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String[] searchReq = new String[1];
        final String[] selectNumFarang = new String[1];
        final String[] selectCity = new String[1];
        final View.OnClickListener oclSrchImg = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchReq[0] = search_str.getText().toString();
                tv.setText(searchReq[0]);
            }
        };
        searchbtn.setOnClickListener(oclSrchImg);
        View.OnClickListener oclFormBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCity[0] = AutoCompleteCities.getText().toString();
                selectNumFarang[0] = spin_farang.getSelectedItem().toString();
                tv.setText(selectCity[0]+" "+selectNumFarang[0]);
            }
        };
        sendForm.setOnClickListener(oclFormBtn);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
