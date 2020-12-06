package com.example.sharedspace.Room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedspace.EmptyFragment;
import com.example.sharedspace.FriendsFragment;
import com.example.sharedspace.ProfileFragment;
import com.example.sharedspace.R;
import com.example.sharedspace.Subject.SubjectActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RoomListActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
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

    private String courseTitle;


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
        courseTitle = getIntent().getStringExtra(SubjectActivity.SUBJECT_TITLE);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");
//
        actionBar.setTitle(courseTitle);

        listViewRooms = findViewById(R.id.ListViewRooms);

        // Create adapter passing in the sample user data
        FirebaseListAdapter<Room> roomListAdapter = new FirebaseListAdapter<Room>(this, Room.class,
                R.layout.room_card, mDatabase.child(courseType).child("roomList")) {
            @Override
            protected void populateView(View v, Room model, int position) {
                final Room thisModel = model;
                TextView roomTitleTextView, timeClosedTextView, numberOfPeopleTextView, descriptionTextView;
                Button joinRoomButton;
                roomTitleTextView = v.findViewById(R.id.room_title);
                timeClosedTextView = v.findViewById(R.id.time_closed);
                descriptionTextView = v.findViewById(R.id.desc);
                numberOfPeopleTextView = v.findViewById(R.id.number_of_people);
                joinRoomButton = v.findViewById(R.id.join_room_button);
                //
                // Creating date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                // Creating date from milliseconds
                Date dateDisplayed = new Date(model.getTimeToClose());
                timeClosedTextView.setText("Due date: " + simpleDateFormat.format(dateDisplayed));

                roomTitleTextView.setText(model.getTitle());
                numberOfPeopleTextView.setText(String.valueOf(model.getSizeOfRoom()));
                descriptionTextView.setText(model.getRoomDescription());
                joinRoomButton.setText(model.isFull() ? "Room Full":"Join Room");
                joinRoomButton.setEnabled(!model.isFull());
                joinRoomButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String enterRoomUID = String.valueOf(thisModel.getRoomUID());

                        Intent intent = new Intent(RoomListActivity.this,RoomActivity.class);
                        intent.putExtra(RoomListActivity.ROOM_UID, enterRoomUID);
                        intent.putExtra(RoomListActivity.STUDENT_UID, firebaseAuth.getCurrentUser().getUid());
                        intent.putExtra(SubjectActivity.SUBJECT_TYPE, courseType);
                        intent.putExtra(SubjectActivity.SUBJECT_TITLE, thisModel.getTitle());
                        HashMap<String, Object> updates = new HashMap<>();
                        updates.put(firebaseAuth.getCurrentUser().getUid(), new Integer(1));
                        FirebaseDatabase.getInstance().getReference().child("subjects").child(courseType)
                                .child("roomList").child(enterRoomUID)
                                .child("studentUIDList").updateChildren(updates);
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

        fab_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomListActivity.this, RoomAddActivity.class);
                intent.putExtra(SubjectActivity.SUBJECT_TYPE, courseType);
                startActivity(intent);
            }
        });

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        actionBar.setTitle(courseTitle);
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


