package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studentmanagement.teacher.Adapter.StudentAdapter;
import com.studentmanagement.teacher.models.Student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class MyStudentsActivity extends AppCompatActivity {

    private RecyclerView Student_List;
    private StudentAdapter programAdapter;
    private List<Student> studentList;
    private DatabaseReference mStudentDatabase;
    private FloatingActionButton add;
    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    private StorageReference mProfileImageStorage;
    private DatabaseReference mExtraCurricularDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mStudentDatabase = FirebaseDatabase.getInstance().getReference("Student");
        mStudentDatabase.keepSynced(true);

        Student_List =  findViewById(R.id.student_list);
        Student_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MyStudentsActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Student_List.setLayoutManager(mLayoutManager);
        studentList = new ArrayList<>();
        programAdapter = new StudentAdapter(MyStudentsActivity.this, studentList);
        Student_List.setAdapter(programAdapter);

        readPrograms();
        add=findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_extra_curricular=new Intent(MyStudentsActivity.this,CreateStudentsActivity.class);
                startActivity(add_extra_curricular);
            }
        });


    }
    private void readPrograms() {

        mStudentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    if(student.getCreated_by().equals(mCurrentUserId)) {

                        studentList.add(student);
                    }
                }
                programAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}