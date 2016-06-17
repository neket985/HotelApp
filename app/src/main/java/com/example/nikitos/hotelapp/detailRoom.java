package com.example.nikitos.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class detailRoom extends AppCompatActivity {
    String maintxt = "";
    String imageId;
    int stateDescrptn = 0;
    TextView textDescrptn;
    TextView textMore;
    ImageView image;
    Context context = detailRoom.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        Intent getIntnt = getIntent();
        imageId = getIntnt.getStringExtra("imageId");

        textDescrptn = (TextView) findViewById(R.id.descriptionRoom);
        textMore = (TextView) findViewById(R.id.moreRoom);
        image = (ImageView) findViewById(R.id.imageDetailRoom);

        Picasso.with(context)
                .load("http://www.h4y.ru/images/x500/" + imageId)
                .placeholder(R.drawable.icon_nullspace)
                .into(image);

        try {
            maintxt = getIntnt.getStringExtra("descr");
        }
        catch (Exception e){
            textDescrptn.setText("Текст отсутствует.");
        }

        if(maintxt.length()>405) {
            textDescrptn.setText(maintxt.substring(0, 400) + "...");
            textMore.setText("показать...");
            stateDescrptn = 2;
        }
        else if (maintxt.length()==0)
            textDescrptn.setText("Текст отсутствует.");
        else
            textDescrptn.setText(maintxt);
    }

    public void MoreDescription2 (View view){
        if(stateDescrptn > 1) {
            ++stateDescrptn;
            if (stateDescrptn % 2 == 1) {
                textDescrptn.setText(maintxt);
                textMore.setText("скрыть...");
            } else {
                textDescrptn.setText(maintxt.substring(0, 400) + "...");
                textMore.setText("показать...");
            }
        }
    }
}
