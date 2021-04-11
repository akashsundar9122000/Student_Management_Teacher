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

import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.models.ExtraCurricular;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ExtraCurricularAdapter extends RecyclerView.Adapter<ExtraCurricularAdapter.ImageViewHolder> {

    private Context mContext;
    private List<ExtraCurricular> mExtraCurricular;

    public ExtraCurricularAdapter(Context context, List<ExtraCurricular> extraCurriculars){
        mContext = context;
        mExtraCurricular = extraCurriculars;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_extra_curricular_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final ExtraCurricular extraCurricular = mExtraCurricular.get(position);


        holder.activity_name.setText(extraCurricular.getActivity());
        holder.organisation.setText(extraCurricular.getOrganisation());
        holder.Pdf_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(extraCurricular.getUrl()), "application/pdf");
                mContext.startActivity(browserIntent);
            }
        });
        holder.Pdf_Name.setText(FirebaseStorage.getInstance().getReferenceFromUrl(extraCurricular.getUrl()).getName());

    }

    @Override
    public int getItemCount() {
        return mExtraCurricular.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView activity_name, organisation, Pdf_Name;
        private RelativeLayout Pdf_Layout;


        public ImageViewHolder(View itemView) {
            super(itemView);

            activity_name = itemView.findViewById(R.id.activity_name);
            organisation = itemView.findViewById(R.id.organisation);
            Pdf_Name = itemView.findViewById(R.id.pdf_name);
            Pdf_Layout = itemView.findViewById(R.id.pdf_layout);
        }
    }



}