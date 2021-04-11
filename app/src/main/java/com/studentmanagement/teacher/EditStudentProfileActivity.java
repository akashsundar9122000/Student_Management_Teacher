package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.studentmanagement.teacher.models.Student;
import com.studentmanagement.teacher.models.Teacher;

import java.util.HashMap;

public class EditStudentProfileActivity extends AppCompatActivity {

    private TextInputEditText cgpa,current_year,standing_arrear,history_of_arrear;

    private FloatingActionButton save;

    private FirebaseUser mFirebaseUser;
    private String student_id;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        Intent intent = getIntent();
        student_id = intent.getStringExtra("user_id");


        mUsersDatabase = FirebaseDatabase.getInstance().getReference("Student").child(student_id);
        mUsersDatabase.keepSynced(true);

        cgpa=findViewById(R.id.cgpa);
        current_year=findViewById(R.id.current_year);
        standing_arrear=findViewById(R.id.standing_arrear);
        history_of_arrear=findViewById(R.id.history_of_arrear);
        save=findViewById(R.id.done);

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                cgpa.setText(student.getCgpa());
                current_year.setText(student.getCurrent_year());
                standing_arrear.setText(student.getStanding_arrear());
                history_of_arrear.setText(student.getHistory_of_arrear());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cg = cgpa.getText().toString();
                String cur = current_year.getText().toString();
                String sta = standing_arrear.getText().toString();
                String his = history_of_arrear.getText().toString();

                if(isEmpty(cg,cur,sta,his)){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("cgpa", cg);
                    map.put("current_year", cur);
                    map.put("standing_arrear", sta);
                    map.put("history_of_arrear", his);

                    mUsersDatabase.updateChildren(map);

                    Toast.makeText(EditStudentProfileActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditStudentProfileActivity.this, ShowStudentProfileActivity.class);
                    intent.putExtra("student_id", student_id);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }



    private boolean isEmpty(String cg,String cur,String sta,String his) {
        if (cg.isEmpty() || cur.isEmpty() || sta.isEmpty() || his.isEmpty()) {
            Toast.makeText(EditStudentProfileActivity.this, "PLEASE ENTER EVERY DETAILS", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}