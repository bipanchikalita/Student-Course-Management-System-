package com.vityarthi.services;

import com.vityarthi.models.Student;
import com.vityarthi.models.Teacher;
import com.vityarthi.storage.DataStore;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final DataStore store;

    public UserService(DataStore store) { this.store = store; }

    public Student createStudent(String name, String email) {
        String id = "S-" + UUID.randomUUID().toString().substring(0,8);
        Student s = new Student(id, name, email);
        store.students.add(s);
        store.markDirty();
        System.out.println("Created student: " + s.getId());
        return s;
    }

    public Teacher createTeacher(String name, String email) {
        String id = "T-" + UUID.randomUUID().toString().substring(0,8);
        Teacher t = new Teacher(id, name, email);
        store.teachers.add(t);
        store.markDirty();
        System.out.println("Created teacher: " + t.getId());
        return t;
    }

    public List<Student> listStudents() { return store.students; }
    public List<Teacher> listTeachers() { return store.teachers; }

    public Student findStudent(String id) {
        return store.students.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public Teacher findTeacher(String id) {
        return store.teachers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean updateStudent(String id, String newName, String newEmail) {
        Student s = findStudent(id);
        if (s == null) return false;
        // Simple approach: create new Student object with same id
        Student updated = new Student(s.getId(), newName == null || newName.isBlank() ? s.getName() : newName,
                newEmail == null || newEmail.isBlank() ? s.getEmail() : newEmail);
        // preserve grades
        updated.getGrades().putAll(s.getGrades());
        store.students.remove(s);
        store.students.add(updated);
        store.markDirty();
        System.out.println("Updated student: " + id);
        return true;
    }

    public boolean deleteStudent(String id) {
        Student s = findStudent(id);
        if (s == null) return false;
        store.students.remove(s);
        // remove from courses enrollments and grades
        store.courses.forEach(c -> {
            c.getStudentIds().remove(id);
            c.getStudentGrades().remove(id);
        });
        store.markDirty();
        System.out.println("Deleted student: " + id);
        return true;
    }

    public boolean updateTeacher(String id, String newName, String newEmail) {
        Teacher t = findTeacher(id);
        if (t == null) return false;
        Teacher updated = new Teacher(t.getId(), newName == null || newName.isBlank() ? t.getName() : newName,
                newEmail == null || newEmail.isBlank() ? t.getEmail() : newEmail);
        store.teachers.remove(t);
        store.teachers.add(updated);
        store.markDirty();
        System.out.println("Updated teacher: " + id);
        return true;
    }

    public boolean deleteTeacher(String id) {
        Teacher t = findTeacher(id);
        if (t == null) return false;
        // remove teacher reference from courses
        store.courses.forEach(c -> { if (id.equals(c.getTeacherId())) {
            // set to null
            try {
                java.lang.reflect.Field f = c.getClass().getDeclaredField("teacherId");
                f.setAccessible(true);
                f.set(c, null);
            } catch (Exception ignored) {}
        }});
        store.teachers.remove(t);
        store.markDirty();
        System.out.println("Deleted teacher: " + id);
        return true;
    }
}
