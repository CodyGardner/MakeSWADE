package com.example.makeswade;

public class Hinderance {
    private int id;
    private String name;
    private String description;
    private boolean severity;

    public Hinderance(int id, String name, String description, boolean severity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.severity = severity;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public boolean getSeverity() { return severity; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSeverity(boolean severity) { this.severity = severity; }
}

