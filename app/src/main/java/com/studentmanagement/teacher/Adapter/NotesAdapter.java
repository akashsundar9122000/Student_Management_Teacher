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
import com.studentmanagement.teacher.models.Notes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Notes> mNotes;

    public NotesAdapter(Context context, List<Notes> notes){
        mContext = context;
        mNotes = notes;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_note_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Notes notes = mNotes.get(position);


        holder.Name.setText(notes.getNote());



    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;


        public ImageViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
        }
    }



}