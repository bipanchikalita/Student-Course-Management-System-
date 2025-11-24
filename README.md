Project Title: Student Course Management System (Vityarthi)

Overview
This Java console application demonstrates a simple Student Course Management System designed to meet the course project requirements. It lets users manage students, teachers, courses, enrollments, grades, and generate basic reports and analytics.

Features
- User management: create/list students and teachers
- Course management: create courses and enroll students
- Grading: assign grades to enrolled students
- Reporting: generate student transcript and course analytics (average grade)
- Persistence: simple file-based serialization for storage

Technologies / Tools
- Java 11+ (standard library only)
- No external dependencies

How to compile & run
1. Open a terminal in the project root (where `src` is located).
2. Compile:
```powershell
javac -d out $(Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
```
3. Run:
```powershell
java -cp out com.vityarthi.Main
```

Quick usage
- Follow the menu prompts in the console to add students/courses, enroll, assign grades, and generate reports.

Project structure
- `src/com/vityarthi` - main entry and top-level services
- `src/com/vityarthi/models` - domain models (Student, Teacher, Course)
- `src/com/vityarthi/services` - business logic (UserService, CourseService, ReportService)
- `src/com/vityarthi/storage` - simple persistence (DataStore)
- `README.md`, `statement.md` - documentation files required by the assignment

Notes
- The project uses Java serialization to persist data to `data/store.dat`.
- This is a minimal, self-contained implementation intended for demonstration and grading.

