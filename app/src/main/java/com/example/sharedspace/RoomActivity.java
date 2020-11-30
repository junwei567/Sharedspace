package com.example.sharedspace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    DatabaseReference mDatabase;

    private FirebaseListAdapter<Message> roomActivityAdapter;
    FloatingActionButton fabPostMessage;
    ListView listOfMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);

        final String roomUID = getIntent().getStringExtra(RoomListActivity.ROOM_UID);
        actionBar.setTitle(roomUID);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(roomUID);

        fabPostMessage = findViewById(R.id.fab_write);
        fabPostMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = findViewById(R.id.input);
                mDatabase.push().setValue(new Message(inp.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                inp.setText("");
            }
        });
        displayChatMessages();

    }

    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_msg);
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


}

