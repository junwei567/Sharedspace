package com.example.sharedspace;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private ArrayList<Subject> subjectList;
    Context mContext;
    int total_subjects;

//    public static class InfoSysViewHolder extends RecyclerView.ViewHolder {
//        TextView subjectTitle, subjectCode, currStudying;
//        CardView cardView;
//        Button isButton;
//
//        public InfoSysViewHolder(View view) {
//            super(view);
//
//            this.subjectTitle = view.findViewById(R.id.card_header);
//            this.subjectCode = view.findViewById(R.id.card_subjectID);
//            this.currStudying = view.findViewById(R.id.card_studying);
//            this.cardView = view.findViewById(R.id.card_view);
//            this.isButton = view.findViewById(R.id.isButton);
//        }
//    }
//
//    public static class CompStructViewHolder extends RecyclerView.ViewHolder {
//        TextView subjectTitle, subjectCode, currStudying;
//        CardView cardView;
//        Button csButton;
//
//        public CompStructViewHolder(View view) {
//            super(view);
//
//            this.subjectTitle = view.findViewById(R.id.card_header);
//            this.subjectCode = view.findViewById(R.id.card_subjectID);
//            this.currStudying = view.findViewById(R.id.card_studying);
//            this.cardView = view.findViewById(R.id.card_view);
//            this.csButton = view.findViewById(R.id.csButton);
//        }
//    }
//
//    public static class AlgoViewHolder extends RecyclerView.ViewHolder {
//        TextView subjectTitle, subjectCode, currStudying;
//        CardView cardView;
//        Button alButton;
//
//        public AlgoViewHolder(View view) {
//            super(view);
//
//            this.subjectTitle = view.findViewById(R.id.card_header);
//            this.subjectCode = view.findViewById(R.id.card_subjectID);
//            this.currStudying = view.findViewById(R.id.card_studying);
//            this.cardView = view.findViewById(R.id.card_view);
//            this.alButton = view.findViewById(R.id.alButton);
//        }
//    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView cardHeaderTextView, cardSubjectIDTextView, cardStudyingTextView;
        CardView subjectCard;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            Log.i("Subject","ViewHolder accessed");
            cardHeaderTextView = itemView.findViewById(R.id.card_header);
            cardSubjectIDTextView = itemView.findViewById(R.id.card_subjectID);
            cardStudyingTextView = itemView.findViewById(R.id.card_studying);
            subjectCard = itemView.findViewById(R.id.subject_card_view);
        }
    }

    public SubjectAdapter(ArrayList<Subject>data, Context context) {
        this.subjectList = data;
        this.mContext = context;
        this.total_subjects = subjectList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card, parent, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int listPosition) {
        Subject object = subjectList.get(listPosition);
        holder.cardHeaderTextView.setText(object.getCourseTitle());
        holder.cardSubjectIDTextView.setText(object.getCourseID());
        holder.cardStudyingTextView.setText("69");
        holder.subjectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext,RoomActivity.class);
//                mContext.startActivity(intent);
                
            }
        });


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
