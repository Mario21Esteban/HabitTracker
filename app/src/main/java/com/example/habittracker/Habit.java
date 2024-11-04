package com.example.habittracker;

public class Habit {
    private int id;
    private String name;
    private String description;
    private int priority;
    private boolean isCompleted;

    public Habit(int id, String name, String description, int priority, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}

