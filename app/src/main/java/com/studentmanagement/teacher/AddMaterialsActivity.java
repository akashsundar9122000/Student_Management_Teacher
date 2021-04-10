package com.studentmanagement.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.studentmanagement.teacher.models.Materials;

import java.io.File;
import java.util.HashMap;

public class AddMaterialsActivity extends AppCompatActivity {

    private Button Add;
    private EditText Subject_Name, Unit;
    private RelativeLayout Pdf_Layout;
    private TextView Pdf_Name;
    private FloatingActionButton Done;

    private StorageReference mMaterialStorage;
    private DatabaseReference mMaterialDatabase;

    private String mCurrentUserId;
    private Uri mUri;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materials);

        mMaterialStorage = FirebaseStorage.getInstance().getReference("materials");

        Add = findViewById(R.id.add);
        Subject_Name = findViewById(R.id.subject_name);
        Unit = findViewById(R.id.unit);
        Done = findViewById(R.id.done);
        Pdf_Layout = findViewById(R.id.pdf_layout);
        Pdf_Name = findViewById(R.id.pdf_name);
        Pdf_Layout.setVisibility(View.GONE);

        mProgressDialog = new ProgressDialog(this);

        mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mMaterialDatabase = FirebaseDatabase.getInstance().getReference("Materials").child(mCurrentUserId);
        mMaterialDatabase.keepSynced(true);

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
                if (!Subject_Name.getText().toString().isEmpty() && !Unit.getText().toString().isEmpty()){
                    if (mUri!=null){
                        uploadFile(mUri);
                    }else{
                        Toast.makeText(AddMaterialsActivity.this, "Upload File", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddMaterialsActivity.this, "Fill Everything", Toast.LENGTH_SHORT);
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
                //uploading the file
                Pdf_Layout.setVisibility(View.VISIBLE);
                String uriString = data.getData().toString();
                File file = new File(uriString);

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = getContentResolver().query(data.getData(), null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            Pdf_Name.setText(cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    Pdf_Name.setText(file.getName());
                }
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
        StorageReference sRef = mMaterialStorage.child(Pdf_Name.getText().toString());
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
                    map1.put("unit", Unit.getText().toString());
                    map1.put("subject_name", Subject_Name.getText().toString());
                    map1.put("url", miUrlOk);
                    String key = mMaterialDatabase.push().getKey();
                    map1.put("material_key", key);
                    mMaterialDatabase.child(key).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddMaterialsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMaterialsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}