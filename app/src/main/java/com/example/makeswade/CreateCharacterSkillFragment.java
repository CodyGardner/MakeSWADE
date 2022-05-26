package com.example.makeswade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateCharacterSkillFragment extends Fragment {
    private TextView skillPtsRemainingView;
    private TextView hindPtsRemainingView;
    private RecyclerView skillRecycler;
    private CreateCharacterActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View createSkillFrag = inflater.inflate(R.layout.fragment_skill_character_create, container, false);
        baseActivity = (CreateCharacterActivity) getActivity();
        baseActivity.setSkillFrag(this);

        skillPtsRemainingView = createSkillFrag.findViewById(R.id.skillPtsRemainingView);
        hindPtsRemainingView = createSkillFrag.findViewById(R.id.skillHindPointsRemainingView);
        skillPtsRemainingView.setText(Integer.toString(baseActivity.getSkillPtsRemaining()));
        hindPtsRemainingView.setText(Integer.toString(baseActivity.getHindPtsRemaining()));
        skillRecycler = createSkillFrag.findViewById(R.id.skillRecyclerView);
        SkillAdapter skillAdapter = new SkillAdapter(baseActivity,
                baseActivity.getNewCharacter().getSkills());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(baseActivity.getApplicationContext());
        skillRecycler.setLayoutManager(layoutManager);
        skillRecycler.addItemDecoration(new DividerItemDecoration(baseActivity.getApplicationContext(),
                LinearLayoutManager.VERTICAL));
        skillRecycler.setAdapter(skillAdapter);

        return createSkillFrag;
    }

    public void updateSkillPointsRemaining(int skillPts) {
        this.skillPtsRemainingView.setText(Integer.toString(skillPts));
    }

    public void updateHindPointsRemaining(int hindPts) {
        this.hindPtsRemainingView.setText(Integer.toString(hindPts));
    }
}
