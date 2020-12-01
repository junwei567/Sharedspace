package com.example.sharedspace;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class Subject{
    private String courseID;
    private String courseTitle;
    private ArrayList<String> studentList;
    private String courseType; // to be used by firebase to separate the children

    // To be removed when firebase functionality added
    private HashMap<String, Object> roomList;

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

    public void createRoom(String title, String roomDescription, String studentID, long timeToClose){ // Also input studentID so we can automatically add the creator into the room
        Room newRoom =  new Room(title, roomDescription, studentID, timeToClose, courseType);
        roomList.put(String.valueOf(newRoom.getRoomUID()), newRoom);
    }




    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public HashMap<String, Object> getRoomList() {
        Room newRoom;

        // to be deleted if no need test case
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
