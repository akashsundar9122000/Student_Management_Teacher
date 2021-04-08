package com.studentmanagement.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateTimeTableActivity extends AppCompatActivity {

    private TextInputEditText subject_name,subject_code,staff_name;
    private Switch isBreak;
    private boolean is_break=false;

    private FirebaseAuth mAuth;

    private FloatingActionButton save;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId,day;
    private DatabaseReference mTimeTableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_time_table);

        Intent intent = getIntent();

        day=intent.getStringExtra("Day");

        mAuth=FirebaseAuth.getInstance();

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();


        mTimeTableDatabase = FirebaseDatabase.getInstance().getReference("TimeTable").child(mCurrentUserId);
        mTimeTableDatabase.keepSynced(true);


        subject_name=findViewById(R.id.subject_name);
        subject_code=findViewById(R.id.subject_code);
        staff_name=findViewById(R.id.staff_name);
        isBreak=findViewById(R.id.isBreak);
        save=findViewById(R.id.done);

        isBreak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    subject_name.setVisibility(View.GONE);
                    subject_code.setVisibility(View.GONE);
                    staff_name.setVisibility(View.GONE);
                    is_break=true;
                }
                else{
                    subject_name.setVisibility(View.VISIBLE);
                    subject_code.setVisibility(View.VISIBLE);
                    staff_name.setVisibility(View.VISIBLE);
                    is_break=false;
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub = subject_name.getText().toString();
                String code = subject_code.getText().toString();
                String staff = staff_name.getText().toString();


                if(isEmpty(sub,code,staff)){
                    String key=mTimeTableDatabase.push().getKey();
                    HashMap<String, Object> map = new HashMap<>();
                    if(is_break){
                        map.put("period","");
                        map.put("subject_code", "");
                        map.put("staff_name",  "");
                        map.put("day",day);
                        map.put("isBreak",is_break);
                    }
                    else{
                        map.put("period", sub);
                        map.put("subject_code", code);
                        map.put("staff_name", staff);
                        map.put("day",day);
                        map.put("isBreak",is_break);
                    }



                    mTimeTableDatabase.child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(CreateTimeTableActivity.this, "Successfully Created!", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }
                    });


                }
            }
        });
    }

    private boolean isEmpty(String sub,String code,String staff) {
        if (sub.isEmpty() || code.isEmpty() || staff.isEmpty()) {
            Toast.makeText(CreateTimeTableActivity.this, "PLEASE ENTER EVERY DETAILS", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}