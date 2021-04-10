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
import com.studentmanagement.teacher.models.Links;
import com.studentmanagement.teacher.models.Notes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinkAdapter  extends RecyclerView.Adapter<LinkAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Links> mLinks;

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