package com.example.sharedspace;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    // firebase auth
    FirebaseAuth firebaseAuth;

    // views
    TextView mProfileTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        firebaseAuth = FirebaseAuth.getInstance();

        //init view
        mProfileTv = findViewById(R.id.profileTv);

    }

    private void checkUserStatus() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // user is signed in
            mProfileTv.setText(user.getEmail());
        } else {
            // go to main activity
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
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
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}