package gui;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class Ayuda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea txtAbout;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

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
		setTitle("Acerca del Proyecto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 205, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Título
		JLabel lblTitulo = new JLabel("INFORMACIÓN DEL PROYECTO");
		lblTitulo.setBackground(new Color(0, 100, 0));
		lblTitulo.setBounds(119, 4, 310, 30);
		lblTitulo.setForeground(new Color(47, 79, 79));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		contentPane.add(lblTitulo);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 55, 390, 291);
		contentPane.add(scrollPane);
		
		txtAbout = new JTextArea();
		txtAbout.setEditable(false);
		txtAbout.setLineWrap(true);
		txtAbout.setFont(new Font("Monospaced", Font.BOLD, 14));
		txtAbout.setForeground(new Color(0, 0, 0));
		txtAbout.setBackground(new Color(102, 205, 170));
		txtAbout.setText("Integrantes del Grupo:\r\n1. YANCARLOS CALDERON ESPINOLA\r\n2. EDWIN MIGUEL VERGARAY VILLAFANA\r\n3. JESENIA CATHERINE COTRINA GUTIERREZ\r\n4. YADHIRA SALOME HAYASHI AMORETTI\r\n\r\nEmpresa Desarrolladora: CODEV STUDIO\r\nVersi\u00F3n del Proyecto: 1.0\r\nSemestre Acad\u00E9mico: 2025-II\r\n\r\nEste sistema fue desarrollado como parte\r\ndel curso Algoritmos y Estructura de Datos\r\ndel instituto Cibertec.");
		scrollPane.setViewportView(txtAbout);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Ayuda.class.getResource("/imagenes/logoAyuda.jpg")));
		lblNewLabel.setBounds(446, 114, 101, 150);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Ayuda.class.getResource("/imagenes/logocibertec.jpg")));
		lblNewLabel_1.setBounds(10, 4, 60, 40);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Ayuda.class.getResource("/imagenes/insignia_mini.jpg")));
		lblNewLabel_2.setBounds(487, 11, 60, 50);
		contentPane.add(lblNewLabel_2);
	}
}