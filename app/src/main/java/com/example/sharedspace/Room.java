package com.example.sharedspace;

import java.util.ArrayList;

public class Room {
    private long roomUID;
    private String title;
    private String roomDescription;
    private long timeToClose;
    private ArrayList<String> studentIDList;
    private final int MAX_ROOM_SIZE = 5; //idk if it's necessary to keep a max room size, we can delete it later



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


    public void addStudent(String studentID) {
        this.studentIDList.add(studentID);
    }

    public int getSizeOfRoom() {
        return this.studentIDList.size();
    }

    //do we need an is_full? I'll instantiate a dummy MAX_ROOM_SIZE here but we can delete that later
    public Boolean isFull() {
        if (getSizeOfRoom() < MAX_ROOM_SIZE) return false;
        return true;
    }

    public String getTimeStarted() {
        return Long.toString(roomUID); //since roomUID and timestarted are now the same variable
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