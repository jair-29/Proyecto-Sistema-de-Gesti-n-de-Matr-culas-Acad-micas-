package gui;

import java.awt.Color;
import Conexion.Conexion;
import java.sql.*;
import java.io.File;
import java.awt.image.BufferedImage;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import app.MenuPrincipal;

public class ReportVigentes extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtReporte;
	private JButton btnMostrar; 
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ReportVigentes frame = new ReportVigentes();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReportVigentes() {
		setTitle("Reporte: Alumnos con Matr�cula Vigente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// T�tulo
		JLabel lblTitulo = new JLabel("ALUMNOS CON MATR�CULA VIGENTE");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(62, 19, 500, 30);
		contentPane.add(lblTitulo);

		// �rea de reporte
		txtReporte = new JTextArea();
		txtReporte.setForeground(new Color(0, 0, 255));
		txtReporte.setBackground(new Color(255, 255, 224));
		txtReporte.setEditable(false);
		txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(txtReporte);
		scrollPane.setBounds(30, 60, 472, 330);
		contentPane.add(scrollPane);

		// Bot�n: Mostrar
		btnMostrar = new JButton("Mostrar");
		btnMostrar.setBackground(new Color(102, 205, 170));
		btnMostrar.addActionListener(this);
		btnMostrar.setBounds(204, 401, 120, 30);
		contentPane.add(btnMostrar);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ReportVigentes.class.getResource("/imagenes/insigna.jpg")));
		lblNewLabel.setBounds(538, 132, 146, 194);
		contentPane.add(lblNewLabel);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnMostrar) {
		 generarPDF(); 
		}
	}


private void generarPDF() {

    String sql = "SELECT codAlumno, nombres, apellidos, dni, estado "
            + "FROM alumno WHERE estado = 'Matriculado' "
            + "ORDER BY apellidos";

    File archivo = new File(System.getProperty("java.io.tmpdir"),
            "Reporte_Vigentes_" + System.currentTimeMillis() + ".pdf");

    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();
         PDDocument pdf = new PDDocument()) {

        PDPage pagina = new PDPage(PDRectangle.A4);
        pdf.addPage(pagina);

        PDType0Font normal = PDType0Font.load(pdf,
                new File("C:/Windows/Fonts/arial.ttf"));
        PDType0Font negrita = PDType0Font.load(pdf,
                new File("C:/Windows/Fonts/arialbd.ttf"));

        PDPageContentStream c = new PDPageContentStream(pdf, pagina);

        String titulo = "ALUMNOS CON MATRÍCULA VIGENTE";
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
        escribir(c, negrita, 10, 60, 710, "Código");
        escribir(c, negrita, 10, 140, 710, "Alumno");
        escribir(c, negrita, 10, 370, 710, "DNI");
        escribir(c, negrita, 10, 455, 710, "Estado");

        float y = 700;
        c.setNonStrokingColor(Color.BLACK);

        while (rs.next() && y > 60) {

            String alumno = rs.getString("nombres") + " "
                    + rs.getString("apellidos");

            c.setStrokingColor(Color.GRAY);
            c.addRect(50, y - 30, 495, 30);
            c.stroke();

            for (int x : new int[]{130, 360, 445}) {
                c.moveTo(x, y);
                c.lineTo(x, y - 30);
            }
            c.stroke(); 

            escribir(c, normal, 9, 60, y - 19,
                    rs.getString("codAlumno"));
            escribir(c, normal, 9, 140, y - 19,
                    ajustar(alumno, 35));
            escribir(c, normal, 9, 370, y - 19,
                    rs.getString("dni"));
            escribir(c, normal, 9, 455, y - 19,
                    rs.getString("estado"));

            y -= 30;
        }

        c.close();
        pdf.save(archivo);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        return;
    }

    mostrarPDF(archivo);
    
}

private void escribir(PDPageContentStream c, PDType0Font fuente,
        int tamaño, float x, float y, String texto) throws Exception {

    c.beginText();
    c.setFont(fuente, tamaño);
    c.newLineAtOffset(x, y);
    c.showText(texto == null ? "" : texto.replace("\uFFFD", ""));
    c.endText();
}

private String ajustar(String texto, int maximo) {
    if (texto == null) return "";
    return texto.length() > maximo
            ? texto.substring(0, maximo - 3) + "..."
            : texto;
}


private void mostrarPDF(File archivo) {
    try (PDDocument pdf = Loader.loadPDF(archivo)) {

        PDFRenderer renderer = new PDFRenderer(pdf);
        BufferedImage imagen = renderer.renderImageWithDPI(0, 85);

        JFrame ventana = new JFrame("Alumnos con Matrícula Vigente");
        ventana.add(new JScrollPane(new JLabel(new ImageIcon(imagen))));
        ventana.setSize(900, 750);
        ventana.setLocationRelativeTo(this);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

}