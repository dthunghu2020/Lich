package com.hungdt.calendarview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.hungdt.calendarview.weekcalendar.WeekCalendar;
import com.hungdt.calendarview.weekcalendar.listener.DateSelectListener;
import com.hungdt.calendarview.weekcalendar.listener.GetViewHelper;
import com.hungdt.calendarview.weekcalendar.listener.WeekChangeListener;
import com.hungdt.calendarview.weekcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekCalendarActivity extends AppCompatActivity {
    private TextView tvSelectDate;
    private TextView tvWeekChange;

    private List<DateTime> eventDates;
    private WeekCalendar weekCalendar;
    private TextView currentFirstDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_calendar);
        tvSelectDate = findViewById(R.id.tv_select_date);
        tvWeekChange = findViewById(R.id.tv_week_change);
        currentFirstDay =  findViewById(R.id.tv_current_firstday);
        eventDates = new ArrayList<>();

        weekCalendar = findViewById(R.id.week_calendar);
        weekCalendar.setGetViewHelper(new GetViewHelper() {
            @Override
            public View getDayView(int position, View convertView, ViewGroup parent, DateTime dateTime, boolean select) {
                if(convertView == null){
                    convertView = LayoutInflater.from(WeekCalendarActivity.this).inflate(R.layout.item_day, parent, false);
                }
                TextView tvDay = (TextView) convertView.findViewById(R.id.tv_day);
                tvDay.setText(dateTime.toString("d"));
                if(CalendarUtil.isToday(dateTime) && select){
                    tvDay.setTextColor(Color.WHITE);
                    tvDay.setBackgroundResource(R.drawable.circular_blue);
                } else if(CalendarUtil.isToday(dateTime)){
                    tvDay.setTextColor(getResources().getColor(R.color.colorTodayText));
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                } else if(select){
                    tvDay.setTextColor(Color.WHITE);
                    tvDay.setBackgroundResource(R.drawable.circular_blue);
                } else {
                    tvDay.setTextColor(Color.BLACK);
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                }

                ImageView ivPoint = (ImageView) convertView.findViewById(R.id.iv_point);
                ivPoint.setVisibility(View.GONE);
                for (DateTime d : eventDates) {
                    if(CalendarUtil.isSameDay(d, dateTime)){
                        ivPoint.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                return convertView;
            }

            @Override
            public View getWeekView(int position, View convertView, ViewGroup parent, String week) {
                if(convertView == null){
                    convertView = LayoutInflater.from(WeekCalendarActivity.this).inflate(R.layout.item_week, parent, false);
                }
                TextView tvWeek = (TextView) convertView.findViewById(R.id.tv_week);
                tvWeek.setText(week);
                if(position == 0 || position == 6){
                    tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                return convertView;
            }
        });

        weekCalendar.setDateSelectListener(new DateSelectListener() {
            @Override
            public void onDateSelect(DateTime selectDate) {
                String text = "Ngày bạn chọn là：" + selectDate.toString("yyyy-MM-dd");
                tvSelectDate.setText(text);
            }
        });

        weekCalendar.setWeekChangedListener(new WeekChangeListener() {
            @Override
            public void onWeekChanged(DateTime firstDayOfWeek) {
                String text = "Ngày đầu tiên trong tuần:" + firstDayOfWeek.toString("yyyy-MM-dd")
                        + ",Ngày cuối tuần:" + new DateTime(firstDayOfWeek).plusDays(6).toString("yyyy-MM-dd");
                tvWeekChange.setText(text);
            }
        });

    }

    int plus = 0;
    public void addEvent(View view) {
        eventDates.add(new DateTime().plusDays(plus ++));
        weekCalendar.refresh();
    }

    public void gotoDate(View view) {
        weekCalendar.gotoDate(new DateTime().plusWeeks((int) (Math.random() * 10)));
    }

    public void gotoToday(View view) {
        weekCalendar.gotoDate(new DateTime());
    }

    public void setSelectDate(View view) {
        weekCalendar.setSelectDateTime(new DateTime(2017, 6, 10, 0, 0));
    }

    public void getCurrentFirstDay(View view) {
        String text = "Ngày đầu tiên của trang hiện tại：" + weekCalendar.getCurrentFirstDay().toString("yyyy-MM-dd");
        currentFirstDay.setText(text);
    }
}
