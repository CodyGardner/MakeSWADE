package com.example.makeswade;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.makeswade.Hinderance;
import com.example.makeswade.Edge;
import com.example.makeswade.Skill;
import com.example.makeswade.Attribute;

public class Character {
    private int id;
    private String name;
    private Bitmap thumbnail;
    private int level;
    private ArrayList<Attribute> attrs;
    private ArrayList<Skill> skills;
    private ArrayList<Hinderance> hinderances;
    private ArrayList<Edge> edges;

    public Character() {
        this.id = 0;
        this.name = "";
        this.thumbnail = null;
        this.level = 0;
        this.attrs = new ArrayList<Attribute>();
        this.skills = new ArrayList<Skill>();
        this.hinderances = new ArrayList<Hinderance>();
        this.edges = new ArrayList<Edge>();
    }

    public Character(int id, String name, Bitmap thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Bitmap getThumbnail() { return thumbnail; }

    public int getLevel() {
        return level;
    }

    public ArrayList<Attribute> getAttrs() {
        return attrs;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Hinderance> getHinderances() {
        return hinderances;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(Bitmap thumbnail) { this.thumbnail = thumbnail; }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addAttribute (Attribute attribute) { this.attrs.add(attribute); }
    public void addSkill (Skill skill) { this.skills.add(skill); }
    public boolean compressThumbnail(ByteArrayOutputStream baos) {
        return this.thumbnail.compress(Bitmap.CompressFormat.PNG, 100, baos);
    }
    public void recycleThumbnail() { this.thumbnail.recycle(); }
}
