package arreglos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import Conexion.Conexion;
import java.util.ArrayList;

import objetos.Retiro;

public class ArregloRetiros {

    // Lista de retiros
    private ArrayList<Retiro> retiros;	

    // Constructor
    public ArregloRetiros() {
    	retiros = new ArrayList<>();
    }

    // Agrega un nuevo retiro a la lista
    public void adicionar(Retiro r) {
    	retiros.add(r);
    }

    public void eliminarTodos() {
        retiros.clear();
    }
    
    // Devuelve la cantidad de retiros
    public int tamagno() {
        return retiros.size();
    }

    // Retorna un retiro por �ndice
    public Retiro obtener(int i) {
        return retiros.get(i);
    }
    
    //GENERA UN CODIGO CORRELATIVO
    public int codigoCorrelativo() {
        if (tamagno() == 0) {
            return 4001;
        } else {
            return obtener(tamagno() - 1).getNumRetiro() + 1;
        }
    }

    // Buscar un retiro por n�mero de retiro
    public Retiro buscar(int numRetiro) {
        for (Retiro r : retiros) {
            if (r.getNumRetiro() == numRetiro) {
                return r;
            }
        }
        return null;
    }

    // Elimina un retiro si existe
    public boolean eliminar(int numRetiro) {
        Retiro r = buscar(numRetiro);
        if (r != null) {
        	retiros.remove(r);
            return true;
        }
        return false;
    }

    // Devuelve toda la lista de retiros
    public ArrayList<Retiro> listar() {
        return retiros;
    }

    // Verifica si una matr�cula ya tiene un retiro asociado
    public boolean matriculaYaRetirada(int numMatricula) {
        for (Retiro r : retiros) {
            if (r.getNumMatricula() == numMatricula) {
                return true;
            }
        }
        return false;
    }
    
    public void cargarRetiros() {
	    eliminarTodos();
    	adicionar(new Retiro(codigoCorrelativo(), 3001, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3002, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3003, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3004, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3045, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3046, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3047, "24/06/2025", "08:30"));
    	adicionar(new Retiro(codigoCorrelativo(), 3048, "24/06/2025", "08:30"));
    }
    
    public void cargardesdeBD() {
    	eliminarTodos();
    	String sql ="SELECT * FROM retiro";
    	try {
    		Connection cn=Conexion.conectar();
    		PreparedStatement ps=cn.prepareStatement(sql);
    		ResultSet rs=ps.executeQuery();
    		while (rs.next()) {
    			String fecha= rs.getDate("fecha").toString();
    			String hora=rs.getTime("hora").toString();
    			Retiro r=new Retiro(
    					rs.getInt("idRetiro"),
    					rs.getInt("numMatricula"),
    					fecha,
    					hora
    					);
    			
    			adicionar(r);
    		}
    		rs.close();
    		ps.close();
    		cn.close();
    		System.out.println("Retiros cargados desde MySQL.");
    	}catch (Exception e) {
    		System.out.println("Errir al cargar retiros:"+e.getMessage());
    	}
    }
    
    public boolean adicionarBD(Retiro r) {
        String sql = "INSERT INTO retiro (idRetiro, numMatricula, fecha, hora) VALUES (?, ?, ?, ?)";

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, r.getNumRetiro());
            ps.setInt(2, r.getNumMatricula());

            java.util.Date fechaConvertida =
                new SimpleDateFormat("dd/MM/yyyy").parse(r.getFecha());

            ps.setDate(3, new java.sql.Date(fechaConvertida.getTime()));

            String hora = r.getHora();
            if (hora.length() == 5) {
                hora += ":00";
            }

            ps.setTime(4, java.sql.Time.valueOf(hora));

            int filas = ps.executeUpdate();

            ps.close();
            cn.close();

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al guardar retiro: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarBD(int numRetiro) {
        String sql = "DELETE FROM retiro WHERE idRetiro = ?";

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, numRetiro);

            int filas = ps.executeUpdate();

            ps.close();
            cn.close();

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar retiro: " + e.getMessage());
            return false;
        }
    }
    
    
}
