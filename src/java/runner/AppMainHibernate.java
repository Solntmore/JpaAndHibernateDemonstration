package runner;

import jpa.db.HibernateDBManager;
import jpa.entity.Student;

import java.util.List;

public class AppMainHibernate {
    public static void main(String[] args) {
        // Create a few students
        HibernateDBManager.createStudent("Ivanov", "Ivan", "A-01", "2024-09-01");
        HibernateDBManager.createStudent("Petrov", "Petr", "B-02", "2024-09-02");

        // Read all active
        List<Student> students = HibernateDBManager.getAllActiveStudents();
        System.out.println("Active students: " + students.size());

        // Update first
        if (!students.isEmpty()) {
            Student first = students.get(0);
            HibernateDBManager.updateStudent(first.getSurname(), first.getName() + "-upd", first.getGroups(), "2024-09-10", String.valueOf(first.getId()));
        }

        // Fetch by id
        if (!students.isEmpty()) {
            Student fetched = HibernateDBManager.getStudentById(String.valueOf(students.get(0).getId()));
            System.out.println("Fetched by id: " + (fetched != null ? fetched.getName() : null));
        }

        students = HibernateDBManager.getAllActiveStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}


