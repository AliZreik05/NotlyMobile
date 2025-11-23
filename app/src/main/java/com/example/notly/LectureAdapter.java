package com.example.notly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureViewHolder> {

    private List<Lecture> lectures;

    public LectureAdapter(List<Lecture> lectures) {
        this.lectures = lectures;
    }
    public List<Lecture> getLectures() {
        return lectures;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lecture, parent, false);
        return new LectureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureViewHolder holder, int position) {
        Lecture lecture = lectures.get(position);

        holder.titleTextView.setText(lecture.getTitle());
        holder.avatarTextView.setText(lecture.getInitial());

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(lecture.isSelected());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                lecture.setSelected(isChecked));

        holder.itemView.setOnClickListener(v -> {
            boolean newState = !lecture.isSelected();
            lecture.setSelected(newState);
            holder.checkBox.setChecked(newState);
        });
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    static class LectureViewHolder extends RecyclerView.ViewHolder {
        TextView avatarTextView;
        TextView titleTextView;
        CheckBox checkBox;

        public LectureViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarTextView = itemView.findViewById(R.id.lectureAvatar);
            titleTextView = itemView.findViewById(R.id.lectureTitleTextView);
            checkBox = itemView.findViewById(R.id.lectureCheckBox);
        }
    }
}
