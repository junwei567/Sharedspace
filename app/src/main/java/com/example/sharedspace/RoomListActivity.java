package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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


//public class ChatActivityInfoSys extends AppCompatActivity {
//
//    FloatingActionButton fab;
//    private FirebaseListAdapter<ModelChatMessage> adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        fab = findViewById(R.id.fab_write);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText inp = findViewById(R.id.input);
//                FirebaseDatabase.getInstance()
//                        .getReference("InfoSys")
//                        .push()
//                        .setValue(new ModelChatMessage(inp.getText().toString(),
//                                FirebaseAuth.getInstance()
//                                        .getCurrentUser()
//                                        .getDisplayName())
//                        );
//                inp.setText("");
//            }
//        });
//
//        displayChatMessages();
//    }
//
//    private void displayChatMessages() {
//        ListView listOfMessages = findViewById(R.id.list_of_msg);
//        adapter = new FirebaseListAdapter<ModelChatMessage>(this, ModelChatMessage.class,
//                R.layout.message, FirebaseDatabase.getInstance().getReference("InfoSys")) {
//            @Override
//            protected void populateView(View v, ModelChatMessage model, int position) {
//                // Get references to the views of message.xml
//                TextView messageText = (TextView)v.findViewById(R.id.message_text);
//                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
//
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
//            }
//        };
//
//        listOfMessages.setAdapter(adapter);
//    }
//
//
//}
public class RoomListActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth; //is this necessary here? firebase should only be called in our controllers
    DatabaseReference mDatabase;

    ActionBar actionBar;
    BottomNavigationView navigationView;
    ListView listViewRooms;
    Subject thisSubject;

    public final static String COURSE_ID_KEY = "COURSE_ID_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);



        //gets the course_id that was passed to SubjectActivity from DashboardActivity (so you know what course's rooms to display)
        final String courseType = getIntent().getStringExtra(SubjectActivity.SUBJECT_TYPE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey() == courseType) {
                    thisSubject = dataSnapshot.getValue(Subject.class);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        actionBar.setTitle("title");//thisSubject.getCourseTitle()); //TODO

        listViewRooms = findViewById(R.id.ListViewRooms);

        //we need the controller to help us get the Subject from its courseID, this is just a dummy to test
        //thisSubject = new Subject("500001", "info sys");

        // Create adapter passing in the sample user data
        FirebaseListAdapter<Room> adapter = new FirebaseListAdapter<Room>(this, Room.class,
                R.layout.room_card, mDatabase.child("50001").child("roomList")) {
            @Override
            protected void populateView(View v, Room model, int position) {
                TextView roomTitleTextView, timeClosedTextView, numberOfPeopleTextView;
                Button joinRoomButton;
                roomTitleTextView = v.findViewById(R.id.room_title);
                timeClosedTextView = v.findViewById(R.id.time_closed);
                numberOfPeopleTextView = v.findViewById(R.id.number_of_people);
                joinRoomButton = v.findViewById(R.id.join_room_button);
//                roomTitleTextView.setText(room.getTitle());
//                timeClosedTextView.setText("Closed at "+room.getTimeToClose());
//                numberPeopleTextView.setText(String.valueOf(room.getSizeOfRoom()));
//                button.setText(room.isFull() ? "Room Full":"Join Room");
//                button.setEnabled(!room.isFull());
                roomTitleTextView.setText(model.getTitle());
                timeClosedTextView.setText("Closed at" + model.getTimeToClose());
                numberOfPeopleTextView.setText(String.valueOf(model.getSizeOfRoom()));
                joinRoomButton.setText(model.isFull() ? "Room Full":"Join Room");
                joinRoomButton.setEnabled(!model.isFull());

            }
        };
        // Attach the adapter to the recyclerview to populate items
        listViewRooms.setAdapter(adapter);
        // Set layout manager to position the items

    }


}


