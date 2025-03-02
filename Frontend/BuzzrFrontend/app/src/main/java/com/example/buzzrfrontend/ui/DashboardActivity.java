package com.example.buzzrfrontend.ui.dashboardView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.buzzrfrontend.DynamicRVAdapter;
import com.example.buzzrfrontend.DynamicRVModel;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.StaticRvAdapter;
import com.example.buzzrfrontend.StaticRvModel;
import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.Const;
import com.example.buzzrfrontend.data.Navigation;


import com.example.buzzrfrontend.DRVinterface.LoadMore;
import com.example.buzzrfrontend.data.model.UserData;
import com.example.buzzrfrontend.data.model.UserType;
import com.example.buzzrfrontend.ui.loginView.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.buzzrfrontend.data.model.UserType.admin;
import static com.example.buzzrfrontend.data.model.UserType.barber;
import static com.example.buzzrfrontend.data.model.UserType.client;

public class DashboardActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;

    public ApplicationVar appVar;

    List<DynamicRVModel> items = new ArrayList();
    public DynamicRVAdapter dynamicRVAdapter;

    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        setContentView(R.layout.activity_dashboard);
        appVar  = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);
        TextView greetName = findViewById(R.id.greetName);
        greetName.setText(appVar.getLoggedInUser().getName());
        super.onCreate(savedInstanceState);

        Button userProfile = findViewById(R.id.profileButton);

        jsonArrGet();

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToClientProfile();
            }
        });

    }
    public void setData()
    {

        ArrayList<StaticRvModel> item = new ArrayList<>();
        for(UserData x: users)
        {
            item.add(new StaticRvModel(R.drawable.barboo, x.getName()));
        }

        recyclerView = findViewById(R.id.rv_1);
        staticRvAdapter = new StaticRvAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(staticRvAdapter);

        for(UserData x: users)
        {
            items.add(new DynamicRVModel(x.getName(), x.getId()));
        }

        RecyclerView drv = findViewById(R.id.rv_2);

        drv.setLayoutManager(new LinearLayoutManager(this));
        dynamicRVAdapter = new DynamicRVAdapter(drv,this, items);
        drv.setAdapter(dynamicRVAdapter);

        dynamicRVAdapter.setLoadMore(new LoadMore()
        {
            @Override
            public void onLoadMore()
            {
                if(items.size()<=10) {
                    items.add(null);
                    dynamicRVAdapter.notifyItemInserted(items.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size() - 1);
                            dynamicRVAdapter.notifyItemRemoved(items.size());

                            dynamicRVAdapter.notifyDataSetChanged();
                            dynamicRVAdapter.setLoaded();
                        }
                    }, 4000);
                }
                else
                {
                    Toast.makeText(DashboardActivity.this, "Data Completed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    List<UserData> users = new ArrayList<UserData>();

    public void jsonArrGet()
    {
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest req = new JsonArrayRequest(Const.URL + "/persons",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject JsonObj = response.getJSONObject(i);
                                UserData temp = new UserData();
                                temp.setName(JsonObj.getString("name"));
                                temp.setId(JsonObj.getInt("id"));
                                switch(JsonObj.getString("userType"))
                                {
                                    case "Barber:":
                                    case "barber":
                                        users.add(temp);
                                        break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("Volley", response.toString());
                        setData();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, "Connection to the Server Failed", Toast.LENGTH_SHORT).show();
                VolleyLog.d("Volley", "Error: " + error.getMessage());
            }

        });
        requestQueue.add(req);
    }
}