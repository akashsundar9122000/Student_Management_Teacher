package com.studentmanagement.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;


import com.studentmanagement.teacher.Adapter.CalenderAdapter;
import com.studentmanagement.teacher.models.Calender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {

    private RecyclerView Days_List;

    ArrayList<Calender> daysList;
    LinearLayoutManager layoutManager;

    CalenderAdapter calenderAdapter;

    private String key, user_id, f_user_id;

    public static ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Days_List = findViewById(R.id.days_list);

        daysList = new ArrayList<>();
        Days_List.setLayoutManager(new GridLayoutManager(this, 4));
        Days_List.setHasFixedSize(false);
        calenderAdapter = new CalenderAdapter(CalenderActivity.this, CalenderActivity.this, daysList);
        Days_List.setAdapter(calenderAdapter);

        getDates();

    }

    private void getDates () {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        for (int i = 0; i < 7; i++) {
            String day = new SimpleDateFormat("EEE").format(c.getTime());
            String date = new SimpleDateFormat("dd").format(c.getTime());
            String month_year = new SimpleDateFormat("MMMM, YYYY").format(c.getTime());
            String complete_id = new SimpleDateFormat("dd-MMM-yyyy").format(c.getTime());
            Log.i("fe",day+date);
            daysList.add(new Calender(day));
            c.add(Calendar.DATE, 1);


        }

    }
}