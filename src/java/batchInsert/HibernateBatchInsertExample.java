package batchInsert;

import jpa.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateBatchInsertExample {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("persistence.xml")
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 1; i <= 1000; i++) {
            Student student = new Student();
            student.setName("Student " + i);
            student.setSurname("Surname " + i);
            student.setGroups("Group A");
            student.setStatus(1);

            session.persist(student);

            if (i % 50 == 0) {
                session.flush();   // отправляем пачку INSERT
                session.clear();   // освобождаем память
            }
            /**
             * 👉 Тогда Hibernate сам будет оптимизировать пакетные вставки (INSERT INTO ... VALUES (...), (...), (...)) и работать быстрее.
             */
        }

        tx.commit();
        session.close();
        sessionFactory.close();
    }
}
