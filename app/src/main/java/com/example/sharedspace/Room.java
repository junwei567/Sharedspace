package com.example.sharedspace;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Room {
    private long roomUID;
    private String title;
    private long timeStarted;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentIDList;

    public Room(String title, String roomDescription, String studentID, long timeToClose, long timeStarted){
        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        studentIDList.add(studentID);
        this.timeStarted = timeStarted;
    }

    public Room(){ // Default constructor needed by Firebase

    }

    // Create room then put in values with a hash map, using time as the room UID
    private void registerRoom(String name) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Long datetime = System.currentTimeMillis();
        String roomUID = String.valueOf(datetime);

        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("uid", roomUID);
        hashMap.put("name", name);
        // path to store user data named "Rooms"
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Rooms");
        // put hash map in db
        reference.child(roomUID).setValue(hashMap);

//        Toast.makeText(RegisterActivity.this, "Registered \n" + user.getEmail(), Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
//        finish();

    }

    // count the number of rooms in firebase
    private int countRoom () {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final int[] roomNo = {0};
        databaseReference.child("Rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomNo[0] = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return roomNo[0];
    }

    public long getRoomUID() {
        return roomUID;
    }
}
