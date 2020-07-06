package com.hungdt.calendarview;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomCalendarView extends LinearLayout {

    ImageView imgPrevious, imgNext;
    TextView txtDate;
    GridView gvDay;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance();
    Context context;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");


    List<Date> dates = new ArrayList<>();

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        setUpCalendar();
        imgPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar();
            }
        });
        imgNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar();
            }
        });

        gvDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, ""+dateFormat.format(dates.get(position))+"/"+monthFormat.format(dates.get(position))+"/"+yearFormat.format(dates.get(position)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_calendar_view, this);
        imgNext = view.findViewById(R.id.imgNext);
        imgPrevious = view.findViewById(R.id.imgPrevious);
        txtDate = view.findViewById(R.id.txtDate);
        gvDay = view.findViewById(R.id.gvDay);
    }

    private void setUpCalendar() {
        String instanceDate = dateFormat.format(calendar.getTime());
        txtDate.setText(instanceDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }


}