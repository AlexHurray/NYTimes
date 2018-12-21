package com.example.ermolaenkoalex.nytimes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ChipsRecyclerAdapter extends RecyclerView.Adapter<ChipsRecyclerAdapter.ViewHolder> {

    @NonNull
    private Section[] sections;

    @NonNull
    private final LayoutInflater inflater;

    @Nullable
    private final OnItemClickListener clickListener;

    ChipsRecyclerAdapter(@NonNull Context context, @NonNull Section[] sections, @Nullable OnItemClickListener clickListener) {
        this.sections = sections;
        this.clickListener = clickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(viewType, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(sections[position]);
    }

    @Override
    public int getItemCount() {
        return sections.length;
    }

    @Override
    public int getItemViewType(int position) {
       return R.layout.item_section;
    }

    public interface OnItemClickListener {
        void onItemClick(Section section);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Chip chip;

        ViewHolder(@NonNull View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(sections[position]);
                }
            });
        }

        void bind(@NonNull Section section) {
            TextViewCompat.setTextAppearance(chip, R.style.TextAppearance_AppCompat_Title_Inverse);
            chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
            chip.setText(section.getSectionNameResId());
        }
    }
}
