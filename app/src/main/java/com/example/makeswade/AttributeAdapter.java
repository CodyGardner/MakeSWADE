package com.example.makeswade;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttrListItemHolder> {
    private CreateCharacterActivity activity;
    private ArrayList<Attribute> attributes;

    public AttributeAdapter(CreateCharacterActivity activity, ArrayList<Attribute> attributes) {
        this.activity = activity;
        this.attributes = attributes;
    }

    @NonNull
    @Override
    public AttrListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_attr, parent, false);
        return new AttrListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AttrListItemHolder holder, int position) {
        Attribute attribute = attributes.get(position);
        holder.attrNameView.setText(attribute.getName());
        holder.dieTypeView.setImageDrawable(activity.dieTypeForLevel(attribute.getLevel()));
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    public class AttrListItemHolder extends RecyclerView.ViewHolder {
        private TextView attrNameView;
        private ImageView attrDecBtn;
        private ImageView attrIncBtn;
        private ImageView dieTypeView;

        public AttrListItemHolder(View itemView) {
            super(itemView);
            attrNameView = itemView.findViewById(R.id.attrNameView);
            attrDecBtn = itemView.findViewById(R.id.attrDecreaseBtn);
            attrIncBtn = itemView.findViewById(R.id.attrIncreaseBtn);
            dieTypeView = itemView.findViewById(R.id.attrLevelView);

            attrIncBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                    int lvl = activity.increaseAttr(getAdapterPosition());
                    if (lvl > -1)
                        dieTypeView.setImageDrawable(activity.dieTypeForLevel(lvl));
                    else
                        Toast.makeText(v.getContext(), R.string.insufficient_pts,
                                Toast.LENGTH_LONG).show();
                }
            });

            attrDecBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                    int lvl = activity.decreaseAttr(getAdapterPosition());
                    dieTypeView.setImageDrawable(activity.dieTypeForLevel(lvl));
                }
            });
        }
    }
}
