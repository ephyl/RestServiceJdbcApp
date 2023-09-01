package ephyl;

import ephyl.util.ConnectionManager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ConnectionManagerTest {
    private static final String URL_KEY = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME_KEY = "egor";
    private static final String PASSWORD_KEY = "admin123";

    @Test
    void getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(URL_KEY, USERNAME_KEY, PASSWORD_KEY);

        assertEquals(connection.getSchema(), ConnectionManager.getConnection().getSchema());
        assertEquals(connection.getClientInfo(), ConnectionManager.getConnection().getClientInfo());


    }
}