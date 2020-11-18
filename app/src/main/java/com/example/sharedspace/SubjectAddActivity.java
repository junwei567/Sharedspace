package com.example.sharedspace;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sharedspace.dummy.DummyController;

import java.util.List;

public class SubjectAddActivity extends AppCompatActivity {
    ActionBar actionBar;
    DummyController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);
        actionBar = getSupportActionBar();

        actionBar.setTitle("Edit Subjects");

        controller = DummyController.getInstance();
        List<Subject> userSubjects = controller.getUserSubjects();
        List<Subject> allSubjects = controller.getAllSubjects();

        RecyclerView rvSubjectAdd = (RecyclerView) findViewById(R.id.rv_subject_add);
        SubjectAddAdapter subjectAdapter = new SubjectAddAdapter(userSubjects, allSubjects);
        rvSubjectAdd.setAdapter(subjectAdapter);
        rvSubjectAdd.setLayoutManager(new LinearLayoutManager(this));


    }

    public class SubjectAddAdapter extends RecyclerView.Adapter<SubjectAddAdapter.ViewHolder>{
        private List<Subject> userSubjects;
        private List<Subject> allSubjects;

        public SubjectAddAdapter(List<Subject> userSubjects, List<Subject> allSubjects){
            this.userSubjects = userSubjects;
            this.allSubjects = allSubjects;
        }

        @Override
        public SubjectAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View subjectView = inflater.inflate(R.layout.subject_add_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(subjectView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SubjectAddAdapter.ViewHolder holder, int position){
            // Get the data model based on position
            final Subject subject = allSubjects.get(position);

            // Set item views based on your views and data model
            final TextView subjectNameView = holder.subjectNameView;
            subjectNameView.setText(subject.getCourseTitle());

            TextView subjectIDView = holder.subjectIDView;
            final String courseID = subject.getCourseID();
            subjectIDView.setText(courseID);

            Switch addSwitch = holder.addSwitch;
            if (userSubjects.contains(subject)){
                addSwitch.setChecked(true);
            }

            controller = DummyController.getInstance();
            addSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true){
                        controller.AddSubject(subject);
                    }
                    else{
                        controller.RemoveSubject(subject);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return allSubjects.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView subjectNameView;
            public TextView subjectIDView;
            public Switch addSwitch;

            public ViewHolder(View itemView){
                super(itemView);

                subjectNameView = itemView.findViewById(R.id.add_subjectTitle);
                subjectIDView = itemView.findViewById(R.id.add_subjectID);
                addSwitch = itemView.findViewById(R.id.add_switch);


            }
        }
    }
}