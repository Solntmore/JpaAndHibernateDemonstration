package jpa.db;

import jpa.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * JpaDBManager → использует JPA API (EntityManager, EntityTransaction, TypedQuery).
 */
public class JpaDBManager {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("students-h2");

    public static List<Student> getAllActiveStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.status = 1", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static Student getStudentById(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Student.class, Integer.parseInt(id));
        } finally {
            em.close();
        }
    }

    public static List<Student> createStudent(String surname, String name, String groups, String date) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = new Student();
            student.setSurname(surname);
            student.setName(name);
            student.setGroups(groups);
            student.setDate(parse(date));
            student.setStatus(1);
            em.persist(student);
            em.getTransaction().commit();
            return getAllActiveStudents();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public static List<Student> updateStudent(String surname, String name, String groups, String date, String id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, Integer.parseInt(id));
            if (student != null) {
                student.setSurname(surname);
                student.setName(name);
                student.setGroups(groups);
                student.setDate(parse(date));
            }
            em.getTransaction().commit();
            return getAllActiveStudents();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private static Date parse(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}


