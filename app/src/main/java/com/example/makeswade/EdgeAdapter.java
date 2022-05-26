package com.example.makeswade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EdgeAdapter extends RecyclerView.Adapter<EdgeAdapter.EdgeListItemHolder> {
    private CreateCharacterActivity activity;
    private ArrayList<Edge> edges;

    public EdgeAdapter(CreateCharacterActivity activity, ArrayList<Edge> edges) {
        this.activity = activity;
        this.edges = edges;
    }

    @NonNull
    @Override
    public EdgeListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_edge, parent, false);
        return new EdgeListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull EdgeListItemHolder holder, int position) {
        Edge edge = edges.get(position);
        holder.nameView.setText(edge.getName());
        holder.descriptionView.setText(edge.getDescription());
    }

    @Override
    public int getItemCount() {
        return edges.size();
    }

    public class EdgeListItemHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView descriptionView;

        public EdgeListItemHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.edgeItemNameView);
            descriptionView = itemView.findViewById(R.id.edgeItemDescriptionView);
        }
    }
}
