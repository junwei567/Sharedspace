package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//TODO: add functionality to create room, and specifically allow creator of the room to delete the room. 

public class RoomListActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth; //is this necessary here? firebase should only be called in our controllers
    DatabaseReference mDatabase;

    ActionBar actionBar;
    BottomNavigationView navigationView;
    ListView listViewRooms;
    FloatingActionButton fab_add_room;

    // fragments used in layouts
    EmptyFragment emptyFragment;
    ProfileFragment profileFragment;
    FriendsFragment friendFragment;

    public final static String ROOM_UID = "room_uid";
    public final static String STUDENT_UID = "student_uid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        fab_add_room = findViewById(R.id.add_room_button);
        
        //gets the course_id that was passed to SubjectActivity from DashboardActivity (so you know what course's rooms to display)
        final String courseType = getIntent().getStringExtra(SubjectActivity.SUBJECT_TYPE);
        final String courseTitle = getIntent().getStringExtra(SubjectActivity.SUBJECT_TITLE);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");
//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if (dataSnapshot.getKey() == courseType) {
//                    thisSubject = dataSnapshot.getValue(Subject.class);
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        actionBar.setTitle(courseTitle); //TODO

        listViewRooms = findViewById(R.id.ListViewRooms);

        // Create adapter passing in the sample user data
        FirebaseListAdapter<Room> roomListAdapter = new FirebaseListAdapter<Room>(this, Room.class,
                R.layout.room_card, mDatabase.child(courseType).child("roomList")) {
            @Override
            protected void populateView(View v, Room model, int position) {
                final Room thisModel = model;
                TextView roomTitleTextView, timeClosedTextView, numberOfPeopleTextView;
                Button joinRoomButton;
                roomTitleTextView = v.findViewById(R.id.room_title);
                timeClosedTextView = v.findViewById(R.id.time_closed);
                numberOfPeopleTextView = v.findViewById(R.id.number_of_people);
                joinRoomButton = v.findViewById(R.id.join_room_button);
                //
                roomTitleTextView.setText(model.getTitle());
                timeClosedTextView.setText("Closed at " + model.getTimeToClose());
                numberOfPeopleTextView.setText(String.valueOf(model.getSizeOfRoom()));
                joinRoomButton.setText(model.isFull() ? "Room Full":"Join Room");
                joinRoomButton.setEnabled(!model.isFull());
                joinRoomButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: update Room.studentIDList using addStudent() inside firebase.
                        // as of now its using studentID, but can modify to use the user's uid too.


                        Intent intent = new Intent(RoomListActivity.this,RoomActivity.class);
                        intent.putExtra(RoomListActivity.ROOM_UID, String.valueOf(thisModel.getRoomUID()));
                        intent.putExtra(RoomListActivity.STUDENT_UID, firebaseAuth.getCurrentUser().getUid());
                        startActivity(intent);
                    }
                });

            }
        };
        // Attach the adapter to the recyclerview to populate items
        listViewRooms.setAdapter(roomListAdapter);

        // creates the default homeFragment
        emptyFragment = new EmptyFragment();
        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
        hft.replace(R.id.content, emptyFragment, "");
        hft.commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        actionBar.setTitle("Home");
                        emptyFragment = new EmptyFragment();
                        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
                        hft.replace(R.id.content, emptyFragment, "");
                        hft.commit();
                        listViewRooms.setVisibility(View.VISIBLE);
                        fab_add_room.show();
                        return true;

                    case R.id.nav_profile:
                        // profile fragment transaction
                        actionBar.setTitle("Profile");
                        profileFragment = new ProfileFragment();
                        FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                        pft.replace(R.id.content, profileFragment, "");
                        pft.commit();
                        listViewRooms.setVisibility(View.INVISIBLE);
                        fab_add_room.hide();
                        return true;

                    case R.id.nav_friends:
                        // users fragment transaction
                        actionBar.setTitle("Friends");
                        friendFragment = new FriendsFragment();
                        FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                        fft.replace(R.id.content, friendFragment, "");
                        fft.commit();
                        listViewRooms.setVisibility(View.INVISIBLE);
                        fab_add_room.hide();
                        return true;
                }
                return false;
            }
        };

}


