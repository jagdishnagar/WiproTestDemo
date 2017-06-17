package weathertestdemo.wipro.com.wiprotestdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class WeatherForeCastShowDataActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private WeatherAdapter mAdapter;
        private ProgressDialog mdialog;
        private ArrayList<WeatherPojo> mWeatherforecastList;

       @Override
       protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activty_weather_forecast);
          

          recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
          mWeatherforecastList = new ArrayList<WeatherPojo>();

           mdialog = new ProgressDialog(WeatherForeCastShowDataActivity.this);
           mdialog.setMessage("please wait....");
           mdialog.show();

           // NetWork Connection Check
           if(ConnectionCheck.Check(this)) {
              RequestQueue queue = Volley.newRequestQueue(this);
              String url = Util.URL_BASE+"Bangalore,india"+"&mode=Json&appid=1614c2f62c53acdf7943cc4015737357";
              // Request a string response from the provided URL.

              StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                      new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {
                              try {
                                  JSONObject jsonObject = new JSONObject(response);
                                  JSONArray jsonArray = jsonObject.getJSONArray("list");

                                  for (int i = 0; i < jsonArray.length(); i++) {
                                      WeatherPojo mWeatherforecast = new WeatherPojo();
                                      JSONObject jobj = jsonArray.getJSONObject(i);
                                      if (jobj.has("main")) {
                                          mWeatherforecast.setmMinTemperature(jobj.getJSONObject("main").getString("temp_min"));
                                          mWeatherforecast.setmMaxTemperature(jobj.getJSONObject("main").getString("temp_max"));
                                          mWeatherforecast.setmTime(jobj.getString("dt_txt"));
                                          mWeatherforecastList.add(mWeatherforecast);
                                      }
                                  }

                                  if (mWeatherforecastList != null && mWeatherforecastList.size() > 0) {
                                      mAdapter = new WeatherAdapter(mWeatherforecastList);
                                      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                      recyclerView.setLayoutManager(mLayoutManager);
                                      recyclerView.setItemAnimator(new DefaultItemAnimator());
                                      recyclerView.setAdapter(mAdapter);
                                      mdialog.dismiss();
                                  } else {
                                      Toast.makeText(getApplicationContext(), "For this location Data is not Available", Toast.LENGTH_LONG).show();
                                  }

                              } catch (Exception e) {
                                  e.printStackTrace();
                                  mdialog.dismiss();
                              }
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
          }else {
              Toast.makeText(this, "Please check yuor internet connection" ,Toast.LENGTH_LONG).show();
          }
    }
}

