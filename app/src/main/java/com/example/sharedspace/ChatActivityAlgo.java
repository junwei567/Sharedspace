package com.example.sharedspace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivityAlgo extends AppCompatActivity {

    FloatingActionButton fab;
    private FirebaseListAdapter<ModelChatMessage> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fab = findViewById(R.id.fab_write);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inp = findViewById(R.id.input);
                FirebaseDatabase.getInstance()
                        .getReference("Algo")
                        .push()
                        .setValue(new ModelChatMessage(inp.getText().toString(),
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
        adapter = new FirebaseListAdapter<ModelChatMessage>(this, ModelChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("Algo")) {
            @Override
            protected void populateView(View v, ModelChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}