package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    DatabaseReference mDatabase;
    DatabaseReference mRoom_StudentUIDListDataBase;

    private FirebaseListAdapter<Message> roomActivityAdapter;
    FloatingActionButton fabPostMessage;
    ListView listOfMessages;
    RelativeLayout messageBoard;

    String roomUID;
    String studentUID;
    String courseType;

    // fragments used in layouts
    EmptyFragment emptyFragment;
    ProfileFragment profileFragment;
    FriendsFragment friendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        messageBoard = findViewById(R.id.message_board);

        roomUID = getIntent().getStringExtra(RoomListActivity.ROOM_UID);
        studentUID = getIntent().getStringExtra(RoomListActivity.STUDENT_UID);
        courseType = getIntent().getStringExtra(SubjectActivity.SUBJECT_TYPE);

        actionBar.setTitle(roomUID);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(roomUID);

        //update firebase.Room.studentUIDList upon entering the room.

//        mRoom_StudentUIDListDataBase = FirebaseDatabase.getInstance().getReference().child("subjects").child(courseType)
//                .child("roomList").child(roomUID).child("studentUIDList");
//        mRoom_StudentUIDListDataBase.push().setValue(studentUID);


        fabPostMessage = findViewById(R.id.fab_write);
        fabPostMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = findViewById(R.id.input);
                if (inp.getText().length()!=0) {
                    mDatabase.push().setValue(new Message(inp.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName())
                    );
                }
                inp.setText("");
            }
        });
        displayChatMessages();

        // creates the default homeFragment
        emptyFragment = new EmptyFragment();
        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
        hft.replace(R.id.content, emptyFragment, "");
        hft.commit();

    }

    private void displayChatMessages() {
        listOfMessages = findViewById(R.id.list_of_msg);
        roomActivityAdapter = new FirebaseListAdapter<Message>(this, Message.class,
                R.layout.message, mDatabase) {
            @Override
            protected void populateView(View v, Message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(roomActivityAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            actionBar.setTitle("Home");
                            messageBoard.setVisibility(View.VISIBLE);
                            FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
                            hft.replace(R.id.content, emptyFragment, "");
                            hft.commit();
                            return true;

                        case R.id.nav_profile:
                            // profile fragment transaction
                            actionBar.setTitle("Profile");
                            profileFragment = new ProfileFragment();
                            FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                            pft.replace(R.id.content, profileFragment, "");
                            pft.commit();
                            messageBoard.setVisibility(View.INVISIBLE);
                            return true;

                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "");
                            fft.commit();
                            messageBoard.setVisibility(View.INVISIBLE);
                            return true;
                    }
                    return false;

                }
            };

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseReference minReference = FirebaseDatabase.getInstance().getReference()
                .child("subjects").child(courseType).child("roomList").child(roomUID);
        minReference.child("studentUIDList").child(studentUID).removeValue();
        minReference.child("studentUIDList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()==null) {
                    FirebaseDatabase.getInstance().getReference().child("subjects").child(courseType)
                        .child("roomList").child(roomUID).removeValue();
                    //startActivity(new Intent(RoomActivity.this, RoomListActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if (check.getRoot() == null) {
//            FirebaseDatabase.getInstance().getReference().child("subjects").child(courseType)
//                    .child("roomList").child(roomUID).removeValue();
//        }
        // Logic to remove room
    }
}


