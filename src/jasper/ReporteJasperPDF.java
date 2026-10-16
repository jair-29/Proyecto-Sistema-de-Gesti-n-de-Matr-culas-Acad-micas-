package jasper;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Conexion.Conexion;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteJasperPDF {
	
	public void generarReporte(String tipoReporte) {

	    String estado;
	    String rutaJasper;
	    String nombrePDF;
	    String tituloVentana;

	    // Configuración del reporte según el tipo
	    switch (tipoReporte.toUpperCase()) {

	    	case "CURSO":
	    		estado = "CURSO";
	    		rutaJasper = "/jasper/ReporteAlumPorCurso.jasper";
	    		nombrePDF = "ReporteAlumPorCurso.pdf";
	    		tituloVentana = "Reporte de cursos con alumnos matriculados";
	    		break;
	    		
	        case "REGISTRADO":
	            estado = "Registrado";
	            rutaJasper = "/jasper/ReporteAlumMatPend.jasper";
	            nombrePDF = "ReporteAlumMatPend.pdf";
	            tituloVentana = "Reporte de alumnos con matrícula pendientes";
	            break;

	        case "MATRICULADO":
	            estado = "MATRICULADO";
	            rutaJasper = "/jasper/ReporteAlumMatReg.jasper";
	            nombrePDF = "ReporteAlumMatReg.pdf";
	            tituloVentana = "Reporte de alumnos con matrícula registrada";
	            break;

	        default:
	            JOptionPane.showMessageDialog(null, "Error tipo de reporte no válido");
	            return;
	    }

	    // Ejecutar la consulta y generar reporte
	    try (Connection cnx = Conexion.conectar()) {

	        ConsultaMySQL sql = new ConsultaMySQL(cnx);

	        try (PreparedStatement ps = sql.prepararConsulta(estado);
	        		ResultSet rs = sql.ejecutarQuery(ps);
	        		InputStream logo =  getClass().getResourceAsStream("/jasper/logo_cibertec.jpeg")) {
	        	
	        	JasperPrint jprint;
	        	Map<String, Object> parametros = new HashMap<>();
	        	parametros.put("img_title", logo);
	        	
	            // Convertir ResultSet a JRResultSetDataSource
	            JRResultSetDataSource datos = new JRResultSetDataSource(rs);

	            // Cargar el reporte Jasper
	            JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource(rutaJasper));
	                        
	            // Generar el reporte
	            if (estado == "CURSO") {	            	
	            	jprint = JasperFillManager.fillReport(reporte, parametros, datos);					
				}
	            else {
	            	parametros.put("ds", datos);
	            	jprint = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource(1));	            	
				}

	            // Guardar PDF
	            guardarReportePDF(jprint, nombrePDF, tituloVentana);

	            // Mostrar reporte
	            /*JasperViewer view = new JasperViewer(jprint, false);

	            view.setTitle(tituloVentana);
	            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	            view.setVisible(true);*/
	        }

	    } catch (Exception e) {
	        e.printStackTrace();

	        JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
	    }    
	}
	
	private void guardarReportePDF(JasperPrint jprint, String nombrePDF, String tituloVentana) throws JRException {

	    JFileChooser ventanaGuardar = new JFileChooser();

	    ventanaGuardar.setDialogTitle(tituloVentana);

	    ventanaGuardar.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf"));

	    ventanaGuardar.setSelectedFile(new File(nombrePDF));

	    int resultado = ventanaGuardar.showSaveDialog(null);
	    
	    // Si no confirmó retorna para continuar con mostrar reporte desde Jasper Viewer
	    if (resultado != JFileChooser.APPROVE_OPTION) {
	        return;
	    }

	    File archivo = ventanaGuardar.getSelectedFile();

	    // Verificar extensión
	    if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
	        archivo = new File(archivo.getAbsolutePath() + ".pdf");
	    }

	    // Confirmar si el archivo ya existe
	    if (archivo.exists()) {

	        int opcion = JOptionPane.showConfirmDialog(null, "El archivo ya existe. ¿Desea reemplazarlo?", "Confirmar", JOptionPane.YES_NO_OPTION);

	        if (opcion != JOptionPane.YES_OPTION) {
	            return;
	        }
	    }

	    // Exportar el reporte
	    JasperExportManager.exportReportToPdfFile(jprint, archivo.getAbsolutePath());

	    JOptionPane.showMessageDialog(null,"PDF guardado correctamente");
	}
			
	/*ic static void main(String[] args) {
	
		ReporteJasperPDF jasper = new ReporteJasperPDF();
		jasper.generarReporte("CURSO");
	}*/

}
