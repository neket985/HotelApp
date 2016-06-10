package com.example.nikitos.hotelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikitos on 06.06.16.
 */
public class FirstAdapter extends BaseAdapter {

    private final static String TAG = "ImageManager";
    private Context mContext;
    private String[] mData;

    public FirstAdapter(Context context, String[] objects) {
        mContext = context;
        mData = objects;
    }

    static class ViewHolder {
        TextView txtItem1;
        TextView txtItem2;
        TextView txtItem3;
        TextView txtItem4;
    }

    @Override
    public String getItem(int i) {
        return mData[i];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.txtItem1 = (TextView) convertView.findViewById(R.id.txt1);
            viewHolder.txtItem2 = (TextView) convertView.findViewById(R.id.txt2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String str = getItem(position);
        String s1 = str.substring(0, str.indexOf('&'));
        String s2 = str.substring(str.indexOf('&')+1, str.length());
        viewHolder.txtItem1.setText(s1);
        viewHolder.txtItem2.setText(s2);

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return mData.length;
    }
}
