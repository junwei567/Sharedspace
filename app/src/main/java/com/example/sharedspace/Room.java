package com.example.sharedspace;

import java.util.ArrayList;

import java.util.Date;

public class Room {
    public final int MAX_ROOM_SIZE = 5;
    private static int roomNumber = 1; // Tracks the number of rooms created


    private int roomID;
    private String title;
    private long timeStarted;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentIDList;

    public Room(String title, String roomDescription, long timeToClose, String studentID){
        this.title = title;
        this.roomDescription = roomDescription;
        this.timeToClose = timeToClose;
        this.studentIDList = new ArrayList<>();
        this.studentIDList.add(studentID);

        this.timeStarted = new Date().getTime();
        this.roomID = roomNumber;
        roomNumber++; // Increment room number so that next room will be set to roomNumber 2, 3, 4, etc.
    }

    public String getTitle() {
        return title;
    }

    public void addStudent(String studentID) {
        this.studentIDList.add(studentID);
    }

    public int getSizeOfRoom() {
        return this.studentIDList.size();
    }

    public Boolean isFull() {
        if (getSizeOfRoom() < MAX_ROOM_SIZE) return false;
        return true;
    }

    public String getTimeStarted() {
        return Long.toString(timeStarted);
    }
}

/*


public class Room {
    private String mRoomTitle;
    private String mTimeClosed;
    private ArrayList<String> mPeopleList;
    private static ArrayList<Room> mRoomList;


    public Room(String mRoomTitle) {
        this.mRoomTitle = mRoomTitle;
    }

    public String getmRoomTitle() {
        return mRoomTitle;
    }

    public String getmTimeClosed() {
        return mTimeClosed;
    }

    public ArrayList<String> getmPeopleList() {
        return mPeopleList;
    }

    private static int lastContactId = 0;

    public static ArrayList<Room> createContactsList(int numContacts) {
        ArrayList<Room> rooms = new ArrayList<Room>();

        for (int i = 1; i <= numContacts; i++) {
            rooms.add(new Room("Person " + ++lastContactId));
        }

        return rooms;
    }

    public static ArrayList<Room> getRoomList() {
        if (mRoomList == null) {
            mRoomList =  createContactsList(20);
        }
        return mRoomList;
    }

    /*
    * Need to add static methods and arraylist to access the current rooms on firebase



}



 */