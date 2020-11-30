package com.example.sharedspace;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class RoomActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);

        final String roomUID = getIntent().getStringExtra(RoomListActivity.ROOM_UID);
        actionBar.setTitle(roomUID);

    }
}