package jdbcVsHibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 👉 Тут для каждой операции приходится писать SQL вручную: INSERT, UPDATE, DELETE.
 */
public class JdbcExample {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb",
                "user", "password"
        );

        // INSERT
        PreparedStatement psInsert = connection.prepareStatement(
                "INSERT INTO users (name, age) VALUES (?, ?)");
        psInsert.setString(1, "Anna");
        psInsert.setInt(2, 25);
        psInsert.executeUpdate();

        // UPDATE
        PreparedStatement psUpdate = connection.prepareStatement(
                "UPDATE users SET age = ? WHERE name = ?");
        psUpdate.setInt(1, 26);
        psUpdate.setString(2, "Anna");
        psUpdate.executeUpdate();

        // DELETE
        PreparedStatement psDelete = connection.prepareStatement(
                "DELETE FROM users WHERE name = ?");
        psDelete.setString(1, "Anna");
        psDelete.executeUpdate();

        /** пример с кешом */
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?");
        ps.setInt(1, 1);

        ResultSet rs1 = ps.executeQuery(); // 1-й запрос
        ResultSet rs2 = ps.executeQuery(); // 2-й запрос

        connection.close();
    }
}