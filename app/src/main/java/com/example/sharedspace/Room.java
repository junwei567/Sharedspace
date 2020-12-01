package com.example.sharedspace;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class Room{
    private long roomUID;
    private String title;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentUIDList;
    private final int MAX_ROOM_SIZE = 5; //idk if it's necessary to keep a max room size, we can delete it later
    private String subjectCourseType;
    DatabaseReference mDatabase;



    public Room(String title, String roomDescription, String studentUID, long timeToClose, String subjectCourseType){
        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        //this.roomUID = timeStarted; // roomUID and time started will be in the same variable
        this.roomUID = String.valueOf(new Date().getTime()).hashCode();
        this.studentUIDList = new ArrayList<>();
        studentUIDList.add(studentUID);
        this.subjectCourseType = subjectCourseType; //unsure if needed.
        this.mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects").child(subjectCourseType);
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

    public ArrayList<String> getStudentUIDList() {
        return studentUIDList;
    }

    public void addStudent(String studentUID) {
        this.studentUIDList.add(studentUID);
        //notifySubject(studentUID);
    }

    public int getSizeOfRoom() {
        return this.studentUIDList.size();
    }

    public Boolean isFull() {
        if (getSizeOfRoom() < MAX_ROOM_SIZE) return false;
        return true;
    }

//    @Override
//    public void notifySubject(String studentUID) {
//    }
    // TODO: removed getTimeStarted as roomUID is now a unique hashcode.
//    public String getTimeStarted() {
//        return Long.toString(roomUID); //since roomUID and timestarted are now the same variable
//    }
}
