package com.example.sharedspace.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedspace.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    ArrayList<ModelUser> userList;

    public AdapterUsers(Context context, ArrayList<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_card, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String userImage = userList.get(i).getImage();
        final String userName = userList.get(i).getName();
        String userPhone = userList.get(i).getPhone();
        String userClass = userList.get(i).getCclass();

        holder.mNameTv.setText(userName);
        holder.mPhoneTv.setText(userPhone);
        holder.mClassTv.setText(userClass);
        try {
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_smiley_black).into(holder.mAvatarIv);
        } catch (Exception e) {

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+ userName ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
            ImageView mAvatarIv;
            TextView mNameTv, mPhoneTv, mClassTv;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                //init views
                mAvatarIv = itemView.findViewById(R.id.avatarIv);
                mNameTv = itemView.findViewById(R.id.nameTv);
                mPhoneTv = itemView.findViewById(R.id.phoneTv);
                mClassTv = itemView.findViewById(R.id.classTv);

            }
    }


}
