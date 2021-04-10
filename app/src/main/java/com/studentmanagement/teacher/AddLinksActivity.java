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
import com.studentmanagement.teacher.models.Links;
import com.studentmanagement.teacher.models.TimeTable;

import java.util.HashMap;

public class AddLinksActivity extends AppCompatActivity {


    private EditText SubName, Time, Link;
    private FloatingActionButton Done;


    private DatabaseReference mLinkDatabase;

    private String  mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_links);




        SubName = findViewById(R.id.subname);
        Time=findViewById(R.id.time);
        Link=findViewById(R.id.link);
        Done = findViewById(R.id.done);

        mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mLinkDatabase = FirebaseDatabase.getInstance().getReference("Links").child(mCurrentUserId);
        mLinkDatabase.keepSynced(true);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subname=SubName.getText().toString();
                String time= Time.getText().toString();
                String link=Link.getText().toString();


                if (subname.isEmpty() && time.isEmpty() && link.isEmpty()){
                    Toast.makeText(AddLinksActivity.this, "Add Something..", Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("sub_name",subname);
                    map1.put("time",time);
                    map1.put("link",link);
                    String key = mLinkDatabase.push().getKey();
                    map1.put("link_key", key);
                    mLinkDatabase.child(key).setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
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


}