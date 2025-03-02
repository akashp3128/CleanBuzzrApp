package com.example.buzzrfrontend.ui.clientProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class clientAppointmentActivity extends AppCompatActivity {

    ApplicationVar appVar;
    private RequestQueue queue;
    private final List<LinearLayout> layouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_appointments_list);
        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);

        Button goBack = (Button) findViewById(R.id.goBack);
        LinearLayout layout = (LinearLayout) findViewById(R.id.apptList);
        layouts.add(layout);
        getUsers(appVar.getLoggedInUser().getId(), this);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToDashboard();
            }
        });
    }

    private void refresh(final int client, final Context context) {
        this.getUsers(client, context);
    }

    public void getUsers(final int client, final Context context){
        final HashMap<Integer, String> barberNames = new HashMap<>();
        final List<LinearLayout> lays = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        String url = Const.URL + Const.listPersonsEndpoint;
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    public void getAppointments(final int clientToLookup){
                        String url = Const.URL + "/appointments";
                        JsonArrayRequest req = new JsonArrayRequest(url,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        for(int i = 0; i < response.length(); i++)
                                        {
                                            try {
                                                JSONObject j = response.getJSONObject(i); // Step 2 - get information for all the appointments of the current client
                                                if (j.getJSONObject("client").getInt("id") == clientToLookup) {
                                                    String apptText = "";
                                                    apptText += "Barber: " + barberNames.get(j.getJSONObject("barber").getInt("id")) + "\n";
                                                    apptText += "Date: " + j.getString("date") + "\n";
                                                    apptText += "Time: " + j.getString("time") + "\n";
                                                    apptText += "Style: " + j.getString("style") + "\n";
                                                    apptText += "Price: " + j.getString("price") + "\n";
                                                    apptText += "Location: " + j.getString("location");
                                                    final int apptId = j.getInt("id");

                                                    LinearLayout childLayout = new LinearLayout(context);
                                                    childLayout.setGravity(Gravity.CENTER);
                                                    childLayout.setOrientation(LinearLayout.HORIZONTAL);
                                                    TextView apptInfo = new TextView(context);

                                                    apptInfo.setText(apptText);
                                                    childLayout.addView(apptInfo);
                                                    Button button = new Button(context);
                                                    button.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            String deleteUrl = Const.URL + "/appointment/" + apptId;
                                                            StringRequest dr = new StringRequest(Request.Method.DELETE, deleteUrl,
                                                                    new Response.Listener<String>()
                                                                    {
                                                                        @Override // step 3 create delete buttons for each appointment
                                                                        public void onResponse(String response) {
                                                                            Toast.makeText(context, "Appointment Deleted!", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    },
                                                                    new Response.ErrorListener()
                                                                    {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            Toast.makeText(context, "Failed to delete appointment!", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                            );
                                                            queue.add(dr);

                                                            layouts.get(0).removeAllViews();
                                                            refresh(client, context);
                                                        }
                                                    });
                                                    button.setText("Delete Appointment");
                                                    childLayout.addView(button);
                                                    lays.add(childLayout);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        for(View v: lays) {
                                            layouts.get(0).addView(v);
                                        }
                                        Log.d("getAppts() Response", response.toString());
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("Volley", "Error: " + error.getMessage());
                            }

                        });
                        queue.add(req);
                    }

                    @Override // Step 1 get all the appointments
                    public void onResponse(JSONArray response) {
                        int cId = client;
                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject j = response.getJSONObject(i);
                                if(!j.isNull("client") && j.getInt("id") == client) {
                                    cId = j.getJSONObject("client").getInt("id");
                                }
                                if(!j.isNull("barber")) {
                                    barberNames.put(j.getJSONObject("barber").getInt("id"), j.getString("name"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        getAppointments(cId);
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