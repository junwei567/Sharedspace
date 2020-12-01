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
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    //TextView appDescriptionTextView;
    Button discussionChatButton, calendarButton;
    ImageView blackboardImage, piazzaImage, outlookImage, myportalImage;

    // fragments used in layouts
    EmptyFragment emptyFragment;
    ProfileFragment profileFragment;
    FriendsFragment friendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar = getSupportActionBar();
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigation);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //enables persistence offline!!

        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //appDescriptionTextView = findViewById(R.id.app_description);
        discussionChatButton = findViewById(R.id.discussion_chat);
        calendarButton = findViewById(R.id.calendar);
        blackboardImage = findViewById(R.id.blackboard);
        piazzaImage = findViewById(R.id.piazza);
        outlookImage = findViewById(R.id.outlook);
        myportalImage = findViewById(R.id.myportal);

        blackboardImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://edimension.sutd.edu.sg/"));
                startActivity(intent);
            }
        });

        piazzaImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://piazza.com/"));
                startActivity(intent);
            }
        });

        outlookImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://outlook.office365.com/mail"));
                startActivity(intent);
            }
        });

        myportalImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://myportal.sutd.edu.sg/"));
                startActivity(intent);
            }
        });

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

        // creates the default emptyfragment
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
                            blackboardImage.setVisibility(View.VISIBLE);
                            piazzaImage.setVisibility(View.VISIBLE);
                            outlookImage.setVisibility(View.VISIBLE);
                            myportalImage.setVisibility(View.VISIBLE);
                            calendarButton.setVisibility(View.VISIBLE);
                            discussionChatButton.setVisibility(View.VISIBLE);
                            FragmentTransaction hft = getSupportFragmentManager().beginTransaction();
                            hft.replace(R.id.content, emptyFragment, "");
                            hft.commit();
                            return true;

                        case R.id.nav_profile:
                            // profile fragment transaction
                            actionBar.setTitle("Profile");
                            profileFragment = new ProfileFragment();
                            FragmentTransaction pft = getSupportFragmentManager().beginTransaction();
                            pft.replace(R.id.content, profileFragment, "");
                            pft.commit();
                            blackboardImage.setVisibility(View.INVISIBLE);
                            piazzaImage.setVisibility(View.INVISIBLE);
                            myportalImage.setVisibility(View.INVISIBLE);
                            outlookImage.setVisibility(View.INVISIBLE);
                            calendarButton.setVisibility(View.INVISIBLE);
                            discussionChatButton.setVisibility(View.INVISIBLE);
                            return true;

                        case R.id.nav_friends:
                            // users fragment transaction
                            actionBar.setTitle("Friends");
                            friendFragment = new FriendsFragment();
                            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
                            fft.replace(R.id.content, friendFragment, "");
                            fft.commit();
                            blackboardImage.setVisibility(View.INVISIBLE);
                            piazzaImage.setVisibility(View.INVISIBLE);
                            myportalImage.setVisibility(View.INVISIBLE);
                            outlookImage.setVisibility(View.INVISIBLE);
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
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                firebaseAuth.signOut();
                checkUserStatus();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}