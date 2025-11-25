package com.example.notly;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExamResultQuestionAdapter extends RecyclerView.Adapter<ExamResultQuestionAdapter.ResultViewHolder> {

    private List<ExamResultQuestion> questions;

    public ExamResultQuestionAdapter(List<ExamResultQuestion> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_result_question, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        ExamResultQuestion q = questions.get(position);

        holder.titleTextView.setText("Question " + (position + 1));
        holder.descriptionTextView.setText(q.getDescription());

        List<String> opts = q.getOptions();

        holder.option1TextView.setText(opts.size() > 0 ? opts.get(0) : "");
        holder.option2TextView.setText(opts.size() > 1 ? opts.get(1) : "");
        holder.option3TextView.setText(opts.size() > 2 ? opts.get(2) : "");

        applyColors(holder, q.getCorrectIndex(), q.getSelectedIndex());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    private void applyColors(ResultViewHolder holder, int correctIndex, int selectedIndex) {
        TextView[] optionsViews = {
                holder.option1TextView,
                holder.option2TextView,
                holder.option3TextView
        };

        // safety: if indexes are out of range, ignore them
        if (correctIndex < 0 || correctIndex >= optionsViews.length) {
            correctIndex = -1;
        }
        if (selectedIndex < 0 || selectedIndex >= optionsViews.length) {
            selectedIndex = -1;
        }

        int correctBg = ContextCompat.getColor(holder.itemView.getContext(), R.color.resultCorrectBg);
        int correctText = ContextCompat.getColor(holder.itemView.getContext(), R.color.resultCorrectText);

        int wrongBg = ContextCompat.getColor(holder.itemView.getContext(), R.color.resultWrongBg);
        int wrongText = ContextCompat.getColor(holder.itemView.getContext(), R.color.resultWrongText);

        int neutralBg = ContextCompat.getColor(holder.itemView.getContext(), R.color.resultOptionNeutralBg);
        int neutralText = ContextCompat.getColor(holder.itemView.getContext(), R.color.resultOptionNeutralText);

        for (int i = 0; i < optionsViews.length; i++) {
            TextView tv = optionsViews[i];
            if (tv == null) continue;

            // default neutral
            tv.setBackgroundColor(neutralBg);
            tv.setTextColor(neutralText);
            tv.setTypeface(null, Typeface.NORMAL);

            // correct answer
            if (i == correctIndex) {
                tv.setBackgroundColor(correctBg);
                tv.setTextColor(correctText);
                tv.setTypeface(null, Typeface.BOLD);
            }

            // wrong selected answer
            if (selectedIndex != -1 && i == selectedIndex && selectedIndex != correctIndex) {
                tv.setBackgroundColor(wrongBg);
                tv.setTextColor(wrongText);
                tv.setTypeface(null, Typeface.BOLD);
            }
        }
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView option1TextView;
        TextView option2TextView;
        TextView option3TextView;

        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.resultQuestionTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.resultQuestionDescriptionTextView);
            option1TextView = itemView.findViewById(R.id.resultOption1TextView);
            option2TextView = itemView.findViewById(R.id.resultOption2TextView);
            option3TextView = itemView.findViewById(R.id.resultOption3TextView);
        }
    }
}
