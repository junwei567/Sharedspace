package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {
    // firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    ListView mListViewSubjects;
    final static String SUBJECT_TYPE="subject";
    final static String SUBJECT_TITLE = "title";
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

        //loading userSubjects from sharedprefs



//        //TODO: useful for adding new objects manually
//        ArrayList<Subject> subjectArrayList = new ArrayList<>();
//        subjectArrayList.add(new Subject("50.001","50001","Introduction to Information Systems and Programming"));
//        subjectArrayList.add(new Subject("50.002","50002","Computer Structures"));
//        subjectArrayList.add(new Subject("50.004","50004","Introduction to Algorithms"));
//
//        for (Subject sub:subjectArrayList) FirebaseDatabase.getInstance().getReference().child("subjects").child(sub.getCourseType()).setValue(sub);
//        FirebaseDatabase.getInstance().getReference().child("messages").child("10000").setValue("messages");

        // creates the default homeFragment
        emptyFragment = new EmptyFragment();
        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
        hft.replace(R.id.content, emptyFragment, "");
        hft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userSubjects = SharedPrefUtils.loadArray(this);

        //a temporary list. testing queries for specific things
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");
        //final List<String> testList = Arrays.asList("50001", "50002");

        subjectList = new ArrayList<>();
        if (userSubjects.size() != 0){
            for (String courseType : userSubjects){
                Query query = mDatabase.orderByChild("courseType").equalTo(courseType);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                            actionBar.setTitle("Home");
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
            cardStudyingTextView.setText(String.valueOf(thisSubject.getRoomList().size()));
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