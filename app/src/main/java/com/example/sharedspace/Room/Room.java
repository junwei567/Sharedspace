package com.example.sharedspace.Room;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Room{
    private long roomUID;
    private String title;
    private String roomDescription;
    private long timeToClose;
    private HashMap<String, Object> studentUIDList;
    private final int MAX_ROOM_SIZE = 5; //idk if it's necessary to keep a max room size, we can delete it later
    private String subjectCourseType;
    DatabaseReference mDatabase;



    public Room(String title, String roomDescription, String studentUID, long timeToClose, String subjectCourseType){
        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        //this.roomUID = timeStarted; // roomUID and time started will be in the same variable
        this.roomUID = String.valueOf(new Date().getTime()).hashCode();
        this.studentUIDList = new HashMap<>();
        studentUIDList.put(studentUID, new Integer(2));
        this.subjectCourseType = subjectCourseType; //unsure if needed.
        this.mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects")
                .child(subjectCourseType).child("roomList");
        HashMap<String, Object> newRoom = new HashMap<>();
        newRoom.put(String.valueOf(roomUID), this);
        mDatabase.updateChildren(newRoom);
    }

    public Room(){ // Default constructor needed by Firebase

    }

    public long getRoomUID() {
        return roomUID;
    }

    public String getTitle() {
        return title;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public long getTimeToClose() {
        return timeToClose;
    }

    public HashMap<String, Object> getStudentUIDList() {
        return studentUIDList;
    }

    public void addStudent(String studentUID) {
        HashMap<String, Object> updates = new HashMap<>();
        mDatabase.child(String.valueOf(roomUID)).child("studentUIDList").updateChildren(updates);
        //notifySubject(studentUID);
    }

    public int getSizeOfRoom() {
        try {
            return this.studentUIDList.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public Boolean isFull() {
        if (getSizeOfRoom() < MAX_ROOM_SIZE) return false;
        return true;
    }

}
