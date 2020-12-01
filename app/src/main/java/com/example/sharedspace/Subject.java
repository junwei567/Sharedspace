package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
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

    public static void createRoom(String title, String roomDescription, String studentID, long timeToClose, String thisCourseType){ // Also input studentID so we can automatically add the creator into the room
        final Room newRoom =  new Room(title, roomDescription, studentID, timeToClose, thisCourseType);
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(String.valueOf(newRoom.getRoomUID()), newRoom);
        mDataBase.child(thisCourseType).child("roomList").updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                //roomList.put(String.valueOf(newRoom.getRoomUID()), newRoom);
            }
        });
    }




    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public HashMap<String, Object> getRoomList() {


        // to be deleted if no need test case
        Room newRoom;
        if (roomList.size()==0) {
            for (int i=1; i< 5;i++) {
                newRoom = new Room("Room "+i,"This describes a room", FirebaseAuth.getInstance().getCurrentUser().getUid(), 69000+i, courseType);


                // this extra line is just to simulate test case
                if (i==2||i==7||i==18) {
                    for (int j=0;j<4;j++) newRoom.addStudent("1004483");
                }
                roomList.put(String.valueOf(newRoom.getRoomUID()), newRoom);
            }
        }
        return roomList;
    }

    //TODO
//    @Override
//    public void update(String msg) {
//
//    }
}
