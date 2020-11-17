package com.example.sharedspace;

import java.util.ArrayList;

public class Subject {
    private String courseID;
    private String courseTitle;
    private ArrayList<String> studentList;
    private ArrayList<Room> roomList;

    public void Subject(String courseID, String courseTitle){
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
        roomList.add(new Room());
    }

    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
}
