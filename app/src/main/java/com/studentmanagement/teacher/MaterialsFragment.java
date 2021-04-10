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
import com.studentmanagement.teacher.Adapter.MaterialsAdapter;
import com.studentmanagement.teacher.Adapter.NotesAdapter;
import com.studentmanagement.teacher.Adapter.TimeTableAdapter;
import com.studentmanagement.teacher.models.Materials;
import com.studentmanagement.teacher.models.Notes;
import com.studentmanagement.teacher.models.TimeTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MaterialsFragment extends Fragment {


    private RecyclerView Materials_List;
    private FloatingActionButton Add;

    private MaterialsAdapter materialsAdapter;
    private List<Materials> materialsList;
    private DatabaseReference mMaterialsDatabase;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materials, container, false);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mMaterialsDatabase = FirebaseDatabase.getInstance().getReference("Materials").child(mCurrentUserId);
        mMaterialsDatabase.keepSynced(true);

        Materials_List =  view.findViewById(R.id.material_list);
        Materials_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        Materials_List.setLayoutManager(mLayoutManager);
        materialsList = new ArrayList<>();
        materialsAdapter = new MaterialsAdapter(getContext(), materialsList);
        Materials_List.setAdapter(materialsAdapter);

        readMaterials();
        Add = view.findViewById(R.id.add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_material=new Intent(getContext(),AddMaterialsActivity.class);
                startActivity(add_material);
            }
        });


        return view;
    }

    private void readMaterials() {

        mMaterialsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                materialsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Materials materials = snapshot.getValue(Materials.class);
                    materialsList.add(materials);

                }
                Collections.reverse(materialsList);
                materialsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}