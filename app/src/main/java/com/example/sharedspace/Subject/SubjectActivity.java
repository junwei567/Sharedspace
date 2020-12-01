package com.example.sharedspace.Subject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharedspace.EmptyFragment;
import com.example.sharedspace.FriendsFragment;
import com.example.sharedspace.ProfileFragment;
import com.example.sharedspace.R;
import com.example.sharedspace.Room.Room;
import com.example.sharedspace.Room.RoomListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {
    public final static String SUBJECT_TYPE="subject";
    public final static String SUBJECT_TITLE = "title";
    // firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    ListView mListViewSubjects;
    ArrayList<Subject> subjectList;
    SubjectAdapter subjectAdapter;
    List<String> userSubjects;

    // fragments used in layouts
    EmptyFragment emptyFragment;
    ProfileFragment profileFragment;
    FriendsFragment friendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        mListViewSubjects = findViewById(R.id.ListViewSubjects);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //Test cases for adding Subjects.
//        ArrayList<Subject> subjectArrayList = new ArrayList<>();
//        subjectArrayList.add(new Subject("50.001","50001","Introduction to Information Systems and Programming"));
//        subjectArrayList.add(new Subject("50.002","50002","Computer Structures"));
//        subjectArrayList.add(new Subject("50.004","50004","Introduction to Algorithms"));
//        subjectArrayList.add(new Subject("50.003", "50003","Elements of Software Construction"));
//        subjectArrayList.add(new Subject("02.143","02143","Artificial Intelligence and Ethics"));
//        subjectArrayList.add(new Subject("02.204","02204","Technology and the Self"));
//        subjectArrayList.add(new Subject("02.104","02104","The History of International Development in Asia: The Role of Engineers and Designers"));
//
//        for (Subject sub:subjectArrayList) FirebaseDatabase.getInstance().getReference().child("subjects").child(sub.getCourseType()).setValue(sub);

        // Test cases for adding rooms.
        //Subject.createRoom("Homework 3 discussion","Trying to complete Homework by today","1004485", Date.valueOf("2020-12-03").getTime() - 7200,"50004");
        //Subject.createRoom("Week 5 content","Understanding Hash tables","1004485", Date.valueOf("2020-12-02").getTime() + 80800000,"50004");
        //Subject.createRoom("Week 5 content","Understanding Hash tables","1004485", Date.valueOf("2020-12-02").getTime() + 80800000,"50004");
        //Room testRoom1 = Subject.createRoom("Bellman-Ford Algo","help!","1004485", Date.valueOf("2020-12-02").getTime() + 60800000,"50004");

        // Test cases for adding Students in rooms.
//        HashMap<String, Object> updates = new HashMap<>();
//        updates.put("131314", new Integer(1));
//        updates.put("114114", new Integer(1));
//        FirebaseDatabase.getInstance().getReference().child("subjects").child("50004")
//                .child("roomList").child(String.valueOf(testRoom1.getRoomUID()))
//                .child("studentUIDList").updateChildren(updates);


        // creates the default homeFragment
        emptyFragment = new EmptyFragment();
        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
        hft.replace(R.id.content, emptyFragment, "");
        hft.commit();

        actionBar.setTitle("Subjects");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("callback:", "ONRESUME");
        userSubjects = SharedPrefUtils.loadArray(this);

        //a temporary list. testing queries for specific things
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");
        //final List<String> testList = Arrays.asList("50001", "50002");
        subjectList = new ArrayList<>();
        if (userSubjects.size() != 0){
            for (String courseType : userSubjects){
                Query query = mDatabase.orderByChild("courseType").equalTo(courseType);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.w("callback:", "ONDATACHANGE");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Subject subject = snapshot.getValue(Subject.class);
                            subjectList.add(subject);
                            subjectAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            mListViewSubjects.setAdapter(null);
            subjectAdapter = new SubjectAdapter(this, subjectList);
            mListViewSubjects.setAdapter(subjectAdapter);
        }
        else{
            mListViewSubjects.setAdapter(null);
            mListViewSubjects.setEmptyView(findViewById(R.id.empty_list));
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            actionBar.setTitle("Subjects");
                            emptyFragment = new EmptyFragment();
                            FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
                            hft.replace(R.id.content, emptyFragment, "");
                            hft.commit();
                            mListViewSubjects.setVisibility(View.VISIBLE);
                            return true;
                        case R.id.nav_profile:
                            // profile fragment transaction
                            actionBar.setTitle("Profile");
                            profileFragment = new ProfileFragment();
                            FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                            pft.replace(R.id.content, profileFragment, "");
                            pft.commit();
                            mListViewSubjects.setVisibility(View.INVISIBLE);
                            return true;
                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "");
                            fft.commit();
                            mListViewSubjects.setVisibility(View.INVISIBLE);
                            return true;
                    }
                    return false;
                }
            };

    public class SubjectAdapter extends ArrayAdapter<Subject> {
        public SubjectAdapter(Context context, ArrayList<Subject> subjects){
            super(context, 0, subjects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Subject thisSubject = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.subject_card, parent, false);
            }
            // Lookup view for data population
            TextView cardHeaderTextView = convertView.findViewById(R.id.card_header);
            TextView cardSubjectIDTextView = convertView.findViewById(R.id.card_subjectID);
            TextView cardStudyingTextView = convertView.findViewById(R.id.card_studying);
            CardView subjectCard = convertView.findViewById(R.id.subject_card_view);

            // Populate the data into the template view using the data object
            cardHeaderTextView.setText(thisSubject.getCourseTitle());
            cardSubjectIDTextView.setText(thisSubject.getCourseID());
            try {
                cardStudyingTextView.setText(String.valueOf(thisSubject.getRoomList().size()));
            } catch (NullPointerException e) {
                cardStudyingTextView.setText("0");
            }
            subjectCard.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(SubjectActivity.this, RoomListActivity.class);
                   intent.putExtra(SubjectActivity.SUBJECT_TYPE, thisSubject.getCourseType());
                   intent.putExtra(SubjectActivity.SUBJECT_TITLE, thisSubject.getCourseTitle());
                   SubjectActivity.this.startActivity(intent);
               }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }


    //Creates edit button in actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.edit_subjects, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_subjects_button:
                startActivity(new Intent(SubjectActivity.this, SubjectAddActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}