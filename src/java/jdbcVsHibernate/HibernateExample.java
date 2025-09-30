package jdbcVsHibernate;

import jpa.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.Persistence;
import java.util.List;

/**
 * 👉 Здесь мы работаем с объектом как с обычным Java-классом:
 * 	•	save(user) → Hibernate подготовит INSERT.
 * 	•	user.setAge(26) → Hibernate сам увидит изменение и сделает UPDATE при коммите.
 * 	•	remove(user) → Hibernate сгенерирует DELETE.
 */
public class HibernateExample {
    public static void main(String[] args) {
        SessionFactory sessionFactory = Persistence
                .createEntityManagerFactory("students-h2")
                .unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        // INSERT (достаточно просто сохранить объект)
        ExampleUser user = new ExampleUser("Anna", 25);
        session.save(user);

        // UPDATE (просто меняем поле в объекте — Hibernate сам сделает UPDATE)
        user.setAge(26);

        // DELETE (просто вызываем remove)
        session.remove(user);

        /** пример с кешом */
        // Первый запрос: Hibernate пойдет в БД
        ExampleUser user1 = session.get(ExampleUser.class, 1L);

        // Второй раз тот же объект: Hibernate вернёт из кэша,
        // в базу больше не пойдет
        ExampleUser user2 = session.get(ExampleUser.class, 1L);

        System.out.println(user1 == user2); // true (один и тот же объект в памяти)
        tx.commit();

        Query<ExampleUser> query = session.createQuery("SELECT e FROM ExampleUser e", ExampleUser.class);
        List<ExampleUser> users = query.getResultList();
        users.forEach(System.out::println);

        session.close();

    }
}
