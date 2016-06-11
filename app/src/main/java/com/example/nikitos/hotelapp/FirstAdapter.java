package com.example.nikitos.hotelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
    String str;
    String s3;
    ViewHolder viewHolder;

    public static void fetchImage(final String iUrl, final ImageView iView) {
        if ( iUrl == null || iView == null ) {
            Log.e(TAG, "error");
            return;
        }

        final Bitmap image = downloadImage(iUrl);
        iView.setImageBitmap(image);
        Log.w(TAG, "it's ok");
    }

    public static Bitmap downloadImage(String iUrl) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream buf_stream = null;
        try {
            Log.v(TAG, "Starting loading image by URL: " + iUrl);
            conn = (HttpURLConnection) new URL(iUrl).openConnection();
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
            bitmap = BitmapFactory.decodeStream(buf_stream);
            buf_stream.close();
            conn.disconnect();
            buf_stream = null;
            conn = null;
        } catch (MalformedURLException ex) {
            Log.e(TAG, "Url parsing was failed: " + iUrl);
        } catch (IOException ex) {
            Log.d(TAG, iUrl + " does not exists");
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "Out of memory!!!");
            return null;
        } finally {
            if ( buf_stream != null )
                try { buf_stream.close(); } catch (IOException ex) {}
            if ( conn != null )
                conn.disconnect();
        }
        return bitmap;
    }

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

        myTask3 mt = new myTask3();
        mt.execute();

        return convertView;
    }

    public class myTask3 extends AsyncTask <Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            String s1 = str.substring(0, str.indexOf('&'));
            str = str.substring(str.indexOf('&') + 1, str.length());
            String s2 = str.substring(0, str.indexOf('&'));
            str = str.substring(str.indexOf('&') + 1, str.length());
            s3 = str.substring(0, str.indexOf('&'));
            String s4 = str.substring(str.indexOf('&') + 1, str.length());
            viewHolder.txtItem1.setText(s1);
            viewHolder.txtItem2.setText(s2);
            viewHolder.txtItem3.setText(s4);
            viewHolder.image.setImageResource(R.drawable.icon_nullspace);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fetchImage("http://www.h4y.ru/images/x100x100/" + s3, viewHolder.image);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
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
