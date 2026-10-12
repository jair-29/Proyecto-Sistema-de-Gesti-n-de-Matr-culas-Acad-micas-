import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import objetos.Alumno;
import Conexion.Conexion;
import arreglos.ArregloAlumnos;

public class PruebaConexion {

    public static void main(String[] args) {

        Connection cn = Conexion.conectar();

        if (cn != null) {

            try {

                String sql = "SELECT * FROM alumno";

                Statement st = cn.createStatement();

                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {

                    System.out.println("Código: " + rs.getInt("codAlumno"));
                    System.out.println("Nombres: " + rs.getString("nombres"));
                    System.out.println("Apellidos: " + rs.getString("apellidos"));
                    System.out.println("DNI: " + rs.getString("dni"));
                    System.out.println("Edad: " + rs.getInt("edad"));
                    System.out.println("Celular: " + rs.getString("celular"));
                    System.out.println("Estado: " + rs.getString("estado"));

                    System.out.println("-------------------------");
                }

                rs.close();
                st.close();
                cn.close();
                
                ArregloAlumnos lista=new ArregloAlumnos();
                lista.cargarDesdeBD();
                System.out.println("cantidad de alumnos"+lista.tamagno());
                Alumno prueba = new Alumno(
                	    20250002,
                	    "Pedro",
                	    "Prueba",
                	    "99999999",
                	    20,
                	    999999999,
                	    0
                	);

                	lista.adicionarBD(prueba);  

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}