package arreglos;

import java.util.ArrayList;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import objetos.Curso;

public class ArregloCursos {

	// LISTA DE CURSOS
	private ArrayList<Curso> cursos;

	// Constructor: inicializa la lista
	public ArregloCursos() {
		cursos = new ArrayList<>();
	}

	// M�todo para retornar la cantidad total de CURSOS registrados
	public int tamagno() {
		return cursos.size();
	}

	// M�todo para obtener un CURSO por posici�n en la lista
	public Curso obtener(int i) {
		return cursos.get(i);
	}

	// M�todo para agregar un nuevo CURSO a la lista
	public void adicionar(Curso c) {
		cursos.add(c);
		ordenarPorCodigo();
	}

	// M�todo para eliminar un CURSO
	public void eliminar(Curso c) {
		cursos.remove(c);
	}

	public void eliminarTodo() {
		cursos.clear();
	}

	// METODO PARA BUSCAR CURSO POR SU CODIGO
	public Curso buscarPorCodigo(int codigo) {
		for (int i = 0; i < tamagno(); i++) {
			if (obtener(i).getCodCurso() == codigo) {
				return obtener(i);
			}
		}
		return null;
	}

	public Curso buscarPorNombre(String nombre) {
		for (int i = 0; i < tamagno(); i++) {
			Curso c = obtener(i);
			if (c.getAsignatura().equalsIgnoreCase(nombre)) {
				return c;
			}
		}
		return null;
	}

	public int generarCodigo(int min, int max) {
		return (int) ((max - min + 1) * Math.random()) + min;
	}

	private void ordenarPorCodigo() {
		for (int i = 0; i < cursos.size() - 1; i++) {
			for (int j = i + 1; j < cursos.size(); j++) {
				if (cursos.get(i).getCodCurso() > cursos.get(j).getCodCurso()) {
					// Intercambio de elementos
					Curso temp = cursos.get(i);
					cursos.set(i, cursos.get(j));
					cursos.set(j, temp);
				}
			}
		}
	}

	public void cargarCursos() {
		eliminarTodo();
		adicionar(new Curso(2564, "Matem�tica B�sica", 1, 4, 64));
		adicionar(new Curso(2002, "F�sica I", 1, 4, 64));
		adicionar(new Curso(4525, "Programaci�n I", 1, 5, 80));
		adicionar(new Curso(4574, "Base de Datos", 2, 4, 64));
		adicionar(new Curso(4521, "�lgebra Lineal", 1, 3, 48));
		adicionar(new Curso(1252, "Contabilidad I", 2, 3, 48));
		adicionar(new Curso(1457, "Ingl�s B�sico", 1, 2, 32));
		adicionar(new Curso(8565, "Estad�stica", 3, 4, 64));
		adicionar(new Curso(8524, "Literatura", 2, 3, 48));
		adicionar(new Curso(4854, "Filosof�a", 3, 2, 32));
	}

	public void cargardesdeBD() {

		eliminarTodo();
		String sql = "SELECT * FROM curso";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Curso c = new Curso(rs.getInt("codCurso"), rs.getString("asignatura"), rs.getInt("ciclo"),
						rs.getInt("creditos"), rs.getInt("horas")

				);

				adicionar(c);
			}

			rs.close();
			ps.close();
			cn.close();

			System.out.println("Cursos cargados desde MySQL");
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}

	public boolean adicionarBD(Curso c) {
		String sql = "INSERT INTO curso" + "(codCurso,asignatura,ciclo,creditos,horas)" + "VALUES(?,?,?,?,?)";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);

			ps.setInt(1, c.getCodCurso());
			ps.setString(2, c.getAsignatura());
			ps.setInt(3, c.getCiclo());
			ps.setInt(4, c.getCreditos());
			ps.setInt(5, c.getHoras());
			ps.executeUpdate();
			ps.close();
			cn.close();
			System.out.println("Curso guardado en MySQL");
			return true;
		} catch (Exception e) {
			System.out.println("Error al guardar el curso:" + e.getMessage());
			return false;
		}

	}

	public Curso buscarPorCodigoBD(int codigo) {
		String sql = "SELECT * FROM curso WHERE codCurso=?";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Curso c = new Curso(rs.getInt("codCurso"), rs.getString("asignatura"), rs.getInt("ciclo"),
						rs.getInt("creditos"), rs.getInt("horas")

				);
				rs.close();
				ps.close();
				cn.close();
				return c;

			}

			rs.close();
			ps.close();
			cn.close();
		} catch (Exception e) {
			System.out.println("Error al buscar curso:" + e.getMessage());
		}
		return null;

	}

	public boolean modificarDB(Curso c) {
		String sql = "UPDATE  curso SET asignatura=?,ciclo=?,creditos=?,horas=? WHERE codCurso=?";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, c.getAsignatura());
			ps.setInt(2, c.getCiclo());
			ps.setInt(3, c.getCreditos());
			ps.setInt(4, c.getHoras());
			ps.setInt(5, c.getCodCurso());
			ps.executeUpdate();
			ps.close();
			cn.close();

			System.out.println("Curso modificado en MySQL");
			return true;

		} catch (Exception e) {
			System.out.println("Error al  modificar curso:" + e.getMessage());
			return false;
		}
	}

	public boolean eliminarBD(int codigo) {
		String sql = "DELETE  FROM  curso WHERE codCurso=?";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ps.executeUpdate();
			ps.close();
			cn.close();
			System.out.println("Curso eliminado en Mysql");
			return true;
		} catch (Exception e) {
			System.out.println("Error al eliminar curso:" + e.getMessage());
			return false;

		}
	}

	public boolean eliminarTodosBD() {

		String sql = "DELETE FROM curso";

		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);

			ps.executeUpdate();

			ps.close();
			cn.close();

			System.out.println("Todos los cursos fueron eliminados de MySQL.");
			return true;

		} catch (Exception e) {
			System.out.println("Error al eliminar todos los cursos: " + e.getMessage());
			return false;
		}
	}

}