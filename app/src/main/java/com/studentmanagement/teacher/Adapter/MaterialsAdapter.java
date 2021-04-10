package com.studentmanagement.teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studentmanagement.teacher.CalenderActivity;
import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.TimeTableActivity;
import com.studentmanagement.teacher.models.Calender;
import com.studentmanagement.teacher.models.Materials;
import com.studentmanagement.teacher.models.Notes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Materials> mMaterials;

    public MaterialsAdapter(Context context, List<Materials> materials){
        mContext = context;
        mMaterials = materials;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_material_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Materials materials = mMaterials.get(position);


        holder.Name.setText(materials.getMaterial());



    }

    @Override
    public int getItemCount() {
        return mMaterials.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;


        public ImageViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
        }
    }



}