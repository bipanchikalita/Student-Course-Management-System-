package com.vityarthi.tests;

import com.vityarthi.storage.DataStore;
import com.vityarthi.services.CourseService;
import com.vityarthi.services.ReportService;
import com.vityarthi.services.UserService;

public class TestRunner {
    public static void main(String[] args) {
        DataStore store = new DataStore();
        UserService us = new UserService(store);
        CourseService cs = new CourseService(store);
        ReportService rs = new ReportService(store);

        var s1 = us.createStudent("Alice","alice@example.com");
        var s2 = us.createStudent("Bob","bob@example.com");
        var t1 = us.createTeacher("Dr. Smith","smith@example.com");
        var c1 = cs.createCourse("Intro to Java", t1.getId());
        cs.enrollStudent(c1.getId(), s1.getId());
        cs.enrollStudent(c1.getId(), s2.getId());
        cs.assignGrade(c1.getId(), s1.getId(), 85.0);
        cs.assignGrade(c1.getId(), s2.getId(), 92.0);
        System.out.println(rs.courseAnalytics(c1.getId()));
        System.out.println(rs.generateTranscript(s1.getId()));
    }
}
