package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import com.studentmanagement.teacher.models.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView first_name, email_id, phone_number,  gender, address,
            city, state, pincode, institution, department;

    private FloatingActionButton edit_profile;

    private CircleImageView profile_image;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference("Teacher").child(mCurrentUserId);
        mUsersDatabase.keepSynced(true);

        first_name = findViewById(R.id.name);
        email_id = findViewById(R.id.email_id);
        phone_number = findViewById(R.id.phone_number);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pincode = findViewById(R.id.pincode);
        institution = findViewById(R.id.institution);
        department = findViewById(R.id.dept);
        profile_image = findViewById(R.id.profile_image);


        edit_profile = findViewById(R.id.edit_profile);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit_profile_intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(edit_profile_intent);
            }
        });

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Teacher teacher = dataSnapshot.getValue(Teacher.class);
                first_name.setText(teacher.getName());
                email_id.setText(teacher.getEmail_id());
                phone_number.setText(teacher.getPhone());
                gender.setText(teacher.getGender());
                address.setText(teacher.getAddress());
                city.setText(teacher.getCity());
                state.setText(teacher.getState());
                pincode.setText(teacher.getPincode());
                institution.setText(teacher.getInstitution());
                department.setText(teacher.getDepartment());

                if (teacher.getProfile_pic().isEmpty())
                    Glide.with(getApplicationContext()).load(R.drawable.about_me).into(profile_image);
                else
                    Glide.with(getApplicationContext()).load(teacher.getProfile_pic()).placeholder(R.drawable.about_me).into(profile_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}