package com.example.makeswade;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeswade.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Character> characterArrayList;
    private AppBarConfiguration appBarConfiguration;
    private DataManager dataManager;
    private CharacterAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = makeCreatorIntent();
                startActivity(intent);
            }
        });

        characterArrayList = new ArrayList<Character>();
        dataManager = new DataManager(this);
        recyclerView = findViewById(R.id.characterRecyclerView);
        characterAdapter = new CharacterAdapter(this, characterArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(characterAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData() {
        Cursor cursor = dataManager.selectAllCharacters();
        characterArrayList.clear();
        int id;
        String name;
        byte[] bytes;
        Bitmap thumbnail;
        Character character;
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                id = cursor.getInt(0);
                name = cursor.getString(1);
                bytes = cursor.getBlob(2);
                if(bytes.length > 0) {
                    thumbnail = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                } else { thumbnail = null; }
                character = new Character(id, name, thumbnail);
                characterArrayList.add(character);
            }
        }
        characterAdapter.notifyDataSetChanged();
    }

    private Intent makeCreatorIntent() {
        return new Intent(this, CreateCharacterActivity.class);
    }
}