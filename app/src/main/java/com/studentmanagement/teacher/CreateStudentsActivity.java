package com.studentmanagement.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateStudentsActivity extends AppCompatActivity {

    private TextInputEditText first_name,last_name,phone_number,father_name,mother_name,date_of_birth,address,
            city,state,pincode,email_id,register_number,cgpa,gender, qualification,year_of_passing,current_year
            ,institution,standing_arrear,history_of_arrear,password,confirm;

    ;
    private FirebaseAuth mAuth;

    private FloatingActionButton save;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_students);
        mAuth=FirebaseAuth.getInstance();

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();


        mUsersDatabase = FirebaseDatabase.getInstance().getReference("Student");
        mUsersDatabase.keepSynced(true);


        first_name=findViewById(R.id.first_name);
        last_name=findViewById(R.id.last_name);
        email_id=findViewById(R.id.email_id);
        register_number=findViewById(R.id.register_number);
        phone_number=findViewById(R.id.phone);
        father_name=findViewById(R.id.father);
        mother_name=findViewById(R.id.mother);
        date_of_birth=findViewById(R.id.dob);
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
        password = findViewById(R.id.password);
        confirm=findViewById(R.id.confirm_password);

        save=findViewById(R.id.done);





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = first_name.getText().toString();
                String last = last_name.getText().toString();
                String mail=email_id.getText().toString();
                String reg=register_number.getText().toString();
                String phone = phone_number.getText().toString();
                String father = father_name.getText().toString();
                String mother = mother_name.getText().toString();
                String dob = date_of_birth.getText().toString();
                String cg=cgpa.getText().toString();
                String add = address.getText().toString();
                String gen=gender.getText().toString();
                String ci = city.getText().toString();
                String sta = state.getText().toString();
                String pin = pincode.getText().toString();
                String quali=qualification.getText().toString();
                String year=year_of_passing.getText().toString();
                String cur=current_year.getText().toString();
                String ins=institution.getText().toString();
                String stand=standing_arrear.getText().toString();
                String his=history_of_arrear.getText().toString();
                String pass=password.getText().toString();
                String con=confirm.getText().toString();




                if(isEmpty(first,last,phone,father,mother,dob,add,ci,sta,pin,mail,reg,cg,quali,year,cur,ins,stand,his,gen,pass,con)){

                    if(pass.equals(con)){
                        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("first_name", first);
                                    map.put("last_name", last);
                                    map.put("email_id", mail);
                                    map.put("roll_no", reg);
                                    map.put("cgpa", cg);
                                    map.put("gender", gen);
                                    map.put("qualification", quali);
                                    map.put("year_of_passing", year);
                                    map.put("current_year", cur);
                                    map.put("institution", ins);
                                    map.put("standing_arrear", stand);
                                    map.put("history_of_arrear", his);
                                    map.put("phone", phone);
                                    map.put("father_name", father);
                                    map.put("mother_name", mother);
                                    map.put("date_of_birth", dob);
                                    map.put("address", add);
                                    map.put("city", ci);
                                    map.put("created_by",mCurrentUserId);
                                    map.put("state", sta);
                                    map.put("pincode", pin);
                                    map.put("profile_pic", "");

                                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = current_user.getUid();

                                    map.put("student_id",uid);

                                    mUsersDatabase.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isComplete()){
                                                mAuth.getInstance().signOut();
                                                Toast.makeText(CreateStudentsActivity.this, "Successfully Created!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(CreateStudentsActivity.this,MainActivity.class));
                                                finish();
                                            }
                                        }
                                    });

                                } else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(CreateStudentsActivity.this, "Error : " + error, Toast.LENGTH_LONG).show();


                                }

                            }
                        });

                    } else {

                        Toast.makeText(CreateStudentsActivity.this, "Confirm Password and Password Field doesn't match.", Toast.LENGTH_LONG).show();

                    }





                }

            }
        });
    }

    private boolean isEmpty(String first, String last,String phone,String father,String mother,String dob,String add,String ci,String sta,String pin,String mail,String reg,String cg,String quali,String year,String cur,String ins,String stand,String his,String gen,String pass,String con) {
        if (first.isEmpty() || last.isEmpty() || phone.isEmpty() || father.isEmpty() || mother.isEmpty() || dob.isEmpty() || add.isEmpty() || ci.isEmpty() || sta.isEmpty() || pin.isEmpty() || mail.isEmpty() || cg.isEmpty() || reg.isEmpty() || quali.isEmpty() || year.isEmpty() || cur.isEmpty() || stand.isEmpty() || his.isEmpty() || ins.isEmpty() || gen.isEmpty() || pass.isEmpty() || con.isEmpty()) {
            Toast.makeText(CreateStudentsActivity.this, "PLEASE ENTER EVERY DETAILS", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}