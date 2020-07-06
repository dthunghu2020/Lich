package com.hungdt.calendarview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity2 extends AppCompatActivity {
    CustomCalendarView customCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);

        customCalendarView = findViewById(R.id.custom_calendar_view);
    }
}
