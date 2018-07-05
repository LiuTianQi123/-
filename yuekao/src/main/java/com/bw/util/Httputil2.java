package com.bw.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Httputil2 {
    private static Httplistener httplistener;
    private static Httputil2 httputil2;
    public static Httputil2 getinstance(){
        if (httputil2==null){
            httputil2=new Httputil2();
        }
        return httputil2;
    }
    public void getdata(String url){
        Myasy myasy=new Myasy();
        myasy.execute(url);
    }
    class Myasy extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int code = urlConnection.getResponseCode();

                if (code==200){
                    InputStream inputStream = urlConnection.getInputStream();
                    s = jsondata(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("TAG", "doInBackground: "+s);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            httplistener.jsondatas(s);
        }
    }
    public String jsondata(InputStream inputStream) throws IOException {
        InputStreamReader reader=new InputStreamReader(inputStream);
        BufferedReader reader1=new BufferedReader(reader);
        String s=null;
        StringBuilder builder=new StringBuilder();
        while ((s=reader1.readLine())!=null){
            builder.append(s);
        }
        return builder.toString();
    }



    public interface Httplistener{
        void jsondatas(String s);
    }
    public void setHttplistener(Httplistener httplistener){
        this.httplistener=httplistener;
    }
}
