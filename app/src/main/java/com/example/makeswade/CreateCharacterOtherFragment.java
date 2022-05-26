package com.example.makeswade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateCharacterOtherFragment extends Fragment {
    private CreateCharacterActivity baseActivity;
    private RecyclerView hindRecycler;
    private RecyclerView edgeRecycler;
    private TextView nameView;
    private TextView descriptionView;
    private Button addEdgeBtn;
    private Button addMajorBtn;
    private Button addMinorBtn;
    private HindranceAdapter hindranceAdapter;
    private EdgeAdapter edgeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View createOtherFrag = inflater.inflate(R.layout.fragment_other_character_create, container, false);
        baseActivity = (CreateCharacterActivity) getActivity();
        baseActivity.setOtherFrag(this);

        hindRecycler = createOtherFrag.findViewById(R.id.hindRecyclerView);
        edgeRecycler = createOtherFrag.findViewById(R.id.edgeRecyclerView);
        nameView = createOtherFrag.findViewById(R.id.addEdgeHindNameView);
        descriptionView = createOtherFrag.findViewById(R.id.addEdgeHindDescView);
        addEdgeBtn = createOtherFrag.findViewById(R.id.addEdgeBtn);
        addMajorBtn = createOtherFrag.findViewById(R.id.addMajorBtn);
        addMinorBtn = createOtherFrag.findViewById(R.id.addMinorBtn);

        hindranceAdapter = new HindranceAdapter(baseActivity,
                baseActivity.getNewCharacter().getHinderances());
        edgeAdapter = new EdgeAdapter(baseActivity,
                baseActivity.getNewCharacter().getEdges());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(baseActivity.getApplicationContext());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(baseActivity.getApplicationContext());
        hindRecycler.setLayoutManager(layoutManager1);
        edgeRecycler.setLayoutManager(layoutManager2);
        hindRecycler.addItemDecoration(new DividerItemDecoration(baseActivity.getApplicationContext(), LinearLayoutManager.VERTICAL));
        edgeRecycler.addItemDecoration(new DividerItemDecoration(baseActivity.getApplicationContext(), LinearLayoutManager.VERTICAL));
        hindRecycler.setAdapter(hindranceAdapter);
        edgeRecycler.setAdapter(edgeAdapter);

        addEdgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edge e = new Edge(0, nameView.getText().toString(),
                        descriptionView.getText().toString());
                if (!baseActivity.addEdge(e))
                    Toast.makeText(v.getContext(), R.string.insufficient_pts, Toast.LENGTH_LONG).show();
                else
                    clearText();
            }
        });

        addMajorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.addHindrance(new Hinderance(0, nameView.getText().toString(),
                        descriptionView.getText().toString(), true));
                clearText();
            }
        });

        addMinorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.addHindrance(new Hinderance(0, nameView.getText().toString(),
                        descriptionView.getText().toString(), false));
                clearText();
            }
        });

        return createOtherFrag;
    }

    private void clearText() {
        nameView.setText("");
        descriptionView.setText("");
    }

    public void notifyHindChange() {
        hindranceAdapter.notifyDataSetChanged();
    }

    public void notifyEdgeChange() {
        edgeAdapter.notifyDataSetChanged();
    }
}
