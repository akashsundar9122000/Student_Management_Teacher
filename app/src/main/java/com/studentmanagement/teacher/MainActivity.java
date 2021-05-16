package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private CardView My_Students,Time_Table,Class_Room,Semester,Extra_Curricular,Attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        My_Students=findViewById(R.id.my_students);
        Time_Table=findViewById(R.id.time_table);
        Class_Room=findViewById(R.id.class_room);
        Semester=findViewById(R.id.semester);
        Extra_Curricular =findViewById(R.id.extra_curricular);
        Attendance=findViewById(R.id.attendance);

        My_Students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about_me_intent = new Intent(MainActivity.this,MyStudentsActivity.class);
                about_me_intent.putExtra("key", "ms");
                startActivity(about_me_intent);
            }
        });

        Time_Table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time_table_intent = new Intent(MainActivity.this,CalenderActivity.class);
                startActivity(time_table_intent);
            }
        });

        Class_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent class_room_intent = new Intent(MainActivity.this,ClassRoomActivity.class);
                startActivity(class_room_intent);
            }
        });

        Semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent semester_intent = new Intent(MainActivity.this,SemesterActivity.class);
                startActivity(semester_intent);
            }
        });

        Extra_Curricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent extra_curricular_intent = new Intent(MainActivity.this,MyStudentsActivity.class);
                extra_curricular_intent.putExtra("key", "eca");
                startActivity(extra_curricular_intent);
            }
        });

        Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gpa_intent = new Intent(MainActivity.this,AttendanceActivity.class);
                startActivity(gpa_intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(login_intent);
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                sendToLogin();
                return true;
            case R.id.profile:
                Intent profile_intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(profile_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}