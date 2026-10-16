package jasper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaMySQL {
	
	private final Connection conexion;
	
	public ConsultaMySQL(Connection conexion) {
		this.conexion = conexion;
	}
	
	public PreparedStatement prepararConsulta(String estado) throws SQLException{
		
		String sql;
		PreparedStatement ps;
		
		if (estado == "CURSO") {
			
			sql = "SELECT C.codCurso , C.asignatura , A.codAlumno , A.nombres , A.apellidos , A.dni "
					+ "FROM curso C "
					+ "LEFT JOIN matricula M ON C.codCurso = M.codCurso "
					+ "LEFT JOIN alumno A ON M.codAlumno = A.codAlumno "
					+ "ORDER BY C.codCurso, A.codAlumno";
			
			ps = conexion.prepareStatement(sql);
		}
		else {
			
			sql = "SELECT codAlumno AS codigo , CONCAT(nombres, ' ', apellidos) AS alumno , dni , edad , celular , estado "
					+ "FROM alumno "
					+ "WHERE estado = ? "
					+ "ORDER BY codAlumno";
			
			ps = conexion.prepareStatement(sql);			
			ps.setString(1, estado);
		}		
		
		return ps;
	}		
	
	public ResultSet ejecutarQuery(PreparedStatement ps) throws SQLException {
		return ps.executeQuery();
	}
}
