package com.vityarthi.services;

import com.vityarthi.models.Course;
import com.vityarthi.models.Student;
import com.vityarthi.storage.DataStore;

import java.util.List;
import java.util.UUID;

public class CourseService {
    private final DataStore store;

    public CourseService(DataStore store) { this.store = store; }

    public Course createCourse(String title, String teacherId) {
        String id = "C-" + UUID.randomUUID().toString().substring(0,8);
        Course c = new Course(id, title, teacherId);
        store.courses.add(c);
        store.markDirty();
        System.out.println("Created course: " + c.getId());
        return c;
    }

    public List<Course> listCourses() { return store.courses; }

    public Course findCourse(String id) {
        return store.courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean enrollStudent(String courseId, String studentId) {
        Course c = findCourse(courseId);
        if (c==null) {
            System.out.println("Course not found.");
            return false;
        }
        Student s = store.students.stream().filter(x->x.getId().equals(studentId)).findFirst().orElse(null);
        if (s==null) {
            System.out.println("Student not found.");
            return false;
        }
        c.enroll(studentId);
        store.markDirty();
        System.out.println("Enrolled " + s.getName() + " in " + c.getTitle());
        return true;
    }

    public boolean assignGrade(String courseId, String studentId, double grade) {
        Course c = findCourse(courseId);
        if (c==null) {
            System.out.println("Course not found.");
            return false;
        }
        Student s = store.students.stream().filter(x->x.getId().equals(studentId)).findFirst().orElse(null);
        if (s==null) {
            System.out.println("Student not found.");
            return false;
        }
        c.assignGrade(studentId, grade);
        s.assignGrade(courseId, grade);
        store.markDirty();
        System.out.println("Assigned grade " + grade + " to " + s.getName());
        return true;
    }

    public boolean updateCourse(String courseId, String newTitle, String newTeacherId) {
        Course c = findCourse(courseId);
        if (c == null) return false;
        try {
            if (newTitle != null && !newTitle.isBlank()) {
                java.lang.reflect.Field f = c.getClass().getDeclaredField("title");
                f.setAccessible(true);
                f.set(c, newTitle);
            }
            if (newTeacherId != null) {
                java.lang.reflect.Field f2 = c.getClass().getDeclaredField("teacherId");
                f2.setAccessible(true);
                f2.set(c, newTeacherId.isBlank() ? null : newTeacherId);
            }
            store.markDirty();
            System.out.println("Updated course: " + courseId);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to update course: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCourse(String courseId) {
        Course c = findCourse(courseId);
        if (c == null) return false;
        store.courses.remove(c);
        // remove course references from students
        store.students.forEach(s -> s.getGrades().remove(courseId));
        store.markDirty();
        System.out.println("Deleted course: " + courseId);
        return true;
    }
}
