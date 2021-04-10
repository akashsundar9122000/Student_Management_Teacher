package com.studentmanagement.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.studentmanagement.teacher.models.Materials;

import java.io.File;
import java.util.HashMap;

public class AddMaterialsActivity extends AppCompatActivity {

    private Button Add;
    private EditText Material;
    private FloatingActionButton Done;

    private StorageReference mMaterialStorage;
    private DatabaseReference mMaterialDatabase;

    private String url="", mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materials);

        mMaterialStorage = FirebaseStorage.getInstance().getReference("Materials");

        Add = findViewById(R.id.add);
        Material = findViewById(R.id.material);
        Done = findViewById(R.id.done);

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
                if (Material.getText().toString().isEmpty() && url.isEmpty()){
                    Toast.makeText(AddMaterialsActivity.this, "Add Something..", Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("url", url);
                    if (!Material.getText().toString().isEmpty())
                        map1.put("material", Material.getText().toString());
                    else
                        map1.put("material","");

                    if (!url.isEmpty())
                        map1.put("url", url);
                    else
                        map1.put("url", "");
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
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {

        StorageReference sRef = mMaterialStorage.child(System.currentTimeMillis() + ".pdf");;
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        url = taskSnapshot.getUploadSessionUri().toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}