package com.example.nikitos.hotelapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by nikitos on 26.05.16.
 */
public class searchResultFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        LinearLayout insideLay1 = new LinearLayout(context);
        LinearLayout insideLay2 = new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        insideLay1.setOrientation(LinearLayout.HORIZONTAL);
        insideLay1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        insideLay2.setOrientation(LinearLayout.HORIZONTAL);
        insideLay2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
        TextView text1 = new TextView(context);
        text1.setText("Название гостиницы");
        TextView text2 = new TextView(context);
        TextView text3 = new TextView(context);
        TextView text4 = new TextView(context);
        TextView getSumm = new TextView(context);
        LinearLayout.LayoutParams param_text1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        LinearLayout.LayoutParams param_text2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        text1.setLayoutParams(param_text1);
        text2.setLayoutParams(param_text2);
        text3.setLayoutParams(param_text1);
        text4.setLayoutParams(param_text2);
        getSumm.setLayoutParams(param_text2);
        text2.setText("****");
        text2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        text3.setText("категория");
        text4.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        text4.setText("сумма: ");
        getSumm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        getSumm.setText("5200");
        text1.setTextColor(Color.BLACK);
        text2.setTextColor(Color.BLACK);
        text3.setTextColor(Color.BLACK);
        text4.setTextColor(Color.BLACK);
        getSumm.setTextColor(Color.BLACK);
        insideLay1.addView(text1);
        insideLay1.addView(text2);
        insideLay2.addView(text3);
        insideLay2.addView(text4);
        insideLay2.addView(getSumm);
        layout.addView(insideLay1);
        layout.addView(insideLay2);
        return layout;
    }
}
