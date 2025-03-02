package com.example.buzzrfrontend.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.buzzrfrontend.data.model.LoggedInUser;
import com.example.buzzrfrontend.data.model.UserData;
import com.example.buzzrfrontend.data.model.UserType;
import com.example.buzzrfrontend.ui.loginView.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

public class LoginDataSource {
    private static final String TAG = "LoginDataSource";
    private RequestQueue requestQueue;
    private List<LoggedInUser> loggedInUser = new ArrayList<>();
    private boolean isLoginError = false;
    private String errorMessage = "";

    public Result<LoggedInUser> login(String username, String password, final Context context) {
        try {
            // Check if force login is enabled
            if (Const.FORCE_LOGIN_SUCCESS || Const.LOCAL_MODE) {
                Log.d(TAG, "Bypassing normal login in LoginDataSource - FORCE_LOGIN_SUCCESS or LOCAL_MODE is enabled");
                
                // Create a mock user for the forced login
                LoggedInUser mockUser;
                
                // Check if this is an admin login
                if (username.equals(Const.EMERGENCY_USERNAME) && password.equals(Const.EMERGENCY_PASSWORD)) {
                    Log.d(TAG, "Creating admin user for emergency login in DataSource");
                    mockUser = new LoggedInUser(
                        1, 
                        "Admin User",
                        Const.EMERGENCY_USERNAME,
                        "admin@example.com",
                        "555-ADMIN",
                        Const.EMERGENCY_PASSWORD,
                        UserType.admin
                    );
                } else {
                    // Regular user login
                    Log.d(TAG, "Creating client user for forced login in DataSource: " + username);
                    mockUser = new LoggedInUser(
                        1000, 
                        username.isEmpty() ? "Client User" : username,
                        username,
                        username + "@example.com",
                        "555-123-4567",
                        password,
                        UserType.client
                    );
                }
                
                // Return successful result
                return new Result.Success<>(mockUser);
            }
            
            // Check if we're in LOCAL_MODE before proceeding with network requests
            if (Const.LOCAL_MODE) {
                Log.d(TAG, "LOCAL_MODE is enabled, skipping server connection");
                LoggedInUser mockUser = new LoggedInUser(
                    1000, 
                    username.isEmpty() ? "Local User" : username,
                    username,
                    username + "@example.com",
                    "555-LOCAL",
                    password,
                    UserType.client
                );
                return new Result.Success<>(mockUser);
            }
            
            // Reset error flags and login list
            isLoginError = false;
            errorMessage = "";
            loggedInUser.clear();
            
            // Use real authentication instead of fake user
            validateUser(username, password, context);
            
            // Wait for validation to complete (this is a simple approach - in production, use callbacks)
            int maxRetries = 10;
            while (loggedInUser.isEmpty() && !isLoginError && maxRetries > 0) {
                Thread.sleep(500); // Wait half a second
                maxRetries--;
            }
            
            if (isLoginError) {
                String message = errorMessage.isEmpty() ? 
                    "Network error. Please check your internet connection." : errorMessage;
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return new Result.Error(new IOException(message));
            } else if (!loggedInUser.isEmpty()) {
                return new Result.Success<>(loggedInUser.get(0));
            } else {
                String message = "Invalid username or password";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return new Result.Error(new IOException(message));
            }
        } catch (Exception e) {
            String message = "Error logging in: " + e.getMessage();
            Log.e(TAG, message, e);
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            return new Result.Error(new IOException(message, e));
        }
    }

    public void logout(Context context, LoggedInUser user) {
        user.logout();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private void setLoggedInUser(LoggedInUser usr) {
        loggedInUser.add(usr);
    }

    // attempt to fix login
    private void validateUser(String username, String password, Context context) {
        final String user = username;
        final String pass = password;

        Log.d(TAG, "Validating user: " + username + " against server: " + Const.URL + Const.listPersonsEndpoint);
        
        requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest req = new JsonArrayRequest(Const.URL + Const.listPersonsEndpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response received: " + response.toString());
                        boolean userFound = false;
                        
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject JsonObj = response.getJSONObject(i);
                                
                                String serverUsername = JsonObj.getString("username");
                                String serverPassword = JsonObj.getString("password");
                                
                                Log.d(TAG, "Checking server user: " + serverUsername);
                                
                                if(serverUsername.equalsIgnoreCase(user) && serverPassword.equals(pass)){
                                    userFound = true;
                                    UserData usrData = new UserData();
                                    usrData.setUserName(serverUsername); // Use the server's casing
                                    usrData.setEmail(JsonObj.getString("email"));
                                    usrData.setId(JsonObj.getInt("id"));
                                    usrData.setPassword(serverPassword);
                                    
                                    // Handle different phone number field names in the API
                                    try {
                                        if (JsonObj.has("phoneNo")) {
                                            usrData.setPhoneNumber(JsonObj.getString("phoneNo"));
                                        } else if (JsonObj.has("phonenumber")) {
                                            usrData.setPhoneNumber(JsonObj.getString("phonenumber"));
                                        } else if (JsonObj.has("phone")) {
                                            usrData.setPhoneNumber(JsonObj.getString("phone"));
                                        } else {
                                            usrData.setPhoneNumber("Unknown");
                                        }
                                    } catch (Exception e) {
                                        Log.e(TAG, "Error parsing phone number: " + e.getMessage());
                                        usrData.setPhoneNumber("Error");
                                    }
                                    
                                    // Handle user type with better error checking
                                    String userTypeStr = "";
                                    try {
                                        userTypeStr = JsonObj.getString("userType").toLowerCase().trim();
                                    } catch (Exception e) {
                                        Log.e(TAG, "Error getting userType: " + e.getMessage());
                                        userTypeStr = "client"; // Default to client
                                    }
                                    
                                    switch(userTypeStr) {
                                        case "admin": usrData.setUserType(UserType.admin); break;
                                        case "client": usrData.setUserType(UserType.client); break;
                                        case "barber": usrData.setUserType(UserType.barber); break;
                                        default: 
                                            Log.w(TAG, "Unknown user type: " + userTypeStr + ", defaulting to client");
                                            usrData.setUserType(UserType.client); 
                                            break;
                                    }
                                    
                                    // Try to get name, default to username if not available
                                    try {
                                        if (JsonObj.has("name") && !JsonObj.getString("name").isEmpty()) {
                                            usrData.setName(JsonObj.getString("name"));
                                        } else {
                                            usrData.setName(serverUsername);
                                        }
                                    } catch (Exception e) {
                                        usrData.setName(serverUsername);
                                    }
                                    
                                    LoggedInUser lusr = new LoggedInUser(usrData);
                                    Log.d(TAG, "User authenticated successfully: " + serverUsername);
                                    setLoggedInUser(lusr);
                                    return;
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON parsing error", e);
                                errorMessage = "Error parsing server response";
                                isLoginError = true;
                            }
                        }
                        
                        if (!userFound && !isLoginError) {
                            Log.d(TAG, "No matching user found for: " + user);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoginError = true;
                Log.e(TAG, "Network error: " + error.toString());
                
                if (error.networkResponse != null) {
                    // Get status code and detailed error
                    int statusCode = error.networkResponse.statusCode;
                    String data = new String(error.networkResponse.data);
                    Log.e(TAG, "Status code: " + statusCode + ", Response data: " + data);
                    errorMessage = "Server error (Code: " + statusCode + ")";
                } else if (error.getMessage() != null) {
                    // Connection error or timeout
                    errorMessage = "Network error: " + error.getMessage();
                    Log.e(TAG, errorMessage);
                } else {
                    // Unknown error
                    errorMessage = "Cannot connect to server. Please check if the server is running.";
                    Log.e(TAG, "Unknown network error");
                }
                
                // Log the URL being used to help with debugging
                Log.e(TAG, "Attempted to connect to: " + Const.URL + Const.listPersonsEndpoint);
            }
        });
        
        // Add timeout for the request
        req.setShouldCache(false);
        req.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
            10000, // 10 seconds timeout
            1, // Max retries
            1.0f // No backoff multiplier
        ));
        
        requestQueue.add(req);
    }
}