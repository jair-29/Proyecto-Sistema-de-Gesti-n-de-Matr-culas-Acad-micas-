package gui;

import java.awt.Color;
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
	private JButton btnLimpiar;
	private JButton btnCerrar;
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
		setTitle("Reporte: Alumnos con Matrícula Vigente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título
		JLabel lblTitulo = new JLabel("ALUMNOS CON MATRÍCULA VIGENTE");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(62, 19, 500, 30);
		contentPane.add(lblTitulo);

		// Área de reporte
		txtReporte = new JTextArea();
		txtReporte.setForeground(new Color(0, 0, 255));
		txtReporte.setBackground(new Color(255, 255, 224));
		txtReporte.setEditable(false);
		txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(txtReporte);
		scrollPane.setBounds(30, 60, 472, 330);
		contentPane.add(scrollPane);

		// Botón: Mostrar
		btnMostrar = new JButton("Mostrar");
		btnMostrar.setBackground(new Color(102, 205, 170));
		btnMostrar.addActionListener(this);
		btnMostrar.setBounds(40, 410, 120, 30);
		contentPane.add(btnMostrar);

		// Botón: Limpiar
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(102, 205, 170));
		btnLimpiar.addActionListener(this);
		btnLimpiar.setBounds(210, 410, 120, 30);
		contentPane.add(btnLimpiar);

		// Botón: Cerrar
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(250, 128, 114));
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(380, 410, 120, 30);
		contentPane.add(btnCerrar);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ReportVigentes.class.getResource("/imagenes/insigna.jpg")));
		lblNewLabel.setBounds(538, 132, 146, 194);
		contentPane.add(lblNewLabel);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
		if (e.getSource() == btnLimpiar) {
			actionPerformedBtnLimpiar(e);
		}
		if (e.getSource() == btnMostrar) {
			actionPerformedBtnMostrar(e);
		}
	}
	protected void actionPerformedBtnMostrar(ActionEvent e) {
	    txtReporte.setText(""); // Limpiar el área antes de mostrar

	    for (int i = 0; i < MenuPrincipal.alumnos.tamagno(); i++) {
	        var a = MenuPrincipal.alumnos.obtener(i);
	        if (a.getEstado() == 1) { // Solo los matriculados
	            txtReporte.append("Código     : " + a.getCodAlumno() + "\n");
	            txtReporte.append("Nombres    : " + a.getNombres() + "\n");
	            txtReporte.append("Apellidos  : " + a.getApellidos() + "\n");
	            txtReporte.append("DNI        : " + a.getDni() + "\n");
	            txtReporte.append("Edad       : " + a.getEdad() + "\n");
	            txtReporte.append("Celular    : " + a.getCelular() + "\n");
	            txtReporte.append("Estado     : MATRICULADO\n");
	            txtReporte.append("------------------------------------------------\n");
	        }
	    }

	    if (txtReporte.getText().isEmpty()) {
	        txtReporte.setText("No hay alumnos matriculados actualmente.");
	    }
	}
	protected void actionPerformedBtnLimpiar(ActionEvent e) {
		 txtReporte.setText("");
	}
	protected void actionPerformedBtnCerrar(ActionEvent e) {
	    dispose();
	}
}