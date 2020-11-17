package com.example.sharedspace;

import java.util.ArrayList;
import java.util.Date;

public class Room {
    private static int roomNumber = 1; // Tracks the number of rooms created

    private int roomID;
    private String title;
    private long timeStarted;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentIDList;

    public void Room(String title, String roomDescription, long timeToClose, String studentID){
        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        studentIDList.add(studentID);

        this.timeStarted = new Date().getTime();
        this.roomID = roomNumber;
        roomNumber++; // Increment room number so that next room will be set to roomNumber 2, 3, 4, etc.
    }
}
