package com.example.sharedspace.Subject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sharedspace.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SubjectAddActivity extends AppCompatActivity {
    ActionBar actionBar;
    ListView subjectEditView;
    List<String> userSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Subjects");

        userSubjects = SharedPrefUtils.loadArray(this);

        subjectEditView = findViewById(R.id.ListViewEditSubjects);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("subjects");
        FirebaseListAdapter<Subject> subjectEditAdapter = new FirebaseListAdapter<Subject>(this, Subject.class,
                R.layout.subject_add_item, mDatabase) {
            @Override
            protected void populateView(View v, Subject s, int position) {
                final Subject sub = s;
                TextView subTitleTextView, subIDTextView;
                Switch subSwitch;
                subTitleTextView = v.findViewById(R.id.add_subjectTitle);
                subIDTextView = v.findViewById(R.id.add_subjectID);
                subSwitch = v.findViewById(R.id.add_switch);

                subTitleTextView.setText(s.getCourseTitle());
                subIDTextView.setText(s.getCourseID());

                if (userSubjects.contains(sub.getCourseType())){
                    subSwitch.setChecked(true);
                }

                subSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true){
                            userSubjects.add(sub.getCourseType());
                        }
                        else{
                            userSubjects.remove(sub.getCourseType());
                        }
                    }
                });
            }
        };

        subjectEditView.setAdapter(subjectEditAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPrefUtils.saveArray(this, userSubjects);
    }

    //    public class SubjectAddAdapter extends RecyclerView.Adapter<SubjectAddAdapter.ViewHolder>{
//        private List<Subject> userSubjects;
//        private List<Subject> allSubjects;
//
//        public SubjectAddAdapter(List<Subject> userSubjects, List<Subject> allSubjects){
//            this.userSubjects = userSubjects;
//            this.allSubjects = allSubjects;
//        }
//
//        @Override
//        public SubjectAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//            Context context = parent.getContext();
//            LayoutInflater inflater = LayoutInflater.from(context);
//
//            View subjectView = inflater.inflate(R.layout.subject_add_item, parent, false);
//
//            ViewHolder viewHolder = new ViewHolder(subjectView);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(SubjectAddAdapter.ViewHolder holder, int position){
//            // Get the data model based on position
//            final Subject subject = allSubjects.get(position);
//
//            // Set item views based on your views and data model
//            final TextView subjectNameView = holder.subjectNameView;
//            subjectNameView.setText(subject.getCourseTitle());
//
//            TextView subjectIDView = holder.subjectIDView;
//            final String courseID = subject.getCourseID();
//            subjectIDView.setText(courseID);
//
//            Switch addSwitch = holder.addSwitch;
//            if (userSubjects.contains(subject)){
//                addSwitch.setChecked(true);
//            }
//
//            //controller = DummyController.getInstance();
//            addSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked == true){
//                        //TODO: save a local list of subjects
//                        //controller.AddSubject(subject);
//                    }
//                    else{
//                        //controller.RemoveSubject(subject);
//                    }
//                }
//            });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return allSubjects.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//
//            public TextView subjectNameView;
//            public TextView subjectIDView;
//            public Switch addSwitch;
//
//            public ViewHolder(View itemView){
//                super(itemView);
//
//                subjectNameView = itemView.findViewById(R.id.add_subjectTitle);
//                subjectIDView = itemView.findViewById(R.id.add_subjectID);
//                addSwitch = itemView.findViewById(R.id.add_switch);
//
//
//            }
//        }
//    }
}