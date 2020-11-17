package com.example.sharedspace;

import java.util.ArrayList;

public class Subject {
    private String courseID;
    private String courseTitle;
    private ArrayList<String> studentList;
    private ArrayList<Room> roomList;

    public Subject(String courseID, String courseTitle){
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        studentList = new ArrayList<>();
        roomList = new ArrayList<>();
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

    public void createRoom(String title, String roomDescription, long timeToClose, String studentID){ // Also input studentID so we can automatically add the creator into the room
        //roomList.add(new Room());
    }



    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public ArrayList<Room> getRoomList() {
        if (roomList.size()==0) {
            for (int i=1; i< 21;i++) {
                roomList.add(new Room("Room "+i,"This describes a room",3,"1004485"));

                // this extra line is just to simulate test case
                if (i==3||i==7||i==18) {
                    for (int j=0;j<4;j++) roomList.get(i-1).addStudent("1004483");
                }
            }
        }
        return roomList;
    }


}
