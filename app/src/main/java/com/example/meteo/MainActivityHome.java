package com.example.meteo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityHome extends AppCompatActivity {
    TextView ville;
    TextView tmp;
    TextView tmpmin;
    TextView tmpmax;
    TextView txtpression;
    TextView txthumidite;
    TextView windsSpeed;
    TextView txtdate;
    TextView degwind;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        final Context co = this;
        Intent intent = getIntent();

        longitude =  intent.getStringExtra("longitude");
        latitude = intent.getStringExtra("latitude");

        System.out.println(longitude + "recuperer");
        System.out.println(latitude + "recupereee");

        ville=findViewById(R.id.txtville);
        tmp=findViewById(R.id.temp);
        tmpmin=findViewById(R.id.tempmin);
        tmpmax=findViewById(R.id.tempmax);
        txtpression=findViewById(R.id.pression);
        txthumidite=findViewById(R.id.humid);
        windsSpeed=findViewById(R.id.speedwind);
        degwind=findViewById(R.id.degwind);
        txtdate=findViewById(R.id.date);

   //     Toolbar toolbar = findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        ImageView imgview=findViewById(R.id.img);
        imgview.setImageResource(R.drawable.cloudy);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=e457293228d5e1465f30bcbe1aea456b";
        //https://api.openweathermap.org/data/2.5/weather?q=London&appid=e457293228d5e1465f30bcbe1aea456b
        // l'ancienne clé : 5bd7e048cf1ef62c79254f75dfe27d19
        // la clé actuelle: e457293228d5e1465f30bcbe1aea456b
        //clé 2022 : e457293228d5e1465f30bcbe1aea456b


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.i("MyLog","----------------------------------------------");
                    Log.i("MyLog",response);

                    JSONObject jsonObject=new JSONObject(response);

                    Date date=new Date(jsonObject.getLong("dt")*1000);
                    SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("dd-MMM-yyyy' T 'HH:mm");
                    String dateString=simpleDateFormat.format(date);

                    JSONObject main=jsonObject.getJSONObject("main");
                    JSONObject wind=jsonObject.getJSONObject("wind");
                    JSONObject coord=jsonObject.getJSONObject("coord");
                    String name=jsonObject.getString("name");
                    int Temp=(int)(main.getDouble("temp")-273.15);
                    int TempMin=(int)(main.getDouble("temp_min")-273.15);
                    int TempMax=(int)(main.getDouble("temp_max")-273.15);
                    int Pression=(int)(main.getDouble("pressure"));
                    int Humidite=(int)(main.getDouble("humidity"));
                    int windspped=(int)(wind.getDouble("speed"));
                  //  String nomm=(String) (name.getString("name"));
                    int deg=(int)(wind.getDouble("deg"));
                    double lon=(coord.getDouble("lon"));
                    double lat=(coord.getDouble("lat"));

                    JSONArray weather=jsonObject.getJSONArray("weather");
                    String meteo=weather.getJSONObject(0).getString("main");

                    txtdate.setText(dateString);
                    tmp.setText(String.valueOf(Temp+"°C"));
                    tmpmin.setText(String.valueOf(TempMin)+"°C");
                    tmpmax.setText(String.valueOf(TempMax)+"°C");
                    txtpression.setText(String.valueOf(Pression+" hPa"));
                    windsSpeed.setText(String.valueOf(windspped+" km/h"));
                    degwind.setText(String.valueOf(deg+" degree wind"));
                    txthumidite.setText(String.valueOf(Humidite)+ "%");
                    ville.setText(name);

                    Log.i("Weather","----------------------------------------------");
                    Log.i("Meteo",meteo);
                    setImage(meteo);
                    Toast.makeText(co,meteo, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext( ), response, Toast.LENGTH_LONG).show( );

                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myIntent = new Intent(MainActivityHome.this, MapsActivityHome.class);
                            myIntent.putExtra("key", ville.getText()); //Optional parameters
                            longitude =  String.valueOf(lon);
                            latitude =  String.valueOf(lat);
                            myIntent.putExtra("longitude",  longitude);
                            myIntent.putExtra("latitude",latitude);
                            MainActivityHome.this.startActivity(myIntent);

                        }
                    });


                    FloatingActionButton courbe = findViewById(R.id.courbe);
                    courbe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myIntent = new Intent(MainActivityHome.this, Diagram.class);
                            // myIntent.putExtra("key", ville.getText()); //Optional parameters
                            myIntent.putExtra("ville",ville.getText());
                            longitude =  String.valueOf(lon);
                            latitude =  String.valueOf(lat);
                            myIntent.putExtra("longitude",  longitude);
                            myIntent.putExtra("latitude",latitude);
                            MainActivityHome.this.startActivity(myIntent);

                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.i("MyLog","-------Connection problem-------------------");
                        Toast.makeText(MainActivityHome.this,
                                "City not fond",Toast.LENGTH_LONG).show();


                    }
                });

        queue.add(stringRequest);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       // SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));




        return true;
    }

    public void setImage(String s)
    {
        ImageView imgview=findViewById(R.id.img);
        if(s.equals("Rain")) {
            imgview.setImageResource(R.drawable.rainy);
        }
        else if(s.equals("Clear")){
            imgview.setImageResource(R.drawable.sunny);}
        else if(s.equals("Thunderstorm")) {
            imgview.setImageResource(R.drawable.thunderstorm);}
        else if(s.equals("Clouds"))
        {
            imgview.setImageResource(R.drawable. atmospheric);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
