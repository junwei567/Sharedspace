package com.example.sharedspace;


import com.example.sharedspace.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//FOR TESTING PURPOSES
//This controller stores a local copy of userSubjects and allSubjects and is implemented as a singleton
//possible to extend methods for rooms as well
//We can think about having a sync method here? then this can be our real controller, if it's not been built yet

public class DummyController {

    private static DummyController INSTANCE = null;
    private List<Subject> userSubjects;
    private List<Subject> allSubjects;

    private DummyController() {
        userSubjects = new ArrayList<>();
        allSubjects = new ArrayList<>();
        allSubjects.addAll(createAllSubjects());
    }

    ;

    public static DummyController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DummyController();
        }
        return INSTANCE;
    }

    private static List<Subject> createAllSubjects() {
        //adds a few selections for allSubjects
        Subject s1 = new Subject("50.001", "Introduction to Information Systems and Programming");
        Subject s2 = new Subject("50.002", "Computational Structures");
        Subject s3 = new Subject("50.003", "Elements of Software Construction");
        Subject s4 = new Subject("50.004", "Introduction to Algorithms");
        Subject s5 = new Subject("50.005", "Computer System Engineering");
        Subject s6 = new Subject("02.137DH", "Introduction to Digital Humanities");
        Subject s7 = new Subject("02.147TS", "Form and Content in Arts, Science and Society");

        return Arrays.asList(s1, s2, s3, s4, s5, s6, s7);
    }

    public List<Subject> getUserSubjects() {
        return userSubjects;
    }

    public List<Subject> getAllSubjects() {
        return allSubjects;
    }

    public void AddSubject(Subject s) {
        userSubjects.add(s);
    }

    public void RemoveSubject(Subject s) {
        userSubjects.remove(s);
    }
}
