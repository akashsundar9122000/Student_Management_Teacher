package com.studentmanagement.teacher;

import android.content.Intent;
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
import com.studentmanagement.teacher.Adapter.PreviousSemesterAdapter;
import com.studentmanagement.teacher.models.PreviousSemester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PreviousSemesterActivity extends AppCompatActivity {

    private RecyclerView Previous_Semester_List;

    private PreviousSemesterAdapter previousSemesterAdapter;
    private List<PreviousSemester> previousSemesterList;
    private DatabaseReference mPreviousSemesterDatabase;

    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_semester);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("student_id");

        mPreviousSemesterDatabase = FirebaseDatabase.getInstance().getReference("PreviousSemester").child(user_id);
        mPreviousSemesterDatabase.keepSynced(true);

        Previous_Semester_List = findViewById(R.id.previous_semester_list);
        Previous_Semester_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(PreviousSemesterActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Previous_Semester_List.setLayoutManager(mLayoutManager);
        previousSemesterList = new ArrayList<>();
        previousSemesterAdapter = new PreviousSemesterAdapter(PreviousSemesterActivity.this, previousSemesterList);
        Previous_Semester_List.setAdapter(previousSemesterAdapter);

        readPreviousSemester();



    }

    private void readPreviousSemester() {

        mPreviousSemesterDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                previousSemesterList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PreviousSemester previousSemester = snapshot.getValue(PreviousSemester.class);
                    previousSemesterList.add(previousSemester);

                }
                Collections.reverse(previousSemesterList);
                previousSemesterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}