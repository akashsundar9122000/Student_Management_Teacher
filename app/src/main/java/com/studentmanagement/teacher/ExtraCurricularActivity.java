package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentmanagement.teacher.Adapter.ExtraCurricularAdapter;
import com.studentmanagement.teacher.models.ExtraCurricular;
import com.studentmanagement.teacher.models.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtraCurricularActivity extends AppCompatActivity {

    private RecyclerView ExtraCurricular_List;


    private ExtraCurricularAdapter extraCurricularAdapter;
    private List<ExtraCurricular> extraCurricularList;
    private DatabaseReference mExtraCurricularDatabase, mUsersDatabase;

    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_curricular);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("student_id");


        mExtraCurricularDatabase = FirebaseDatabase.getInstance().getReference("Extracurricular").child(user_id);
        mExtraCurricularDatabase.keepSynced(true);

        ExtraCurricular_List =  findViewById(R.id.extra_curricular_list);
        ExtraCurricular_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExtraCurricularActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        ExtraCurricular_List.setLayoutManager(mLayoutManager);
        extraCurricularList = new ArrayList<>();
        extraCurricularAdapter = new ExtraCurricularAdapter(ExtraCurricularActivity.this, extraCurricularList);
        ExtraCurricular_List.setAdapter(extraCurricularAdapter);

        readMaterials();
    }

    private void readMaterials() {

        mExtraCurricularDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                extraCurricularList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ExtraCurricular extraCurricular = snapshot.getValue(ExtraCurricular.class);
                    extraCurricularList.add(extraCurricular);

                }
                Collections.reverse(extraCurricularList);
                extraCurricularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}