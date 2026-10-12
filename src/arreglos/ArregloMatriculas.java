package arreglos;

import java.util.ArrayList;
import objetos.Matricula;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArregloMatriculas {

	// Lista de matr�culas
	private ArrayList<Matricula> matriculas;

	// Constructor
	public ArregloMatriculas() {
		matriculas = new ArrayList<>();
	}

	// Agregar nueva matr�cula
	public void adicionar(Matricula m) {
		matriculas.add(m);
	}

	public void eliminarTodos() {
		matriculas.clear();
	}

	// Obtener tama�o de la lista
	public int tamagno() {
		return matriculas.size();
	}

	// Obtener matr�cula por posici�n
	public Matricula obtener(int i) {
		return matriculas.get(i);
	}

	// Buscar matr�cula por n�mero
	public Matricula buscar(int numMatricula) {
		for (Matricula m : matriculas) {
			if (m.getNumMatricula() == numMatricula) {
				return m;
			}
		}
		return null;
	}

	// Eliminar matr�cula por n�mero
	public boolean eliminar(int numMatricula) {
		Matricula m = buscar(numMatricula);
		if (m != null) {
			matriculas.remove(m);
			return true;
		}
		return false;
	}

	// Retornar lista completa
	public ArrayList<Matricula> listar() {
		return matriculas;
	}

	// Verificar si un alumno ya est� matriculado (evita duplicados)
	public boolean alumnoYaMatriculado(int codAlumno) {
		for (Matricula m : matriculas) {
			if (m.getCodAlumno() == codAlumno) {
				return true;
			}
		}
		return false;
	}

	// Verifica si existe alguna matr�cula para un curso espec�fico
	public boolean existeMatriculaConCurso(int codCurso) {
		for (int i = 0; i < matriculas.size(); i++) {
			if (matriculas.get(i).getCodCurso() == codCurso) {
				return true;
			}
		}
		return false;
	}

	// GENERA UN CODIGO CORRELATIVO
	public int codigoCorrelativo() {
		if (tamagno() == 0) {
			return 1001;
		} else {
			return obtener(tamagno() - 1).getNumMatricula() + 1;
		}
	}

	public boolean adicionarMatricula(int codAlumno, int codCurso, String fecha, String hora) {
		// Verificaci�n: �el alumno ya est� matriculado en otro curso?
		if (alumnoYaMatriculado(codAlumno)) {
			return false;
		}
		// Crear matr�cula
		Matricula nueva = new Matricula(codigoCorrelativo(), codAlumno, codCurso, fecha, hora);
		adicionar(nueva);
		return true;
	}

	// Verifica si un alumno ya est� registrado en un curso espec�fico
	public boolean alumnoYaMatriculadoEnCurso(int codAlumno, int codCurso) {
		for (int i = 0; i < matriculas.size(); i++) {
			Matricula m = matriculas.get(i);
			if (m.getCodAlumno() == codAlumno && m.getCodCurso() == codCurso) {
				return true;
			}
		}
		return false;
	}

	public void cargarMatriculas() {
		eliminarTodos();
		adicionar(new Matricula(3001, 20250001, 1252, "24/06/2025", "08:30"));
		adicionar(new Matricula(3002, 20250002, 1457, "24/06/2025", "08:30"));
		adicionar(new Matricula(3003, 20250003, 2002, "24/06/2025", "09:30"));
		adicionar(new Matricula(3004, 20250004, 2564, "24/06/2025", "09:30"));
		adicionar(new Matricula(3005, 20250005, 2105, "24/07/2025", "10:30"));
		adicionar(new Matricula(3006, 20250006, 8524, "24/07/2025", "10:00"));
		adicionar(new Matricula(3007, 20250007, 8565, "24/07/2025", "10:20"));
	}

	public void cargardesdeBD() {

		eliminarTodos();
		String sql = "SELECT * FROM matricula";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String fecha = rs.getDate("fecha").toString();
				String hora = rs.getTime("hora").toString();

				Matricula m = new Matricula(rs.getInt("numMatricula"), rs.getInt("codAlumno"), rs.getInt("codCurso"),
						fecha, hora);
				adicionar(m);

			}
			rs.close();
			ps.close();
			cn.close();
			System.out.println("Matriculas cargadas desde MySQL");
		} catch (Exception e) {
			System.out.println("Error al cargar matriculas:" + e.getMessage());
		}

	}

	public boolean adicionarBD(Matricula m) {
		String sql = "INSERT INTO matricula (numMatricula,codAlumno,codCurso,fecha,hora)VALUES(?,?,?,?,?)";

		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);

			ps.setInt(1, m.getNumMatricula());
			ps.setInt(2, m.getCodAlumno());
			ps.setInt(3, m.getCodCurso());
			java.util.Date fechaConvertida = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(m.getFecha());
			ps.setDate(4, new java.sql.Date(fechaConvertida.getTime()));
			ps.setTime(5, java.sql.Time.valueOf(m.getHora() + ":00"));
			ps.executeUpdate();
			ps.close();
			cn.close();
			System.out.println("Matricula guardad en Mysql");
			return true;

		} catch (Exception e) {
			System.out.println("error al guardar matricula:" + e.getMessage());
			return false;

		}

	}

	public boolean modificarBD(Matricula m) {
		String sql = "UPDATE matricula SET codAlumno=?,codCurso=?,fecha=?,hora=? WHERE numMatricula=?";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, m.getCodAlumno());
			ps.setInt(2, m.getCodCurso());

			String fecha = m.getFecha();
			java.sql.Date fechaSQL;

			if (fecha.contains("-")) {
				fechaSQL = java.sql.Date.valueOf(fecha);

			} else {
				java.util.Date fechaConvertida = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(fecha);
				fechaSQL = new java.sql.Date(fechaConvertida.getTime());
			}
			ps.setDate(3, fechaSQL);
			String hora = m.getHora();
			if (hora.length() == 5) {
				hora += ":00";
			}
			ps.setTime(4, java.sql.Time.valueOf(hora));
			ps.setInt(5, m.getNumMatricula());
			int filas = ps.executeUpdate();
			ps.close();
			cn.close();

			if (filas > 0) {
				System.out.println("Matricula modificada en MySQL");
				return true;
			}
			System.out.println("No se  encontro la matricula para modificar");
			return false;

		} catch (Exception e) {
			System.out.println("ERROR AL MODIFICAR:" + e.getMessage());
			return false;
		}

	}

	public boolean eliminarBD(int numMatricula) {
		String sql = "DELETE FROM matricula WHERE numMatricula=?";
		try {
			Connection cn = Conexion.conectar();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, numMatricula);
			int filas = ps.executeUpdate();
			ps.close();
			cn.close();
			if (filas > 0) {
				System.out.println("Matricula eliminada desde MySQL");
				return true;

			}
			System.out.println("No se encontro  la matricula para eliminar");
			return false;
		} catch (Exception e) {
			System.out.println("Error al encontrar matricula:" + e.getMessage());
			return false;
		}
	}

}
