package com.example.sharedspace;

import java.util.ArrayList;

public class Room {
    private long roomUID;
    private String title;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentIDList;

    public Room(String title, String roomDescription, String studentID, long timeStarted, long timeToClose){
        studentIDList = new ArrayList<>();

        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        studentIDList.add(studentID);
        this.roomUID = timeStarted; // roomUID and time started will be in the same variable
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

    public ArrayList<String> getStudentIDList() {
        return studentIDList;
    }
}
