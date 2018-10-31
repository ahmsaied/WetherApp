package com.example.rcc.session36;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tv_day, tv_date, tv_high, tv_low, tv_speed, tv_humidity, tv_pressure;
    private ListView lv_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_day=findViewById(R.id.tv_day);
        tv_date=findViewById(R.id.tv_date);
        tv_high=findViewById(R.id.tv_high);
        tv_low=findViewById(R.id.tv_low);
        tv_speed=findViewById(R.id.tv_speed);
        tv_humidity=findViewById(R.id.tv_humidity);
        tv_pressure=findViewById(R.id.tv_pressure);

        lv_forecast=findViewById(R.id.lv_forecast);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22cairo%2C%20eg%22)%20and%20u%3D'c'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root=new JSONObject(response);
                            JSONObject query=root.getJSONObject("query");
                            JSONObject results=query.getJSONObject("results");
                            JSONObject channel=results.getJSONObject("channel");

                            JSONObject wind=channel.getJSONObject("wind");
                            String wind_speed=wind.getString("speed");
                            tv_speed.setText(wind_speed);

                            JSONObject atmosphere=channel.getJSONObject("atmosphere");
                            String humidity=atmosphere.getString("humidity");
                            tv_humidity.setText(humidity);
                            String pressure=atmosphere.getString("pressure");
                            tv_pressure.setText(pressure);

                            JSONObject item=channel.getJSONObject("item");



                            JSONArray forecast=item.getJSONArray("forecast");
                            JSONObject to_day=forecast.getJSONObject(0);
                            String day=to_day.getString("day");
                            tv_day.setText(day);
                            String date=to_day.getString("date");
                            tv_date.setText(date);
                            String high=to_day.getString("high");
                            tv_high.setText(high);
                            String low=to_day.getString("low");
                            tv_low.setText(low);

                            ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_list_item_1,0);

                            for (int i=1;i<forecast.length();i++){
                                JSONObject fjo=forecast.getJSONObject(i);
                                String fday= fjo.getString("day");
                                String fdate=fjo.getString("date");
                                String fhigh=fjo.getString("high");
                                String flow=fjo.getString("low");
                                String forecast_result="day: "+fday+"\t\tdate: "+
                                        fdate+"\t\thigh: "+fhigh+"\t\tlow: "+flow;
                                adapter.add(forecast_result);
                            }

                            lv_forecast.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                RequestQueue queue = Volley.newRequestQueue(this);
                String url ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22cairo%2C%20eg%22)%20and%20u%3D'c'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.substring(0,500));
                                try {
                                    JSONObject root=new JSONObject(response);
                                    JSONObject query=root.getJSONObject("query");
                                    JSONObject results=query.getJSONObject("results");
                                    JSONObject channel=results.getJSONObject("channel");

                                    JSONObject wind=channel.getJSONObject("wind");
                                    String wind_speed=wind.getString("speed");
                                    tv_speed.setText(wind_speed);

                                    JSONObject atmosphere=channel.getJSONObject("atmosphere");
                                    String humidity=atmosphere.getString("humidity");
                                    tv_humidity.setText(humidity);
                                    String pressure=atmosphere.getString("pressure");
                                    tv_pressure.setText(pressure);

                                    JSONObject item=channel.getJSONObject("item");
                                    JSONArray forecast=item.getJSONArray("forecast");
                                    JSONObject to_day=forecast.getJSONObject(0);
                                    String day=to_day.getString("day");
                                    tv_day.setText(day);
                                    String date=to_day.getString("date");
                                    tv_date.setText(date);
                                    String high=to_day.getString("high");
                                    tv_high.setText(high);
                                    String low=to_day.getString("low");
                                    tv_low.setText(low);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
                break;
        }
        return true;
    }
}
