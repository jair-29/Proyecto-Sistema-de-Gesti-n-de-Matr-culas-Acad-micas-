package gui;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import app.MenuPrincipal;
import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import arreglos.ArregloRetiros;
import objetos.Alumno;
import objetos.Curso;
import objetos.Matricula;
import objetos.Retiro;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JButton btnAdicionar;
	private JButton btnConsultar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnAceptar;
	private JButton btnVolver;

	private JScrollPane scrollPane;
	private JTable tblTable;
	private DefaultTableModel model;

	// TIPO DE OPERACION A PROCESAR
	private int tipoOperacion;

	// CONSTANTES PARA TIPO DE OPERACION
	public static final int ADICIONAR = 0;
	public static final int CONSULTAR = 1;
	public static final int MODIFICAR = 2;
	public static final int ELIMINAR = 3;

	// Declaraciones globales
	ArregloRetiros retiros;
	ArregloMatriculas matriculas;
	ArregloAlumnos alumnos;
	ArregloCursos cursos;
	private JLabel lblNewLabel;

	public RegisRetiro(ArregloRetiros retiros, ArregloMatriculas matriculas, ArregloAlumnos alumnos,
			ArregloCursos cursos) {
		this.retiros = retiros;
		this.matriculas = matriculas;
		this.alumnos = alumnos;
		this.cursos = cursos;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(RegisRetiro.class.getResource("/imagenes/logoPrincipal.jpeg")));
		setTitle("Registro de Retiros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 470);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título
		JLabel lblTitulo = new JLabel("REGISTRO DE RETIROS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(250, 23, 300, 30);
		contentPane.add(lblTitulo);

		// Número de retiro (generado automáticamente)
		lblNumRetiro = new JLabel("N° Retiro:");
		lblNumRetiro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumRetiro.setBounds(30, 64, 89, 20);
		contentPane.add(lblNumRetiro);
		txtNumRetiro = new JTextField();
		txtNumRetiro.setBounds(130, 64, 100, 20);
		txtNumRetiro.setEditable(false);
		contentPane.add(txtNumRetiro);

		// Número de matrícula
		lblNumMat = new JLabel("N° Matrícula:");
		lblNumMat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumMat.setBounds(30, 94, 89, 20);
		contentPane.add(lblNumMat);
		txtNumMatricula = new JTextField();
		txtNumMatricula.setBounds(130, 94, 100, 20);
		contentPane.add(txtNumMatricula);

		lblCurso = new JLabel("Curso:");
		lblCurso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurso.setBounds(20, 125, 100, 20);
		contentPane.add(lblCurso);

		txtCurso = new JTextField();
		txtCurso.setBounds(130, 125, 100, 20);
		contentPane.add(txtCurso);

		// Fecha
		lblFecha = new JLabel("Fecha:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setBounds(30, 156, 89, 20);
		contentPane.add(lblFecha);
		txtFecha = new JTextField();
		txtFecha.setBounds(130, 156, 100, 20);
		txtFecha.setEditable(false); // Fecha generada automáticamente
		contentPane.add(txtFecha);

		// Hora
		lblHora = new JLabel("Hora:");
		lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHora.setBounds(30, 186, 89, 20);
		contentPane.add(lblHora);
		txtHora = new JTextField();
		txtHora.setBounds(130, 186, 100, 20);
		txtHora.setEditable(false); // Hora generada automáticamente
		contentPane.add(txtHora);

		// Botones
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBackground(new Color(102, 205, 170));
		btnAdicionar.setBounds(303, 82, 120, 45);
		contentPane.add(btnAdicionar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(this);
		btnConsultar.setBackground(new Color(102, 205, 170));
		btnConsultar.setBounds(456, 82, 120, 45);
		contentPane.add(btnConsultar);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		btnModificar.setBackground(new Color(102, 205, 170));
		btnModificar.setBounds(303, 142, 120, 45);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBackground(new Color(102, 205, 170));
		btnEliminar.setBounds(456, 142, 120, 45);
		contentPane.add(btnEliminar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 258, 647, 159);
		contentPane.add(scrollPane);

		tblTable = new JTable(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Esto evita que las celdas sean editables
			}
		};
		tblTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tblTable.setBackground(new Color(255, 248, 220));

		tblTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblTable);

		model = new DefaultTableModel();
		model.addColumn("N° Retiro");
		model.addColumn("N° Matrícula");
		model.addColumn("Curso");
		model.addColumn("Fecha");
		model.addColumn("Hora");
		tblTable.setModel(model);
		// Centrar texto en todas las columnas
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tblTable.getColumnCount(); i++) {
			tblTable.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}
		tblTable.getTableHeader().setResizingAllowed(false);

		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(this);
		btnAceptar.setBackground(new Color(240, 230, 140));
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAceptar.setBounds(68, 224, 100, 23);
		contentPane.add(btnAceptar);

		btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(this);
		btnVolver.setBackground(new Color(240, 230, 140));
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnVolver.setBounds(191, 224, 100, 23);
		contentPane.add(btnVolver);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RegisRetiro.class.getResource("/imagenes/insignia_mini.jpg")));
		lblNewLabel.setBounds(592, 11, 62, 61);
		contentPane.add(lblNewLabel);
		cursos.cargardesdeBD();
		matriculas.cargardesdeBD();
		alumnos.cargarDesdeBD();
		retiros.cargardesdeBD();
		listar();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnVolver) {
			actionPerformedBtnVolver(e);
		}
		if (e.getSource() == btnAceptar) {
			actionPerformedBtnAceptar(e);
		}
		if (e.getSource() == btnEliminar) {
			actionPerformedBtnEliminar(e);
		}
		if (e.getSource() == btnModificar) {
			actionPerformedBtnModificar(e);
		}
		if (e.getSource() == btnConsultar) {
			actionPerformedBtnConsultar(e);
		}
		if (e.getSource() == btnAdicionar) {
			actionPerformedBtnAdicionar(e);
		}
	}

	protected void actionPerformedBtnAdicionar(ActionEvent e) {
		tipoOperacion = ADICIONAR;

		// Generamos un número de retiro correlativo
		txtNumRetiro.setText("" + (retiros.codigoCorrelativo()));
		habilitarEntradas(true);
		habilitarBotones(false);
		txtNumMatricula.requestFocus();
	}

	protected void actionPerformedBtnConsultar(ActionEvent e) {
		tipoOperacion = CONSULTAR;

		habilitarEntradas(false);
		habilitarBotones(false);
		btnAceptar.setEnabled(true);
		txtNumRetiro.setEditable(true);
		txtNumRetiro.requestFocus();
	}

	protected void actionPerformedBtnModificar(ActionEvent e) {
		tipoOperacion = MODIFICAR;

		txtCurso.setEditable(true);
		txtNumRetiro.setEditable(false);
		txtNumMatricula.setEditable(false);
		txtFecha.setEditable(false);
		txtHora.setEditable(false);

		btnAceptar.setEnabled(true);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);
		btnVolver.setEnabled(true);

		txtCurso.requestFocus();
	}

	protected void actionPerformedBtnEliminar(ActionEvent e) {
		tipoOperacion = ELIMINAR;

		habilitarEntradas(false);
		habilitarBotones(false);
		btnAceptar.setEnabled(true);
		txtNumRetiro.setEditable(true);
		txtNumRetiro.requestFocus();
	}

	protected void actionPerformedBtnVolver(ActionEvent e) {
		limpiar();
		habilitarEntradas(false);
		habilitarBotones(true);
		txtNumRetiro.setEditable(false);
		txtCurso.setEditable(false);
	}

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

	void listar() {
		model.setRowCount(0);
		for (int i = 0; i < retiros.tamagno(); i++) {
			Retiro r = retiros.obtener(i);
			Matricula m = matriculas.buscar(r.getNumMatricula());

			String nombreCurso = "N/C";
			if (m != null) {
				int codCurso = m.getCodCurso();
				Curso c = cursos.buscarPorCodigo(codCurso);
				if (c != null) {
					nombreCurso = c.getAsignatura();
				}
			}

			model.addRow(
					new Object[] { r.getNumRetiro(), r.getNumMatricula(), nombreCurso, r.getFecha(), r.getHora() });
		}
	}

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
				error("No se encontro el alumno.", txtNumMatricula);
				return;
			}
			if (a.getEstado() == 2) {
				error("El alumno  ya esta retirado.", txtNumMatricula);
				return;
			}

			String fecha = obtenerFechaActual();
			String hora = obtenerHoraActual();

			Retiro r = new Retiro(numRetiro, numMatricula, fecha, hora);
			if (!retiros.adicionarBD(r)) {
				mensaje("No se pudo guardar el retiro en MySQL");
				return;
			}
			retiros.adicionar(r);
			a.setEstado(2);
			if (!alumnos.modificarBD(a)) {
				a.setEstado(1);
				retiros.eliminarBD(numRetiro);
				retiros.eliminar(numRetiro);
				mensaje("No se pudo actualizar  el estado del alumno.");
				return;
			}
			listar();
			mensaje("Retiro registrado correctamente.");
			limpiar();
			habilitarEntradas(false);
			habilitarBotones(true);
		} catch (NumberFormatException e) {
			error("Ingrese datos numéricos válidos para N° Retiro y N° Matrícula.", txtNumMatricula);
		}
	}

	void consultarRetiro() {
		try {
			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());
			Retiro r = retiros.buscar(numRetiro);

			if (r != null) {
				txtNumMatricula.setText("" + r.getNumMatricula());
				txtFecha.setText(r.getFecha());
				txtHora.setText(r.getHora());

				// ✅ Agregar esto al final del método:
				Matricula m = matriculas.buscar(r.getNumMatricula());
				if (m != null) {
					int codCurso = m.getCodCurso();
					if (MenuPrincipal.cursos.buscarPorCodigo(codCurso) != null) {
						String nombreCurso = MenuPrincipal.cursos.buscarPorCodigo(codCurso).getAsignatura();
						txtCurso.setText(String.valueOf(codCurso));
					} else {
						txtCurso.setText("N/C");
					}
				} else {
					txtCurso.setText("Matrícula no encontrada");
				}

				btnModificar.setEnabled(true);
				btnEliminar.setEnabled(true);
			} else {
				error("N° Retiro no existe", txtNumRetiro);
			}
		} catch (NumberFormatException e) {
			error("Ingrese un N° Retiro válido.", txtNumRetiro);
		}
	}

	void modificarRetiro() {
		try {
			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());
			Retiro r = retiros.buscar(numRetiro);

			if (r != null) {
				Matricula m = matriculas.buscar(r.getNumMatricula());
				if (m == null) {
					mensaje("No se encontró la matrícula asociada.");
					return;
				}

				Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());
				if (a == null || a.getEstado() != 2) {
					error("Solo puede cambiar el curso si el alumno está retirado.", txtCurso);
					return;
				}

				int nuevoCodCurso = Integer.parseInt(txtCurso.getText().trim());
				Curso nuevoCurso = cursos.buscarPorCodigo(nuevoCodCurso);

				if (nuevoCurso == null) {
					error("El curso ingresado no existe.", txtCurso);
					return;
				}
				int cursoAnterior = m.getCodCurso();

				m.setCodCurso(nuevoCurso.getCodCurso());
				if (!matriculas.modificarBD(m)) {
					m.setCodCurso(cursoAnterior);
					mensaje("No se pudo modificar el curso en MySQL.");
					return;
				}
				listar();
				mensaje("Curso modificado correctamente.");
				limpiar();
			} else {
				error("N° Retiro no existe.", txtNumRetiro);
			}
		} catch (NumberFormatException e) {
			error("Ingrese un número válido para N° Retiro.", txtNumRetiro);
		}
	}

	void eliminarRetiro() {
		try {
			int numRetiro = Integer.parseInt(txtNumRetiro.getText().trim());
			Retiro r = retiros.buscar(numRetiro);
			if (r == null) {
				error("El retiro N° " + numRetiro + " no existe.", txtNumRetiro);
				return;
			}

			// Buscar la matrícula asociada al retiro
			Matricula m = matriculas.buscar(r.getNumMatricula());
			if (m == null) {
				error("La matrícula asociada no existe.", txtNumMatricula);
				return;
			}

			// Buscar el alumno de la matrícula
			Alumno a = alumnos.buscarPorCodigo(m.getCodAlumno());
			if (a == null) {
				error("El alumno de la matrícula no existe.", txtNumMatricula);
				return;
			}

			// Solo permitir si el alumno está en estado 2 (Retirado)
			if (a.getEstado() != 2) {
				mensaje("No se puede cancelar el retiro porque el alumno no está retirado.");
				return;
			}

			// Confirmación
			int resp = JOptionPane.showConfirmDialog(this,
					"¿Seguro que desea CANCELAR este retiro?\nEl alumno volverá a estar MATRICULADO.", "Confirmación",
					JOptionPane.YES_NO_OPTION);

			if (resp == JOptionPane.YES_OPTION) {
				// Eliminar el retiro de MySQL
				if (!retiros.eliminarBD(numRetiro)) {
					mensaje("No se pudo eliminar el retiro de MySQL.");
					return;
				}

				// Cambiar el estado del alumno a MATRICULADO
				a.setEstado(1);

				if (!alumnos.modificarBD(a)) {
					a.setEstado(2);

					// Intentar restaurar el retiro en MySQL
					retiros.adicionarBD(r);

					mensaje("No se pudo actualizar el estado del alumno.");
					return;
				}

				// Actualizar la lista en memoria
				retiros.eliminar(numRetiro);

				listar();
				mensaje("Retiro cancelado correctamente. El alumno ahora está MATRICULADO.");
			}

		} catch (NumberFormatException e) {
			error("Ingrese un número de retiro válido", txtNumRetiro);
		}
	}

	void habilitarEntradas(boolean sino) {
		if (tipoOperacion == ADICIONAR) {
			txtNumMatricula.setEditable(sino);
			txtCurso.setEditable(false); // curso se llena automático
		} else if (tipoOperacion == MODIFICAR) {
			txtNumMatricula.setEditable(false);
			txtCurso.setEditable(sino); // solo curso se edita
		} else {
			txtNumMatricula.setEditable(false);
			txtCurso.setEditable(false);
		}
	}

	void habilitarBotones(boolean sino) {
		btnAdicionar.setEnabled(sino);
		btnConsultar.setEnabled(sino);
		btnModificar.setEnabled(sino);
		btnEliminar.setEnabled(sino);

		if (tipoOperacion == CONSULTAR) {
			btnAceptar.setEnabled(false);
		} else {
			btnAceptar.setEnabled(!sino);
		}
		btnVolver.setEnabled(!sino);
	}

	void limpiar() {
		txtNumRetiro.setText("");
		txtNumMatricula.setText("");
		txtCurso.setText("");
		txtFecha.setText("");
		txtHora.setText("");

		txtNumRetiro.setEditable(false);
		txtNumMatricula.setEditable(false);
		txtCurso.setEditable(false);

		habilitarEntradas(false);
		habilitarBotones(true);
	}

	void mensaje(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	void error(String s, JTextField txt) {
		mensaje(s);
		txt.setText("");
		txt.requestFocus();
	}

	String obtenerFechaActual() {
		return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
	}

	String obtenerHoraActual() {
		return new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
	}

}
