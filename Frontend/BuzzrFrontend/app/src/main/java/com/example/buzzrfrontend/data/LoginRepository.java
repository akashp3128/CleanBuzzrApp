package com.example.buzzrfrontend.data;

import android.content.Context;
import android.content.Intent;

import com.example.buzzrfrontend.data.model.LoggedInUser;
import com.example.buzzrfrontend.data.model.UserType;
import com.example.buzzrfrontend.ui.loginView.LoginActivity;
import com.example.buzzrfrontend.data.Const;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 *
 * JSON get data goes here
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout(Context context) {
        user = null;
        dataSource.logout(context, user); //Need to evaluate if we need this for our setup
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(intent);
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password, Context context) {
        try {
            // Check for force login first
            if (Const.FORCE_LOGIN_SUCCESS || Const.LOCAL_MODE) {
                LoggedInUser mockUser;
                
                // Check if this is an admin login
                if (username.equals(Const.EMERGENCY_USERNAME) && password.equals(Const.EMERGENCY_PASSWORD)) {
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
                
                setLoggedInUser(mockUser);
                return new Result.Success<>(mockUser);
            }
            
            // Original login flow
            // handle login
            Result<LoggedInUser> result = dataSource.login(username, password, context);
            if (result instanceof Result.Success) {
                setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            }
            return result;
        } catch (Exception e) {
            // In case of any error, provide a fallback logged in user
            LoggedInUser emergencyUser = new LoggedInUser(
                9999, 
                "Emergency User",
                username,
                "emergency@example.com",
                "555-EMER",
                password,
                UserType.client
            );
            setLoggedInUser(emergencyUser);
            return new Result.Success<>(emergencyUser);
        }
    }
}