package com.hungdt.calendarview;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.hungdt.calendarview.weekcalendar.WeekCalendar;
import com.hungdt.calendarview.weekcalendar.listener.DateSelectListener;
import com.hungdt.calendarview.weekcalendar.listener.GetViewHelper;
import com.hungdt.calendarview.weekcalendar.listener.WeekChangeListener;
import com.hungdt.calendarview.weekcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeekCalendarActivity extends AppCompatActivity {
    private TextView tvSelectDate;
    private TextView tvWeekChange;

    private List<DateTime> eventDates;
    private WeekCalendar weekCalendar;
    private TextView currentFirstDay;

    private EditText edtDate, edtNumberDay, edtCircle;
    private Button btnSet;

    String beginDay = "01-07-2020";
    SimpleDateFormat sdfDateMy = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfDateLibrary = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYeah = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    Date date = null;
    Date firstDate = null;
    ////////////////////////////
    int countChuKi = 25;
    int countRotKinh = 5;
    int beginRed = 0;
    int endRed = 0;
    int beginEgg = 0;
    int endEgg = 0;
    int eggDay = 0;

    //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_week_calendar);
        tvSelectDate = findViewById(R.id.tv_select_date);
        tvWeekChange = findViewById(R.id.tv_week_change);
        currentFirstDay = findViewById(R.id.tv_current_firstday);
        edtDate = findViewById(R.id.edtDate);
        edtNumberDay = findViewById(R.id.edtNumberDay);
        edtCircle = findViewById(R.id.edtCircle);
        btnSet = findViewById(R.id.btnSet);


        eventDates = new ArrayList<>();

        weekCalendar = findViewById(R.id.week_calendar);
        weekCalendar.setGetViewHelper(new GetViewHelper() {
            @Override
            public View getDayView(int position, View convertView, ViewGroup parent, DateTime dateTime, boolean select) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(WeekCalendarActivity.this).inflate(R.layout.item_day, parent, false);
                }
                //////////
                ImageView imgRedDay = convertView.findViewById(R.id.imgRedDay);
                ImageView imgChoseDay = convertView.findViewById(R.id.imgChoseDay);
                ImageView imgEggDay = convertView.findViewById(R.id.imgEggDay);
                ImageView imgEgg = convertView.findViewById(R.id.imgEgg);

                imgRedDay.setVisibility(View.GONE);
                imgEggDay.setVisibility(View.GONE);
                imgEgg.setVisibility(View.GONE);
                imgChoseDay.setVisibility(View.GONE);
                ////////////
                TextView tvDay = (TextView) convertView.findViewById(R.id.tv_day);
                tvDay.setText(dateTime.toString("dd"));
                if (CalendarUtil.isToday(dateTime) && select) {
                    tvDay.setTextColor(getResources().getColor(R.color.red_800));
                    imgChoseDay.setVisibility(View.VISIBLE);
                } else if (CalendarUtil.isToday(dateTime)) {
                    tvDay.setTextColor(getResources().getColor(R.color.red_800));
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                } else if (select) {
                    tvDay.setTextColor(Color.WHITE);
                    //tvDay.setBackgroundResource(R.drawable.circular_blue);
                    imgChoseDay.setVisibility(View.VISIBLE);
                } else {
                    tvDay.setTextColor(Color.BLACK);
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                }

                //////////
                try {
                    date = sdfDateMy.parse(beginDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                assert date != null;
                calendar.setTime(date);
                Date dateCheck = null;
                try {
                    dateCheck = sdfDateMy.parse(beginDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // chuyển từ date của nó về Date của mình
                String dateLibrary = dateTime.toString();
                String itemDay = dateLibrary.substring(0, 10);
                Date itemDate = null;
                try {
                    itemDate = sdfDateLibrary.parse(itemDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                assert itemDate != null;
                itemDay = sdfDateMy.format(itemDate);
                try {
                    itemDate = sdfDateMy.parse(itemDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("123123", "getDayView: " + itemDay + " / " + itemDate);
                ///////
                Date firstDateOfWeek = null;
                try {
                    firstDateOfWeek = sdfDateMy.parse(weekCalendar.getCurrentFirstDay().toString("dd-MM-yyyy"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("123123", "getCurrentFirstDay: " + firstDateOfWeek);
                while (dateCheck.before(firstDateOfWeek)) {
                    calendar.add(Calendar.DATE, 25);
                    dateCheck = calendar.getTime();
                }
                calendar.add(Calendar.DATE, -25);
                firstDate = calendar.getTime();
                /////////////////
                beginRed = countNumberDay(Integer.parseInt(sdfYeah.format(firstDate)), Integer.parseInt(sdfMonth.format(firstDate)), Integer.parseInt(sdfDay.format(firstDate)));
                endRed = beginRed + countRotKinh;
                eggDay = beginRed + countChuKi - 15;
                beginEgg = eggDay - 6;
                endEgg = eggDay + 4;
                int displayDate = countNumberDay(Integer.parseInt(sdfYeah.format(itemDate)), Integer.parseInt(sdfMonth.format(itemDate)), Integer.parseInt(sdfDay.format(itemDate)));
                if (displayDate > endRed) {
                    beginRed += countChuKi;
                    endRed += countChuKi;
                }
                if (displayDate > eggDay) {
                    eggDay += countChuKi;
                }
                if (displayDate > endEgg) {
                    beginEgg += countChuKi;
                    endEgg += countChuKi;
                }
                Boolean isRedDay = false;
                if (beginRed <= displayDate && displayDate < endRed) {
                    isRedDay=true;
                           /* if (displayDate == beginRed) {
                                imgLeft.setVisibility(View.VISIBLE);
                            }
                            if (displayDate == endRed - 1) {
                                imgRight.setVisibility(View.VISIBLE);
                            }*/
                    imgRedDay.setVisibility(View.VISIBLE);
                }
                if (beginEgg <= displayDate && displayDate <= endEgg) {
                    if(isRedDay){
                        beginEgg++;
                    }else {
                               /* if (displayDate == beginEgg) {
                                    imgLeft.setVisibility(View.VISIBLE);
                                }
                                if (displayDate == endEgg) {
                                    imgRight.setVisibility(View.VISIBLE);
                                }*/
                        imgEggDay.setVisibility(View.VISIBLE);
                    }
                }

                if (displayDate == eggDay) {
                    imgEgg.setVisibility(View.VISIBLE);
                }
                ///////////////

                /*while (dateCheck.before(currentDate)){
                    calendar.add(Calendar.DATE, 25);
                    dateCheck = calendar.getTime();
                }
                calendar.add(Calendar.DATE, -25);
                firstDate = calendar.getTime();*/

              /*  if (CalendarUtil.isRedDay(dateTime)) {
                    imgRedDay.setVisibility(View.VISIBLE);
                }
                if (CalendarUtil.isEggDays(dateTime)) {
                    imgEggDay.setVisibility(View.VISIBLE);
                }
                if (CalendarUtil.isEggDay(dateTime)) {
                    imgEgg.setVisibility(View.VISIBLE);
                }*/

                /*ImageView ivPoint = (ImageView) convertView.findViewById(R.id.iv_point);
                ivPoint.setVisibility(View.GONE);
                for (DateTime d : eventDates) {
                    if(CalendarUtil.isSameDay(d, dateTime)){
                        ivPoint.setVisibility(View.VISIBLE);
                        break;
                    }
                }*/
                return convertView;
            }

            @Override
            public View getWeekView(int position, View convertView, ViewGroup parent, String week) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(WeekCalendarActivity.this).inflate(R.layout.item_week, parent, false);
                }
                TextView tvWeek = (TextView) convertView.findViewById(R.id.tv_week);
                tvWeek.setText(week);
                if (position == 0 || position == 6) {
                    tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                return convertView;
            }
        });

        weekCalendar.setDateSelectListener(new DateSelectListener() {
            @Override
            public void onDateSelect(DateTime selectDate) {
                String text = "Ngày bạn chọn là：" + selectDate.toString("dd-MM-yyyy");
                tvSelectDate.setText(text);
            }
        });

        weekCalendar.setWeekChangedListener(new WeekChangeListener() {
            @Override
            public void onWeekChanged(DateTime firstDayOfWeek) {
                String text = "Ngày đầu tiên trong tuần:" + firstDayOfWeek.toString("dd-MM-yyyy")
                        + ",Ngày cuối tuần:" + new DateTime(firstDayOfWeek).plusDays(6).toString("dd-MM-yyyy");
                tvWeekChange.setText(text);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginDay = edtDate.getText().toString();
                countRotKinh = Integer.parseInt(edtNumberDay.getText().toString());
                countChuKi = Integer.parseInt(edtCircle.getText().toString());
                weekCalendar.removeAllViews();
                weekCalendar.setGetViewHelper(new GetViewHelper() {
                    @Override
                    public View getDayView(int position, View convertView, ViewGroup parent, DateTime dateTime, boolean select) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(WeekCalendarActivity.this).inflate(R.layout.item_day, parent, false);
                        }
                        //////////
                        ImageView imgRedDay = convertView.findViewById(R.id.imgRedDay);
                        ImageView imgChoseDay = convertView.findViewById(R.id.imgChoseDay);
                        ImageView imgEggDay = convertView.findViewById(R.id.imgEggDay);
                        ImageView imgEgg = convertView.findViewById(R.id.imgEgg);

                        imgRedDay.setVisibility(View.GONE);
                        imgEggDay.setVisibility(View.GONE);
                        imgEgg.setVisibility(View.GONE);
                        imgChoseDay.setVisibility(View.GONE);
                        ////////////
                        TextView tvDay = (TextView) convertView.findViewById(R.id.tv_day);
                        tvDay.setText(dateTime.toString("dd"));
                        if (CalendarUtil.isToday(dateTime) && select) {
                            tvDay.setTextColor(getResources().getColor(R.color.red_800));
                            imgChoseDay.setVisibility(View.VISIBLE);
                        } else if (CalendarUtil.isToday(dateTime)) {
                            tvDay.setTextColor(getResources().getColor(R.color.red_800));
                            tvDay.setBackgroundColor(Color.TRANSPARENT);
                        } else if (select) {
                            tvDay.setTextColor(Color.WHITE);
                            //tvDay.setBackgroundResource(R.drawable.circular_blue);
                            imgChoseDay.setVisibility(View.VISIBLE);
                        } else {
                            tvDay.setTextColor(Color.BLACK);
                            tvDay.setBackgroundColor(Color.TRANSPARENT);
                        }

                        //////////
                        try {
                            date = sdfDateMy.parse(beginDay);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calendar = Calendar.getInstance();
                        assert date != null;
                        calendar.setTime(date);
                        Date dateCheck = null;
                        try {
                            dateCheck = sdfDateMy.parse(beginDay);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // chuyển từ date của nó về Date của mình
                        String dateLibrary = dateTime.toString();
                        String itemDay = dateLibrary.substring(0, 10);
                        Date itemDate = null;
                        try {
                            itemDate = sdfDateLibrary.parse(itemDay);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        assert itemDate != null;
                        itemDay = sdfDateMy.format(itemDate);
                        try {
                            itemDate = sdfDateMy.parse(itemDay);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("123123", "getDayView: " + itemDay + " / " + itemDate);
                        ///////
                        Date firstDateOfWeek = null;
                        try {
                            firstDateOfWeek = sdfDateMy.parse(weekCalendar.getCurrentFirstDay().toString("dd-MM-yyyy"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("123123", "getCurrentFirstDay: " + firstDateOfWeek);
                        while (dateCheck.before(firstDateOfWeek)) {
                            calendar.add(Calendar.DATE, 25);
                            dateCheck = calendar.getTime();
                        }
                        calendar.add(Calendar.DATE, -25);
                        firstDate = calendar.getTime();
                        /////////////////
                        beginRed = countNumberDay(Integer.parseInt(sdfYeah.format(firstDate)), Integer.parseInt(sdfMonth.format(firstDate)), Integer.parseInt(sdfDay.format(firstDate)));
                        endRed = beginRed + countRotKinh;
                        eggDay = beginRed + countChuKi - 15;
                        beginEgg = eggDay - 6;
                        endEgg = eggDay + 4;
                        int displayDate = countNumberDay(Integer.parseInt(sdfYeah.format(itemDate)), Integer.parseInt(sdfMonth.format(itemDate)), Integer.parseInt(sdfDay.format(itemDate)));
                        if (displayDate > endRed) {
                            beginRed += countChuKi;
                            endRed += countChuKi;
                        }
                        if (displayDate > eggDay) {
                            eggDay += countChuKi;
                        }
                        if (displayDate > endEgg) {
                            beginEgg += countChuKi;
                            endEgg += countChuKi;
                        }
                        Boolean isRedDay = false;
                        if (beginRed <= displayDate && displayDate < endRed) {
                            isRedDay=true;
                           /* if (displayDate == beginRed) {
                                imgLeft.setVisibility(View.VISIBLE);
                            }
                            if (displayDate == endRed - 1) {
                                imgRight.setVisibility(View.VISIBLE);
                            }*/
                            imgRedDay.setVisibility(View.VISIBLE);
                        }
                        if (beginEgg <= displayDate && displayDate <= endEgg) {
                            if(isRedDay){
                                beginEgg++;
                            }else {
                               /* if (displayDate == beginEgg) {
                                    imgLeft.setVisibility(View.VISIBLE);
                                }
                                if (displayDate == endEgg) {
                                    imgRight.setVisibility(View.VISIBLE);
                                }*/
                                imgEggDay.setVisibility(View.VISIBLE);
                            }
                        }

                        if (displayDate == eggDay) {
                            imgEgg.setVisibility(View.VISIBLE);
                        }
                        ///////////////

                /*while (dateCheck.before(currentDate)){
                    calendar.add(Calendar.DATE, 25);
                    dateCheck = calendar.getTime();
                }
                calendar.add(Calendar.DATE, -25);
                firstDate = calendar.getTime();*/

              /*  if (CalendarUtil.isRedDay(dateTime)) {
                    imgRedDay.setVisibility(View.VISIBLE);
                }
                if (CalendarUtil.isEggDays(dateTime)) {
                    imgEggDay.setVisibility(View.VISIBLE);
                }
                if (CalendarUtil.isEggDay(dateTime)) {
                    imgEgg.setVisibility(View.VISIBLE);
                }*/

                /*ImageView ivPoint = (ImageView) convertView.findViewById(R.id.iv_point);
                ivPoint.setVisibility(View.GONE);
                for (DateTime d : eventDates) {
                    if(CalendarUtil.isSameDay(d, dateTime)){
                        ivPoint.setVisibility(View.VISIBLE);
                        break;
                    }
                }*/
                        return convertView;
                    }

                    @Override
                    public View getWeekView(int position, View convertView, ViewGroup parent, String week) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(WeekCalendarActivity.this).inflate(R.layout.item_week, parent, false);
                        }
                        TextView tvWeek = (TextView) convertView.findViewById(R.id.tv_week);
                        tvWeek.setText(week);
                        if (position == 0 || position == 6) {
                            tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                        return convertView;
                    }
                });
            }
        });

    }

    // Click
    int plus = 0;

    public void addEvent(View view) {
        eventDates.add(new DateTime().plusDays(plus++));
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
        String text = "Ngày đầu tiên của trang hiện tại：" + weekCalendar.getCurrentFirstDay().toString("dd-MM-yyyy");
        currentFirstDay.setText(text);
    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }
}
