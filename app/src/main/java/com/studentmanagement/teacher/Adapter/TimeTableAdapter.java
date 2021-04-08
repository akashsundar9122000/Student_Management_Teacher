package com.studentmanagement.teacher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.TimeTableActivity;
import com.studentmanagement.teacher.models.TimeTable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ImageViewHolder>{

    private Context mContext;
    private List<TimeTable> mUsers;


    TimeTableActivity activity;



    public TimeTableAdapter(Context context, List<TimeTable> users){
        mContext = context;
        mUsers = users;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_timetable_layout, parent, false);
        return new TimeTableAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final TimeTable calender = mUsers.get(position);


        if(calender.getSubject_code().equals("")){
            holder.staff_name.setVisibility(View.GONE);
            holder.subject_name.setText("Break");
            holder.subject_code.setVisibility(View.GONE);
        }
        else{
            holder.staff_name.setVisibility(View.VISIBLE);
            holder.subject_code.setVisibility(View.VISIBLE);
            holder.staff_name.setText(calender.getStaff_name());
            holder.subject_name.setText(calender.getPeriod());
            holder.subject_code.setText(calender.getSubject_code());

        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView subject_code,subject_name,staff_name;
        public LinearLayout Circle_Day;

        public ImageViewHolder(View itemView) {
            super(itemView);


            subject_name = itemView.findViewById(R.id.subject_name);
            subject_code = itemView.findViewById(R.id.subject_code);
            staff_name = itemView.findViewById(R.id.staff_name);

            Circle_Day = itemView.findViewById(R.id.circle_date);
        }
    }

}
