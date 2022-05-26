package com.example.makeswade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillListItemHolder> {
    private CreateCharacterActivity activity;
    private ArrayList<Skill> skills;

    public SkillAdapter(CreateCharacterActivity activity, ArrayList<Skill> skills) {
        this.activity = activity;
        this.skills = skills;
    }

    @NonNull
    @Override
    public SkillListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_skill, parent, false);
        return new SkillListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SkillListItemHolder holder, int position) {
        Skill skill = skills.get(position);
        holder.nameView.setText(skill.getName());
        holder.abvView.setText(skill.getAttr().getAbv());
        holder.dieTypeView.setImageDrawable(activity.dieTypeForLevel(skill.getLevel()));
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class SkillListItemHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView abvView;
        private ImageView skillIncBtn;
        private ImageView skillDecBtn;
        private ImageView dieTypeView;

        public SkillListItemHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.skillNameView);
            abvView = itemView.findViewById(R.id.attrAbvView);
            skillIncBtn = itemView.findViewById(R.id.skillIncreaseView);
            skillDecBtn = itemView.findViewById(R.id.skillDecreaseBtn);
            dieTypeView = itemView.findViewById(R.id.skillLevelView);

            skillIncBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                    int lvl = activity.increaseSkill(getAdapterPosition());
                    if (lvl > -1)
                        dieTypeView.setImageDrawable(activity.dieTypeForLevel(lvl));
                    else
                        Toast.makeText(itemView.getContext(), R.string.insufficient_pts,
                                Toast.LENGTH_LONG).show();
                }
            });

            skillDecBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                    int lvl = activity.decreaseSkill(getAdapterPosition());
                    dieTypeView.setImageDrawable(activity.dieTypeForLevel(lvl));
                }
            });
        }
    }
}
