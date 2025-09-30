package jdbcVsHibernate;

import javax.persistence.*;
import java.util.List;

/**
 * 👉 Тут используем JPQL для операций: INSERT, UPDATE, DELETE.
 * JPQL работает с объектами, а не с таблицами напрямую.
 */
public class JpqlExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("students-h2");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // INSERT - создаем объект User и сохраняем его
            ExampleUser user = new ExampleUser();
            user.setName("Anna");
            user.setAge(25);
            em.persist(user);

            // UPDATE - обновляем возраст пользователя
            Query updateQuery = em.createQuery("UPDATE ExampleUser u SET u.age = :newAge WHERE u.name = :name");
            updateQuery.setParameter("newAge", 26);
            updateQuery.setParameter("name", "Anna");
            updateQuery.executeUpdate();

            // DELETE - удаляем пользователя по имени
            Query deleteQuery = em.createQuery("DELETE FROM ExampleUser u WHERE u.name = :name");
            deleteQuery.setParameter("name", "Anna");
            deleteQuery.executeUpdate();

            TypedQuery<ExampleUser> query = em.createQuery("SELECT e FROM ExampleUser e", ExampleUser.class);
            List<ExampleUser> users = query.getResultList();
            users.forEach(System.out::println);

            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
}
