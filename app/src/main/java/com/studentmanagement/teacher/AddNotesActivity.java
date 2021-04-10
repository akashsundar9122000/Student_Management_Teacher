package com.studentmanagement.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class AddNotesActivity extends AppCompatActivity {

    private Button Add;
    private EditText Note;
    private FloatingActionButton Done;
    private RelativeLayout Pdf_Layout;
    private TextView Pdf_Name;

    private StorageReference mNotesStorage;
    private DatabaseReference mNoteDatabase;

    private Uri mUri;
    private String mCurrentUserId;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        mNotesStorage = FirebaseStorage.getInstance().getReference("notes");

        Add = findViewById(R.id.add);
        Note = findViewById(R.id.note);
        Done = findViewById(R.id.done);
        Pdf_Layout = findViewById(R.id.pdf_layout);
        Pdf_Name = findViewById(R.id.pdf_name);
        Pdf_Layout.setVisibility(View.GONE);

        mProgressDialog = new ProgressDialog(this);

        mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mNoteDatabase = FirebaseDatabase.getInstance().getReference("Notes").child(mCurrentUserId);
        mNoteDatabase.keepSynced(true);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    return;
                }

                //creating an intent for file chooser
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), 001);

            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Note.getText().toString().isEmpty()){
                    if (mUri!=null){
                        uploadFile(mUri);
                    }else{
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("note", Note.getText().toString());
                        map1.put("url", "");
                        String key = mNoteDatabase.push().getKey();
                        map1.put("note_key", key);
                        mNoteDatabase.child(key).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                    finish();
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(AddNotesActivity.this, "Fill Everything", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                mUri = data.getData();
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {

        mProgressDialog.setTitle("Upload");
        mProgressDialog.setMessage("uploading..");
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
        StorageReference sRef = mNotesStorage.child(Pdf_Name.getText().toString());
        UploadTask uploadTask = sRef.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return sRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String miUrlOk = downloadUri.toString();
                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("note", Note.getText().toString());
                    map1.put("url", miUrlOk);
                    String key = mNoteDatabase.push().getKey();
                    map1.put("note_key", key);
                    mNoteDatabase.child(key).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddNotesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNotesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}