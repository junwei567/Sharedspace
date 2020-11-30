package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    // firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    ListView mListViewSubjects;
    final static String SUBJECT_TYPE="subject";
    final static String SUBJECT_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");

        navigationView.setOnNavigationItemSelectedListener(selectedListener);



        mListViewSubjects = findViewById(R.id.ListViewSubjects);

        FirebaseListAdapter<Subject> subjectListAdapter = new FirebaseListAdapter<Subject>(this, Subject.class,
                R.layout.subject_card, mDatabase) {
            @Override
            protected void populateView(View v, Subject model, int position) {
//                if (model.getCourseType() in subjecttitlearraylist) {
//                    storagesubjectarray store model
//                }

                final Subject thisModel = model;
                TextView cardHeaderTextView, cardSubjectIDTextView, cardStudyingTextView;
                CardView subjectCard;
                cardHeaderTextView = v.findViewById(R.id.card_header);
                cardSubjectIDTextView = v.findViewById(R.id.card_subjectID);
                cardStudyingTextView = v.findViewById(R.id.card_studying);
                subjectCard = v.findViewById(R.id.subject_card_view);
                cardHeaderTextView.setText(model.getCourseTitle());
                cardSubjectIDTextView.setText(model.getCourseID());
                cardStudyingTextView.setText(String.valueOf(model.getRoomList().size()));
                subjectCard.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SubjectActivity.this, RoomListActivity.class);
                        intent.putExtra(SubjectActivity.SUBJECT_TYPE, thisModel.getCourseType());
                        intent.putExtra(SubjectActivity.SUBJECT_TITLE, thisModel.getCourseTitle());
                        SubjectActivity.this.startActivity(intent);

                    }
                });
            }
        };


        mListViewSubjects.setAdapter(subjectListAdapter);

        //TODO: useful for adding new objects manually
        ArrayList<Subject> subjectArrayList = new ArrayList<>();
        subjectArrayList.add(new Subject("50.001","50001","Introduction to Information Systems and Programming"));
        subjectArrayList.add(new Subject("50.002","50002","Computer Structures"));
        subjectArrayList.add(new Subject("50.004","50004","Introduction to Algorithms"));

        //for (Subject sub:subjectArrayList) mDatabase.child(sub.getCourseType()).setValue(sub);
        //FirebaseDatabase.getInstance().getReference().child("messages").child("10000").setValue("messages");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            actionBar.setTitle("Home");
                            EmptyFragment emptyFragment = new EmptyFragment();
                            FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
                            hft.replace(R.id.content, emptyFragment, "");
                            hft.addToBackStack(null);
                            hft.commit();
                            mListViewSubjects.setVisibility(View.VISIBLE);

                            //removes ALL fragments, should show the basic layout of dashboard activity
//                            for (Fragment fragment: getSupportFragmentManager().getFragments()){
//                                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                            }
                            return true;

                        case R.id.nav_profile:
                            // profile fragment transaction
                            actionBar.setTitle("Profile");
                            ProfileFragment profileFragment = new ProfileFragment();
                            FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                            pft.replace(R.id.content, profileFragment, "");
                            pft.addToBackStack(null);
                            pft.commit();

                            mListViewSubjects.setVisibility(View.INVISIBLE);
                            return true;

                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            FriendsFragment friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "");
                            fft.addToBackStack(null);
                            fft.commit();

                            mListViewSubjects.setVisibility(View.INVISIBLE);
                            return true;
                    }
                    return false;
                }
            };
}