package com.vityarthi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String teacherId;
    private List<String> studentIds = new ArrayList<>();
    private Map<String, Double> studentGrades = new HashMap<>();

    public Course(String id, String title, String teacherId) {
        this.id = id;
        this.title = title;
        this.teacherId = teacherId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getTeacherId() { return teacherId; }
    public List<String> getStudentIds() { return studentIds; }

    public void enroll(String studentId) {
        if (!studentIds.contains(studentId)) studentIds.add(studentId);
    }

    public void assignGrade(String studentId, double grade) {
        if (studentIds.contains(studentId)) studentGrades.put(studentId, grade);
    }

    public Map<String, Double> getStudentGrades() { return studentGrades; }

    @Override
    public String toString() {
        return String.format("%s | %s | Teacher:%s | Enrolled:%d", id, title, teacherId==null?"-":teacherId, studentIds.size());
    }
}
