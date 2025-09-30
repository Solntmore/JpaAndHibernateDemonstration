package jpa.db;

import jpa.entity.Student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * HibernateDBManager → использует Hibernate API напрямую (Session, Transaction, Query).
 */
public class HibernateDBManager {
    private static final SessionFactory sessionFactory = Persistence
            .createEntityManagerFactory("students-h2")
            .unwrap(SessionFactory.class);

    public static List<Student> getAllActiveStudents() {
        Session session = sessionFactory.openSession();
        try {
            Query<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.status = 1", Student.class);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    public static Student getStudentById(String id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Student.class, Integer.parseInt(id));
        } finally {
            session.close();
        }
    }

    public static List<Student> createStudent(String surname, String name, String groups, String date) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student student = new Student();
            student.setSurname(surname);
            student.setName(name);
            student.setGroups(groups);
            student.setDate(parse(date));
            student.setStatus(1);
            session.persist(student);
            tx.commit();
            return getAllActiveStudents();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public static List<Student> updateStudent(String surname, String name, String groups, String date, String id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, Integer.parseInt(id));
            if (student != null) {
                student.setSurname(surname);
                student.setName(name);
                student.setGroups(groups);
                student.setDate(parse(date));
            }
            tx.commit();
            return getAllActiveStudents();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
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


