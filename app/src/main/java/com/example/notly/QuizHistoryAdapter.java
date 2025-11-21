package com.example.notly; // change to your package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizHistoryAdapter extends RecyclerView.Adapter<QuizHistoryAdapter.QuizHistoryViewHolder> {

    private List<QuizHistory> items;
    private OnQuizHistoryClickListener listener;

    public interface OnQuizHistoryClickListener {
        void onHistoryItemClick(QuizHistory quiz);
    }

    public QuizHistoryAdapter(List<QuizHistory> items, OnQuizHistoryClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuizHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_quiz_item, parent, false);
        return new QuizHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizHistoryViewHolder holder, int position) {
        QuizHistory quiz = items.get(position);

        holder.avatarTextView.setText(quiz.getCourseInitial());
        holder.titleTextView.setText(quiz.getTitle());
        holder.subtitleTextView.setText(quiz.getSubtitle());

        if (quiz.isPassed()) {
            holder.statusChip.setText("Passed");
            holder.statusChip.setBackgroundResource(R.drawable.history_status_chip);
            holder.statusChip.setTextColor(
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.resultCorrectText));
        } else {
            holder.statusChip.setText("Failed");
            holder.statusChip.setBackgroundResource(R.drawable.history_status_chip);
            holder.statusChip.setTextColor(
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.resultWrongText));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHistoryItemClick(quiz);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class QuizHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView avatarTextView;
        TextView titleTextView;
        TextView subtitleTextView;
        TextView statusChip;

        QuizHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarTextView = itemView.findViewById(R.id.historyAvatarTextView);
            titleTextView = itemView.findViewById(R.id.historyTitleTextView);
            subtitleTextView = itemView.findViewById(R.id.historySubtitleTextView);
            statusChip = itemView.findViewById(R.id.historyStatusChip);
        }
    }
}
