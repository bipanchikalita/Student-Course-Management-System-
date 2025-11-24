package com.vityarthi.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    private Map<String, Double> grades = new HashMap<>(); // courseId -> grade

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Map<String, Double> getGrades() { return grades; }

    public void assignGrade(String courseId, double grade) {
        grades.put(courseId, grade);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", id, name, email);
    }
}
