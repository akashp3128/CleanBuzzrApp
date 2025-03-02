package com.example.buzzrfrontend.ui.barberprofile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.Const;
import com.example.buzzrfrontend.data.Navigation;
import com.example.buzzrfrontend.data.model.UserData;
import com.example.buzzrfrontend.data.model.UserType;
import com.example.buzzrfrontend.ui.loginView.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static com.example.buzzrfrontend.data.model.UserType.admin;
import static com.example.buzzrfrontend.data.model.UserType.barber;
import static com.example.buzzrfrontend.data.model.UserType.client;

public class Booking extends AppCompatActivity {

    TimePicker picker;
    Button button;
    ApplicationVar appVar;

    private Navigation nav = new Navigation(this);
    private RequestQueue queue;
    private int barberId;
    private int clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);
        final int barberProfileId = appVar.getNav().getProfileId();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        final String date = getIntent().getExtras().getString("date");

        picker = (TimePicker) findViewById(R.id.timePicker);
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                button.setText(newTime(hour, minute));
            }
        });

        button = (Button) findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String t = picker.getCurrentHour() + ":" + picker.getCurrentMinute();
                getUsers(appVar.getLoggedInUser().getId(), barberProfileId, date, t);
                nav.openToBarberProfileActivity(barberProfileId);
            }
        });
    }

    public static String newTime(int hour, int minute)
    {
        DecimalFormat formatter = new DecimalFormat("00");
        String minuteFormatted = formatter.format(minute);

        if(hour == 0)
        {
            return("Confirm Time of: " + (hour + 12) + ":" + minuteFormatted + " AM");
        }
        else if(hour < 12)
        {
            return("Confirm Time of: " + hour + ":" + minuteFormatted + " AM");
        }
        else if(hour == 12)
        {
            return("Confirm Time of: " + hour + ":" + minuteFormatted + " PM");
        }
        else
        {
            return("Confirm Time of: " + (hour - 12) + ":" + minuteFormatted + " PM");
        }
    }

    public void getUsers(final int client, final int barber, final String date, final String time){
        queue = Volley.newRequestQueue(this);
        String url = Const.URL + Const.listPersonsEndpoint;
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    public void makeAppointment(final String cl, final String barb){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("date", date);
                        params.put("time", time);
                        params.put("style", "haircut");
                        params.put("price", "$10");
                        params.put("location", "stall" + (int) Math.random()+10);

                        String url = Const.URL + "/appointment/" + cl + "/" + barb;
                        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.e("Error: ", error.getMessage());
                            }


                        });
                        queue.add(req);
                    }

                    @Override
                    public void onResponse(JSONArray response) {
                        int cId = client, bId = barber;
                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject j = response.getJSONObject(i);
                                if(!j.isNull("client") && j.getInt("id") == client) {
                                    cId = j.getJSONObject("client").getInt("id");
                                } else if (!j.isNull("barber") && j.getInt("id") == barber) {
                                    bId = j.getJSONObject("barber").getInt("id");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        makeAppointment(Integer.toString(cId), Integer.toString(bId));
                        Log.d("GetUsers() Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley", "Error: " + error.getMessage());
            }

        });
        queue.add(req);
    }
}