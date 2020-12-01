package com.example.sharedspace.Subject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sharedspace.Room.Room;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Subject{
    private String courseID;
    private String courseTitle;
    private ArrayList<String> studentList;
    private String courseType; // to be used by firebase to separate the children

    // To be removed when firebase functionality added
    private HashMap<String, Object> roomList;
    final private static DatabaseReference mDataBase = FirebaseDatabase.getInstance()
            .getReference().child("subjects");

    public Subject(String courseID, String courseType,String courseTitle){
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        studentList = new ArrayList<>();
        roomList = new HashMap<>();
        this.courseType = courseType;
    }

    public Subject() {

    }

    public String getCourseType() {
        return courseType;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void addStudent(String studentID){
        studentList.add(studentID);
    }

    public static Room createRoom(String title, String roomDescription, String studentID, long timeToClose, String thisCourseType){ // Also input studentID so we can automatically add the creator into the room
        final Room newRoom =  new Room(title, roomDescription, studentID, timeToClose, thisCourseType);
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(String.valueOf(newRoom.getRoomUID()), newRoom);
        mDataBase.child(thisCourseType).child("roomList").updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            }
        });
        return newRoom;
    }


    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public HashMap<String, Object> getRoomList() {
        try {
            return roomList;
        } catch (NullPointerException e) {
            return new HashMap<>();
        }

    }
}
