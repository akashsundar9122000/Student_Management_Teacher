package com.studentmanagement.teacher.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.models.PreviousSemester;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class PreviousSemesterAdapter extends RecyclerView.Adapter<PreviousSemesterAdapter.ImageViewHolder> {

    private Context mContext;
    private List<PreviousSemester> mPreviousSemester;
    private DatabaseReference mPreviousSemesterDatabase;


    public PreviousSemesterAdapter(Context context, List<PreviousSemester> previousSemesters){
        mContext = context;
        mPreviousSemester = previousSemesters;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_previous_semester_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final PreviousSemester previousSemester = mPreviousSemester.get(position);


        holder.semester_name.setText(previousSemester.getSemester_name());
        Glide.with(mContext).load(previousSemester.getUrl()).into(holder.Image);

        holder.Image_Name.setText(FirebaseStorage.getInstance().getReferenceFromUrl(previousSemester.getUrl()).getName());



}



    @Override
    public int getItemCount() {
        return mPreviousSemester.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView semester_name, Image_Name;
        private RelativeLayout Image_Layout;
        private ImageView Image;


        public ImageViewHolder(View itemView) {
            super(itemView);

            semester_name = itemView.findViewById(R.id.semester_name);
            Image_Name = itemView.findViewById(R.id.image_name);
            Image_Layout = itemView.findViewById(R.id.image_layout);
            Image = itemView.findViewById(R.id.image);
        }
    }



}