package com.example.makeswade;

import android.os.Bundle;
import android.provider.ContactsContract;
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

public class CreateCharacterAttributeFragment extends Fragment {
    private TextView attrPointsRemainingView;
    private TextView hindPointsRemainingView;
    private RecyclerView attrRecycler;
    private DataManager dataManager;
    private CreateCharacterActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View createAttrFrag = inflater.inflate(R.layout.fragment_attribute_character_create, container, false);
        baseActivity = (CreateCharacterActivity) getActivity();
        baseActivity.setAttrFrag(this);

        attrPointsRemainingView = createAttrFrag.findViewById(R.id.attrPtsRemainingView);
        hindPointsRemainingView = createAttrFrag.findViewById(R.id.attrHindPtsRemainingView);
        attrPointsRemainingView.setText(Integer.toString(baseActivity.getAttrPtsRemaining()));
        hindPointsRemainingView.setText(Integer.toString(baseActivity.getHindPtsRemaining()));
        attrRecycler = createAttrFrag.findViewById(R.id.attrRecyclerView);
        AttributeAdapter attributeAdapter = new AttributeAdapter(baseActivity, baseActivity.getNewCharacter().getAttrs());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(baseActivity.getApplicationContext());
        attrRecycler.setLayoutManager(layoutManager);
        attrRecycler.addItemDecoration(new DividerItemDecoration(baseActivity.getApplicationContext(), LinearLayoutManager.VERTICAL));
        attrRecycler.setAdapter(attributeAdapter);

        return createAttrFrag;
    }

    public void updateAttrPointsRemaining(int attrPts) {
        this.attrPointsRemainingView.setText(Integer.toString(attrPts));
    }

    public void updateHindPointsRemaining(int hindPts) {
        this.hindPointsRemainingView.setText(Integer.toString(hindPts));
    }
}
