package com.example.makeswade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterListItemHolder> {
    private MainActivity mainActivity;
    private ArrayList<Character> characterArrayList;

    public CharacterAdapter(MainActivity mainActivity, ArrayList<Character> characterArrayList) {
        this.mainActivity = mainActivity;
        this.characterArrayList = characterArrayList;
    }

    @NonNull
    @Override
    public CharacterListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View characterListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_character, parent, false);
        return new CharacterListItemHolder(characterListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharacterListItemHolder holder, int position) {
        Character character = characterArrayList.get(position);
        // TODO: Add image
        // holder.characterImageView.setImageBitmap();
        holder.characterNameView.setText(character.getName());
        holder.characterImageView.setImageBitmap(character.getThumbnail());
    }

    @Override
    public int getItemCount() { return characterArrayList.size(); }

    public class CharacterListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView characterImageView;
        private TextView characterNameView;

        public CharacterListItemHolder (View view) {
            super(view);
            characterImageView = view.findViewById(R.id.characterThumbnailView);
            characterNameView = view.findViewById(R.id.characterNameView);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //TODO: implement fragment load
            Snackbar.make(v, "Row clicked...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
