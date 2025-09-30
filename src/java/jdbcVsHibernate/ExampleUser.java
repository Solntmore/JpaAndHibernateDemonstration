package jdbcVsHibernate;

import javax.persistence.*;


/**
 * 📌 Пояснения:
 * 	•	Аннотация @Entity говорит Hibernate, что это сущность.
 * 	•	@Table(name = "users") указывает таблицу в базе.
 * 	•	@Id и @GeneratedValue — первичный ключ с автоинкрементом.
 * 	•	Поля name и age будут автоматически отображаться на колонки в таблице users.
 */
@Entity
@Table(name = "example_user") // таблица в БД
public class ExampleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автоинкремент
    private Long id;

    private String name;

    private int age;

    public ExampleUser() {

    }

    public ExampleUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Для удобного вывода
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
