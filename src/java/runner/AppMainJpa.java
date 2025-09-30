package runner;

import jpa.db.JpaDBManager;
import jpa.entity.Student;

import java.util.List;

public class AppMainJpa {
    public static void main(String[] args) {
        // Create a few students
        JpaDBManager.createStudent("Ivanov", "Ivan", "A-01", "2024-09-01");
        JpaDBManager.createStudent("Petrov", "Petr", "B-02", "2024-09-02");

        // Read all active
        List<Student> students = JpaDBManager.getAllActiveStudents();
        System.out.println("Active students: " + students.size());

        // Update first
        if (!students.isEmpty()) {
            Student first = students.get(0);
            JpaDBManager.updateStudent(first.getSurname(), first.getName() + "-upd", first.getGroups(), "2024-09-10", String.valueOf(first.getId()));
        }

        // Fetch by id
        if (!students.isEmpty()) {
            Student fetched = JpaDBManager.getStudentById(String.valueOf(students.get(0).getId()));
            System.out.println("Fetched by id: " + (fetched != null ? fetched.getName() : null));
        }

        students = JpaDBManager.getAllActiveStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}


