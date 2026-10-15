package gui;

import java.awt.Color;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.MenuPrincipal;
import objetos.Alumno;
import objetos.Curso;
import objetos.Matricula;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.Desktop;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ReportCurso extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtReporte;
	private JButton btnPDF;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ReportCurso frame = new ReportCurso();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReportCurso() {
		setTitle("Reporte: Alumnos Matriculados por Curso");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// T�tulo de la ventana
		JLabel lblTitulo = new JLabel("ALUMNOS MATRICULADOS POR CURSO");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(69, 11, 500, 30);
		contentPane.add(lblTitulo);

		// �rea de texto para mostrar el reporte
		txtReporte = new JTextArea();
		txtReporte.setForeground(new Color(0, 0, 255));
		txtReporte.setBackground(new Color(255, 255, 224));
		txtReporte.setEditable(false);
		txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));

		JScrollPane scrollPane = new JScrollPane(txtReporte);
		scrollPane.setBounds(30, 60, 455, 330);
		contentPane.add(scrollPane);
		scrollPane.setVisible(false);
		// Botón Exportar PDF
		btnPDF = new JButton("Exportar PDF");
		btnPDF.setBackground(new Color(255, 215, 0));
		btnPDF.addActionListener(this);
		btnPDF.setBounds(184, 420, 145, 30);
		contentPane.add(btnPDF);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ReportCurso.class.getResource("/imagenes/insigna.jpg")));
		lblNewLabel.setBounds(517, 112, 148, 196);
		contentPane.add(lblNewLabel);
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btnPDF) {
	        generarPDF();
	    }
	}
	private void cargarDatosMySQL() {
		txtReporte.setText("");

		String sql = "SELECT c.asignatura, a.nombres, a.apellidos " + "FROM curso c "
				+ "LEFT JOIN matricula m ON c.codCurso = m.codCurso "
				+ "LEFT JOIN alumno a ON m.codAlumno = a.codAlumno " + "ORDER BY c.asignatura";

		try (Connection cn = Conexion.conectar();
				PreparedStatement ps = cn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				txtReporte.append("Curso: " + rs.getString("asignatura") + "\n");

				if (rs.getString("nombres") != null) {
					txtReporte.append("Alumno: " + rs.getString("nombres") + " " + rs.getString("apellidos") + "\n");
				}

				txtReporte.append("------------------------------\n");
			}

		} catch (Exception e) {
			txtReporte.setText("");
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void generarPDF() {

		String sql = "SELECT a.codAlumno, a.nombres, a.apellidos, c.asignatura " + "FROM matricula m "
				+ "INNER JOIN alumno a ON m.codAlumno = a.codAlumno " + "INNER JOIN curso c ON m.codCurso = c.codCurso "
				+ "ORDER BY c.asignatura";

		try (Connection cn = Conexion.conectar();
				PreparedStatement ps = cn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				PDDocument pdf = new PDDocument()) {

			PDPage pagina = new PDPage(PDRectangle.A4);
			pdf.addPage(pagina);

			PDType0Font normal = PDType0Font.load(pdf, new File("C:/Windows/Fonts/arial.ttf"));

			PDType0Font negrita = PDType0Font.load(pdf, new File("C:/Windows/Fonts/arialbd.ttf"));

			PDPageContentStream c = new PDPageContentStream(pdf, pagina);

			// Título centrado
			String titulo = "ALUMNOS MATRICULADOS POR CURSO";
			float ancho = negrita.getStringWidth(titulo) / 1000f * 14;

			c.beginText();
			c.setFont(negrita, 14);
			c.newLineAtOffset((595 - ancho) / 2, 770);
			c.showText(titulo);
			c.endText();

			// Encabezado azul
			c.setNonStrokingColor(new Color(0, 65, 95));
			c.addRect(50, 700, 495, 30);
			c.fill();

			c.setNonStrokingColor(Color.WHITE);
			escribir(c, negrita, 10, 65, 710, "Código");
			escribir(c, negrita, 10, 170, 710, "Alumno");
			escribir(c, negrita, 10, 420, 710, "Curso");

			float y = 700;
			c.setNonStrokingColor(Color.BLACK);

			while (rs.next() && y > 60) {

				String codigo = rs.getString("codAlumno");
				String alumno = rs.getString("nombres") + " " + rs.getString("apellidos");
				String curso = rs.getString("asignatura");

				// Bordes de la fila
				c.setStrokingColor(Color.GRAY);
				c.addRect(50, y - 30, 495, 30);
				c.stroke();

				c.moveTo(140, y);
				c.lineTo(140, y - 30);
				c.moveTo(400, y);
				c.lineTo(400, y - 30);
				c.stroke();

				// Datos
				escribir(c, normal, 9, 65, y - 19, codigo);
				escribir(c, normal, 9, 150, y - 19, ajustar(alumno, 43));
				escribir(c, normal, 9, 410, y - 19, ajustar(curso, 22));

				y -= 30;
			}

			c.close();

			File archivo = new File(System.getProperty("java.io.tmpdir"),
					"Reporte_Cursos_" + System.currentTimeMillis() + ".pdf");

			pdf.save(archivo);
			mostrarPDF(archivo);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void mostrarPDF(File archivo) {
		try (PDDocument pdf = org.apache.pdfbox.Loader.loadPDF(archivo)) {

			PDFRenderer renderer = new PDFRenderer(pdf);
			BufferedImage imagen = renderer.renderImageWithDPI(0, 85);

			JFrame ventana = new JFrame("Reporte de Alumnos Matriculados");
			ventana.setSize(900, 750);
			ventana.setLocationRelativeTo(this);
			ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel(new BorderLayout());
			panel.setBackground(new Color(230, 235, 240));

			JLabel titulo = new JLabel("VISTA PREVIA DEL REPORTE", SwingConstants.CENTER);
			titulo.setFont(new Font("Arial", Font.BOLD, 18));
			titulo.setForeground(Color.WHITE);
			titulo.setOpaque(true);
			titulo.setBackground(new Color(0, 65, 95));
			titulo.setPreferredSize(new Dimension(900, 55));

			JLabel hoja = new JLabel(new ImageIcon(imagen));
			hoja.setHorizontalAlignment(SwingConstants.CENTER);

			JScrollPane scroll = new JScrollPane(hoja);
			scroll.getViewport().setBackground(new Color(230, 235, 240));

			JButton cerrar = new JButton("Cerrar");
			cerrar.addActionListener(e -> ventana.dispose());

			JPanel pie = new JPanel();
			pie.add(cerrar);

			panel.add(titulo, BorderLayout.NORTH);
			panel.add(scroll, BorderLayout.CENTER);
			panel.add(pie, BorderLayout.SOUTH);
 
			ventana.add(panel);
			ventana.setVisible(true);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

private void escribir(PDPageContentStream c, PDType0Font fuente,
        int tamaño, float x, float y, String texto) throws Exception {

    c.beginText();
    c.setFont(fuente, tamaño);
    c.newLineAtOffset(x, y);
    c.showText(texto.replace("\uFFFD", ""));
    c.endText();
}

private String ajustar(String texto, int maximo) {
    if (texto.length() > maximo) {
        return texto.substring(0, maximo - 3) + "...";
    }
    return texto;
}


}
