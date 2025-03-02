package com.example.buzzrfrontend.ui.barberprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.android.volley.RequestQueue;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.Navigation;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Appointment extends AppCompatActivity {

    CalendarView calendar;
    Button button;

    private Navigation nav = new Navigation(this);
    private RequestQueue requestQueue;
    private String apptDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        button = (Button) findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.openToBookingActivity(apptDate);
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                DecimalFormat formatter = new DecimalFormat("00");
                String dayFormatted = formatter.format(day);
                String monthFormatted = formatter.format(month + 1);

                apptDate = monthFormatted +  "/" + dayFormatted + "/" + year;
                button.setText(apptDate);
            }
        });
        calendar.setDate(calendar.getDate() + TimeUnit.DAYS.toMillis(7));
    }

    public static String newDate(int day, int month, int year)
    {
        DecimalFormat formatter = new DecimalFormat("00");
        String dayFormatted = formatter.format(day);
        String monthFormatted = formatter.format(month + 1);

        return("Pick Time For: " + monthFormatted +  " / " + dayFormatted + " / " + year);
    }
}