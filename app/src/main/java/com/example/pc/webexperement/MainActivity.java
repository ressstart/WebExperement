package com.example.pc.webexperement;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    static final String PATH = "http://myitschool.ru";
    static final String TAG = "MAIN";
    TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getWeather(View view) {
        EditText editText = findViewById(R.id.edit_text);
        new GetWeather().execute("http://api.apixu.com/v1/current.json?key=b097c735a5ae48efbe4120721182504&q=ufa&quot" + editText);
    }

    private class GetWeather extends AsyncTask<String,Void,String>{ //1-данные до запуска, 2-прогресс(классы-обертки)
        @Override
        protected String doInBackground(String... strings) {
            return getResponse(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            TextView textView = findViewById(R.id.textView);
            try{
                JSONObject jsonObject = new JSONObject(s);
                textView.setText(jsonObject.getJSONObject("current").getString("temp_c"));
            }catch (JSONException e){
                 e.printStackTrace();
            }
            //textView.setText(s);
            // super.onPostExecute(s);
        }

        private String getResponse(String urlString){
            BufferedReader reader = null;
            //textView = (TextView) findViewById(R.id.urlText);
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                urlConnection.connect();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder s = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    s.append(line + "\n");
                }
                return s.toString();


            } catch (Exception e) {
                e.printStackTrace();
               // Log.d(TAG, "No internet for you " + e.getMessage());
            }
            return "";
        }

    }




}




    /*private class ProgressURL extends AsyncTask <String,Void, String> {

        private String cenConnect(){
            BufferedReader reader = null;
            textView = (TextView) findViewById(R.id.textView);

            try{
                URL url = new URL(PATH);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                urlConnection.connect();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder s = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null) {
                    s.append((line + "\n"));
                }
            }
            catch (Exception e){
                Log.d(TAG, "No internet for you" + e.getMessage());
            }
        }

    }
}*/
