package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection Conexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            String url = "jdbc:mysql://localhost:3306/DB_Ahorcado?useSSL=false&allowPublicKeyRetrieval=true";
            String user = "quintom";
            String password = "admin";
            conexion = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException error) {
            System.out.println("Error: Driver JDBC no encontrado");
            error.printStackTrace();
        } catch (SQLException error) {
            System.out.println("Error de conexión a la BD");
            error.printStackTrace();
        }
        return conexion;
    }
}
