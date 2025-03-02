package com.example.buzzrfrontend.data;

import android.content.Context;
import android.content.Intent;

import com.example.buzzrfrontend.ui.adminDashboardView.adminDashboardActivity;
import com.example.buzzrfrontend.ui.barberDashboardView.barberDashboardActivity;
import com.example.buzzrfrontend.ui.clientProfile.clientAppointmentActivity;
import com.example.buzzrfrontend.ui.clientProfile.clientProfileActivity;
import com.example.buzzrfrontend.ui.dashboardView.DashboardActivity;
import com.example.buzzrfrontend.ui.barberprofile.Appointment;
import com.example.buzzrfrontend.ui.barberprofile.Booking;
import com.example.buzzrfrontend.ui.barberprofile.Chat;
import com.example.buzzrfrontend.ui.barberprofile.ProfileActivity;
import com.example.buzzrfrontend.ui.loginView.LoginActivity;
import com.example.buzzrfrontend.ui.registerView.RegisterActivity;

public class Navigation {
    Context context;
    int profId;
    public Navigation(Context context){
        this.context = context;
    }
    public Navigation()
    {

    }
    public void setContext(Context context)
    {
        this.context = context;
    }
    public Context getContext() {
        return context;
    }
    public void openToRegisterActivity(){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
    public void openToBarberProfileActivity(int id) {
        profId = id;
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }
    public void openToLoginActivity(){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    public void openToDashboard(){
        Intent intent = new Intent(context, DashboardActivity.class);
        context.startActivity(intent);
    }
    public void openToAppointmentActivity()
    {
        Intent intent = new Intent(context, Appointment.class);
        context.startActivity(intent);
    }
    public void openToBookingActivity(String date)
    {
        Intent intent = new Intent(context, Booking.class);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }
    public void openToChatActivity()
    {
        Intent intent = new Intent(context, Chat.class);
        context.startActivity(intent);
    }
    public void openToAdminDashboard()
    {
        Intent intent = new Intent(context, adminDashboardActivity.class);
        context.startActivity(intent);
    }
    public void openToBarberDashboard()
    {
        Intent intent = new Intent(context, barberDashboardActivity.class);
        context.startActivity(intent);
    }
    public void openToClientProfile()
    {
        Intent intent = new Intent(context, clientProfileActivity.class);
        context.startActivity(intent);
    }
    public void openToClientAppointmentActivity()
    {
        Intent intent = new Intent(context, clientAppointmentActivity.class);
        context.startActivity(intent);
    }
    public int getProfileId()
    {
        return profId;
    }
    //Need to figure out how to link information between activities.
}
