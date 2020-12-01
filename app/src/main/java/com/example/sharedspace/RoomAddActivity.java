package com.example.sharedspace;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomAddActivity extends AppCompatActivity implements View.OnClickListener{
    ActionBar actionBar;
    EditText addRoomTitle, addRoomDesc, addRoomDate, addRoomTime;
    Button saveButton, dateButton, timeButton;
    String courseType;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference subjectReference;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_add);

        addRoomTitle = findViewById(R.id.add_room_title);
        addRoomDesc = findViewById(R.id.add_room_desc);
        addRoomDate = findViewById(R.id.add_room_date);
        addRoomTime = findViewById(R.id.add_room_time);
        saveButton = findViewById(R.id.save_button);
        dateButton = findViewById(R.id.date_button);
        timeButton = findViewById(R.id.time_button);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Add a Room");

        courseType = getIntent().getStringExtra(SubjectActivity.SUBJECT_TYPE);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        subjectReference = firebaseDatabase.getReference("subjects").child(courseType).child("roomList");
    }

    @Override
    public void onClick(View v) {

        if (v == dateButton) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    addRoomDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == timeButton) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,
                                      int minute) {

                    addRoomTime.setText(hourOfDay + ":" + minute);
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == saveButton) {

            //saves all, creates new firebase room
            String title = addRoomTitle.getText().toString();
            String desc = addRoomDesc.getText().toString();
            String creatorID = mAuth.getCurrentUser().getUid();

            long outTime;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String dateString = addRoomDate.getText().toString() + " " + addRoomTime.getText().toString() + ":00";
            try{
                //formatting the dateString to convert it into a Date
                Date date = sdf.parse(dateString);
                outTime = date.getTime();
                Subject.createRoom(title, desc, creatorID, outTime, courseType);
                startActivity(new Intent(RoomAddActivity.this, RoomListActivity.class));

            }catch(ParseException e){
                e.printStackTrace();
                Toast.makeText(this, "Please input a real date", Toast.LENGTH_SHORT).show();
            }


        }
    }


}
