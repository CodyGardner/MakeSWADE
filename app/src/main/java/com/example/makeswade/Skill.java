package com.example.makeswade;

public class Skill {
    private int id;
    private String name;
    private int level;
    private Attribute attr;

    public Skill(String name, Attribute attr) {
        this.id = 0;
        this.name = name;
        this.level = 0;
        this.attr = attr;
    }

    public Skill(int id, String name, int level, Attribute attr) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.attr = attr;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    public Attribute getAttr() { return attr; }

    public void setId(int id) { this.id = id; }
    public void setLevel(int level) { this.level = level; }
    public void setName(String name) { this.name = name; }
    public void setAttr(Attribute attr) { this.attr = attr; }
}
