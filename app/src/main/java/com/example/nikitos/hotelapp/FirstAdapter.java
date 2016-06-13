package com.example.nikitos.hotelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikitos on 06.06.16.
 */
public class FirstAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mData;
    String str;
    String s3;
    ViewHolder viewHolder;

    public FirstAdapter(Context context, String[] objects) {
        mContext = context;
        mData = objects;
    }

    static class ViewHolder {
        TextView txtItem1;
        TextView txtItem2;
        TextView txtItem3;
        ImageView image;
    }

    @Override
    public String getItem(int i) {
        return mData[i];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.txtItem1 = (TextView) convertView.findViewById(R.id.txt1);
            viewHolder.txtItem2 = (TextView) convertView.findViewById(R.id.txt2);
            viewHolder.txtItem3 = (TextView) convertView.findViewById(R.id.txt3);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.iconHotel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        str = getItem(position);

        String s1 = str.substring(0, str.indexOf('&'));
        str = str.substring(str.indexOf('&') + 1, str.length());
        String s2 = str.substring(0, str.indexOf('&'));
        str = str.substring(str.indexOf('&') + 1, str.length());
        s3 = str.substring(0, str.indexOf('&'));
        String s4 = str.substring(str.indexOf('&') + 1, str.length());
        viewHolder.txtItem1.setText(s1);
        viewHolder.txtItem2.setText(s2);
        viewHolder.txtItem3.setText(s4);
        Picasso.with(mContext)
                .load("http://www.h4y.ru/images/x100/" + s3)
                .placeholder(R.drawable.icon_nullspace)
                .into(viewHolder.image);

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
