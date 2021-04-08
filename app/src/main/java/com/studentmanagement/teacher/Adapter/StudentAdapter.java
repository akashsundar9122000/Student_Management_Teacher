package com.studentmanagement.teacher.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.ShowStudentProfileActivity;
import com.studentmanagement.teacher.models.Student;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Student> mFeature;




    public StudentAdapter(Context context, List<Student> features){
        mContext = context;
        mFeature = features;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_student_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Student student = mFeature.get(position);

        holder.Student_Name.setText("Name : "+student.getFirst_name()+" "+student.getLast_name());
        holder.Student_Register_Number.setText("Roll No. "+student.getRoll_no());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShowStudentProfileActivity.class);
                intent.putExtra("student_id",student.getStudent_id());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFeature.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Student_Name,Student_Register_Number;

        public ImageViewHolder(View itemView) {
            super(itemView);

            Student_Name = itemView.findViewById(R.id.student_name);
            Student_Register_Number = itemView.findViewById(R.id.student_register_number);
        }
    }
}