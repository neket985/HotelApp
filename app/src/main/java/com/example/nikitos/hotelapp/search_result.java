package com.example.nikitos.hotelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class search_result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intnt = getIntent();
        String req = intnt.getStringExtra("GET");

        ListView lView = (ListView) findViewById(R.id.fragment_list);
        FirstAdapter adapter = new FirstAdapter(this, getDataSet());
        lView.setAdapter(adapter);
    }

    private String[] getDataSet() {

        String[] mDataSet = new String[100];
        for (int i = 0; i < 100; i++) {
            mDataSet[i] = "Hotel" + i + "&";
            for(int j=-1;j<(i%5);++j){
                mDataSet[i] += "*";
            }
            //mDataSet[i] += "&";
        }
        return mDataSet;
    }
    public void clickItem (View view){
        Intent intent = new Intent(search_result.this, detailActivity.class);
        startActivity(intent);
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
