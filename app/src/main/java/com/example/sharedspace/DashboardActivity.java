//TODO: (zhiyou): URL links to blackboard, piazza, myportal, office365

package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedspace.Calendar.CalendarActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends AppCompatActivity {

    // firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    BottomNavigationView navigationView;
    //FirebaseDatabase firebaseDatabase;


    TextView appDescriptionTextView;
    Button discussionChatButton, calendarButton;
    EmptyFragment emptyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);

<<<<<<< HEAD

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //enables persistence offline!!


=======
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //enables persistence offline!!



>>>>>>> master
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        appDescriptionTextView = findViewById(R.id.app_description);
        discussionChatButton = findViewById(R.id.discussion_chat);
        calendarButton = findViewById(R.id.calendar);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        discussionChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SubjectActivity.class);
                startActivity(intent);
            }
        });

        // modifies actionbar
        actionBar.setTitle("Home");
       // actionBar.setDisplayHomeAsUpEnabled(true);


        // creates the default homeFragment
        emptyFragment = new EmptyFragment();
        FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
        hft.replace(R.id.content, emptyFragment, "");
        hft.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            actionBar.setTitle("Home");
                            appDescriptionTextView.setVisibility(View.VISIBLE);
                            calendarButton.setVisibility(View.VISIBLE);
                            discussionChatButton.setVisibility(View.VISIBLE);

                            FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
                            hft.replace(R.id.content, emptyFragment, "");
                            hft.addToBackStack("");
                            hft.commit();
<<<<<<< HEAD
=======

>>>>>>> master
                            return true;

                        case R.id.nav_profile:
                            // profile fragment transaction
                            actionBar.setTitle("Profile");
                            ProfileFragment profileFragment = new ProfileFragment();
                            FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                            pft.replace(R.id.content, profileFragment, "");
                            pft.addToBackStack("");
                            pft.commit();
                            appDescriptionTextView.setVisibility(View.INVISIBLE);
                            calendarButton.setVisibility(View.INVISIBLE);
                            discussionChatButton.setVisibility(View.INVISIBLE);
                            return true;

                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            FriendsFragment friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "");
                            fft.addToBackStack("");
                            fft.commit();
                            appDescriptionTextView.setVisibility(View.INVISIBLE);
                            calendarButton.setVisibility(View.INVISIBLE);
                            discussionChatButton.setVisibility(View.INVISIBLE);
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
            case R.id.edit_subjects:
                getSupportFragmentManager().popBackStack();
                startActivity(new Intent(DashboardActivity.this, SubjectAddActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}