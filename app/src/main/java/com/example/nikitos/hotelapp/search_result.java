package com.example.nikitos.hotelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search_result extends AppCompatActivity {
    Context context = search_result.this;
    String cityId;
    String mainStr;
    JsonArray jsArr;

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

    int countHotels = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intnt = getIntent();
        cityId = intnt.getStringExtra("cityId");

        myTask2 mt = new myTask2();
        mt.execute();
    }

    private String[] getDataSet() {

        String[] mDataSet = new String[countHotels];
        for (int i = 0; i < countHotels; i++) {
            mDataSet[i] = jsArr.get(i).getAsJsonObject().get("title").getAsString() + "&****";
        }
        return mDataSet;
    }

    class myTask2 extends AsyncTask <Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mainStr = getHTML("http://h4y.ru:8480/mobile-booking/region/"+cityId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            JsonParser prsr = new JsonParser();
            jsArr = prsr.parse(mainStr).getAsJsonArray();
            countHotels = jsArr.size();

            ListView lView = (ListView) findViewById(R.id.fragment_list);
            FirstAdapter adapter = new FirstAdapter(context, getDataSet());
            lView.setAdapter(adapter);

            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Log.d("MYTAG", "itemClick: position = " + position + ", id = "
                            + id);
                }
            });
        }
    }
        /*Intent intnt = getIntent();

        // получаем экземпляр FragmentTransaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // добавляем фрагмент
        for(int i=0;i<10;++i) {
            searchResultFragment myFragment = new searchResultFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_list, myFragment);
            ft.commit();
        }
        fragmentTransaction.commit();
    }*/
}
