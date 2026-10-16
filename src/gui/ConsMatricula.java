
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import arreglos.ArregloMatriculas;
import arreglos.ArregloRetiros;
import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;

import objetos.Matricula;
import objetos.Retiro;
import objetos.Alumno;
import objetos.Curso;

public class ConsMatricula extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtNumMatricula;
	private JTextField txtNumRetiro;

	private JButton btnBuscarMatricula;
	private JButton btnBuscarRetiro;
	private JButton btnBorrar;

	private JTextArea txtResultado;
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

		setTitle("Consulta: Matrículas y Retiros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// TITULO
		JLabel lblTitulo = new JLabel("CONSULTA DE MATRÍCULAS Y RETIROS");

		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(160, 10, 450, 30);
		contentPane.add(lblTitulo);

		// NUMERO DE MATRICULA
		JLabel lblMatricula = new JLabel("N° Matrícula:");
		lblMatricula.setForeground(Color.WHITE);
		lblMatricula.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMatricula.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMatricula.setBounds(59, 62, 88, 20);
		contentPane.add(lblMatricula);

		txtNumMatricula = new JTextField();
		txtNumMatricula.setBounds(159, 62, 100, 20);
		contentPane.add(txtNumMatricula);

		// BOTON BUSCAR MATRICULA
		btnBuscarMatricula = new JButton("Buscar Matrícula");
		btnBuscarMatricula.setBackground(new Color(102, 205, 170));
		btnBuscarMatricula.setBounds(279, 60, 160, 25);
		btnBuscarMatricula.addActionListener(this);
		contentPane.add(btnBuscarMatricula);

		// NUMERO DE RETIRO
		JLabel lblRetiro = new JLabel("N° Retiro:");
		lblRetiro.setForeground(Color.WHITE);
		lblRetiro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRetiro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRetiro.setBounds(59, 102, 88, 20);
		contentPane.add(lblRetiro);

		txtNumRetiro = new JTextField();
		txtNumRetiro.setBounds(159, 102, 100, 20);
		contentPane.add(txtNumRetiro);

		// BOTON BUSCAR RETIRO
		btnBuscarRetiro = new JButton("Buscar Retiro");
		btnBuscarRetiro.setBackground(new Color(102, 205, 170));
		btnBuscarRetiro.setBounds(279, 100, 160, 25);
		btnBuscarRetiro.addActionListener(this);
		contentPane.add(btnBuscarRetiro);

		// AREA DE RESULTADOS
		txtResultado = new JTextArea();
		txtResultado.setForeground(new Color(0, 0, 139));
		txtResultado.setBackground(Color.WHITE);
		txtResultado.setEditable(false);
		txtResultado.setFont(new Font("Monospaced", Font.BOLD, 14));
		txtResultado.setLineWrap(true);
		txtResultado.setWrapStyleWord(true);
		txtResultado.setMargin(new java.awt.Insets(10, 10, 10, 10));

		JScrollPane scrollResultado = new JScrollPane(txtResultado);

		scrollResultado.setBounds(54, 150, 424, 287);
		contentPane.add(scrollResultado);

		// BOTON BORRAR
		btnBorrar = new JButton("BORRAR");
		btnBorrar.setBackground(new Color(250, 128, 114));
		btnBorrar.setBounds(522, 68, 88, 39);
		btnBorrar.addActionListener(this);
		contentPane.add(btnBorrar);

		// FONDO DEL PROYECTO COMPRIMIDO
		insignia = new JLabel("");

		java.net.URL fondo = ConsMatricula.class.getResource("/imagenes/Fondo.Proyecto.jpg");

		if (fondo != null) {
			insignia.setIcon(new ImageIcon(fondo));
		}

		insignia.setBounds(0, 0, 684, 461);
		contentPane.add(insignia);

		// CARGAR DATOS DESDE MYSQL
		cargarDatos();
	}

	// CARGAR DATOS
	private void cargarDatos() {

		alumnos.cargarDesdeBD();
		cursos.cargardesdeBD();
		matriculas.cargardesdeBD();
		retiros.cargardesdeBD();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnBuscarMatricula) {
			actionPerformedBtnBuscarMatricula(e);
		}

		if (e.getSource() == btnBuscarRetiro) {
			actionPerformedBtnBuscarRetiro(e);
		}

		if (e.getSource() == btnBorrar) {
			actionPerformedBtnBorrar(e);
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

	// CONSULTAR MATRICULA
	private void buscarMatricula() {

		try {

			int numMat = Integer.parseInt(txtNumMatricula.getText().trim());

			// ACTUALIZAR DATOS DESDE MYSQL
			cargarDatos();

			Matricula m = matriculas.buscar(numMat);

			if (m == null) {

				txtResultado.setText("Matrícula no encontrada.");
				return;
			}

			Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());

			Curso c = cursos.buscarPorCodigo(m.getCodCurso());

			StringBuilder sb = new StringBuilder();

			sb.append("MATRÍCULA ENCONTRADA\n");
			sb.append("------------------------------\n\n");

			sb.append("N° Matrícula: ").append(m.getNumMatricula()).append("\n");

			sb.append("Fecha: ").append(m.getFecha()).append("\n");

			sb.append("Hora: ").append(m.getHora()).append("\n\n");

			// DATOS DEL ALUMNO
			if (a != null) {

				sb.append("DATOS DEL ALUMNO\n");
				sb.append("------------------------------\n");

				sb.append("Código: ").append(a.getCodAlumno()).append("\n");

				sb.append("Nombre: ").append(a.getNombres()).append(" ").append(a.getApellidos()).append("\n");

				sb.append("DNI: ").append(a.getDni()).append("\n");

				sb.append("Edad: ").append(a.getEdad()).append("\n");

				sb.append("Estado: ").append(a.getEstadoTexto()).append("\n\n");

			} else {

				sb.append("Alumno no encontrado.\n\n");
			}

			// DATOS DEL CURSO
			if (c != null) {

				sb.append("DATOS DEL CURSO\n");
				sb.append("------------------------------\n");

				sb.append("Código: ").append(c.getCodCurso()).append("\n");

				sb.append("Nombre: ").append(c.getAsignatura()).append("\n");

				sb.append("Ciclo: ").append(c.getCiclo()).append("\n");

				sb.append("Créditos: ").append(c.getCreditos()).append("\n");

				sb.append("Horas: ").append(c.getHoras()).append("\n");

			} else {

				sb.append("Curso no encontrado.\n");
			}

			txtResultado.setText(sb.toString());
			txtResultado.setCaretPosition(0);

		} catch (NumberFormatException ex) {

			txtResultado.setText("Ingrese un número válido de matrícula.");

			txtNumMatricula.setText("");
			txtNumMatricula.requestFocus();

		} catch (Exception ex) {

			txtResultado.setText("Error al consultar matrícula: " + ex.getMessage());
		}
	}

	// CONSULTAR RETIRO
	private void buscarRetiro() {

		try {

			int numRet = Integer.parseInt(txtNumRetiro.getText().trim());

			// ACTUALIZAR DATOS DESDE MYSQL
			cargarDatos();

			Retiro r = retiros.buscar(numRet);

			if (r == null) {

				txtResultado.setText("Retiro no encontrado.");
				return;
			}

			Matricula m = matriculas.buscar(r.getNumMatricula());

			Alumno a = null;
			Curso c = null;

			if (m != null) {

				a = alumnos.buscarPorCodigo(m.getCodAlumno());

				c = cursos.buscarPorCodigo(m.getCodCurso());
			}

			StringBuilder sb = new StringBuilder();

			sb.append("RETIRO REGISTRADO\n");
			sb.append("------------------------------\n\n");

			sb.append("N° Retiro: ").append(r.getNumRetiro()).append("\n");

			sb.append("Fecha: ").append(r.getFecha()).append("\n");

			sb.append("Hora: ").append(r.getHora()).append("\n\n");

			// MATRICULA ASOCIADA
			if (m != null) {

				sb.append("MATRÍCULA ASOCIADA\n");
				sb.append("------------------------------\n");

				sb.append("N° Matrícula: ").append(m.getNumMatricula()).append("\n\n");

			} else {

				sb.append("Matrícula asociada no encontrada.\n\n");
			}

			// DATOS DEL ALUMNO
			if (a != null) {

				sb.append("DATOS DEL ALUMNO\n");
				sb.append("------------------------------\n");

				sb.append("Código: ").append(a.getCodAlumno()).append("\n");

				sb.append("Nombre: ").append(a.getNombres()).append(" ").append(a.getApellidos()).append("\n");

				sb.append("Estado: ").append(a.getEstadoTexto()).append("\n\n");

			} else {

				sb.append("Alumno no encontrado.\n\n");
			}

			// DATOS DEL CURSO
			if (c != null) {

				sb.append("DATOS DEL CURSO\n");
				sb.append("------------------------------\n");

				sb.append("Código: ").append(c.getCodCurso()).append("\n");

				sb.append("Nombre: ").append(c.getAsignatura()).append("\n");

			} else {

				sb.append("Curso no encontrado.\n");
			}

			txtResultado.setText(sb.toString());
			txtResultado.setCaretPosition(0);

		} catch (NumberFormatException ex) {

			txtResultado.setText("Ingrese un número válido de retiro.");

			txtNumRetiro.setText("");
			txtNumRetiro.requestFocus();

		} catch (Exception ex) {

			txtResultado.setText("Error al consultar retiro: " + ex.getMessage());
		}
	}
}
