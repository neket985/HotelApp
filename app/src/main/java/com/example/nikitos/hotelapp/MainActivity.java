package com.example.nikitos.hotelapp;

import com.google.gson.*;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.DatePicker;
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
    TextView dateAt;
    TextView dateTo;

    AutoCompleteTextView AutoCompleteCities;
    String[] citiesWithid = { "Moscow :1", "France :2", "Germany :3"};
    String[] cities;
    String[] cityId;
    int DIALOG_DATE1 = 1;
    int DIALOG_DATE2 = 2;
    int myYear1 = 2016;
    int myMonth1 = 01;
    int myDay1 = 01;
    int myYear2 = 2016;
    int myMonth2 = 01;
    int myDay2 = 01;

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
        dateAt = (TextView)findViewById(R.id.dateAt);
        dateTo = (TextView)findViewById(R.id.dateTo);


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
                Intent intent = new Intent(MainActivity.this, search_result.class);
                startActivity(intent);
            }
        };
        sendForm.setOnClickListener(oclFormBtn);
    }

    public void calendar1 (View view){
        showDialog(DIALOG_DATE1);
    }
    public void calendar2 (View view){
        myYear2 = myYear1;
        myMonth2 = myMonth1;
        myDay2 = myDay1;
        showDialog(DIALOG_DATE2);

    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE1) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack1, myYear1, myMonth1, myDay1);
            return tpd;
        }
        else if (id == DIALOG_DATE2) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack2, myYear2, myMonth2, myDay2);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear1 = year;
            myMonth1 = monthOfYear;
            myDay1 = dayOfMonth;
            dateAt.setText(myDay1 + "." + myMonth1 + "." + myYear1);
        }
    };
    DatePickerDialog.OnDateSetListener myCallBack2 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear2 = year;
            myMonth2 = monthOfYear;
            myDay2 = dayOfMonth;
            dateTo.setText(myDay2 + "." + myMonth2 + "." + myYear2);
        }
    };
}
