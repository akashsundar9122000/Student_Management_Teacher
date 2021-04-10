package com.studentmanagement.teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.studentmanagement.teacher.CalenderActivity;
import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.TimeTableActivity;
import com.studentmanagement.teacher.models.Calender;
import com.studentmanagement.teacher.models.Materials;
import com.studentmanagement.teacher.models.Notes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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


        holder.Subject_Name.setText(materials.getSubject_name());
        holder.Unit.setText(materials.getUnit());
        holder.Pdf_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(materials.getUrl()), "application/pdf");
                mContext.startActivity(browserIntent);
            }
        });
        holder.Pdf_Name.setText(FirebaseStorage.getInstance().getReferenceFromUrl(materials.getUrl()).getName());

    }

    @Override
    public int getItemCount() {
        return mMaterials.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Subject_Name, Unit, Pdf_Name;
        private RelativeLayout Pdf_Layout;


        public ImageViewHolder(View itemView) {
            super(itemView);

            Subject_Name = itemView.findViewById(R.id.subject_name);
            Unit = itemView.findViewById(R.id.unit);
            Pdf_Name = itemView.findViewById(R.id.pdf_name);
            Pdf_Layout = itemView.findViewById(R.id.pdf_layout);
        }
    }



}