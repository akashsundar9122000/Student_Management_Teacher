package com.studentmanagement.teacher.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studentmanagement.teacher.CalenderActivity;
import com.studentmanagement.teacher.R;
import com.studentmanagement.teacher.TimeTableActivity;
import com.studentmanagement.teacher.models.Calender;
import com.studentmanagement.teacher.models.Links;
import com.studentmanagement.teacher.models.Notes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class LinkAdapter  extends RecyclerView.Adapter<LinkAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Links> mLinks;
    private DatabaseReference mLinkDatabase;


    public LinkAdapter(Context context, List<Links> links){
        mContext = context;
        mLinks = links;
    }


    @Override
    public LinkAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_link_layout, parent, false);
        return new LinkAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LinkAdapter.ImageViewHolder holder, final int position) {

        final Links links = mLinks.get(position);


        holder.SubName.setText(links.getSub_name());
        holder.time.setText(links.getTime());
        holder.link.setText(links.getLink());

        mLinkDatabase = FirebaseDatabase.getInstance().getReference("Links").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(links.getLink_key());



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Do you want to Delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mLinkDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()){
                                            Toast.makeText(mContext, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    }
                                });

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView SubName, time, link;


        public ImageViewHolder(View itemView) {
            super(itemView);

            SubName = itemView.findViewById(R.id.subname);
            time=itemView.findViewById(R.id.time);
            link=itemView.findViewById(R.id.link);
        }
    }



}