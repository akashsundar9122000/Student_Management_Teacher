package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.studentmanagement.teacher.Adapter.TimeTableAdapter;
import com.studentmanagement.teacher.models.TimeTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeTableActivity extends AppCompatActivity {

    private RecyclerView Time_Table_List;
    private TimeTableAdapter timeTableAdapter;
    private List<TimeTable> timeTableList;
    private DatabaseReference mTimeTableDatabase;
    private FloatingActionButton add;
    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        Intent intent = getIntent();

        day=intent.getStringExtra("Day");

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mTimeTableDatabase = FirebaseDatabase.getInstance().getReference("TimeTable").child(mCurrentUserId);
        mTimeTableDatabase.keepSynced(true);

        Time_Table_List =  findViewById(R.id.time_table_list);
        Time_Table_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TimeTableActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Time_Table_List.setLayoutManager(mLayoutManager);
        timeTableList = new ArrayList<>();
        timeTableAdapter = new TimeTableAdapter(TimeTableActivity.this, timeTableList);
        Time_Table_List.setAdapter(timeTableAdapter);

        readPrograms();
        add=findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_time_table=new Intent(TimeTableActivity.this,CreateTimeTableActivity.class);
                add_time_table.putExtra("Day",day);
                startActivity(add_time_table);
            }
        });


    }
    private void readPrograms() {

        mTimeTableDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timeTableList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimeTable timeTable = snapshot.getValue(TimeTable.class);
                    if(timeTable.getDay().equals(day)){
                        timeTableList.add(timeTable);
                    }

                }
                Collections.reverse(timeTableList);
                timeTableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}