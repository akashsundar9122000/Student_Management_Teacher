package com.studentmanagement.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText Email,Password;

    private Button Login;

    private TextView Forgot;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mStudentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email=findViewById(R.id.email_id);
        Password=findViewById(R.id.password);
        Login=findViewById(R.id.login);
        Forgot=findViewById(R.id.forgot);

        mAuth=FirebaseAuth.getInstance();
        mStudentDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        mStudentDatabase.keepSynced(true);

        mProgressDialog = new ProgressDialog(this);

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(resetIntent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_address = Email.getText().toString();
                String password = Password.getText().toString();
                if(isEmpty(email_address,password)){
                    mProgressDialog.setTitle("Logging In");
                    mProgressDialog.setMessage("Authenticating User..");
                    mProgressDialog.show();
                    mProgressDialog.setCanceledOnTouchOutside(false);

                    mAuth.signInWithEmailAndPassword(email_address, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                mProgressDialog.setMessage("Setting User..");
                                String mCurrent_User_Id = mAuth.getCurrentUser().getUid();

                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                mProgressDialog.hide();
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            }
        });




    }
    private boolean isEmpty(String mobile_number, String password) {
        if (mobile_number.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "PLEASE ENTER THE CREDENTIALS!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}