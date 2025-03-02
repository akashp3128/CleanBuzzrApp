package com.example.buzzrfrontend.ui.barberprofile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    Button button;
    Button button_back;
    Button button_chat;
    Button adminDelete;
    TextView profileName, descriptionHeader, address, description;

    ApplicationVar appVar;
    RequestQueue requestQueue;
    int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);

        profileId = appVar.getNav().getProfileId();

         profileName = findViewById(R.id.chatName);
         descriptionHeader = findViewById(R.id.descHeader);
         address = findViewById(R.id.tv_address);
         description = findViewById(R.id.barbDescription);

        jsonObjGet();
        button = (Button) findViewById(R.id.button_appointment);
        button_back = (Button) findViewById(R.id.button_home);
        button_chat = (Button) findViewById(R.id.button_chat);
        adminDelete = findViewById(R.id.adminDelete);
        if(appVar.getLoggedInUser().getUserType() == UserType.admin)
        {
            adminDelete.setVisibility(View.VISIBLE);
        }
        else
        {
            adminDelete.setVisibility(View.INVISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToAppointmentActivity();
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToDashboard();
            }
        });
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToChatActivity();
            }
        });
        adminDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(appVar.getNav().getContext(), "Deleting User", Toast.LENGTH_SHORT).show();
                                jsonObjDelete();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(appVar.getNav().getContext(), "Action Cancelled", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(appVar.getNav().getContext());
                builder.setMessage("Delete User: Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }

    public void jsonObjDelete()
    {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest dr = new StringRequest(Request.Method.DELETE, Const.URL + "/person/" + profileId,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(appVar.getNav().getContext(), "User Deleted", Toast.LENGTH_SHORT).show();
                        appVar.getNav().openToDashboard();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(appVar.getNav().getContext(), "Action Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(dr);
    }

    public void jsonObjGet()
    {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Const.URL + "/person/" + profileId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    profileName.setText(response.getString("name"));
                    descriptionHeader.setText("About " + response.getString("name"));
                    address.setText(response.getString("phoneNumber"));
                    description.setText(response.getJSONObject("barber").getJSONObject("barberProfile").getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Volley", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley", "Error: " + error.getMessage());
            }
        });
        requestQueue.add(req);
    }
}