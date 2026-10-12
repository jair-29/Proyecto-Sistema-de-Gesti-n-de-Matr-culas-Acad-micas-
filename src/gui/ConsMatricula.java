package gui;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;

import arreglos.ArregloMatriculas;
import arreglos.ArregloRetiros;
import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import objetos.Matricula;
import objetos.Retiro;
import objetos.Alumno;
import objetos.Curso;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsMatricula extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNumMatricula;
	private JTextField txtNumRetiro;
	private JButton btnBuscarMatricula;
	private JButton btnBuscarRetiro;
	private JTextArea txtResultado;
	private JButton btnBorrar;
	private JLabel insignia;

	private ArregloMatriculas matriculas;
	private ArregloRetiros retiros;
	private ArregloAlumnos alumnos;
	private ArregloCursos cursos;

	public ConsMatricula(ArregloMatriculas matriculas, ArregloRetiros retiros, ArregloAlumnos alumnos,
			ArregloCursos cursos) {
		this.matriculas = matriculas;
		this.retiros = retiros;
		this.alumnos = alumnos;
		this.cursos = cursos;
		this.alumnos.cargarDesdeBD();
		this.cursos.cargardesdeBD();
		this.matriculas.cargardesdeBD();
		this.retiros.cargardesdeBD();
		setTitle("Consulta: Matrículas y Retiros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título
		JLabel lblTitulo = new JLabel("CONSULTA DE MATRÍCULAS Y RETIROS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(160, 10, 400, 30);
		contentPane.add(lblTitulo);

		// Matrícula
		JLabel lblMatricula = new JLabel("N° Matrícula:");
		lblMatricula.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMatricula.setBounds(59, 62, 88, 20);
		contentPane.add(lblMatricula);

		txtNumMatricula = new JTextField();
		txtNumMatricula.setBounds(159, 62, 100, 20);
		contentPane.add(txtNumMatricula);

		btnBuscarMatricula = new JButton("Buscar Matrícula");
		btnBuscarMatricula.addActionListener(this);
		btnBuscarMatricula.setBackground(new Color(102, 205, 170));
		btnBuscarMatricula.setBounds(279, 60, 160, 25);
		contentPane.add(btnBuscarMatricula);

		// Retiro
		JLabel lblRetiro = new JLabel("N° Retiro:");
		lblRetiro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRetiro.setBounds(59, 102, 88, 20);
		contentPane.add(lblRetiro);

		txtNumRetiro = new JTextField();
		txtNumRetiro.setBounds(159, 102, 100, 20);
		contentPane.add(txtNumRetiro);

		btnBuscarRetiro = new JButton("Buscar Retiro");
		btnBuscarRetiro.addActionListener(this);
		btnBuscarRetiro.setBackground(new Color(102, 205, 170));
		btnBuscarRetiro.setBounds(279, 100, 160, 25);
		contentPane.add(btnBuscarRetiro);

		// Área de resultados
		txtResultado = new JTextArea();
		txtResultado.setForeground(new Color(0, 0, 139));
		txtResultado.setBackground(new Color(255, 255, 224));
		txtResultado.setEditable(false);
		txtResultado.setFont(new Font("Monospaced", Font.BOLD, 14));

		JScrollPane scrollResultado = new JScrollPane(txtResultado);
		scrollResultado.setBounds(54, 150, 424, 287);
		contentPane.add(scrollResultado);

		btnBorrar = new JButton("BORRAR");
		btnBorrar.addActionListener(this);
		btnBorrar.setBackground(new Color(250, 128, 114));
		btnBorrar.setBounds(522, 68, 88, 39);
		contentPane.add(btnBorrar);

		insignia = new JLabel("");
		insignia.setIcon(new ImageIcon(ConsMatricula.class.getResource("/imagenes/insigna.jpg")));
		insignia.setBounds(499, 191, 147, 196);
		contentPane.add(insignia);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBorrar) {
			actionPerformedBtnBorrar(e);
		}
		if (e.getSource() == btnBuscarRetiro) {
			actionPerformedBtnBuscarRetiro(e);
		}
		if (e.getSource() == btnBuscarMatricula) {
			actionPerformedBtnBuscarMatricula(e);
		}
	}

	protected void actionPerformedBtnBuscarMatricula(ActionEvent e) {
		buscarMatricula();
	}

	protected void actionPerformedBtnBuscarRetiro(ActionEvent e) {
		buscarRetiro();
	}

	protected void actionPerformedBtnBorrar(ActionEvent e) {
		txtResultado.setText("");
		txtNumMatricula.setText("");
		txtNumRetiro.setText("");
		txtNumMatricula.requestFocus();
	}

	private void buscarMatricula() {
		try {
			int numMat = Integer.parseInt(txtNumMatricula.getText().trim());
			Matricula m = matriculas.buscar(numMat);

			if (m == null) {
				txtResultado.setText("	❌ Matrícula no encontrada.");
				return;
			}

			Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());
			Curso c = cursos.buscarPorCodigo(m.getCodCurso());

			StringBuilder sb = new StringBuilder();
			sb.append("	✅ MATRÍCULA ENCONTRADA\n");
			sb.append("	N° Matrícula: ").append(m.getNumMatricula()).append("\n");
			sb.append("	Fecha: ").append(m.getFecha()).append("    Hora: ").append(m.getHora()).append("\n\n");

			if (a != null) {
				sb.append("	👤 Alumno:\n");
				sb.append("	Código: ").append(a.getCodAlumno()).append("\n");
				sb.append("	Nombre: ").append(a.getNombres()).append(" ").append(a.getApellidos()).append("\n");
				sb.append("	DNI: ").append(a.getDni()).append("  Edad: ").append(a.getEdad()).append("\n");
				sb.append("	Estado: ").append(a.getEstadoTexto()).append("\n\n");
			} else {
				sb.append("	⚠️ Alumno no encontrado en el sistema.\n\n");
			}

			if (c != null) {
				sb.append("	📘 Curso:\n");
				sb.append("	Código: ").append(c.getCodCurso()).append("\n");
				sb.append("	Nombre: ").append(c.getAsignatura()).append("\n");
				sb.append("	Ciclo: ").append(c.getCiclo()).append("  Créditos: ").append(c.getCreditos())
						.append("  Horas: ").append(c.getHoras()).append("\n");
			} else {
				sb.append("	⚠️ Curso no encontrado en el sistema.\n");
			}

			txtResultado.setText(sb.toString());

		} catch (NumberFormatException ex) {
			txtResultado.setText("❌ Ingrese un número válido para matrícula.");
			txtNumMatricula.setText("");
			txtNumMatricula.requestFocus();
		}
	}

	private void buscarRetiro() {
		try {
			int numRet = Integer.parseInt(txtNumRetiro.getText().trim());
			Retiro r = retiros.buscar(numRet);

			if (r == null) {
				txtResultado.setText("❌ Retiro no encontrado.");
				return;
			}

			Matricula m = matriculas.buscar(r.getNumMatricula());
			Alumno a = (m != null) ? alumnos.buscarPorCodigo(m.getCodAlumno()) : null;
			Curso c = (m != null) ? cursos.buscarPorCodigo(m.getCodCurso()) : null;

			StringBuilder sb = new StringBuilder();
			sb.append("🚫 RETIRO REGISTRADO\n");
			sb.append("N° Retiro: ").append(r.getNumRetiro()).append("\n");
			sb.append("Fecha: ").append(r.getFecha()).append("    Hora: ").append(r.getHora()).append("\n\n");

			if (m != null) {
				sb.append("📄 Matrícula asociada: ").append(m.getNumMatricula()).append("\n\n");
			} else {
				sb.append("⚠️ Matrícula asociada no encontrada.\n\n");
			}

			if (a != null) {
				sb.append("👤 Alumno:\n");
				sb.append("Código: ").append(a.getCodAlumno()).append("\n");
				sb.append("Nombre: ").append(a.getNombres()).append(" ").append(a.getApellidos()).append("\n");
				sb.append("Estado: ").append(a.getEstadoTexto()).append("\n\n");
			} else {
				sb.append("⚠️ Alumno no encontrado.\n\n");
			}

			if (c != null) {
				sb.append("📘 Curso:\n");
				sb.append("Código: ").append(c.getCodCurso()).append("\n");
				sb.append("Nombre: ").append(c.getAsignatura()).append("\n");
			} else {
				sb.append("⚠️ Curso no encontrado.\n");
			}

			txtResultado.setText(sb.toString());

		} catch (NumberFormatException ex) {
			txtResultado.setText("❌ Ingrese un número válido para retiro.");
			txtNumRetiro.setText("");
			txtNumRetiro.requestFocus();
		}
	}
}
