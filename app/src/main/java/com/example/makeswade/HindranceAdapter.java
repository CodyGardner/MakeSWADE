package com.example.makeswade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HindranceAdapter extends RecyclerView.Adapter<HindranceAdapter.HindranceListItemHolder> {
    private CreateCharacterActivity activity;
    private ArrayList<Hinderance> hinds;

    public HindranceAdapter(CreateCharacterActivity activity, ArrayList<Hinderance> hinds) {
        this.activity = activity;
        this.hinds = hinds;
    }

    @NonNull
    @Override
    public HindranceListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_hindrance, parent, false);
        return new HindranceListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HindranceListItemHolder holder, int position) {
        Hinderance hind = hinds.get(position);
        holder.nameView.setText(hind.getName());
        holder.descriptionView.setText(hind.getDescription());
        holder.severityView.setText(hind.getSeverity() ? R.string.major : R.string.minor);
    }

    @Override
    public int getItemCount() {
        return hinds.size();
    }

    public class HindranceListItemHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView severityView;
        private TextView descriptionView;

        public HindranceListItemHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.hindItemNameView);
            severityView = itemView.findViewById(R.id.hindItemSeverityView);
            descriptionView = itemView.findViewById(R.id.hindItemDescriptionView);
        }
    }
}
