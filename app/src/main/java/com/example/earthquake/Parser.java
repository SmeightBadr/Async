package com.example.earthquake;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Parser extends AsyncTask<Void,Void,Void>{

    private String bufferData = "";
    private String finalData= "";
    public String data = "";
    @Override
    protected Void doInBackground(Void... voids) {
        URL url;
        try {
            url = new URL(Constants.url);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line !=null){
                bufferData = bufferedReader.readLine();
                bufferData = bufferData+line;

            }
            JSONObject jsonObject = new JSONObject(bufferData);
            JSONArray jsonArray = jsonObject.getJSONArray("features");
            for(int i = 0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject1 =jsonArray.getJSONObject(i);
               JSONObject properties =jsonObject1.getJSONObject("properties");
                finalData = "title:"+ properties.get("title")+"\n"
                        +"place:" + properties.get("place");
                data = data+finalData;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        MainActivity.textView.setText(this.data);
    }
}
