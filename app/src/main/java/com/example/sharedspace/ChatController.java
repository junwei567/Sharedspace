package com.example.sharedspace;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatController {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private String mUsername;
    private Room currentRoom;


    public ChatController(Room currentRoom){
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        this.currentRoom = currentRoom;

        mUsername = mFirebaseUser.getDisplayName();
    }

    public void postMessage(String messageText, String messageUserID, long messageTime){
        mFirebaseDatabase.getReference().child("Messages")
                .push()
                .setValue(new ChatMessage(messageText,
                        messageUserID, messageTime)
                );
    }

    //TODO: getMessage method
}
