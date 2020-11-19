package com.example.sharedspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        ArrayList<ModelSubject> list = new ArrayList<>();
        list.add(new ModelSubject(ModelSubject.infoSys, "Introduction to Information Systems and Programming", 1, "50.001", "43"));
        list.add(new ModelSubject(ModelSubject.compStruct, "Computer Structures", 1, "50.002", "69"));
        list.add(new ModelSubject(ModelSubject.algo, "Introduction to Algorithms", 1, "50.004", "1"));

        SubjectAdapter adapter = new SubjectAdapter(list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

    }
}