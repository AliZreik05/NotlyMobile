package com.example.notly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultQuestionAdapter extends RecyclerView.Adapter<ResultQuestionAdapter.ViewHolder> {

    private final List<ExamResultQuestion> items;

    public ResultQuestionAdapter(List<ExamResultQuestion> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_result_question, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamResultQuestion q = items.get(position);

        holder.titleTextView.setText(q.getTitle());
        holder.descriptionTextView.setText(q.getDescription());

        List<String> opts = q.getOptions();
        holder.option1TextView.setText(opts.size() > 0 ? opts.get(0) : "");
        holder.option2TextView.setText(opts.size() > 1 ? opts.get(1) : "");
        holder.option3TextView.setText(opts.size() > 2 ? opts.get(2) : "");

        // simple correct/wrong highlight
        TextView[] optionViews = {
                holder.option1TextView,
                holder.option2TextView,
                holder.option3TextView
        };

        int correct = q.getCorrectIndex();
        int selected = q.getSelectedIndex();

        for (int i = 0; i < optionViews.length; i++) {
            TextView tv = optionViews[i];
            tv.setBackgroundColor(0x00000000); // clear
            tv.setTextColor(ContextCompat.getColor(tv.getContext(), android.R.color.black));

            if (i == correct) {
                // green for correct
                tv.setBackgroundColor(
                        ContextCompat.getColor(tv.getContext(), R.color.resultCorrectText));
                tv.setTextColor(
                        ContextCompat.getColor(tv.getContext(), R.color.resultCorrectText));
            }

            if (selected != -1 && i == selected && selected != correct) {
                // red for wrong selection
                tv.setBackgroundColor(
                        ContextCompat.getColor(tv.getContext(), R.color.resultCorrectBg));
                tv.setTextColor(
                        ContextCompat.getColor(tv.getContext(), R.color.resultWrongBg));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView option1TextView;
        TextView option2TextView;
        TextView option3TextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // ⚠️ Make sure these IDs match exam_result_question.xml
            titleTextView = itemView.findViewById(R.id.resultQuestionTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.resultQuestionDescriptionTextView);
            option1TextView = itemView.findViewById(R.id.resultOption1TextView);
            option2TextView = itemView.findViewById(R.id.resultOption2TextView);
            option3TextView = itemView.findViewById(R.id.resultOption3TextView);
        }
    }
}
