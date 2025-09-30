package batchInsert;

import jpa.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaBatchInsertExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("students-h2");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        for (int i = 1; i <= 1000; i++) {
            Student student = new Student();
            student.setName("Student " + i);
            student.setSurname("Surname " + i);
            student.setGroups("Group A");
            student.setStatus(1);

            em.persist(student);

            // Без этого Hibernate может держать все объекты в памяти
            if (i % 50 == 0) {
                em.flush();   // отправляем SQL в БД
                em.clear();   // очищаем кэш persistence context
            }
            /**
             * 👉 Работает, но управление батчами «ручное» (flush/clear). Настройки батча зависят от Hibernate, но через JPA их не видно.
             */
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
