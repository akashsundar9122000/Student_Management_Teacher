package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentmanagement.teacher.Adapter.AttendanceAdapter;
import com.studentmanagement.teacher.models.Attendance;
import com.studentmanagement.teacher.models.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView Student_List;
    private AttendanceAdapter programAdapter;
    private List<Attendance> studentList;
    private DatabaseReference mStudentDatabase;
    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId, key;
    private FloatingActionButton Attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Intent intent = getIntent();

        key = intent.getStringExtra("key");

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mStudentDatabase = FirebaseDatabase.getInstance().getReference("Student");
        mStudentDatabase.keepSynced(true);

        Student_List =  findViewById(R.id.student_list);
        Attendance = findViewById(R.id.attendance);
        Student_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AttendanceActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Student_List.setLayoutManager(mLayoutManager);
        studentList = new ArrayList<>();
        programAdapter = new AttendanceAdapter(AttendanceActivity.this, studentList);
        Student_List.setAdapter(programAdapter);

        Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attendanceExport(programAdapter.getAttendance());
            }
        });

        readPrograms();


    }
    private void readPrograms() {

        mStudentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    if(student.getCreated_by().equals(mCurrentUserId)) {
                        studentList.add(new Attendance(student.getStudent_id(), student.getFirst_name(), student.getLast_name(), student.getRoll_no(),false));
                    }
                }
                programAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void attendanceExport(ArrayList<Attendance> attendances) {

        StringBuilder data = new StringBuilder();
        data.append("S.No.,Roll Number,Name,Present/Absent");
        for (int i = 0; i < attendances.size(); i++) {

            data.append("\n" + String.valueOf(i + 1) +
                    "," + attendances.get(i).getRoll_no() +
                    "," + attendances.get(i).getFirst_name() + attendances.get(i).getLast_name()+
                    "," + attendances.get(i).isPresent()
            );
        }

        try {
            //saving the file into device
            FileOutputStream out = openFileOutput("attendance.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "attendance.csv");
            Uri path = FileProvider.getUriForFile(context, "com.studentmanagement.teacher.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}