package ch.ti8m.azubi.mnu.pizzashop.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection testConnection() throws SQLException {
        String connectionURL = "jdbc:h2:file:./db/h2-database;DB_CLOSE_ON_EXIT=FALSE";
        return DriverManager.getConnection(connectionURL);
    }
}
