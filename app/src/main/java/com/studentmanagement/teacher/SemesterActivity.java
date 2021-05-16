package com.studentmanagement.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SemesterActivity extends AppCompatActivity {

    private CardView previous,current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        previous=findViewById(R.id.previoussemester);

        current=findViewById(R.id.currentsemester);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent extra_curricular_intent = new Intent(SemesterActivity.this,MyStudentsActivity.class);
                extra_curricular_intent.putExtra("key", "ps");
                startActivity(extra_curricular_intent);
            }
        });

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent extra_curricular_intent = new Intent(SemesterActivity.this,MyStudentsActivity.class);
                extra_curricular_intent.putExtra("key", "cs");
                startActivity(extra_curricular_intent);
            }
        });
    }
}