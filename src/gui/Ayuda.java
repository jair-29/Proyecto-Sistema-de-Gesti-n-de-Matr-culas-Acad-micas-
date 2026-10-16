
package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Ayuda extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea txtAbout;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			try {

				Ayuda frame = new Ayuda();
				frame.setVisible(true);

			} catch (Exception e) {

				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ayuda() {

		// ICONO DEL PROYECTO
		java.net.URL icono = Ayuda.class.getResource("/imagenes/ICONO.LOGO.png");

		if (icono != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(icono));
		}

		setTitle("Acerca del Proyecto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setResizable(false);
		setLocationRelativeTo(null);

		// PANEL PRINCIPAL
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 205, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// TITULO
		JLabel lblTitulo = new JLabel("INFORMACIÓN DEL PROYECTO");

		lblTitulo.setBounds(119, 14, 350, 30);
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		contentPane.add(lblTitulo);

		// AREA DE INFORMACION
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 55, 390, 291);
		contentPane.add(scrollPane);

		txtAbout = new JTextArea();

		txtAbout.setEditable(false);
		txtAbout.setLineWrap(true);
		txtAbout.setWrapStyleWord(true);

		txtAbout.setFont(new Font("Monospaced", Font.BOLD, 14));

		txtAbout.setForeground(Color.BLACK);
		txtAbout.setBackground(Color.WHITE);

		txtAbout.setMargin(new java.awt.Insets(10, 10, 10, 10));

		// INFORMACION ACTUALIZADA DEL PROYECTO
		txtAbout.setText("INTEGRANTES DEL GRUPO:\n\n" + "1. JAIR CHUQUIHUACCHA SOLANO\n\n"
				+ "2. EDWIN MIGUEL VERGARAY VILLAFANA\n\n" + "3. CARLOS ALBERTO MANUEL ARIAS RAMIREZ\n\n"
				+ "4. JOE ANDRE CASTILLO MIRAVAL\n\n" + "--------------------------------\n\n" + "PROYECTO:\n"
				+ "Sistema de Gestión de Matrículas Académicas\n\n" + "VERSIÓN DEL PROYECTO:\n" + "1.4\n\n"
				+ "SEMESTRE ACADÉMICO:\n" + "2026-II\n\n" + "CURSO:\n" + "Experiencia Formativa en Situación "
				+ "Real de Trabajo II\n\n" + "INSTITUCIÓN:\n" + "Instituto Cibertec\n\n" + "DESCRIPCIÓN:\n"
				+ "Sistema desarrollado para gestionar " + "alumnos, cursos, matrículas y retiros, "
				+ "permitiendo realizar consultas y " + "generar reportes académicos.");

		txtAbout.setCaretPosition(0);

		// CORRECCION DEL SCROLL
		scrollPane.setViewportView(txtAbout);

		// FONDO DEL PROYECTO COMPRIMIDO
		lblNewLabel = new JLabel("");

		java.net.URL fondo = Ayuda.class.getResource("/imagenes/Fondo.Proyecto.jpg");

		if (fondo != null) {
			lblNewLabel.setIcon(new ImageIcon(fondo));
		}

		lblNewLabel.setBounds(0, 0, 584, 361);
		contentPane.add(lblNewLabel);

		// LOGO DE CIBERTEC
		lblNewLabel_1 = new JLabel("");

		java.net.URL logo = Ayuda.class.getResource("/imagenes/logocibertec.jpg");

		if (logo != null) {
			lblNewLabel_1.setIcon(new ImageIcon(logo));
		}

		lblNewLabel_1.setBounds(10, 4, 60, 40);
		contentPane.add(lblNewLabel_1);

		// MANTENER EL LOGO DELANTE DEL FONDO
		contentPane.setComponentZOrder(lblNewLabel_1, 0);
	}
}
