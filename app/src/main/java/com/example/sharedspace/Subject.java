package com.example.sharedspace;

import java.util.ArrayList;

public class Subject {
    private String courseID;
    private String courseTitle;
    private ArrayList<String> studentList;
    private String courseType; // to be used by firebase to separate the children

    // To be removed when firebase functionality added
    private ArrayList<Room> roomList;

    public Subject(String courseID, String courseType,String courseTitle){
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        studentList = new ArrayList<>();
        roomList = new ArrayList<>();
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

    public void createRoom(String title, String roomDescription, String studentID, long timeStarted, long timeToClose){ // Also input studentID so we can automatically add the creator into the room
        roomList.add(new Room(title, roomDescription, studentID, timeToClose));
    }




    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public ArrayList<Room> getRoomList() {
<<<<<<< HEAD

//        // to be deleted if no need test case
//        if (roomList.size()==0) {
//            for (int i=1; i< 21;i++) {
//                roomList.add(new Room("Room "+i,"This describes a room","1004485", 69000));
//
//                // this extra line is just to simulate test case
//                if (i==3||i==7||i==18) {
//                    for (int j=0;j<4;j++) roomList.get(i-1).addStudent("1004483");
//                }
//            }
//        }
=======
        // to be deleted if no need test case
        if (roomList.size()==0) {
            for (int i=1; i< 21;i++) {
                roomList.add(new Room("Room "+i,"This describes a room","1004485", 10000, 69000));

                // this extra line is just to simulate test case
                if (i==3||i==7||i==18) {
                    for (int j=0;j<4;j++) roomList.get(i-1).addStudent("1004483");
                }
            }
        }
>>>>>>> master
        return roomList;
    }


}
