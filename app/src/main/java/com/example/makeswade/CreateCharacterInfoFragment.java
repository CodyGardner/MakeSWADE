package com.example.makeswade;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class CreateCharacterInfoFragment extends Fragment {
    private CreateCharacterActivity baseActivity;
    View createInfoFrag;
    private Character character;
    private Button saveButton;
    private Button selectImageButton;
    private ImageView image;
    private EditText nameEdit;
    private EditText levelEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createInfoFrag = inflater.inflate(R.layout.fragment_info_character_create, container, false);
        baseActivity = (CreateCharacterActivity) getActivity();
        baseActivity.setInfoFrag(this);

        character = ((CreateCharacterActivity) getActivity()).getNewCharacter();
        saveButton = createInfoFrag.findViewById(R.id.characterCreateSaveButton);
        selectImageButton = createInfoFrag.findViewById(R.id.characterCreateUploadButton);
        image = createInfoFrag.findViewById(R.id.characterCreateThumbnailView);
        nameEdit = createInfoFrag.findViewById(R.id.characterCreateNameEdit);
        levelEdit = createInfoFrag.findViewById(R.id.characterCreateLevelEdit);
        levelEdit.setText(Integer.toString(character.getLevel()));

//        if (character.getThumbnail() != null)
//            image.setImageBitmap(character.getThumbnail());

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                character.setName(s.toString());
            }
        });

        levelEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { character.setLevel(
                    Integer.parseInt(s.toString())); }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.saveCharacter();
                baseActivity.returnToMain();
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.getThumbnailFromGallery();
            }
        });
        return createInfoFrag;
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (image == null)
            Log.i("info", "Image view is null");
        image.setImageBitmap(bitmap);
    }
}
