package com.vityarthi.services;

import com.vityarthi.models.Course;
import com.vityarthi.models.Student;
import com.vityarthi.storage.DataStore;

import java.util.Map;
import java.util.OptionalDouble;

public class ReportService {
    private final DataStore store;

    public ReportService(DataStore store) { this.store = store; }

    public String generateTranscript(String studentId) {
        Student s = store.students.stream().filter(x->x.getId().equals(studentId)).findFirst().orElse(null);
        if (s==null) return "Student not found.";
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(s.getName()).append(" (").append(s.getId()).append(")\n");
        sb.append("Course | Grade\n");
        s.getGrades().forEach((courseId, grade) -> {
            Course c = store.courses.stream().filter(x->x.getId().equals(courseId)).findFirst().orElse(null);
            sb.append((c==null?courseId:c.getTitle())).append(" | ").append(grade).append("\n");
        });
        OptionalDouble avg = s.getGrades().values().stream().mapToDouble(Double::doubleValue).average();
        sb.append("GPA-estimate (avg): ").append(avg.isPresent() ? String.format("%.2f", avg.getAsDouble()) : "N/A");
        return sb.toString();
    }

    public String courseAnalytics(String courseId) {
        Course c = store.courses.stream().filter(x->x.getId().equals(courseId)).findFirst().orElse(null);
        if (c==null) return "Course not found.";
        StringBuilder sb = new StringBuilder();
        sb.append("Analytics for ").append(c.getTitle()).append(" (").append(c.getId()).append(")\n");
        Map<String, Double> grades = c.getStudentGrades();
        sb.append("Enrolled: ").append(c.getStudentIds().size()).append("\n");
        if (grades.isEmpty()) {
            sb.append("No grades yet.\n");
            return sb.toString();
        }
        double avg = grades.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double max = grades.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double min = grades.values().stream().mapToDouble(Double::doubleValue).min().orElse(0);
        sb.append(String.format("Average: %.2f | Max: %.2f | Min: %.2f\n", avg, max, min));
        sb.append("Grades by student:\n");
        grades.forEach((sid, g) -> {
            Student s = store.students.stream().filter(x->x.getId().equals(sid)).findFirst().orElse(null);
            sb.append((s==null?sid:s.getName())).append(" : ").append(g).append("\n");
        });
        return sb.toString();
    }
}
