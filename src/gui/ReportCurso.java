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

public class ReportCurso extends JFrame implements ActionListener {
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
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título de la ventana
		JLabel lblTitulo = new JLabel("ALUMNOS MATRICULADOS POR CURSO");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(69, 11, 500, 30);
		contentPane.add(lblTitulo);

		// Área de texto para mostrar el reporte
		txtReporte = new JTextArea();
		txtReporte.setForeground(new Color(0, 0, 255));
		txtReporte.setBackground(new Color(255, 255, 224));
		txtReporte.setEditable(false);
		txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));

		JScrollPane scrollPane = new JScrollPane(txtReporte);
		scrollPane.setBounds(30, 60, 455, 330);
		contentPane.add(scrollPane);

		// Botón: Mostrar
		btnMostrar = new JButton("Mostrar");
		btnMostrar.setBackground(new Color(102, 205, 170));
		btnMostrar.addActionListener(this);
		btnMostrar.setBounds(30, 413, 120, 30);
		contentPane.add(btnMostrar);

		// Botón: Limpiar
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(new Color(102, 205, 170));
		btnLimpiar.addActionListener(this);
		btnLimpiar.setBounds(200, 413, 120, 30);
		contentPane.add(btnLimpiar);

		// Botón: Cerrar
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(250, 128, 114));
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(370, 413, 120, 30);
		contentPane.add(btnCerrar);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ReportCurso.class.getResource("/imagenes/insigna.jpg")));
		lblNewLabel.setBounds(517, 112, 148, 196);
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
	    txtReporte.setText("");

	    for (int i = 0; i < MenuPrincipal.cursos.tamagno(); i++) {
	        Curso c = MenuPrincipal.cursos.obtener(i);
	        txtReporte.append("Curso: " + c.getAsignatura() + " (Código: " + c.getCodCurso() + ")\n");
	        txtReporte.append("Alumnos matriculados:\n");

	        boolean hayAlumnos = false;

	        for (int j = 0; j < MenuPrincipal.matriculas.tamagno(); j++) {
	            Matricula m = MenuPrincipal.matriculas.obtener(j);
	            if (m.getCodCurso() == c.getCodCurso()) {
	                Alumno a = MenuPrincipal.alumnos.buscarPorCodigo(m.getCodAlumno());
	                if (a != null) {
	                    txtReporte.append("- " + a.getNombres() + " " + a.getApellidos() + "\n");
	                    hayAlumnos = true;
	                }
	            }
	        }

	        if (!hayAlumnos) {
	            txtReporte.append("  (Ningún alumno matriculado)\n");
	        }

	        txtReporte.append("--------------------------------------------------\n");
	    }
	}
	protected void actionPerformedBtnLimpiar(ActionEvent e) {
	    txtReporte.setText("");
	}
	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}
}
