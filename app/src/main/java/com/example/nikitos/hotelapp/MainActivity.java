package com.example.nikitos.hotelapp;

import com.google.gson.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getIdFromTitle(JsonArray jsArr, String equalStr, String gettingParam){
        String str;
        int i=0;
        for(;i<jsArr.size();++i){
            if (equalStr.equals(jsArr.get(i).getAsJsonObject().get("title").getAsString())){
                break;
            }
        }
        str = jsArr.get(i).getAsJsonObject().get(gettingParam).getAsString();
        return str;
    }
    public static String[] getList (JsonArray jsArr){
        String [] str = new String[jsArr.size()];
        for(int i=0;i<jsArr.size();++i){
            str[i] = jsArr.get(i).getAsJsonObject().get("title").getAsString();
        }
        return str;
    }

    myTask mt;
    TextView tv;
    ImageView searchbtn;
    EditText search_str;
    Spinner spin_farang;
    Button sendForm;
    TextView dateAt;
    TextView dateTo;
    Context context = MainActivity.this;
    String ListHotels;
    int k=0;

    AutoCompleteTextView AutoCompleteCities;
    AutoCompleteTextView AutoCompleteHotels;
    JsonArray js;
    JsonArray js1;
    String mainReq;
    String[] cities;
    String[] hotels;
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

        mt = new myTask();
        mt.execute();

        AutoCompleteCities = (AutoCompleteTextView) findViewById(R.id.Cities);
        AutoCompleteHotels = (AutoCompleteTextView) findViewById(R.id.search_string);
        tv = (TextView)findViewById(R.id.tv);
        searchbtn = (ImageView)findViewById(R.id.serchbtn);
        search_str = (EditText)findViewById(R.id.search_string);
        spin_farang = (Spinner)findViewById(R.id.spin_farang);
        sendForm = (Button)findViewById(R.id.send_form);
        dateAt = (TextView)findViewById(R.id.dateAt);
        dateTo = (TextView)findViewById(R.id.dateTo);
    }

    class myTask extends AsyncTask <Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mainReq = getHTML("http://api.h4y.ru/mobile-booking/");
            ListHotels = getHTML("http://api.h4y.ru/mobile-booking/search?query=");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mainReq==""||ListHotels==""){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error")
                        .setMessage("Требуется подключение к интернету")
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                JsonParser parser = new JsonParser();
                js = parser.parse(mainReq).getAsJsonArray();
                cities = getList(js);
                js1 = parser.parse(ListHotels).getAsJsonArray();
                hotels = getList(js1);

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, cities);
                AutoCompleteCities.setAdapter(adapter1);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, hotels);
                AutoCompleteHotels.setAdapter(adapter2);

                final String[] searchReq = new String[1];
                final String[] selectNumFarang = new String[1];
                final View.OnClickListener oclSrchImg = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, detailActivity.class);
                        intent.putExtra("hotelId", getIdFromTitle(js1, AutoCompleteHotels.getText().toString(), "id"));
                        //intent.putExtra("imageId", getIdFromTitle(js1, AutoCompleteHotels.getText().toString(), "icon"));
                        startActivity(intent);
                        //searchReq[0] = search_str.getText().toString();
                    }
                };
                searchbtn.setOnClickListener(oclSrchImg);
                View.OnClickListener oclFormBtn = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cityId = getIdFromTitle(js, AutoCompleteCities.getText().toString(), "id");
                        selectNumFarang[0] = spin_farang.getSelectedItem().toString();

                        Intent intent = new Intent(MainActivity.this, search_result.class);
                        intent.putExtra("cityId", cityId);
                        startActivity(intent);
                    }
                };
                sendForm.setOnClickListener(oclFormBtn);
            }
        }
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
//Параметры для запроса: "идентификатор горда", "количество мест в номере", "дата заезда", "дата выезда", "Минимальная цена", "Максимальная цена"