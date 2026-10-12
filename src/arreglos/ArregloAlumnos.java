package arreglos;

import java.util.ArrayList;
import objetos.Alumno;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArregloAlumnos {

	// lista de alumnos
	private ArrayList<Alumno> alumnos;

	// Constructor: inicializa la lista
	public ArregloAlumnos() {
		alumnos = new ArrayList<>();
	}

	// Método para retornar la cantidad total de alumnos registrados
	public int tamagno() {
		return alumnos.size();
	}

	// Método para obtener un alumno por posición en la lista
	public Alumno obtener(int i) {
		return alumnos.get(i);
	}

	// Método para agregar un nuevo alumno a la lista
	public void adicionar(Alumno a) {
		alumnos.add(a);
	}

	public void eliminarTodo() {
		alumnos.clear();
	}

	// Método para buscar un alumno por su código
	public Alumno buscarPorCodigo(int codigo) {
		for (int i = 0; i < tamagno(); i++) {
			if (obtener(i).getCodAlumno() == codigo)
				return obtener(i);
		}
		return null;
	}

	public Alumno buscarPorDni(String dni) {
		for (int i = 0; i < tamagno(); i++) {
			if (obtener(i).getDni().equals(dni))
				return obtener(i);
		}
		return null;
	}

	// Método para eliminar un alumno
	public void eliminar(Alumno a) {
		alumnos.remove(a);
	}

	public int codigoCorrelativo() {
		if (tamagno() == 0)
			return 20250001;
		else
			return obtener(tamagno() - 1).getCodAlumno() + 1;
	}

	// Metodo para cargar 10 alumnos de demostracion
	public void cargarAlumnos() {
		eliminarTodo(); // Limpiar antes para evitar duplicados
		adicionar(new Alumno(codigoCorrelativo(), "Juan", "Perez Ramirez", "72145893", 19, 987654321, 2));
		adicionar(new Alumno(codigoCorrelativo(), "Maria", "Gomez Torres", "74517629", 18, 987123456, 2));
		adicionar(new Alumno(codigoCorrelativo(), "Pedro", "Lopez Sanchez", "79385241", 21, 987987654, 2));
		adicionar(new Alumno(codigoCorrelativo(), "Lucia", "Ramirez Alvarez", "47893412", 22, 987456123, 2));
		adicionar(new Alumno(codigoCorrelativo(), "Carlos", "Torres Rivas", "46731258", 21, 987321654, 0));
		adicionar(new Alumno(codigoCorrelativo(), "Ana", "Martinez Diaz", "41598472", 19, 987741258, 0));
		adicionar(new Alumno(codigoCorrelativo(), "Diego", "Ruiz Ortega", "79632541", 25, 987852147, 0));
		adicionar(new Alumno(codigoCorrelativo(), "Sofia", "Garcia Paredes", "74128596", 24, 987963258, 0));
		adicionar(new Alumno(codigoCorrelativo(), "Luis", "Santos Guzman", "43467981", 25, 987159357, 2));
		adicionar(new Alumno(codigoCorrelativo(), "Elena", "Fernandez Cruz", "42835614", 23, 987753951, 1));
	}

	public void cargarDesdeBD() {
		eliminarTodo();
		String sql = "select * from alumno";

		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Alumno a = new Alumno(rs.getInt("codAlumno"), rs.getString("nombres"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getInt("edad"), rs.getInt("celular"),
						ObtenerEstado(rs.getString("estado"))

				);

				adicionar(a);
			}

			rs.close();
			ps.close();
			cn.close();

			System.out.println("Alumnos cargados desde MySQL.");

		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}

	}

	private int ObtenerEstado(String estado) {
		if (estado.equalsIgnoreCase("Activo"))
			return 0;
		if (estado.equalsIgnoreCase("Matriculado"))
			return 1;
		if (estado.equalsIgnoreCase("Retirado"))
			return 2;
		return 0;
	}

	private String ObtenerTexto(int estado) {
		if (estado == 0)
			return "Activo";
		if (estado == 1)
			return "Matriculado";
		if (estado == 2)
			return "Retirado";
		return "Activo";
	}

	public boolean adicionarBD(Alumno a) {
		String sql = "INSERT  INTO alumno" + "(codAlumno,nombres,apellidos,dni,edad,celular,estado)"
				+ "VALUES(?,?,?,?,?,?,?)";

		try {
			Connection cn = Conexion.conectar();

			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, a.getCodAlumno());
			ps.setString(2, a.getNombres());
			ps.setString(3, a.getApellidos());
			ps.setString(4, a.getDni());
			ps.setInt(5, a.getEdad());
			ps.setInt(6, a.getCelular());
			ps.setString(7, ObtenerTexto(a.getEstado()));
			ps.execute();
			ps.close();
			cn.close();
			System.out.println("Alumno guardado en Mysql.");
			return true;
		} catch (Exception e) {
			System.out.println("Error al guardar  alumno" + e.getMessage());
			return false;
		}

	}

	public Alumno buscarPorCodigoBD(int codigo) {
		String sql = "SELECT * FROM alumno where codAlumno=?";

		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Alumno a = new Alumno(rs.getInt("codAlumno"), rs.getString("nombres"), rs.getString("apellidos"),
						rs.getString("dni"), rs.getInt("edad"), rs.getInt("celular"),
						ObtenerEstado(rs.getString("estado")));

				rs.close();
				ps.close();
				cn.close();

				return a;

			}

			rs.close();
			ps.close();
			cn.close();

		} catch (Exception e) {

			System.out.println("Error al buscar alumno" + e.getMessage());
		}

		return null;

	}

	public boolean modificarBD(Alumno a) {
		String sql = "UPDATE alumno SET nombres=?, apellidos=?, dni=?, edad=?, celular=?, estado=? WHERE codAlumno=?";

		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);

			ps.setString(1, a.getNombres());
			ps.setString(2, a.getApellidos());
			ps.setString(3, a.getDni());
			ps.setInt(4, a.getEdad());
			ps.setInt(5, a.getCelular());
			ps.setString(6, ObtenerTexto(a.getEstado()));
			ps.setInt(7, a.getCodAlumno());

			ps.executeUpdate();
			ps.close();
			cn.close();
			System.out.println("Alumno modificado en MySQL");

			return true;
		} catch (Exception e) {
			System.out.println("Error al modificar alumno" + e.getMessage());
			return false;
		}

	}

	public boolean eliminarBD(int codigo) {
		String sql = "DELETE FROM ALUMNO WHERE codAlumno=?";

		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);

			ps.setInt(1, codigo);
			ps.executeUpdate();
			ps.close();
			cn.close();
			System.out.println("Alumno eliminado en MySQL");
			return true;

		} catch (Exception e) {
			System.out.println("Error al eliminar alumno" + e.getMessage());
			return false;
		}

	}
	public boolean eliminarTodosBD() {

	    String sql = "DELETE FROM alumno";

	    try {
	        Connection cn = Conexion.conectar();
	        PreparedStatement ps = cn.prepareStatement(sql);

	        ps.executeUpdate();

	        ps.close();
	        cn.close();

	        System.out.println("Todos los alumnos fueron eliminados de MySQL.");
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error al eliminar todos los alumnos: " + e.getMessage());
	        return false;
	    }
	}

}



