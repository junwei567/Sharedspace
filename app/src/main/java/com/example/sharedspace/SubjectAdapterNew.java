package com.example.sharedspace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapterNew extends RecyclerView.Adapter<SubjectAdapterNew.ViewHolder>{
    private List<Subject> subjectList;
    private Context context;

    public SubjectAdapterNew(List<Subject> subjects){
        this.subjectList = subjects;
    }

    @Override
    public SubjectAdapterNew.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View subjectView = inflater.inflate(R.layout.subject_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(subjectView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapterNew.ViewHolder holder, int position){
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
                Intent toSubjectIntent = new Intent(context, SubjectActivity.class);
                toSubjectIntent.putExtra("COURSE_ID_KEY", courseID);
                context.startActivity(toSubjectIntent);
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
