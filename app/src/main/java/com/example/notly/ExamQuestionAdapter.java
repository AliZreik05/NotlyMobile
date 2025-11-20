package com.example.notly;  // <- change to your package

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExamQuestionAdapter extends RecyclerView.Adapter<ExamQuestionAdapter.QuestionViewHolder> {

    private List<ExamQuestion> questions;

    public ExamQuestionAdapter(List<ExamQuestion> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        ExamQuestion question = questions.get(position);

        holder.titleTextView.setText(question.getTitle());
        holder.descriptionTextView.setText(question.getDescription());

        List<String> opts = question.getOptions();

        holder.option1TextView.setText(opts.size() > 0 ? opts.get(0) : "");
        holder.option2TextView.setText(opts.size() > 1 ? opts.get(1) : "");
        holder.option3TextView.setText(opts.size() > 2 ? opts.get(2) : "");

        // Apply selected style
        applySelectionStyle(holder, question.getSelectedOptionIndex());

        // Click listeners for options
        holder.option1TextView.setOnClickListener(v -> {
            question.setSelectedOptionIndex(0);
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.option2TextView.setOnClickListener(v -> {
            question.setSelectedOptionIndex(1);
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.option3TextView.setOnClickListener(v -> {
            question.setSelectedOptionIndex(2);
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    private void applySelectionStyle(QuestionViewHolder holder, int selectedIndex) {
        // basic highlight: bold + light purple background if selected
        TextView[] views = {
                holder.option1TextView,
                holder.option2TextView,
                holder.option3TextView
        };

        for (int i = 0; i < views.length; i++) {
            TextView tv = views[i];
            if (i == selectedIndex) {
                tv.setTypeface(null, Typeface.BOLD);
                tv.setBackgroundColor(
                        ContextCompat.getColor(tv.getContext(), R.color.notlyPurple)
                );
                tv.setTextColor(ContextCompat.getColor(tv.getContext(), android.R.color.white));
            } else {
                tv.setTypeface(null, Typeface.NORMAL);
                tv.setBackgroundColor(0x00000000); // transparent
                tv.setTextColor(ContextCompat.getColor(tv.getContext(), android.R.color.black));
            }
        }
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView option1TextView;
        TextView option2TextView;
        TextView option3TextView;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.questionTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.questionDescriptionTextView);
            option1TextView = itemView.findViewById(R.id.option1TextView);
            option2TextView = itemView.findViewById(R.id.option2TextView);
            option3TextView = itemView.findViewById(R.id.option3TextView);
        }
    }

    public List<ExamQuestion> getQuestions() {
        return questions;
    }
}
