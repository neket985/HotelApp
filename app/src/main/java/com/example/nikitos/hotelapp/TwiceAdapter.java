package com.example.nikitos.hotelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by nikitos on 13.06.16.
 */
public class TwiceAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mData;
    String str;
    ViewHolder viewHolder;

    public TwiceAdapter(Context context, String[] objects) {
        mContext = context;
        mData = objects;
    }

    static class ViewHolder {
        TextView txtItem1;
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
            convertView = inflater.inflate(R.layout.list_item_rooms, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.txtItem1 = (TextView) convertView.findViewById(R.id.titleRoom);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageRoom);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        str = getItem(position);


        String s1 = str.substring(0, str.indexOf('&'));
        str = str.substring(str.indexOf('&') + 1, str.length());
        viewHolder.txtItem1.setText(s1);
        Picasso.with(mContext)
                .load("http://www.h4y.ru/images/x100/" + str)
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
