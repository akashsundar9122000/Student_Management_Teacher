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
import com.studentmanagement.teacher.Adapter.LinkAdapter;
import com.studentmanagement.teacher.Adapter.NotesAdapter;
import com.studentmanagement.teacher.models.Links;
import com.studentmanagement.teacher.models.Notes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinksFragment extends Fragment {



    private RecyclerView Links_List;
    private FloatingActionButton Add;

    private LinkAdapter linkAdapter;
    private List<Links> linksList;
    private DatabaseReference mLinksDatabase;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mLinksDatabase = FirebaseDatabase.getInstance().getReference("Notes").child(mCurrentUserId);
        mLinksDatabase.keepSynced(true);

        Links_List =  view.findViewById(R.id.note_list);
        Links_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Links_List.setLayoutManager(mLayoutManager);
        linksList = new ArrayList<>();
        linkAdapter = new LinkAdapter(getContext(), linksList);
        Links_List.setAdapter(linkAdapter);

        readLinks();
        Add = view.findViewById(R.id.add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_links_table=new Intent(getContext(),AddLinksActivity.class);
                startActivity(add_links_table);
            }
        });


        return view;
    }

    private void readLinks() {

        mLinksDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linksList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Links links = snapshot.getValue(Links.class);
                    linksList.add(links);

                }
                Collections.reverse(linksList);
                linkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}