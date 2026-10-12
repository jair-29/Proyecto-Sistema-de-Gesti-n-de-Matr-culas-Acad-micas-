package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/Sistema_Gestión_Matrículas_Académicas";

    private static final String USER = "root";

    private static final String PASSWORD = "Gmnhqy4cpc";

    public static Connection conectar() {
        Connection cn = null;

        try {
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a MySQL");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return cn;
    }
}