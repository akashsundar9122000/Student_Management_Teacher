package com.studentmanagement.teacher.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentmanagement.teacher.ExtraCurricularActivity;
import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.ShowStudentProfileActivity;
import com.studentmanagement.teacher.models.Student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Student> mFeature;
    private String key;
    private DatabaseReference mUsersDatabase;


    public StudentAdapter(Context context, List<Student> features, String key){
        mContext = context;
        mFeature = features;
        this.key=key;
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
                if (key.equals("ms")) {
                    Intent intent = new Intent(mContext, ShowStudentProfileActivity.class);
                    intent.putExtra("student_id", student.getStudent_id());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, ExtraCurricularActivity.class);
                    intent.putExtra("student_id", student.getStudent_id());
                    mContext.startActivity(intent);
                }
            }
        });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mUsersDatabase = FirebaseDatabase.getInstance().getReference("Student").child(student.getStudent_id());

                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Do you want to Delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mUsersDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()){
                                            Toast.makeText(mContext, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    }
                                });

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return false;
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