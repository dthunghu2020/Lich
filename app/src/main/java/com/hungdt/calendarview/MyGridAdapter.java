package com.hungdt.calendarview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    int countChuKi = 25;
    int countRotKinh = 5;
    Date redDateBegin;
    Date redDateEnd;
    Calendar firstCalendar;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    List<Date> dates;
    Calendar currentCalendar;
    Calendar calendar = Calendar.getInstance();
    Calendar dateCalendar = Calendar.getInstance();
    LayoutInflater inflater;
    private ImageView imgLeft, imgRight, imgRedDay, imgDay;
    TextView txtDay;

    public MyGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentCalendar, Calendar firstCalendar) {
        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.firstCalendar = firstCalendar;
        this.currentCalendar = currentCalendar;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //20/6/2020 - Calendar
        //15/7/2020

        Date date = dates.get(position);
        dateCalendar.setTime(date);

        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int currentYear = currentCalendar.get(Calendar.YEAR);

        int instanceDay = calendar.get(Calendar.DAY_OF_MONTH);
        int instanceMonth = calendar.get(Calendar.MONTH) + 1;
        int instanceYear = calendar.get(Calendar.YEAR);


        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        initView(view);

        /////
        Date redBegin = firstCalendar.getTime();
        Calendar calendarEnd = firstCalendar;
        calendarEnd.add(Calendar.DAY_OF_MONTH, 5);
        Date redEnd = calendarEnd.getTime();
        Log.e("1", "getView Out: " + sdfDate.format(redBegin)+"/" + sdfDate.format(redEnd));

        boolean check = false;
        while (!check) {
            if (date.after(redBegin) && date.before(redEnd)) {

                Log.e("1", "getView: In " + sdfDate.format(redBegin) +"/"+ sdfDate.format(redEnd));

                imgRedDay.setVisibility(View.VISIBLE);
                check = true;
            } else {
                firstCalendar.add(Calendar.DAY_OF_MONTH, 25);
                redBegin = firstCalendar.getTime();
                calendarEnd.add(Calendar.DAY_OF_MONTH, 5);
                redEnd = calendarEnd.getTime();
                if (redBegin.after(dates.get(dates.size() - 1))) {
                    check = true;
                }
            }
        }
        /////


        if (displayDay == instanceDay && displayMonth == instanceMonth && displayYear == instanceYear) {
            txtDay.setTextColor(getContext().getResources().getColor(R.color.calendar_selected_day_bg));
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            view.setVisibility(View.GONE);
        }

        txtDay.setText(String.valueOf(displayDay));

        return view;
    }

    private void initView(View view) {
        imgLeft = view.findViewById(R.id.imgLeft);
        imgRight = view.findViewById(R.id.imgRight);
        imgRedDay = view.findViewById(R.id.imgRedDay);
        imgDay = view.findViewById(R.id.imgDay);
        txtDay = view.findViewById(R.id.txtDay);

        imgLeft.setVisibility(View.GONE);
        imgRight.setVisibility(View.GONE);
        imgRedDay.setVisibility(View.GONE);
        imgDay.setVisibility(View.GONE);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
