package ch.ti8m.azubi.mnu.pizzashop.service;

import org.mariadb.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MDBConnectionFactory {

    public static Connection mariaDBConnection() throws SQLException{
        Driver driver = new org.mariadb.jdbc.Driver();
        DriverManager.registerDriver(driver);
        String connectionUrl = "jdbc:mariadb://localhost:3306/ti8m.mnupizzashop";
        return DriverManager.getConnection(connectionUrl,"PSWebapp","pass1234");
    }
}
