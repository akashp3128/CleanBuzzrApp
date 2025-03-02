package com.example.buzzrfrontend.ui.barberDashboardView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.Const;
import com.example.buzzrfrontend.data.model.UserType;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class barberDashboardActivity extends AppCompatActivity {

    ApplicationVar appVar;
    RequestQueue requestQueue;
    TextView descInfo;
    int barbId, barbProfId;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_dashboard2);

        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);

        descInfo = findViewById(R.id.descInfo);

        jsonObjGet();

        TextView name = findViewById(R.id.barberName);
        TextView email = findViewById(R.id.barberEmail);
        TextView phoneNum = findViewById(R.id.barberPhoneNum);
        TextView AptInfo = findViewById(R.id.barberApptInfo);

        TextView loggedInAs = findViewById(R.id.barberLoggedInAs);
        TextView greetBarber = findViewById(R.id.greetBarber);

        Button changeDesc = findViewById(R.id.barberChgDesc);
        Button barberChat = findViewById(R.id.barberChat);
        Button logout = findViewById(R.id.barberLogout);

        name.setText("Name: " + appVar.getLoggedInUser().getName());
        email.setText("Email: " + appVar.getLoggedInUser().getEmail());
        phoneNum.setText("Phone Number: " + appVar.getLoggedInUser().getPhoneNumber());
        loggedInAs.setText("Logged in as: " + appVar.getLoggedInUser().getUserName());
        greetBarber.setText("Hello, " + appVar.getLoggedInUser().getName());

        changeDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appVar.getLoggedInUser().getUserType() == UserType.barber) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(appVar.getNav().getContext());
                    builder.setTitle("Title");

                    final EditText input = new EditText(appVar.getNav().getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            test = input.getText().toString();
                            jsonPut();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(appVar.getNav().getContext(), "Action Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();
                } else {
                    Toast.makeText(appVar.getNav().getContext(), "Action Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getLoggedInUser().logout();
                appVar.getNav().openToLoginActivity();
            }
        });
        barberChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToChatActivity();
            }
        });


    }
    public void jsonObjGet()
    {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Const.URL + "/person/" + appVar.getLoggedInUser().getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    descInfo.setText("Current description: " + (response.getJSONObject("barber").getJSONObject("barberProfile").getString("description")));
                    barbId = response.getJSONObject("barber").getInt("id");
                    barbProfId = response.getJSONObject("barber").getJSONObject("barberProfile").getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(appVar.getNav().getContext(), "Data Loaded", Toast.LENGTH_SHORT).show();
                Log.d("Volley", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley", "Error: " + error.getMessage());
                Toast.makeText(appVar.getNav().getContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(req);
    }
    public void jsonPut() {
        try {
            requestQueue = Volley.newRequestQueue(this);
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("description", test);
            StringRequest putRequest = new StringRequest(Request.Method.PUT, Const.URL + "/barberProfile/" + barbId + "/" + barbProfId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Volley", response.toString());
                            jsonObjGet();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(appVar.getNav().getContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return jsonBody == null ? null : jsonBody.toString().getBytes();
                }

            };
            requestQueue.add(putRequest);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}