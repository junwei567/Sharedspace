package com.example.sharedspace.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.sharedspace.R;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;


public class CalendarActivity extends AppCompatActivity implements CalendarPickerController {
    private static final String LOG_TAG = CalendarActivity.class.getSimpleName();
    AgendaCalendarView mAgendaCalendarView;
    Calendar minDate, maxDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        Context context = this;

        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2020 <-> May 2020 (current) <-> May 2021
        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        readCalendarEvent(context, eventList);
        mockList(eventList);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
    }

    public static void readCalendarEvent(Context context,List<CalendarEvent> eventList) {

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.set(2020,7,1);
        endCal.set(2022,1,1);
        String selection = "((dtstart >= "+startCal.getTimeInMillis()+") AND (dtend <= "+endCal.getTimeInMillis()+"))";
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, selection,
                        null, null);
        cursor.moveToFirst();
        String CNames[] = new String[cursor.getCount()];

        Calendar startD;
        String nameOfEvent;
        String descriptions;
        for (int i = 0; i < 50; i++) {

            nameOfEvent = cursor.getString(1);
            descriptions = cursor.getString(2);
            startD = getDate(Long.parseLong(cursor.getString(3)));
//            endD = getDate(Long.parseLong(cursor.getString(4)));
            Calendar startTime = Calendar.getInstance();
            startTime.set(startD.get(Calendar.YEAR),startD.get(Calendar.MONTH),startD.get(Calendar.DAY_OF_MONTH));
            BaseCalendarEvent event1 = new BaseCalendarEvent(nameOfEvent, descriptions, "NIL",
                    ContextCompat.getColor(context, R.color.MediumPurple), startTime, startTime, true);
            eventList.add(event1);

            CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
    }

    public static Calendar getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar;
    }

    private void mockList(List<CalendarEvent> eventList) {

        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        startTime1.set(2020, 10, 30);
        endTime1.set(2020, 10, 30);
        BaseCalendarEvent event1 = new BaseCalendarEvent("50.001 Cohort Based Learning", "Introduction to Information Systems", "2.507",
                ContextCompat.getColor(this, R.color.CornflowerBlue), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(2020, 11, 7);
        endTime3.set(2020, 11, 7);
        BaseCalendarEvent event3 = new BaseCalendarEvent("50.001 Cohort Based Learning", "Introduction to Information Systems", "2.507",
                ContextCompat.getColor(this, R.color.CornflowerBlue), startTime3, endTime3, true);
        eventList.add(event3);

        Calendar startTime4 = Calendar.getInstance();
        Calendar endTime4 = Calendar.getInstance();
        startTime4.set(2020, 11, 14);
        endTime4.set(2020, 11, 14);
        BaseCalendarEvent event4 = new BaseCalendarEvent("50.001 Cohort Based Learning", "Introduction to Information Systems", "2.507",
                ContextCompat.getColor(this, R.color.CornflowerBlue), startTime4, endTime4, true);
        eventList.add(event4);

        Calendar startTime2 = Calendar.getInstance();
        Calendar endTime2 = Calendar.getInstance();
        startTime2.set(2020, 11, 3);
        endTime2.set(2020, 11, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("50.002 Cohort Based Learning", "Computation Structures", "2.412",
                ContextCompat.getColor(this, R.color.Coral), startTime2, endTime2, true);
        eventList.add(event2);

        Calendar startTime5 = Calendar.getInstance();
        Calendar endTime5 = Calendar.getInstance();
        startTime5.set(2020, 11, 10);
        endTime5.set(2020, 11, 10);
        BaseCalendarEvent event5 = new BaseCalendarEvent("50.002 Cohort Based Learning", "Computation Structures", "2.412",
                ContextCompat.getColor(this, R.color.Coral), startTime5, endTime5, true);
        eventList.add(event5);

        Calendar startTime6 = Calendar.getInstance();
        Calendar endTime6 = Calendar.getInstance();
        startTime6.set(2020, 11, 17);
        endTime6.set(2020, 11, 17);
        BaseCalendarEvent event6 = new BaseCalendarEvent("50.002 Cohort Based Learning", "Computation Structures", "2.412",
                ContextCompat.getColor(this, R.color.Coral), startTime6, endTime6, true);
        eventList.add(event6);

    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d(LOG_TAG, String.format("Selected event: %s", event));
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        }
    }
    
}