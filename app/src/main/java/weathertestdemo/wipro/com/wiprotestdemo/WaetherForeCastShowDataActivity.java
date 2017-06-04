package weathertestdemo.wipro.com.wiprotestdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jagdishnagar on 5/28/2017.
 */

public class WaetherForeCastShowDataActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private WeatherAdapter mAdapter;
        private ProgressDialog mdialog;
        private ArrayList<WeatherForecast> mWeatherforecastList;

      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activty_weather_forecast);

          String mGetCityTemp = getIntent().getStringExtra("city");
          String mGetCountryTemp = getIntent().getStringExtra("cpuntry");

          mdialog = new ProgressDialog(WaetherForeCastShowDataActivity.this);
          mdialog.setMessage("please wait....");
          mdialog.show();

          recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
          mWeatherforecastList = new ArrayList<WeatherForecast>();



          RequestQueue queue = Volley.newRequestQueue(this);
          String url = "http://api.openweathermap.org/data/2.5/forecast?q="+mGetCityTemp+","+mGetCountryTemp+"&mode=Json&appid=1614c2f62c53acdf7943cc4015737357";
          // Request a string response from the provided URL.
          StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          // Display the first 500 characters of the response string.
                         // mTextView.setText("Response is: "+ response.substring(0,500));
                          try {
                              JSONObject jsonObject = new JSONObject(response);
                              JSONArray jsonArray = jsonObject.getJSONArray("list");

                              for(int i = 0 ; i<jsonArray.length() ; i++) {
                                  WeatherForecast mWeatherforecast = new WeatherForecast();
                                  JSONObject jobj = jsonArray.getJSONObject(i);
                                  if(jobj.has("main")) {
                                          mWeatherforecast.setmMinTemperature(jobj.getJSONObject("main").getString("temp_min"));
                                          mWeatherforecast.setmMaxTemperature(jobj.getJSONObject("main").getString("temp_max"));
                                          mWeatherforecast.setmTime(jobj.getString("dt_txt"));
                                          mWeatherforecastList.add(mWeatherforecast);
                                  }
                              }

                              if(mWeatherforecastList!=null && mWeatherforecastList.size()>0) {
                                  mAdapter = new WeatherAdapter(mWeatherforecastList);
                                  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                  recyclerView.setLayoutManager(mLayoutManager);
                                  recyclerView.setItemAnimator(new DefaultItemAnimator());
                              }else{
                                  Toast.makeText(getApplicationContext(),"Data is not Available", Toast.LENGTH_LONG).show();
                              }

                          } catch (JSONException e) {
                              e.printStackTrace();
                          }

                          recyclerView.setAdapter(mAdapter);
                          mdialog.dismiss();
                      }
                  }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                 // mTextView.setText("That didn't work!");
                  mdialog.dismiss();
              }
          });
          // Add the request to the RequestQueue.
          queue.add(stringRequest);
    }
}

