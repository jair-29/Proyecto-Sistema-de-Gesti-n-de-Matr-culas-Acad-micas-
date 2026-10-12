package gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.MenuPrincipal;
import objetos.Alumno;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReportPendientes extends JFrame implements ActionListener {
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
				ReportPendientes frame = new ReportPendientes();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReportPendientes() {
		setTitle("Reporte: Alumnos con Matrícula Pendiente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título
		JLabel lblTitulo = new JLabel("ALUMNOS CON MATRÍCULA PENDIENTE");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(54, 11, 500, 30);
		contentPane.add(lblTitulo);

		// Área de reporte
		txtReporte = new JTextArea();
		txtReporte.setForeground(new Color(0, 0, 255));
		txtReporte.setBackground(new Color(255, 255, 224));
		txtReporte.setEditable(false);
		txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(txtReporte);
		scrollPane.setBounds(30, 60, 460, 330);
		contentPane.add(scrollPane);

		// Botón: Mostrar
		btnMostrar = new JButton("Mostrar");
		btnMostrar.setBackground(new Color(102, 205, 170));
		btnMostrar.addActionListener(this);
		btnMostrar.setBounds(30, 408, 120, 30);
		contentPane.add(btnMostrar);

		// Botón: Limpiar
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(102, 205, 170));
		btnLimpiar.addActionListener(this);
		btnLimpiar.setBounds(200, 408, 120, 30);
		contentPane.add(btnLimpiar);

		// Botón: Cerrar
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(250, 128, 114));
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(370, 408, 120, 30);
		contentPane.add(btnCerrar);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ReportPendientes.class.getResource("/imagenes/insigna.jpg")));
		lblNewLabel.setBounds(526, 111, 145, 198);
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
	    StringBuilder sb = new StringBuilder();
	    sb.append("ALUMNOS CON MATRÍCULA PENDIENTE\n\n");
	    boolean hayPendientes = false;
	    for (int i = 0; i < MenuPrincipal.alumnos.tamagno(); i++) {
	        Alumno a = MenuPrincipal.alumnos.obtener(i);
	        if (a.getEstado() == 0) {
	            hayPendientes = true;
	            sb.append("Código: ").append(a.getCodAlumno()).append("\n");
	            sb.append("Nombre: ").append(a.getNombres()).append(" ").append(a.getApellidos()).append("\n");
	            sb.append("DNI: ").append(a.getDni()).append("\n");
	            sb.append("Edad: ").append(a.getEdad()).append("\n");
	            sb.append("Celular: ").append(a.getCelular()).append("\n");
	            sb.append("Estado: REGISTRADO (Pendiente)\n");
	            sb.append("-----------------------------------\n");
	        }
	    }

	    if (!hayPendientes) {
	        sb.append("No hay alumnos con matrícula pendiente.\n");
	    }

	    txtReporte.setText(sb.toString());
	}
	protected void actionPerformedBtnLimpiar(ActionEvent e) {
		txtReporte.setText("");
	}
	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose(); // Cierra la ventana

	}
}

