package com.example.sharedspace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter {
    private ArrayList<ModelSubject> subjectList;
    Context mContext;
    int total_subjects;

    public static class InfoSysViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTitle, subjectCode, currStudying;
        CardView cardView;
        Button isButton;

        public InfoSysViewHolder(View view) {
            super(view);

            this.subjectTitle = view.findViewById(R.id.card_header);
            this.subjectCode = view.findViewById(R.id.card_subjectID);
            this.currStudying = view.findViewById(R.id.card_studying);
            this.cardView = view.findViewById(R.id.card_view);
            this.isButton = view.findViewById(R.id.isButton);
        }
    }

    public static class CompStructViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTitle, subjectCode, currStudying;
        CardView cardView;
        Button csButton;

        public CompStructViewHolder(View view) {
            super(view);

            this.subjectTitle = view.findViewById(R.id.card_header);
            this.subjectCode = view.findViewById(R.id.card_subjectID);
            this.currStudying = view.findViewById(R.id.card_studying);
            this.cardView = view.findViewById(R.id.card_view);
            this.csButton = view.findViewById(R.id.csButton);
        }
    }

    public static class AlgoViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTitle, subjectCode, currStudying;
        CardView cardView;
        Button alButton;

        public AlgoViewHolder(View view) {
            super(view);

            this.subjectTitle = view.findViewById(R.id.card_header);
            this.subjectCode = view.findViewById(R.id.card_subjectID);
            this.currStudying = view.findViewById(R.id.card_studying);
            this.cardView = view.findViewById(R.id.card_view);
            this.alButton = view.findViewById(R.id.alButton);
        }
    }

    public SubjectAdapter(ArrayList<ModelSubject>data, Context context) {
        this.subjectList = data;
        this.mContext = context;
        total_subjects = subjectList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ModelSubject.infoSys:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infosys_card, parent, false);
                return new InfoSysViewHolder(view);
            case ModelSubject.compStruct:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compstr_card, parent, false);
                return new CompStructViewHolder(view);
            case ModelSubject.algo:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.algo_card, parent, false);
                return new AlgoViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (subjectList.get(position).type) {
            case 1:
                return ModelSubject.infoSys;
            case 2:
                return ModelSubject.compStruct;
            case 4:
                return ModelSubject.algo;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int listPosition) {
        ModelSubject object = subjectList.get(listPosition);

        if (object != null) {
            switch (object.type) {
                case ModelSubject.infoSys:
                    ((InfoSysViewHolder) holder).subjectTitle.setText(object.text);
                    ((InfoSysViewHolder) holder).subjectCode.setText(object.code);
                    ((InfoSysViewHolder) holder).currStudying.setText(object.studying);
                    ((InfoSysViewHolder) holder).isButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ChatActivityInfoSys.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case ModelSubject.compStruct:
                    ((CompStructViewHolder) holder).subjectTitle.setText(object.text);
                    ((CompStructViewHolder) holder).subjectCode.setText(object.code);
                    ((CompStructViewHolder) holder).currStudying.setText(object.studying);
                    ((CompStructViewHolder) holder).csButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ChatActivityComStr.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case ModelSubject.algo:
                    ((AlgoViewHolder) holder).subjectTitle.setText(object.text);
                    ((AlgoViewHolder) holder).subjectCode.setText(object.code);
                    ((AlgoViewHolder) holder).currStudying.setText(object.studying);
                    ((AlgoViewHolder) holder).alButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ChatActivityAlgo.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
