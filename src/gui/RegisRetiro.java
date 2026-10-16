
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import arreglos.ArregloRetiros;

import objetos.Alumno;
import objetos.Curso;
import objetos.Matricula;
import objetos.Retiro;

public class RegisRetiro extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtNumRetiro;
	private JTextField txtNumMatricula;
	private JTextField txtCurso;
	private JTextField txtFecha;
	private JTextField txtHora;

	private JLabel lblNumRetiro;
	private JLabel lblNumMat;
	private JLabel lblCurso;
	private JLabel lblFecha;
	private JLabel lblHora;
	private JLabel lblNewLabel;

	private JButton btnAdicionar;
	private JButton btnConsultar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnAceptar;
	private JButton btnVolver;

	private JScrollPane scrollPane;
	private JTable tblTable;
	private DefaultTableModel model;

	private int tipoOperacion = -1;

	public static final int ADICIONAR = 0;
	public static final int CONSULTAR = 1;
	public static final int MODIFICAR = 2;
	public static final int ELIMINAR = 3;

	ArregloRetiros retiros;
	ArregloMatriculas matriculas;
	ArregloAlumnos alumnos;
	ArregloCursos cursos;

	public RegisRetiro(ArregloRetiros retiros, ArregloMatriculas matriculas, ArregloAlumnos alumnos,
			ArregloCursos cursos) {

		this.retiros = retiros;
		this.matriculas = matriculas;
		this.alumnos = alumnos;
		this.cursos = cursos;

		// ICONO
		java.net.URL icono = RegisRetiro.class.getResource("/imagenes/ICONO.LOGO.png");

		if (icono != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(icono));
		}

		setTitle("Registro de Retiros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 470);
		setResizable(false);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// TITULO
		JLabel lblTitulo = new JLabel("REGISTRO DE RETIROS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(250, 23, 300, 30);
		contentPane.add(lblTitulo);

		// NUMERO DE RETIRO
		lblNumRetiro = new JLabel("N° Retiro:");
		lblNumRetiro.setForeground(Color.WHITE);
		lblNumRetiro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumRetiro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumRetiro.setBounds(30, 64, 89, 20);
		contentPane.add(lblNumRetiro);

		txtNumRetiro = new JTextField();
		txtNumRetiro.setBounds(130, 64, 100, 20);
		txtNumRetiro.setEditable(false);
		contentPane.add(txtNumRetiro);

		// NUMERO DE MATRICULA
		lblNumMat = new JLabel("N° Matrícula:");
		lblNumMat.setForeground(Color.WHITE);
		lblNumMat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumMat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumMat.setBounds(30, 94, 89, 20);
		contentPane.add(lblNumMat);

		txtNumMatricula = new JTextField();
		txtNumMatricula.setBounds(130, 94, 100, 20);
		contentPane.add(txtNumMatricula);

		// CURSO
		lblCurso = new JLabel("Curso:");
		lblCurso.setForeground(Color.WHITE);
		lblCurso.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCurso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurso.setBounds(20, 125, 100, 20);
		contentPane.add(lblCurso);

		txtCurso = new JTextField();
		txtCurso.setBounds(130, 125, 100, 20);
		txtCurso.setEditable(false);
		contentPane.add(txtCurso);

		// FECHA
		lblFecha = new JLabel("Fecha:");
		lblFecha.setForeground(Color.WHITE);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setBounds(30, 156, 89, 20);
		contentPane.add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setBounds(130, 156, 100, 20);
		txtFecha.setEditable(false);
		contentPane.add(txtFecha);

		// HORA
		lblHora = new JLabel("Hora:");
		lblHora.setForeground(Color.WHITE);
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHora.setBounds(30, 186, 89, 20);
		contentPane.add(lblHora);

		txtHora = new JTextField();
		txtHora.setBounds(130, 186, 100, 20);
		txtHora.setEditable(false);
		contentPane.add(txtHora);

		// BOTON ADICIONAR
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(102, 205, 170));
		btnAdicionar.setBounds(303, 82, 120, 45);
		btnAdicionar.addActionListener(this);
		contentPane.add(btnAdicionar);

		// BOTON CONSULTAR
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(102, 205, 170));
		btnConsultar.setBounds(456, 82, 120, 45);
		btnConsultar.addActionListener(this);
		contentPane.add(btnConsultar);

		// BOTON MODIFICAR
		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(102, 205, 170));
		btnModificar.setBounds(303, 142, 120, 45);
		btnModificar.addActionListener(this);
		contentPane.add(btnModificar);

		// BOTON ELIMINAR
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(102, 205, 170));
		btnEliminar.setBounds(456, 142, 120, 45);
		btnEliminar.addActionListener(this);
		contentPane.add(btnEliminar);

		// TABLA
		model = new DefaultTableModel();

		model.addColumn("N° Retiro");
		model.addColumn("N° Matrícula");
		model.addColumn("Curso");
		model.addColumn("Fecha");
		model.addColumn("Hora");

		tblTable = new JTable(model) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tblTable.setBackground(Color.WHITE);
		tblTable.setFillsViewportHeight(true);

		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();

		centrado.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblTable.getColumnCount(); i++) {

			tblTable.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}

		tblTable.getTableHeader().setResizingAllowed(false);

		scrollPane = new JScrollPane(tblTable);
		scrollPane.setBounds(15, 258, 647, 159);
		scrollPane.getViewport().setBackground(Color.WHITE);
		contentPane.add(scrollPane);

		// BOTON ACEPTAR
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBackground(new Color(240, 230, 140));
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAceptar.setBounds(68, 224, 100, 23);
		btnAceptar.addActionListener(this);
		contentPane.add(btnAceptar);

		// BOTON VOLVER
		btnVolver = new JButton("VOLVER");
		btnVolver.setBackground(new Color(240, 230, 140));
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnVolver.setBounds(191, 224, 100, 23);
		btnVolver.addActionListener(this);
		contentPane.add(btnVolver);

		// FONDO DEL PROYECTO
		lblNewLabel = new JLabel("");

		java.net.URL fondo = RegisRetiro.class.getResource("/imagenes/Fondo.Proyecto.jpg");

		if (fondo != null) {
			lblNewLabel.setIcon(new ImageIcon(fondo));
		}

		lblNewLabel.setBounds(0, 0, 679, 431);
		contentPane.add(lblNewLabel);

		// CARGAR DATOS DESDE MYSQL
		cursos.cargardesdeBD();
		matriculas.cargardesdeBD();
		alumnos.cargarDesdeBD();
		retiros.cargardesdeBD();

		listar();

		habilitarEntradas(false);
		habilitarBotones(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAdicionar) {
			actionPerformedBtnAdicionar(e);
		}

		if (e.getSource() == btnConsultar) {
			actionPerformedBtnConsultar(e);
		}

		if (e.getSource() == btnModificar) {
			actionPerformedBtnModificar(e);
		}

		if (e.getSource() == btnEliminar) {
			actionPerformedBtnEliminar(e);
		}

		if (e.getSource() == btnAceptar) {
			actionPerformedBtnAceptar(e);
		}

		if (e.getSource() == btnVolver) {
			actionPerformedBtnVolver(e);
		}
	}

	// ADICIONAR
	protected void actionPerformedBtnAdicionar(ActionEvent e) {

		tipoOperacion = ADICIONAR;

		limpiarCampos();

		txtNumRetiro.setText("" + retiros.codigoCorrelativo());

		txtNumRetiro.setEditable(false);

		habilitarEntradas(true);
		habilitarBotones(false);

		txtNumMatricula.requestFocus();
	}

	// CONSULTAR
	protected void actionPerformedBtnConsultar(ActionEvent e) {

		tipoOperacion = CONSULTAR;

		limpiarCampos();

		habilitarEntradas(false);
		habilitarBotones(false);

		txtNumRetiro.setEditable(true);

		txtNumRetiro.requestFocus();
	}

	// MODIFICAR
	protected void actionPerformedBtnModificar(ActionEvent e) {

		tipoOperacion = MODIFICAR;

		habilitarEntradas(false);
		habilitarBotones(false);

		txtNumRetiro.setEditable(txtNumRetiro.getText().trim().isEmpty());

		txtCurso.setEditable(true);

		if (txtNumRetiro.isEditable()) {
			txtNumRetiro.requestFocus();
		} else {
			txtCurso.requestFocus();
		}
	}

	// ELIMINAR
	protected void actionPerformedBtnEliminar(ActionEvent e) {

		tipoOperacion = ELIMINAR;

		habilitarEntradas(false);
		habilitarBotones(false);

		txtNumRetiro.setEditable(true);
		txtNumRetiro.requestFocus();
	}

	// VOLVER
	protected void actionPerformedBtnVolver(ActionEvent e) {

		limpiar();
	}

	// ACEPTAR
	protected void actionPerformedBtnAceptar(ActionEvent e) {

		try {

			switch (tipoOperacion) {

			case ADICIONAR:
				adicionarRetiro();
				break;

			case CONSULTAR:
				consultarRetiro();
				break;

			case MODIFICAR:
				modificarRetiro();
				break;

			case ELIMINAR:
				eliminarRetiro();
				break;

			default:
				mensaje("Seleccione la operación");
			}

		} catch (Exception ex) {

			mensaje("Error en la operación: " + ex.getMessage());
		}
	}

	// LISTAR RETIROS
	void listar() {

		model.setRowCount(0);

		for (int i = 0; i < retiros.tamagno(); i++) {

			Retiro r = retiros.obtener(i);

			Matricula m = matriculas.buscar(r.getNumMatricula());

			String nombreCurso = "N/C";

			if (m != null) {

				Curso c = cursos.buscarPorCodigo(m.getCodCurso());

				if (c != null) {
					nombreCurso = c.getAsignatura();
				}
			}

			model.addRow(
					new Object[] { r.getNumRetiro(), r.getNumMatricula(), nombreCurso, r.getFecha(), r.getHora() });
		}
	}

	// ADICIONAR RETIRO EN MYSQL
	void adicionarRetiro() {

		try {

			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());

			int numMatricula = Integer.parseInt(txtNumMatricula.getText().trim());

			Matricula m = matriculas.buscar(numMatricula);

			if (m == null) {

				error("La matrícula no existe.", txtNumMatricula);
				return;
			}

			Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());

			if (a == null) {

				error("No se encontró el alumno.", txtNumMatricula);
				return;
			}

			if (a.getEstado() == 2) {

				error("El alumno ya está retirado.", txtNumMatricula);
				return;
			}

			String fecha = obtenerFechaActual();
			String hora = obtenerHoraActual();

			Retiro r = new Retiro(numRetiro, numMatricula, fecha, hora);

			// GUARDAR RETIRO EN MYSQL
			if (!retiros.adicionarBD(r)) {

				mensaje("No se pudo guardar el retiro en MySQL.");
				return;
			}

			retiros.adicionar(r);

			// ACTUALIZAR ESTADO DEL ALUMNO
			int estadoAnterior = a.getEstado();

			a.setEstado(2);

			if (!alumnos.modificarBD(a)) {

				a.setEstado(estadoAnterior);

				boolean revertido = retiros.eliminarBD(numRetiro);

				if (revertido) {
					retiros.eliminar(numRetiro);

					mensaje("No se pudo actualizar el estado " + "del alumno. Se canceló el registro " + "del retiro.");
				} else {
					mensaje("No se pudo actualizar el estado " + "del alumno ni revertir el retiro "
							+ "en MySQL. Revise los datos.");
				}

				listar();
				return;
			}

			listar();

			mensaje("Retiro registrado correctamente.");

			limpiar();

		} catch (NumberFormatException e) {

			mensaje("Ingrese datos numéricos válidos " + "para N° Retiro y N° Matrícula.");
		}
	}

	// CONSULTAR RETIRO
	void consultarRetiro() {

		try {

			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());

			Retiro r = retiros.buscar(numRetiro);

			if (r == null) {

				error("N° Retiro no existe.", txtNumRetiro);
				return;
			}

			txtNumMatricula.setText("" + r.getNumMatricula());

			txtFecha.setText(r.getFecha());
			txtHora.setText(r.getHora());

			Matricula m = matriculas.buscar(r.getNumMatricula());

			if (m != null) {

				txtCurso.setText("" + m.getCodCurso());

			} else {

				txtCurso.setText("N/C");
			}

			txtNumRetiro.setEditable(false);
			txtNumMatricula.setEditable(false);
			txtCurso.setEditable(false);

			btnModificar.setEnabled(true);
			btnEliminar.setEnabled(true);

		} catch (NumberFormatException e) {

			error("Ingrese un N° Retiro válido.", txtNumRetiro);
		}
	}

	// MODIFICAR CURSO EN MYSQL
	void modificarRetiro() {

		try {

			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());

			Retiro r = retiros.buscar(numRetiro);

			if (r == null) {

				error("N° Retiro no existe.", txtNumRetiro);
				return;
			}

			Matricula m = matriculas.buscar(r.getNumMatricula());

			if (m == null) {

				mensaje("No se encontró la matrícula asociada.");
				return;
			}

			Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());

			if (a == null || a.getEstado() != 2) {

				mensaje("Solo puede cambiar el curso " + "si el alumno está retirado.");
				return;
			}

			int nuevoCodCurso = Integer.parseInt(txtCurso.getText().trim());

			Curso nuevoCurso = cursos.buscarPorCodigo(nuevoCodCurso);

			if (nuevoCurso == null) {

				error("El curso ingresado no existe.", txtCurso);
				return;
			}

			// EVITAR MATRICULAS DUPLICADAS
			if (nuevoCodCurso != m.getCodCurso()
					&& matriculas.alumnoYaMatriculadoEnCurso(m.getCodAlumno(), nuevoCodCurso)) {

				error("El alumno ya tiene una matrícula " + "en ese curso.", txtCurso);
				return;
			}

			int cursoAnterior = m.getCodCurso();

			m.setCodCurso(nuevoCurso.getCodCurso());

			// ACTUALIZAR MYSQL
			if (!matriculas.modificarBD(m)) {

				m.setCodCurso(cursoAnterior);

				mensaje("No se pudo modificar el curso en MySQL.");
				return;
			}

			listar();

			mensaje("Curso modificado correctamente.");

			limpiar();

		} catch (NumberFormatException e) {

			mensaje("Ingrese un N° Retiro y código " + "de curso válidos.");
		}
	}

	// CANCELAR RETIRO EN MYSQL
	void eliminarRetiro() {

		try {

			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());

			Retiro r = retiros.buscar(numRetiro);

			if (r == null) {

				error("El retiro N° " + numRetiro + " no existe.", txtNumRetiro);
				return;
			}

			Matricula m = matriculas.buscar(r.getNumMatricula());

			if (m == null) {

				mensaje("La matrícula asociada no existe.");
				return;
			}

			Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());

			if (a == null) {

				mensaje("El alumno de la matrícula no existe.");
				return;
			}

			if (a.getEstado() != 2) {

				mensaje("No se puede cancelar el retiro " + "porque el alumno no está retirado.");
				return;
			}

			int resp = JOptionPane.showConfirmDialog(this,
					"¿Seguro que desea CANCELAR este retiro?\n" + "El alumno volverá a estar MATRICULADO.",
					"Confirmación", JOptionPane.YES_NO_OPTION);

			if (resp != JOptionPane.YES_OPTION) {
				return;
			}

			// ELIMINAR RETIRO DE MYSQL
			if (!retiros.eliminarBD(numRetiro)) {

				mensaje("No se pudo eliminar el retiro de MySQL.");
				return;
			}

			// CAMBIAR ESTADO A MATRICULADO
			a.setEstado(1);

			if (!alumnos.modificarBD(a)) {

				a.setEstado(2);

				// INTENTAR RESTAURAR EL RETIRO
				boolean restaurado = retiros.adicionarBD(r);

				if (restaurado) {

					mensaje("No se pudo actualizar el estado " + "del alumno. Se restauró el retiro.");

				} else {

					mensaje("No se pudo actualizar el estado " + "del alumno ni restaurar el retiro "
							+ "en MySQL. Revise los datos.");
				}

				return;
			}

			// ELIMINAR DEL ARREGLO
			retiros.eliminar(numRetiro);

			listar();

			mensaje("Retiro cancelado correctamente. " + "El alumno ahora está MATRICULADO.");

			limpiar();

		} catch (NumberFormatException e) {

			error("Ingrese un número de retiro válido.", txtNumRetiro);
		}
	}

	// HABILITAR ENTRADAS
	void habilitarEntradas(boolean sino) {

		if (tipoOperacion == ADICIONAR) {

			txtNumMatricula.setEditable(sino);
			txtCurso.setEditable(false);

		} else if (tipoOperacion == MODIFICAR) {

			txtNumMatricula.setEditable(false);
			txtCurso.setEditable(sino);

		} else {

			txtNumMatricula.setEditable(false);
			txtCurso.setEditable(false);
		}

		txtFecha.setEditable(false);
		txtHora.setEditable(false);
	}

	// HABILITAR BOTONES
	void habilitarBotones(boolean sino) {

		btnAdicionar.setEnabled(sino);
		btnConsultar.setEnabled(sino);
		btnModificar.setEnabled(sino);
		btnEliminar.setEnabled(sino);

		btnAceptar.setEnabled(!sino);
		btnVolver.setEnabled(!sino);
	}

	// LIMPIAR CAMPOS
	void limpiarCampos() {

		txtNumRetiro.setText("");
		txtNumMatricula.setText("");
		txtCurso.setText("");
		txtFecha.setText("");
		txtHora.setText("");
	}

	// RESTABLECER VENTANA
	void limpiar() {

		limpiarCampos();

		txtNumRetiro.setEditable(false);
		txtNumMatricula.setEditable(false);
		txtCurso.setEditable(false);

		tipoOperacion = -1;

		habilitarEntradas(false);
		habilitarBotones(true);
	}

	// MENSAJES
	void mensaje(String s) {

		JOptionPane.showMessageDialog(this, s);
	}

	void error(String s, JTextField txt) {

		mensaje(s);
		txt.setText("");
		txt.requestFocus();
	}

	// FECHA ACTUAL
	String obtenerFechaActual() {

		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	// HORA ACTUAL
	String obtenerHoraActual() {

		return new SimpleDateFormat("HH:mm").format(new Date());
	}
}
