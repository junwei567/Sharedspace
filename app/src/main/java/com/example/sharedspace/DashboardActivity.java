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
    //Button uselessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);

        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //temporary testing list of subjects
        Subject s1 = new Subject("50.004", "Introduction to Algorithms");
        Subject s2 = new Subject("50.001", "Introduction to Information Systems and Programming");
        List<Subject> slist = new ArrayList<Subject>();
        slist.add(s1);
        slist.add(s2);

        RecyclerView rvSubject = (RecyclerView) findViewById(R.id.rv_subject);
        SubjectAdapter subjectAdapter = new SubjectAdapter(slist);
        rvSubject.setAdapter(subjectAdapter);
        rvSubject.setLayoutManager(new LinearLayoutManager(this));


        //the useless button give new intent to make
//        uselessButton = findViewById(R.id.uselessButton);
//        uselessButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, SubjectActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
//                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
//                    getSupportActionBar().setHomeButtonEnabled(true);
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                } else {
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                    getSupportActionBar().setHomeButtonEnabled(false);
//                }
//            }
//
//        });



        // modifies actionbar
        actionBar.setTitle("Home");
       // actionBar.setDisplayHomeAsUpEnabled(true);


        // creates the default homeFragment
//        HomeFragment homeFragment = new HomeFragment();
//        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
//        hft.replace(R.id.content, homeFragment, "");
//        hft.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            actionBar.setTitle("Home");
//                            HomeFragment homeFragment = new HomeFragment();
//                            FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
//                            hft.replace(R.id.content, homeFragment, "");
//                            hft.addToBackStack(null);
//                            hft.commit();

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
                            pft.replace(R.id.content, profileFragment, "");
                            pft.addToBackStack(null);
                            pft.commit();
                            return true;

                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            FriendsFragment friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "");
                            fft.addToBackStack(null);
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
//            case R.id.home:
//                getSupportFragmentManager().popBackStack();
//                return true;
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
            TextView subjectNameView = holder.subjectNameView;
            subjectNameView.setText(subject.getCourseTitle());

            TextView subjectMembersView = holder.subjectMembersView;
            String numberOfStudents = String.valueOf(subject.getStudentList().size());
            subjectMembersView.setText(numberOfStudents);

            TextView subjectIDView = holder.subjectIDView;
            subjectIDView.setText(subject.getCourseID());

            CardView cardView = holder.cardView;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toSubjectIntent = new Intent(DashboardActivity.this, SubjectActivity.class);
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