package com.example.sharedspace;

import java.util.ArrayList;
import java.util.Date;

public class Room {
    private long roomUID;
    private String title;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentUIDList;
    private final int MAX_ROOM_SIZE = 5; //idk if it's necessary to keep a max room size, we can delete it later



    public Room(String title, String roomDescription, String studentUID,/* long timeStarted, */long timeToClose){
        studentUIDList = new ArrayList<>();

        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        studentUIDList.add(studentUID);
        //this.roomUID = timeStarted; // roomUID and time started will be in the same variable
        this.roomUID = String.valueOf(new Date().getTime()).hashCode();

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


    public void addStudent(String studentID) {
        this.studentUIDList.add(studentID);
    }

    public int getSizeOfRoom() {
        return this.studentUIDList.size();
    }

    //do we need an is_full? I'll instantiate a dummy MAX_ROOM_SIZE here but we can delete that later
    public Boolean isFull() {
        if (getSizeOfRoom() < MAX_ROOM_SIZE) return false;
        return true;
    }
    // TODO: removed getTimeStarted as roomUID is now a unique hashcode.
//    public String getTimeStarted() {
//        return Long.toString(roomUID); //since roomUID and timestarted are now the same variable
//    }
}
