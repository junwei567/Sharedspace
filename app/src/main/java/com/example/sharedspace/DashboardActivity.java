package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharedspace.dummy.DummyController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class DashboardActivity extends AppCompatActivity {

    // firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    DummyController controller;
    //Button uselessButton;
    public final static String COURSE_ID_KEY = "COURSE_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);

        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //temporary testing list of subjects, this list should be retrieved from the controller onCreate
//        Subject s1 = new Subject("50.004", "Introduction to Algorithms");
//        Subject s2 = new Subject("50.001", "Introduction to Information Systems and Programming");
//        List<Subject> slist = new ArrayList<Subject>();
//        slist.add(s1);
//        slist.add(s2);


        //a button to move to subjectActivity, for testing
//        uselessButton = findViewById(R.id.uselessButton);
//        uselessButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, SubjectActivity.class);
//                startActivity(intent);
//            }
//        });



        // modifies actionbar
        actionBar.setTitle("Home");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            actionBar.setTitle("Home");

                            //removes ALL fragments, should show the basic layout of dashboard activity
                            for (Fragment fragment: getSupportFragmentManager().getFragments()){
                                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                            }
                            return true;

                        case R.id.nav_profile:
                            // profile fragment transaction
                            actionBar.setTitle("Profile");
                            ProfileFragment profileFragment = new ProfileFragment();
                            FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                            pft.replace(R.id.content, profileFragment, "Profile");
                            pft.addToBackStack("Profile");
                            pft.commit();
                            return true;

                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            FriendsFragment friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "Friends");
                            fft.addToBackStack("Friends");
                            fft.commit();
                            return true;
                    }
                    return false;
                }
            };

    private void checkUserStatus() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // user is signed in
//            mProfileTv.setText(user.getEmail());
        } else {
            // go to main activity
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    protected void onResume() {
        controller = DummyController.getInstance();
        List<Subject> slist = controller.getUserSubjects();

        //boilerplate code for initiating a recyclerview
        RecyclerView rvSubject = (RecyclerView) findViewById(R.id.rv_subject);
        SubjectAdapter subjectAdapter = new SubjectAdapter(slist);
        rvSubject.setAdapter(subjectAdapter);
        rvSubject.setLayoutManager(new LinearLayoutManager(this));
        super.onResume();
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                firebaseAuth.signOut();
                checkUserStatus();
            case R.id.action_add:
                //moves to SubjectAddActivity
                startActivity(new Intent(DashboardActivity.this, SubjectAddActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Adapter for recyclerview
    public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{
        private List<Subject> subjectList;

        public SubjectAdapter(List<Subject> subjects){
            this.subjectList = subjects;
        }

        @Override
        public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View subjectView = inflater.inflate(R.layout.subject_card, parent, false);

            ViewHolder viewHolder = new ViewHolder(subjectView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position){
            // Get the data model based on position
            Subject subject = subjectList.get(position);

            // Set item views based on your views and data model
            final TextView subjectNameView = holder.subjectNameView;
            subjectNameView.setText(subject.getCourseTitle());

            TextView subjectMembersView = holder.subjectMembersView;
            String numberOfStudents = String.valueOf(subject.getStudentList().size());
            subjectMembersView.setText(numberOfStudents);

            TextView subjectIDView = holder.subjectIDView;
            final String courseID = subject.getCourseID();
            subjectIDView.setText(courseID);

            CardView cardView = holder.cardView;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toSubjectIntent = new Intent(DashboardActivity.this, SubjectActivity.class);
                    toSubjectIntent.putExtra(COURSE_ID_KEY, courseID);
                    startActivity(toSubjectIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return subjectList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView subjectNameView;
            public TextView subjectMembersView;
            public TextView subjectIDView;
            public CardView cardView;

            public ViewHolder(View itemView){
                super(itemView);

                subjectNameView = itemView.findViewById(R.id.card_header);
                subjectMembersView = itemView.findViewById(R.id.card_studying);
                subjectIDView = itemView.findViewById(R.id.card_subjectID);
                cardView = itemView.findViewById(R.id.cardView_1);


            }
        }
    }

}