package com.example.nikitos.hotelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView searchbtn;
    EditText search_str;
    Spinner spin_farang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tv = (TextView)findViewById(R.id.tv);
        searchbtn = (ImageView)findViewById(R.id.serchbtn);
        search_str = (EditText)findViewById(R.id.search_string);
        spin_farang = (Spinner)findViewById(R.id.spin_farang);

        View.OnClickListener oclSrchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str =   search_str.getText().toString();
                String selected = spin_farang.getSelectedItem().toString();
                tv.setText(selected+" "+str);
            }
        };

        searchbtn.setOnClickListener(oclSrchBtn);
    }

}
