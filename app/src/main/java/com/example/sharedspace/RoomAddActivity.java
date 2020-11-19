package com.example.sharedspace;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.widget.EditText;

public class RoomAddActivity extends AppCompatActivity {
    ActionBar actionBar;
    EditText addRoomTitle;
    EditText addRoomDesc;
    EditText addRoomDate;
    EditText addRoomTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_add);

        actionBar = getSupportActionBar();
        addRoomTitle = findViewById(R.id.add_room_title);
        addRoomDesc = findViewById(R.id.add_room_desc);
        addRoomDate = findViewById(R.id.add_room_date);



    }
}