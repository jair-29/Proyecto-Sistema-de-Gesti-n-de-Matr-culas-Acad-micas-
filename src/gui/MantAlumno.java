
package gui;

import arreglos.ArregloAlumnos;
import objetos.Alumno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MantAlumno extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtCodigo, txtNombres, txtApellidos;
	private JTextField txtDni, txtEdad, txtCelular;

	private JButton btnAdicionar, btnConsultar, btnModificar;
	private JButton btnEliminar, btnAceptar, btnVolver;
	private JButton btnCargar, btnBorrar;

	private JScrollPane scrollPane;

	private JLabel lblTitulo, lblCodigo, lblNombres;
	private JLabel lblApellidos, lblDni, lblEdad;
	private JLabel lblCelular, lblEstado, lblNewLabel;

	private JComboBox<String> cboEstado;
	private JTable tblTabla;
	private DefaultTableModel modelo;

	private int tipoOperacion = -1;

	public static final int ADICIONAR = 0;
	public static final int CONSULTAR = 1;
	public static final int MODIFICAR = 2;
	public static final int ELIMINAR = 3;

	ArregloAlumnos alumnos;

	public MantAlumno(ArregloAlumnos alumnos) {

		this.alumnos = alumnos;

		java.net.URL icono = MantAlumno.class.getResource("/imagenes/ICONO.LOGO.png");

		if (icono != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(icono));
		}

		setTitle("Mantenimiento de Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 743, 591);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 192));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// TITULO
		lblTitulo = new JLabel("REGISTRO DE ALUMNOS");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(250, 10, 300, 30);
		contentPane.add(lblTitulo);

		// CODIGO
		lblCodigo = new JLabel("Código:");
		lblCodigo.setForeground(Color.WHITE);
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(30, 60, 83, 20);
		contentPane.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(130, 60, 111, 20);
		txtCodigo.setEditable(false);
		contentPane.add(txtCodigo);

		// NOMBRES
		lblNombres = new JLabel("Nombres:");
		lblNombres.setForeground(Color.WHITE);
		lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombres.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombres.setBounds(30, 90, 83, 20);
		contentPane.add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setBounds(130, 90, 220, 20);
		contentPane.add(txtNombres);

		// APELLIDOS
		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setForeground(Color.WHITE);
		lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setBounds(30, 120, 83, 20);
		contentPane.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(130, 120, 220, 20);
		contentPane.add(txtApellidos);

		// DNI
		lblDni = new JLabel("DNI:");
		lblDni.setForeground(Color.WHITE);
		lblDni.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDni.setBounds(30, 150, 83, 20);
		contentPane.add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(130, 150, 111, 20);
		contentPane.add(txtDni);

		// EDAD
		lblEdad = new JLabel("Edad:");
		lblEdad.setForeground(Color.WHITE);
		lblEdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEdad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdad.setBounds(30, 180, 83, 20);
		contentPane.add(lblEdad);

		txtEdad = new JTextField();
		txtEdad.setBounds(130, 180, 50, 20);
		contentPane.add(txtEdad);

		// CELULAR
		lblCelular = new JLabel("Celular:");
		lblCelular.setForeground(Color.WHITE);
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setBounds(30, 210, 83, 20);
		contentPane.add(lblCelular);

		txtCelular = new JTextField();
		txtCelular.setBounds(130, 210, 111, 20);
		contentPane.add(txtCelular);

		// ESTADO
		lblEstado = new JLabel("Estado:");
		lblEstado.setForeground(Color.WHITE);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setBounds(30, 240, 83, 20);
		contentPane.add(lblEstado);

		cboEstado = new JComboBox<>();
		cboEstado.setBounds(130, 240, 150, 20);
		cboEstado.addItem("Registrado (0)");
		cboEstado.addItem("Matriculado (1)");
		cboEstado.addItem("Retirado (2)");
		contentPane.add(cboEstado);

		// TABLA
		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 287, 697, 252);
		contentPane.add(scrollPane);

		modelo = new DefaultTableModel();

		modelo.addColumn("Código");
		modelo.addColumn("Nombres");
		modelo.addColumn("Apellidos");
		modelo.addColumn("DNI");
		modelo.addColumn("Edad");
		modelo.addColumn("Celular");
		modelo.addColumn("Estado");

		tblTabla = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblTabla.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tblTabla.setBackground(Color.WHITE);
		tblTabla.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblTabla);

		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();

		centrado.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < tblTabla.getColumnCount(); i++) {
			tblTabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}

		tblTabla.getTableHeader().setResizingAllowed(false);

		// BOTON ADICIONAR
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(102, 205, 170));
		btnAdicionar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdicionar.setBounds(394, 95, 100, 45);
		btnAdicionar.addActionListener(this);
		contentPane.add(btnAdicionar);

		// BOTON CONSULTAR
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(102, 205, 170));
		btnConsultar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConsultar.setBounds(520, 95, 100, 45);
		btnConsultar.addActionListener(this);
		contentPane.add(btnConsultar);

		// BOTON MODIFICAR
		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(102, 205, 170));
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnModificar.setBounds(394, 155, 100, 45);
		btnModificar.addActionListener(this);
		contentPane.add(btnModificar);

		// BOTON ELIMINAR
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(102, 205, 170));
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEliminar.setBounds(520, 155, 100, 45);
		btnEliminar.addActionListener(this);
		contentPane.add(btnEliminar);

		// BOTON ACEPTAR
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(new Color(240, 230, 140));
		btnAceptar.setBounds(364, 223, 138, 30);
		btnAceptar.addActionListener(this);
		contentPane.add(btnAceptar);

		// BOTON VOLVER
		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(240, 230, 140));
		btnVolver.setBounds(512, 223, 150, 30);
		btnVolver.addActionListener(this);
		contentPane.add(btnVolver);

		// BOTON LISTAR
		btnCargar = new JButton("LISTAR");
		btnCargar.setBackground(new Color(240, 230, 140));
		btnCargar.setBounds(29, 10, 77, 29);
		btnCargar.addActionListener(this);
		contentPane.add(btnCargar);

		// BOTON BORRAR
		btnBorrar = new JButton("BORRAR");
		btnBorrar.setBackground(new Color(255, 128, 128));
		btnBorrar.setBounds(116, 10, 83, 29);
		btnBorrar.addActionListener(this);
		contentPane.add(btnBorrar);

		// IMAGEN DE FONDO
		lblNewLabel = new JLabel("");

		java.net.URL fondo = MantAlumno.class.getResource("/imagenes/Fondo.Proyecto.jpg");

		if (fondo != null) {
			lblNewLabel.setIcon(new ImageIcon(fondo));
		}

		lblNewLabel.setBounds(0, 0, 729, 552);
		contentPane.add(lblNewLabel);

		// CARGAR ALUMNOS DESDE MYSQL
		ajustarAnchoColumnas();

		alumnos.eliminarTodo();
		alumnos.cargarDesdeBD();

		listar();
		habilitarEntradas(false);
		habilitarBotones(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnBorrar) {
			actionPerformedBtnBorrar(e);
		}

		if (e.getSource() == btnCargar) {
			actionPerformedBtnListar(e);
		}

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

	// ADICIONAR
	protected void actionPerformedBtnAdicionar(ActionEvent e) {

		tipoOperacion = ADICIONAR;

		txtCodigo.setText("" + alumnos.codigoCorrelativo());
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDni.setText("");
		txtEdad.setText("");
		txtCelular.setText("");

		cboEstado.setSelectedIndex(0);

		habilitarEntradas(true);

		// CORREGIDO: PERMITIR SELECCIONAR ESTADO
		cboEstado.setEnabled(true);

		habilitarBotones(false);
		txtNombres.requestFocus();
	}

	// CONSULTAR
	protected void actionPerformedBtnConsultar(ActionEvent e) {

		tipoOperacion = CONSULTAR;

		habilitarEntradas(false);
		txtCodigo.setEditable(true);
		habilitarBotones(false);

		btnAceptar.setEnabled(true);
		txtCodigo.requestFocus();
	}

	// MODIFICAR
	protected void actionPerformedBtnModificar(ActionEvent e) {

		tipoOperacion = MODIFICAR;

		txtCodigo.setEditable(false);
		ponerTextoRojo(txtCodigo);

		habilitarEntradas(true);

		txtDni.setEditable(false);

		// CORREGIDO: PERMITIR CAMBIAR ESTADO
		cboEstado.setEnabled(true);

		ponerTextoRojo(txtDni);

		habilitarBotones(false);
		txtNombres.requestFocus();
	}

	// ELIMINAR
	protected void actionPerformedBtnEliminar(ActionEvent e) {

		tipoOperacion = ELIMINAR;

		habilitarEntradas(false);
		txtCodigo.setEditable(true);
		habilitarBotones(false);

		txtCodigo.requestFocus();
	}

	// ACEPTAR
	protected void actionPerformedBtnAceptar(ActionEvent e) {

		try {

			switch (tipoOperacion) {

			case ADICIONAR:
				adicionarAlumno();
				break;

			case CONSULTAR:
				consultarAlumno();

				if (alumnos.buscarPorCodigo(leerCodigo()) != null) {
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
				}
				break;

			case MODIFICAR:
				modificarAlumno();
				break;

			case ELIMINAR:
				eliminarAlumno();
				break;

			default:
				mensaje("Selecciona la operación");
			}

		} catch (Exception ex) {
			mensaje("Verifica los datos ingresados.");
		}
	}

	// VOLVER
	protected void actionPerformedBtnVolver(ActionEvent e) {

		limpieza();

		txtCodigo.setEditable(false);
		habilitarEntradas(false);
		habilitarBotones(true);

		cboEstado.setSelectedIndex(0);
		tipoOperacion = -1;
	}

	// LISTAR
	protected void actionPerformedBtnListar(ActionEvent e) {

		alumnos.eliminarTodo();
		alumnos.cargarDesdeBD();

		listar();

		mensaje("Se actualizaron los datos de la tabla.");
	}

	// BORRAR TODOS
	protected void actionPerformedBtnBorrar(ActionEvent e) {

		int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar TODOS los registros?",
				"Confirmar", JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {

			if (alumnos.eliminarTodosBD()) {

				alumnos.eliminarTodo();
				listar();

				mensaje("Todos los registros fueron eliminados.");

			} else {
				mensaje("No se pudieron eliminar los registros de MySQL.");
			}
		}
	}

	// AJUSTAR COLUMNAS
	void ajustarAnchoColumnas() {

		TableColumnModel tcm = tblTabla.getColumnModel();

		tcm.getColumn(0).setPreferredWidth(anchoColumna(10));
		tcm.getColumn(1).setPreferredWidth(anchoColumna(15));
		tcm.getColumn(2).setPreferredWidth(anchoColumna(20));
		tcm.getColumn(3).setPreferredWidth(anchoColumna(15));
		tcm.getColumn(4).setPreferredWidth(anchoColumna(15));
		tcm.getColumn(5).setPreferredWidth(anchoColumna(15));
		tcm.getColumn(6).setPreferredWidth(anchoColumna(20));
	}

	// LIMPIAR CAMPOS
	void limpieza() {

		txtCodigo.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDni.setText("");
		txtEdad.setText("");
		txtCelular.setText("");

		txtCodigo.requestFocus();
	}

	// LISTAR ALUMNOS
	void listar() {

		modelo.setRowCount(0);

		for (int i = 0; i < alumnos.tamagno(); i++) {

			Alumno a = alumnos.obtener(i);

			Object[] fila = { a.getCodAlumno(), a.getNombres(), a.getApellidos(), a.getDni(), a.getEdad(),
					a.getCelular(), a.getEstadoTexto() };

			modelo.addRow(fila);
		}
	}

	// CONSULTAR ALUMNO EN MYSQL
	void consultarAlumno() {

		try {

			Alumno x = alumnos.buscarPorCodigoBD(leerCodigo());

			if (x != null) {

				txtNombres.setText(x.getNombres());
				txtApellidos.setText(x.getApellidos());
				txtDni.setText(x.getDni());
				txtEdad.setText("" + x.getEdad());
				txtCelular.setText("" + x.getCelular());

				cboEstado.setSelectedIndex(x.getEstado());

				txtCodigo.requestFocus();

			} else {

				error("El código " + leerCodigo() + " no existe", txtCodigo);
			}

		} catch (Exception e) {

			error("Ingrese CÓDIGO correcto", txtCodigo);
		}
	}

	// ADICIONAR ALUMNO A MYSQL
	void adicionarAlumno() {

		try {

			int codigo = leerCodigo();
			String nombre = leerNombres();
			String apellidos = leerApellidos();
			String dni = leerDni();

			if (nombre.isEmpty()) {
				error("Ingrese NOMBRES correctamente", txtNombres);
				return;
			}

			if (apellidos.isEmpty()) {
				error("Ingrese APELLIDOS correctamente", txtApellidos);
				return;
			}

			if (alumnos.buscarPorDni(dni) != null) {
				error("El DNI " + dni + " ya existe", txtDni);
				return;
			}

			int edad;

			try {
				edad = leerEdad();
			} catch (NumberFormatException ex) {
				error("Ingrese EDAD correctamente", txtEdad);
				return;
			}

			int celular;

			try {
				celular = leerCelular();
			} catch (NumberFormatException ex) {
				error("Ingrese CELULAR correctamente", txtCelular);
				return;
			}

			// CORREGIDO: TOMAR ESTADO DEL COMBOBOX
			int estado = leerEstado();

			Alumno nuevo = new Alumno(codigo, nombre, apellidos, dni, edad, celular, estado);

			if (alumnos.adicionarBD(nuevo)) {

				alumnos.adicionar(nuevo);
				listar();

				txtCodigo.setText("" + alumnos.codigoCorrelativo());

				txtNombres.setText("");
				txtApellidos.setText("");
				txtDni.setText("");
				txtEdad.setText("");
				txtCelular.setText("");

				cboEstado.setSelectedIndex(0);
				txtNombres.requestFocus();

				mensaje("Alumno adicionado correctamente.");

			} else {

				mensaje("No se pudo registrar el alumno en MySQL.");
			}

		} catch (NumberFormatException ex) {

			mensaje("Verifique el código ingresado.");
		}
	}

	// ELIMINAR ALUMNO
	void eliminarAlumno() {

		try {

			int codigo = leerCodigo();
			Alumno x = alumnos.buscarPorCodigo(codigo);

			if (x != null) {

				txtNombres.setText(x.getNombres());
				txtApellidos.setText(x.getApellidos());
				txtDni.setText(x.getDni());
				txtEdad.setText("" + x.getEdad());
				txtCelular.setText("" + x.getCelular());

				cboEstado.setSelectedIndex(x.getEstado());

				if (x.getEstado() == 0) {

					int resp = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al alumno registrado?",
							"Confirmación", JOptionPane.YES_NO_OPTION);

					if (resp == JOptionPane.YES_OPTION) {

						if (alumnos.eliminarBD(codigo)) {

							alumnos.eliminar(x);
							listar();
							limpieza();
							habilitarBotones(true);

							mensaje("Alumno eliminado correctamente.");

						} else {

							mensaje("No se pudo eliminar el alumno de MySQL.");
						}
					}

				} else {

					error("No se puede eliminar al alumno. " + "Su estado debe ser Registrado (0).", txtCodigo);
				}

			} else {

				error("El código " + codigo + " no existe", txtCodigo);
			}

		} catch (NumberFormatException ex) {

			error("Ingrese CÓDIGO correcto", txtCodigo);
		}
	}

	// MODIFICAR ALUMNO
	void modificarAlumno() {

		try {

			int codigo = leerCodigo();
			Alumno x = alumnos.buscarPorCodigo(codigo);

			if (x == null) {

				error("El código " + codigo + " no existe", txtCodigo);
				return;
			}

			String nombres = leerNombres();
			String apellidos = leerApellidos();

			if (nombres.isEmpty()) {
				error("Ingrese NOMBRES correctamente", txtNombres);
				return;
			}

			if (apellidos.isEmpty()) {
				error("Ingrese APELLIDOS correctamente", txtApellidos);
				return;
			}

			int edad;

			try {
				edad = leerEdad();
			} catch (NumberFormatException ex) {
				error("Ingrese EDAD correctamente", txtEdad);
				return;
			}

			int celular;

			try {
				celular = leerCelular();
			} catch (NumberFormatException ex) {
				error("Ingrese CELULAR correctamente", txtCelular);
				return;
			}

			// CORREGIDO: UTILIZAR ESTADO SELECCIONADO
			int estado = leerEstado();

			Alumno actualizado = new Alumno(codigo, nombres, apellidos, x.getDni(), edad, celular, estado);

			if (alumnos.modificarBD(actualizado)) {

				x.setNombres(nombres);
				x.setApellidos(apellidos);
				x.setEdad(edad);
				x.setCelular(celular);

				// ACTUALIZAR ESTADO EN EL OBJETO
				x.setEstado(estado);

				listar();
				txtCodigo.requestFocus();

				mensaje("Alumno modificado correctamente.");

			} else {

				mensaje("No se pudo modificar el alumno en MySQL.");
			}

		} catch (NumberFormatException ex) {

			error("Ingrese CÓDIGO correcto", txtCodigo);
		}
	}

	// CAMBIAR COLOR DE TEXTO
	void ponerTextoRojo(JTextField campo) {
		campo.setForeground(Color.BLUE);
	}

	// MOSTRAR ERROR
	void error(String s, JTextField txt) {

		mensaje(s);
		txt.setText("");
		txt.requestFocus();
	}

	// MOSTRAR MENSAJE
	void mensaje(String s) {
		JOptionPane.showMessageDialog(this, s);
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

	// HABILITAR ENTRADAS
	void habilitarEntradas(boolean sino) {

		txtNombres.setEditable(sino);
		txtApellidos.setEditable(sino);
		txtDni.setEditable(sino);
		txtEdad.setEditable(sino);
		txtCelular.setEditable(sino);

		cboEstado.setEnabled(sino);
	}

	// LEER DATOS
	int leerCodigo() {
		return Integer.parseInt(txtCodigo.getText().trim());
	}

	String leerNombres() {
		return txtNombres.getText().trim();
	}

	String leerApellidos() {
		return txtApellidos.getText().trim();
	}

	String leerDni() {
		return txtDni.getText().trim();
	}

	int leerEdad() {
		return Integer.parseInt(txtEdad.getText().trim());
	}

	int leerCelular() {
		return Integer.parseInt(txtCelular.getText().trim());
	}

	int leerEstado() {
		return cboEstado.getSelectedIndex();
	}

	int anchoColumna(int porcentaje) {
		return porcentaje * scrollPane.getWidth() / 100;
	}

	String enTextoEstadoCivil(int i) {
		return cboEstado.getItemAt(i);
	}
}
