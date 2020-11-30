//NOTE: HomeFragment describes the behaviour of all of the subject cards

package com.example.sharedspace;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import com.example.sharedspace.Calendar.CalendarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public HomeFragment() {
        // Required empty public constructor
    }

    private static final int CALENDAR_REQUEST_CODE = 400;
    String calendarPermission[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        firebaseAuth = FirebaseAuth.getInstance();
//        user = firebaseAuth.getCurrentUser();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("Users");
        calendarPermission = new String[]{Manifest.permission.READ_CALENDAR};
        if (!checkCalendarPermission()) requestCalendarPermission();

        Button butt = view.findViewById(R.id.discussion_chat);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubjectActivity.class);
                startActivity(intent);
            }
        });

        Button cbutt = view.findViewById(R.id.calendar_butt);
        cbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private boolean checkCalendarPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCalendarPermission() {
        requestPermissions(calendarPermission, CALENDAR_REQUEST_CODE);
    }

}