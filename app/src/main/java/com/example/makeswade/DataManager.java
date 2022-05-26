package com.example.makeswade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import com.example.makeswade.Character;
import com.example.makeswade.Attribute;
import com.example.makeswade.Skill;
import com.example.makeswade.Edge;
import com.example.makeswade.Hinderance;

import java.io.ByteArrayOutputStream;

public class DataManager {
    private String DB_NAME = "SWADE_CHARACTER_DB";
    private int DB_VERSION = 1;
    private SQLiteDatabase db;

    public DataManager (Context context) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public Cursor selectAllCharacters() {
        Cursor cursor = null;
        String selectQuery = "select id, name, thumbnail from character";

        try{
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            Log.i("info", "Error in DataManager selectAllCharacters method");
            Log.i("info", e.getMessage());
        }

        Log.i("info", "Loaded " + cursor.getCount() + " characters");
        return cursor;
    }

    public Cursor selectAllAttributes(int characterID) {
        Cursor cursor = null;
        String selectQuery = "select * from attribute where character_id = " + Integer.toString(characterID);

        try{
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            Log.i("info", "Error in DataManager selectAllAttributes method");
            Log.i("info", e.getMessage());
        }

        Log.i("info", "Loaded " + cursor.getCount() + " attributes");
        return cursor;
    }

    public Cursor selectAllSkills(int attrID) {
        Cursor cursor = null;
        String selectQuery = "select * from skill where attribute_id = " + Integer.toString(attrID);

        try{
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            Log.i("info", "Error in DataManager selectAllSkills method");
            Log.i("info", e.getMessage());
        }

        Log.i("info", "Loaded " + cursor.getCount() + " skills");
        return cursor;
    }

    public Cursor selectAllHinderances(int characterID) {
        Cursor cursor = null;
        String selectQuery = "select * from hinderance where character_id = " + Integer.toString(characterID);

        try{
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            Log.i("info", "Error in DataManager selectAllHinderances method");
            Log.i("info", e.getMessage());
        }

        Log.i("info", "Loaded " + cursor.getCount() + " hindrances");
        return cursor;
    }

    public Cursor selectAllEdges(int characterID) {
        Cursor cursor = null;
        String selectQuery = "select * from edge where character_id = " + Integer.toString(characterID);

        try{
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            Log.i("info", "Error in DataManager selectAllEdges method");
            Log.i("info", e.getMessage());
        }

        Log.i("info", "Loaded " + cursor.getCount() + " skills");
        return cursor;
    }

    public long insertCharacter(Character character) {
        long newid = 0;
        ContentValues cv = new ContentValues();
        cv.put("name", character.getName());
        cv.put("level", character.getLevel());
        if (character.getThumbnail() == null) {
            cv.put("thumbnail", (byte[]) null);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            character.compressThumbnail(bos);
            cv.put("thumbnail", bos.toByteArray());
        }
        try {
            newid = db.insert("character", "", cv);
        }
        catch (SQLException e) {
            Log.i("info", "Error in DataManager insertCharacter method");
            Log.i("info", e.getMessage());
        }
        return newid;
    }

    public long insertAttr(Attribute attribute, Character character) {
        long newid = 0;
        ContentValues cv = new ContentValues();
        cv.put("name", attribute.getName());
        cv.put("abv", attribute.getAbv());
        cv.put("level", attribute.getLevel());
        cv.put("character_id", character.getId());
        try {
            newid = db.insert("attribute", "", cv);
        }
        catch (SQLException e) {
            Log.i("info", "Error in DataManager insertAttr method");
            Log.i("info", e.getMessage());
        }
        return newid;
    }

    public long insertSkill(Skill skill) {
        long newid = 0;
        ContentValues cv = new ContentValues();
        cv.put("name", skill.getName());
        cv.put("level", skill.getLevel());
        cv.put("attribute_id", skill.getAttr().getId());
        try {
            newid = db.insert("skill", "", cv);
        }
        catch (SQLException e) {
            Log.i("info", "Error in DataManager insertSkill method");
            Log.i("info", e.getMessage());
        }
        return newid;
    }

    public long insertHinderance(Hinderance hinderance, Character character) {
        long newid = 0;
        ContentValues cv = new ContentValues();
        cv.put("name", hinderance.getName());
        cv.put("description", hinderance.getDescription());
        cv.put("severity", hinderance.getSeverity() ? 1 : 0);
        cv.put("character_id", character.getId());
        try {
            newid = db.insert("hinderance", "", cv);
        }
        catch (SQLException e) {
            Log.i("info", "Error in DataManager insertHinderance method");
            Log.i("info", e.getMessage());
        }
        return newid;
    }

    public long insertEdge(Edge edge, Character character) {
        long newid = 0;
        ContentValues cv = new ContentValues();
        cv.put("name", edge.getName());
        cv.put("description", edge.getDescription());
        cv.put("character_id", character.getId());
        try {
            newid = db.insert("edge", "", cv);
        }
        catch (SQLException e) {
            Log.i("info", "Error in DataManager insertEdge method");
            Log.i("info", e.getMessage());
        }
        return newid;
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper (Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Insert strings to create new tables
            String create_character = "CREATE TABLE character(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, level INTEGER, thumbnail BLOB)";
            String create_attribute = "CREATE TABLE attribute(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "level INTEGER, name TEXT NOT NULL, abv TEXT NOT NULL, character_id INTEGER, " +
                    "FOREIGN KEY(character_id) REFERENCES character(id))";
            String create_skill = "CREATE TABLE skill(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "level INTEGER, name TEXT NOT NULL, attribute_id INTEGER, " +
                    "FOREIGN KEY(attribute_id) REFERENCES attribute(id))";
            String create_hinderance = "CREATE TABLE hinderance(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, description TEXT, character_id INTEGER, severity INTEGER, " +
                    "FOREIGN KEY(character_id) REFERENCES character(id))";
            String create_edge = "CREATE TABLE edge(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, description TEXT, character_id INTEGER, " +
                    "FOREIGN KEY(character_id) REFERENCES character(id))";

            try {
                Log.i("info", "Creating character table");
                db.execSQL(create_character);
                Log.i("info", "Creating attribute table");
                db.execSQL(create_attribute);
                Log.i("info", "Creating skill table");
                db.execSQL(create_skill);
                Log.i("info", "Creating hinderance table");
                db.execSQL(create_hinderance);
                Log.i("info", "Creating edge table");
                db.execSQL(create_edge);
            }
            catch (SQLException e) {
                Log.i("info", "Error in OpenHelper onCreate method");
                Log.i("info", e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Nothing to add yet
        }
    }
}
