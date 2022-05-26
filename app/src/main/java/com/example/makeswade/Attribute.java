package com.example.makeswade;

public class Attribute {
    private int id;
    private int level;
    private String name;
    private String abv;

    public Attribute(String name, String abv){
        this.id = 0;
        this.level = 1;
        this.name = name;
        this.abv = abv;
    }
    public Attribute(int id, int level, String name, String abv) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.abv = abv;
    }

    public int getId() { return id; }
    public int getLevel() { return level; }
    public String getName() { return name; }
    public String getAbv() { return abv; }

    public void setId(int id) { this.id = id; }
    public void setLevel(int level) { this.level = level; }
    public void setName(String name) { this.name = name; }
    public void setAbv(String abv) { this.abv = abv; }
}

