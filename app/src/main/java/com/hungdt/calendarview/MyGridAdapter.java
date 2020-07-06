package com.hungdt.calendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar instantDate;
    LayoutInflater inflater;
    public MyGridAdapter(@NonNull Context context, int resource) {
        super(context, R.layout.sing);
    }
}
