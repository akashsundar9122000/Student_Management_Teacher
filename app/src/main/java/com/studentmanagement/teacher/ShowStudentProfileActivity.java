package com.studentmanagement.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentmanagement.teacher.models.Student;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShowStudentProfileActivity extends AppCompatActivity {

    private TextView first_name,last_name,email_id,register_number,phone_number,father_name,mother_name,date_of_birth,cgpa,gender,address,
    city,state,pincode,qualification,year_of_passing,current_year,institution,standing_arrear,history_of_arrear;

    private FloatingActionButton edit_profile;
    private CircleImageView profile_image;

    private String student_id;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_profile);

        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference("Student").child(student_id);
        mUsersDatabase.keepSynced(true);

        first_name=findViewById(R.id.first_name);
        last_name=findViewById(R.id.last_name);
        email_id=findViewById(R.id.email_id);
        register_number=findViewById(R.id.register_number);
        phone_number=findViewById(R.id.phone_number);
        father_name=findViewById(R.id.father_name);
        mother_name=findViewById(R.id.mother_name);
        date_of_birth=findViewById(R.id.date_of_birth);
        cgpa=findViewById(R.id.cgpa);
        gender=findViewById(R.id.gender);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        qualification=findViewById(R.id.qualification);
        year_of_passing=findViewById(R.id.year_of_passing);
        current_year=findViewById(R.id.current_year);
        institution=findViewById(R.id.institution);
        standing_arrear=findViewById(R.id.standing_arrear);
        history_of_arrear=findViewById(R.id.history_of_arrear);
        profile_image=findViewById(R.id.profile_image);

        edit_profile = findViewById(R.id.edit_student_profile);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit_profile_intent = new Intent(ShowStudentProfileActivity.this, EditStudentProfileActivity.class);
                startActivity(edit_profile_intent);
            }
        });

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                first_name.setText(student.getFirst_name());
                last_name.setText(student.getLast_name());
                email_id.setText(student.getEmail_id());
                register_number.setText(student.getRoll_no());
                phone_number.setText(student.getPhone());
                father_name.setText(student.getFather_name());
                mother_name.setText(student.getMother_name());
                date_of_birth.setText(student.getDate_of_birth());
                cgpa.setText(student.getCgpa());
                gender.setText(student.getGender());
                address.setText(student.getAddress());
                city.setText(student.getCity());
                state.setText(student.getState());
                pincode.setText(student.getPincode());
                qualification.setText(student.getQualification());
                year_of_passing.setText(student.getYear_of_passing());
                current_year.setText(student.getCurrent_year());
                institution.setText(student.getInstitution());
                standing_arrear.setText(student.getStanding_arrear());
                //Toast.makeText(ShowStudentProfileActivity.this, student.getStanding_arrears(), Toast.LENGTH_SHORT).show();
                history_of_arrear.setText(student.getHistory_of_arrear());
                if (student.getProfile_pic().isEmpty())
                    Glide.with(getApplicationContext()).load(R.drawable.about_me).into(profile_image);
                else
                    Glide.with(getApplicationContext()).load(student.getProfile_pic()).placeholder(R.drawable.about_me).into(profile_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}