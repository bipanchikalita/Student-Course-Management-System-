package com.vityarthi;

import com.vityarthi.services.CourseService;
import com.vityarthi.services.ReportService;
import com.vityarthi.services.UserService;
import com.vityarthi.storage.DataStore;
import com.vityarthi.utils.InputUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataStore store = DataStore.load();
        UserService userService = new UserService(store);
        CourseService courseService = new CourseService(store);
        ReportService reportService = new ReportService(store);

        Scanner scanner = new Scanner(System.in);
        InputUtil input = new InputUtil(scanner);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Student Course Management ===");
            System.out.println("1. Add Student");
            System.out.println("2. Add Teacher");
            System.out.println("3. Add Course");
            System.out.println("4. Enroll Student in Course");
            System.out.println("5. Assign Grade");
            System.out.println("6. List Students/Courses/Teachers");
            System.out.println("7. Update/Delete Student/Teacher/Course");
            System.out.println("8. Generate Reports");
            System.out.println("9. Save & Exit");
            System.out.print("Choose an option: ");
            int opt = input.nextInt();
            switch (opt) {
                case 1:
                    System.out.print("Student name: ");
                    String sName = input.nextLine();
                    System.out.print("Email: ");
                    String sEmail = input.nextLine();
                    userService.createStudent(sName, sEmail);
                    break;
                case 2:
                    System.out.print("Teacher name: ");
                    String tName = input.nextLine();
                    System.out.print("Email: ");
                    String tEmail = input.nextLine();
                    userService.createTeacher(tName, tEmail);
                    break;
                case 3:
                    System.out.print("Course title: ");
                    String cTitle = input.nextLine();
                    System.out.print("Teacher ID (or blank): ");
                    String tId = input.nextLine();
                    courseService.createCourse(cTitle, tId.isBlank() ? null : tId);
                    break;
                case 4:
                    System.out.print("Course ID: ");
                    String cId = input.nextLine();
                    System.out.print("Student ID: ");
                    String studId = input.nextLine();
                    courseService.enrollStudent(cId, studId);
                    break;
                case 5:
                    System.out.print("Course ID: ");
                    String cgCourse = input.nextLine();
                    System.out.print("Student ID: ");
                    String cgStudent = input.nextLine();
                    System.out.print("Grade (numeric 0-100): ");
                    double grade = input.nextDouble();
                    courseService.assignGrade(cgCourse, cgStudent, grade);
                    break;
                case 6:
                    System.out.println("-- Students --");
                    userService.listStudents().forEach(System.out::println);
                    System.out.println("-- Teachers --");
                    userService.listTeachers().forEach(System.out::println);
                    System.out.println("-- Courses --");
                    courseService.listCourses().forEach(System.out::println);
                    break;
                case 7:
                    System.out.println("1. Update Student\n2. Delete Student\n3. Update Teacher\n4. Delete Teacher\n5. Update Course\n6. Delete Course");
                    int u = input.nextInt();
                    switch (u) {
                        case 1:
                            System.out.print("Student ID: ");
                            String usid = input.nextLine();
                            System.out.print("New name (blank to keep): ");
                            String un = input.nextLine();
                            System.out.print("New email (blank to keep): ");
                            String ue = input.nextLine();
                            userService.updateStudent(usid, un, ue);
                            break;
                        case 2:
                            System.out.print("Student ID to delete: ");
                            String ds = input.nextLine();
                            userService.deleteStudent(ds);
                            break;
                        case 3:
                            System.out.print("Teacher ID: ");
                            String utid = input.nextLine();
                            System.out.print("New name (blank to keep): ");
                            String utn = input.nextLine();
                            System.out.print("New email (blank to keep): ");
                            String ute = input.nextLine();
                            userService.updateTeacher(utid, utn, ute);
                            break;
                        case 4:
                            System.out.print("Teacher ID to delete: ");
                            String dt = input.nextLine();
                            userService.deleteTeacher(dt);
                            break;
                        case 5:
                            System.out.print("Course ID: ");
                            String ucid = input.nextLine();
                            System.out.print("New title (blank to keep): ");
                            String uct = input.nextLine();
                            System.out.print("New teacher ID (blank to clear): ");
                            String uctid = input.nextLine();
                            courseService.updateCourse(ucid, uct, uctid);
                            break;
                        case 6:
                            System.out.print("Course ID to delete: ");
                            String dco = input.nextLine();
                            courseService.deleteCourse(dco);
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                    break;
                case 8:
                    System.out.println("1. Student Transcript\n2. Course Analytics");
                    int r = input.nextInt();
                    if (r==1) {
                        System.out.print("Student ID: ");
                        String rptId = input.nextLine();
                        System.out.println(reportService.generateTranscript(rptId));
                    } else {
                        System.out.print("Course ID: ");
                        String rptCourse = input.nextLine();
                        System.out.println(reportService.courseAnalytics(rptCourse));
                    }
                    break;
                case 9:
                    store.save();
                    running = false;
                    System.out.println("Saved. Exiting.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
