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

public class detailActivity extends AppCompatActivity {
    String hotelId;
    String mainReq;
    String maintxt = "";
    String imageId;
    JsonObject jsObj;
    int stateDescrptn = 0;
    TextView textDescrptn;
    TextView textMore;
    ImageView image;
    ProgressBar prbr;
    Context context = detailActivity.this;
    int countRooms;
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

    private String[] getDataSet() {

        String[] mDataSet = new String[countRooms];
        for (int i = 0; i < countRooms; i++) {
            mDataSet[i] = jsArr.get(i).getAsJsonObject().get("title").getAsString();
        }

        return mDataSet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent getIntnt = getIntent();
        hotelId = getIntnt.getStringExtra("hotelId");
        imageId = getIntnt.getStringExtra("image");

        textDescrptn = (TextView) findViewById(R.id.description);
        textMore = (TextView) findViewById(R.id.more);
        image = (ImageView) findViewById(R.id.imageDetail);
        prbr = (ProgressBar) findViewById(R.id.progressBar3);

        myTask2 mt = new myTask2();
        mt.execute();
    }

    public class myTask2 extends AsyncTask <Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prbr.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mainReq = getHTML("http://api.h4y.ru/mobile-booking/hotel/" + hotelId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prbr.setVisibility(View.INVISIBLE);
            Picasso.with(context)
                    .load("http://www.h4y.ru/images/x500/" + imageId)
                    .placeholder(R.drawable.icon_nullspace)
                    .into(image);

            JsonParser parser = new JsonParser();
            jsObj = parser.parse(mainReq).getAsJsonObject();



            jsArr = jsObj.get("rooms").getAsJsonArray();
            countRooms = jsArr.size();

            LinearLayout lView = (LinearLayout) findViewById(R.id.rooms);
            TwiceAdapter adapter = new TwiceAdapter(context, getDataSet());
            View item[] = new View[adapter.getCount()];
            for(int i=0;i<adapter.getCount();++i){
                item [i] = adapter.getView(i, null, null);
                lView.addView(item[i]);
                item[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = view.toString();
                        Log.i("Thgfg", str);
                        str = str.substring(str.length() - 9, str.length());
                        str = str.substring(str.indexOf(",") + 1, str.length() - 1);
                        int numRoom = Integer.parseInt(str)/150 - 1;

                        Intent intent1 = new Intent(detailActivity.this, detailRoom.class);
                        intent1.putExtra("roomId", jsArr.get(numRoom).getAsJsonObject().get("id").getAsString());
                        startActivity(intent1);
                    }
                });
            }

            try {
                maintxt = getStringFromHtml(jsObj.get("description").getAsString());
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
    }

    public void MoreDescription (View view){
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

    private String getStringFromHtml (String inputHtml){
        Document doc = Jsoup.parse(inputHtml);
        String neddedString = "";
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.add(doc.body());
        while (nodes.size() != 0){
            Node node = nodes.removeFirst();
            if (node.childNodes() == null || node.childNodes().size() == 0){
                neddedString = node.toString() + "\n" + neddedString;
                continue;
            }
            for (Node n : node.childNodes()){
                nodes.addFirst(n);
            }
        }

        return  neddedString;
    }
}
