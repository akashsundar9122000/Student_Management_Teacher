package com.studentmanagement.teacher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.models.Attendance;
import com.studentmanagement.teacher.models.Student;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Attendance> mFeature;
    private DatabaseReference mUsersDatabase;


    public AttendanceAdapter(Context context, List<Attendance> features){
        mContext = context;
        mFeature = features;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_attendance_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Attendance student = mFeature.get(position);

        holder.Student_Name.setText("Name : "+student.getFirst_name()+" "+student.getLast_name());
        holder.Student_Register_Number.setText("Roll No. "+student.getRoll_no());
        holder.Student_Present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.Student_Present.setImageResource(R.drawable.present);
                holder.Student_Absent.setImageResource(R.drawable.absent_gone);
                student.setPresent(true);
            }
        });

        holder.Student_Absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.Student_Absent.setImageResource(R.drawable.absent);
                holder.Student_Present.setImageResource(R.drawable.present_gone);
                student.setPresent(false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mFeature.size();
    }


    public ArrayList<Attendance> getAttendance() {

        ArrayList<Attendance> arrayList = new ArrayList<>();
        for (Attendance attendance : mFeature){
            arrayList.add(attendance);
        }
        return arrayList;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Student_Name,Student_Register_Number;
        private ImageView Student_Present, Student_Absent;

        public ImageViewHolder(View itemView) {
            super(itemView);

            Student_Name = itemView.findViewById(R.id.student_name);
            Student_Register_Number = itemView.findViewById(R.id.student_register_number);
            Student_Present = itemView.findViewById(R.id.present);
            Student_Absent = itemView.findViewById(R.id.absent);
        }
    }
}