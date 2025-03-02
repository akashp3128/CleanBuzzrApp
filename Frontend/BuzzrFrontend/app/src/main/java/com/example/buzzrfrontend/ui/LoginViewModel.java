package com.example.buzzrfrontend.ui.loginView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.android.volley.VolleyLog;
import com.example.buzzrfrontend.data.Const;
import com.example.buzzrfrontend.data.LoginRepository;
import com.example.buzzrfrontend.data.Result;
import com.example.buzzrfrontend.data.model.LoggedInUser;
import com.example.buzzrfrontend.data.model.UserType;
import com.example.buzzrfrontend.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private static final String TAG = "LoginViewModel";

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, Context context) {
        // Check if force login is enabled
        if (Const.FORCE_LOGIN_SUCCESS) {
            Log.d(TAG, "Bypassing normal login - FORCE_LOGIN_SUCCESS is enabled");
            
            // Create a mock user for the forced login
            LoggedInUser mockUser;
            
            // Check if this is an admin login
            if (username.equals(Const.EMERGENCY_USERNAME) && password.equals(Const.EMERGENCY_PASSWORD)) {
                Log.d(TAG, "Creating admin user for emergency login");
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
                Log.d(TAG, "Creating client user for forced login: " + username);
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
            
            // Set successful login result
            loginResult.setValue(new LoginResult(new LoggedInUserView(mockUser.getName())));
            return;
        }
        
        // Normal login flow if not forcing success
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password, context);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            System.err.println("Volley" + data.toString());
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }


    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}