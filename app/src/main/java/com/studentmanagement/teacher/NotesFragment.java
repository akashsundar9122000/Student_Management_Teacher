package com.studentmanagement.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentmanagement.teacher.Adapter.NotesAdapter;
import com.studentmanagement.teacher.Adapter.TimeTableAdapter;
import com.studentmanagement.teacher.models.Notes;
import com.studentmanagement.teacher.models.TimeTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesFragment extends Fragment {


    private RecyclerView Notes_List;
    private FloatingActionButton Add;

    private NotesAdapter notesAdapter;
    private List<Notes> notesList;
    private DatabaseReference mNotesDatabase;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mNotesDatabase = FirebaseDatabase.getInstance().getReference("Notes").child(mCurrentUserId);
        mNotesDatabase.keepSynced(true);

        Notes_List =  view.findViewById(R.id.note_list);
        Notes_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Notes_List.setLayoutManager(mLayoutManager);
        notesList = new ArrayList<>();
        notesAdapter = new NotesAdapter(getContext(), notesList);
        Notes_List.setAdapter(notesAdapter);

        readNotes();
        Add = view.findViewById(R.id.add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_time_table=new Intent(getContext(),AddNotesActivity.class);
                startActivity(add_time_table);
            }
        });


        return view;
    }

    private void readNotes() {

        mNotesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notes notes = snapshot.getValue(Notes.class);
                    notesList.add(notes);

                }
                Collections.reverse(notesList);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}