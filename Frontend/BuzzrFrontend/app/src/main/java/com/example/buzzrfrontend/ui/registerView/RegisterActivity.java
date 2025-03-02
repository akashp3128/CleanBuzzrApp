/**
 *
 * Need to redesign the screen associated with this.
 *
 */



package com.example.buzzrfrontend.ui.registerView;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.Const;
import com.example.buzzrfrontend.data.Navigation;
import com.example.buzzrfrontend.data.model.UserData;
import com.example.buzzrfrontend.data.model.LoggedInUser;
import com.example.buzzrfrontend.data.model.UserType;
import com.example.buzzrfrontend.ui.loginView.LoginActivity;
import com.google.gson.JsonObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private UserData uD = new UserData();
    private RequestQueue requestQueue;
    private RequestQueue reqQueue;
    private EditText phoneBox, emailBox, nameBox, usernameBox, passwordBox;
    private Navigation nav = new Navigation(this);
    private Switch userSwitch;
    ApplicationVar appVar;
    int currId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button goBack = findViewById(R.id.goBack);
        Button submit = findViewById(R.id.submitButton);

        userSwitch = findViewById(R.id.userSwitch);
        phoneBox = findViewById(R.id.phoneBox);
        emailBox = findViewById(R.id.emailBox);
        usernameBox = findViewById(R.id.usernameBox);
        nameBox = findViewById(R.id.nameBox);
        passwordBox = findViewById(R.id.passwordBox);



        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.openToLoginActivity();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(phoneBox.getText().equals("") || nameBox.getText().equals("") || usernameBox.getText().equals("") || passwordBox.getText().equals("") || emailBox.getText().equals("")))
                {
                    jsonPost(usernameBox.getText().toString(), nameBox.getText().toString(), passwordBox.getText().toString(), emailBox.getText().toString(), phoneBox.getText().toString());
                    Toast.makeText(RegisterActivity.this, "Registration Submitted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        userSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                        userSwitch.setText("I am a Barber");
                } else {
                       userSwitch.setText("I am a Client");
                }
            }
        });
    }
    String idRecord = "";
    String userTyper;
    public void jsonPost(final String username, final String name, final String password, final String email, final String phonenumber) {
        try {
            if(userSwitch.isChecked())
            {
                userTyper = "barber";
            }
            else
            {
                userTyper = "client";
            }
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("username", username);
            jsonBody.put("email", email);
            jsonBody.put("phonenumber", phonenumber);
            jsonBody.put("password", password);
            jsonBody.put("userType", userTyper);
            jsonBody.put("phoneNo", phonenumber);
            final String requestBody = jsonBody.toString();



            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL + "/person", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Volley", response);
                    idRecord = response;
                    response = response.substring(6);
                    String [] splitNum = response.split(",");
                    splitNum[0].replace(",","");
                    currId = Integer.parseInt(splitNum[0]);
                    jsonPostToType(userTyper, currId);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

  private void jsonPostToType(final String userType, int personId)
    {
        reqQueue = Volley.newRequestQueue(this);
            final JSONObject secondJsonBody = new JSONObject();
            //secondJsonBody.put("id", personId);

            final String secondRequestBody = secondJsonBody.toString();

            StringRequest request = new StringRequest(Request.Method.POST, Const.URL + "/" + userType + "/" + personId, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("Volley", response);
                    if(userType.equals("barber") || userType.equals("Barber"))
                    {
                        response = response.substring(6);
                        String [] splitNum = response.split(",");
                        splitNum[0].replace(",","");
                        jsonPostBarberProfile(currId, Integer.parseInt(splitNum[0]));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return secondRequestBody == null ? null : secondRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", secondRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            reqQueue.add(request);
    }
    private void jsonPostBarberProfile(int personId, int barbId)
    {
        reqQueue = Volley.newRequestQueue(this);
        try {
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", personId);
            jsonBody.put("description", "This person has not modified their description yet!");

            final String requestBody = jsonBody.toString();

            StringRequest request = new StringRequest(Request.Method.POST, Const.URL + "/barberProfile/" + barbId, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("Volley", response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            reqQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}