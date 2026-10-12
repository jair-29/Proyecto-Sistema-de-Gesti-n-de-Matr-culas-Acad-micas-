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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MantAlumno extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo, txtNombres, txtApellidos, txtDni, txtEdad, txtCelular;
	private JButton btnAdicionar, btnConsultar, btnModificar, btnEliminar, btnAceptar, btnVolver;;
	private JScrollPane scrollPane;
	private JLabel lblTitulo, lblCodigo, lblNombres, lblApellidos, lblDni, lblEdad, lblCelular, lblEstado;
	private JComboBox<String> cboEstado;
	private JButton btnCargar;
	private JButton btnBorrar;

	private JTable tblTabla;
	private DefaultTableModel modelo;

	// TIPO DE OPERACION A PROCESAR
	private int tipoOperacion;

	// CONSTANTES PARA TIPO DE OPERACION
	public final static int ADICIONAR = 0;
	public final static int CONSULTAR = 1;
	public final static int MODIFICAR = 2;
	public final static int ELIMINAR = 3;

	// Declaracion global
	ArregloAlumnos alumnos;
	private JLabel lblNewLabel;

	public MantAlumno(ArregloAlumnos alumnos) {
		setBackground(new Color(154, 205, 50));
		this.alumnos = alumnos;
		setResizable(false);
		setTitle("Mantenimiento de Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 743, 591);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		lblTitulo = new JLabel("REGISTRO DE ALUMNOS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(250, 10, 300, 30);
		contentPane.add(lblTitulo);

		// Campos de texto y etiquetas
		lblCodigo = new JLabel("Código:");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(30, 60, 83, 20);
		contentPane.add(lblCodigo);
		txtCodigo = new JTextField();
		txtCodigo.setBounds(130, 60, 111, 20);
		txtCodigo.setEditable(false);
		contentPane.add(txtCodigo);

		lblNombres = new JLabel("Nombres:");
		lblNombres.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombres.setBounds(30, 90, 83, 20);
		contentPane.add(lblNombres);
		txtNombres = new JTextField();
		txtNombres.setBounds(130, 90, 220, 20);
		contentPane.add(txtNombres);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setBounds(30, 120, 83, 20);
		contentPane.add(lblApellidos);
		txtApellidos = new JTextField();
		txtApellidos.setBounds(130, 120, 220, 20);
		contentPane.add(txtApellidos);

		lblDni = new JLabel("DNI:");
		lblDni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDni.setBounds(30, 150, 83, 20);
		contentPane.add(lblDni);
		txtDni = new JTextField();
		txtDni.setBounds(130, 150, 111, 20);
		contentPane.add(txtDni);

		lblEdad = new JLabel("Edad:");
		lblEdad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEdad.setBounds(30, 180, 83, 20);
		contentPane.add(lblEdad);
		txtEdad = new JTextField();
		txtEdad.setBounds(130, 180, 50, 20);
		contentPane.add(txtEdad);

		lblCelular = new JLabel("Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setBounds(30, 210, 83, 20);
		contentPane.add(lblCelular);
		txtCelular = new JTextField();
		txtCelular.setBounds(130, 210, 111, 20);
		contentPane.add(txtCelular);

		lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setBounds(30, 240, 83, 20);
		contentPane.add(lblEstado);
		cboEstado = new JComboBox<>();
		cboEstado.setBounds(130, 240, 150, 20);
		cboEstado.addItem("Registrado (0)");
		cboEstado.addItem("Matriculado (1)");
		cboEstado.addItem("Retirado (2)");
		contentPane.add(cboEstado);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 287, 697, 252);
		contentPane.add(scrollPane);

		// Tabla (sin datos todavía)
		tblTabla = new JTable(modelo) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Esto evita que las celdas sean editables
			}
		};
		tblTabla.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tblTabla.setBackground(new Color(255, 248, 220));

		tblTabla.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblTabla);

		modelo = new DefaultTableModel();
		modelo.addColumn("Código");
		modelo.addColumn("Nombres");
		modelo.addColumn("Apellidos");
		modelo.addColumn("DNI");
		modelo.addColumn("Edad");
		modelo.addColumn("Celular");
		modelo.addColumn("Estado");
		tblTabla.setModel(modelo);

		// Centrar texto en todas las columnas
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tblTabla.getColumnCount(); i++) {
			tblTabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}

		tblTabla.getTableHeader().setResizingAllowed(false);

		// Botones
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(102, 205, 170));
		btnAdicionar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBounds(394, 95, 100, 45);
		contentPane.add(btnAdicionar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(102, 205, 170));
		btnConsultar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConsultar.addActionListener(this);
		btnConsultar.setBounds(520, 95, 100, 45);
		contentPane.add(btnConsultar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(102, 205, 170));
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnModificar.addActionListener(this);
		btnModificar.setBounds(394, 155, 100, 45);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(102, 205, 170));
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(520, 155, 100, 45);
		contentPane.add(btnEliminar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(new Color(240, 230, 140));
		btnAceptar.addActionListener(this);
		btnAceptar.setBounds(364, 223, 138, 30);
		contentPane.add(btnAceptar);

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(240, 230, 140));
		btnVolver.addActionListener(this);
		btnVolver.setBounds(512, 223, 150, 30);
		contentPane.add(btnVolver);

		btnCargar = new JButton("LISTAR");
		btnCargar.setBackground(new Color(240, 230, 140));
		btnCargar.addActionListener(this);
		btnCargar.setBounds(29, 10, 77, 29);
		contentPane.add(btnCargar);

		btnBorrar = new JButton("BORRAR");
		btnBorrar.setBackground(new Color(240, 128, 128));
		btnBorrar.addActionListener(this);
		btnBorrar.setBounds(116, 10, 83, 29);
		contentPane.add(btnBorrar);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MantAlumno.class.getResource("/imagenes/insignia_mini.jpg")));
		lblNewLabel.setBounds(635, 10, 55, 60);
		contentPane.add(lblNewLabel);

		ajustarAnchoColumnas();
		alumnos.eliminarTodo();
		alumnos.cargarDesdeBD();
		listar();
	}

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

	// BOTONES
	protected void actionPerformedBtnAdicionar(ActionEvent e) {
		tipoOperacion = ADICIONAR;
		txtCodigo.setText("" + alumnos.codigoCorrelativo());
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDni.setText("");
		txtEdad.setText("");
		txtCelular.setText("");
		habilitarEntradas(true);
		habilitarBotones(false);
		txtNombres.requestFocus();
	}

	protected void actionPerformedBtnConsultar(ActionEvent e) {
		tipoOperacion = CONSULTAR;
		txtCodigo.setEditable(true);
		habilitarEntradas(false);
		habilitarBotones(false);
		btnAceptar.setEnabled(true);
		txtCodigo.requestFocus();
	}

	protected void actionPerformedBtnModificar(ActionEvent e) {
		tipoOperacion = MODIFICAR;
		txtCodigo.setEditable(false);
		ponerTextoRojo(txtCodigo);
		habilitarEntradas(true);
		txtDni.setEditable(false);
		cboEstado.setEditable(false);
		ponerTextoRojo(txtDni);
		habilitarBotones(false);
		txtNombres.requestFocus();

	}

	protected void actionPerformedBtnEliminar(ActionEvent e) {
		tipoOperacion = ELIMINAR;
		txtCodigo.setEditable(true);
		habilitarBotones(false);
		txtCodigo.requestFocus(); // pone el cursor en el código
	}

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
		} catch (Exception e1) {
			mensaje("Selecciona la operacion");
		}
	}

	protected void actionPerformedBtnVolver(ActionEvent e) {
		txtCodigo.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDni.setText("");
		txtEdad.setText("");
		txtCelular.setText("");
		txtCodigo.setEditable(false);
		habilitarEntradas(false);
		habilitarBotones(true);
	}

	protected void actionPerformedBtnListar(ActionEvent e) {
		listar();
		mensaje("Se actualizaron los datos actuales en la tabla.");
	}

	protected void actionPerformedBtnBorrar(ActionEvent e) {
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar TODOS los registros?",
				"Confirmar", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			if (alumnos.eliminarTodosBD()) {
				alumnos.eliminarTodo();
				listar();
				mensaje("Todos los registros fueron eliminados.");
			}

		}
	}

	// Metodos tipo void (sin parametros)
	void ajustarAnchoColumnas() {
		TableColumnModel tcm = tblTabla.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(anchoColumna(10)); // código
		tcm.getColumn(1).setPreferredWidth(anchoColumna(15)); // nombre
		tcm.getColumn(2).setPreferredWidth(anchoColumna(20)); // apellidos
		tcm.getColumn(3).setPreferredWidth(anchoColumna(15)); // Dni
		tcm.getColumn(4).setPreferredWidth(anchoColumna(15)); // edad
		tcm.getColumn(5).setPreferredWidth(anchoColumna(15)); // celular
		tcm.getColumn(6).setPreferredWidth(anchoColumna(20)); // estado
	}

	void limpieza() {
		txtCodigo.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDni.setText("");
		txtEdad.setText("");
		txtCelular.setText("");
		txtCodigo.requestFocus();
	}

	void listar() {
		// Limpiar todas las filas actuales para no repetir registros
		modelo.setRowCount(0);
		for (int i = 0; i < alumnos.tamagno(); i++) {
			Object fila[] = { alumnos.obtener(i).getCodAlumno(), alumnos.obtener(i).getNombres(),
					alumnos.obtener(i).getApellidos(), alumnos.obtener(i).getDni(), alumnos.obtener(i).getEdad(),
					alumnos.obtener(i).getCelular(), alumnos.obtener(i).getEstadoTexto() };
			modelo.addRow(fila);
		}
	}

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
			} else
				error("El código " + leerCodigo() + " no existe", txtCodigo);
		} catch (Exception e) {
			error("Ingrese CÓDIGO correcto", txtCodigo);
		}
	}

	void adicionarAlumno() {
		int codigo = leerCodigo();
		String nombre = leerNombres();
		String apellidos = leerApellidos();
		if (nombre.length() > 0) {
			String dni = leerDni();
			if (alumnos.buscarPorDni(dni) == null) {
				try {
					int edad = leerEdad();
					try {
						int celular = leerCelular();
						int estado = 0;
						Alumno nuevo = new Alumno(codigo, nombre, apellidos, dni, edad, celular, estado);
						if (alumnos.adicionarBD(nuevo)) {
							alumnos.adicionar(nuevo);
						}
						listar();
						txtCodigo.setText("" + alumnos.codigoCorrelativo());
						txtNombres.setText("");
						txtApellidos.setText("");
						txtDni.setText("");
						txtEdad.setText("");
						txtCelular.setText("");
						txtNombres.requestFocus();
						mensaje("Alumno adicionado correctamente.");
					} catch (Exception e) {
						error("Ingrese CELULAR correctamente", txtCelular);
					}
				} catch (Exception e) {
					error("Ingrese EDAD correctamente", txtEdad);
				}
			} else {
				error("El DNI " + dni + " ya existe, ingrese otro", txtDni);
			}
		} else {
			error("Ingrese NOMBRES correctamente", txtNombres);
		}

	}

	void eliminarAlumno() {
		try {
			int codigo = leerCodigo();
			Alumno x = alumnos.buscarPorCodigo(codigo);
			if (x != null) {
				// Carga primero los datos en los campos
				txtNombres.setText(x.getNombres());
				txtApellidos.setText(x.getApellidos());
				txtDni.setText(x.getDni());
				txtEdad.setText("" + x.getEdad());
				txtCelular.setText("" + x.getCelular());
				cboEstado.setSelectedIndex(x.getEstado());
				// Ahora confirmamos si eliminamos
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
							error("no se puede eliminar al alumno de MySQL", txtCodigo);
						}

					}
				} else {
					error("No se puede eliminar al alumno, solo si su estado es 'Registrado' (0).", txtCodigo);
				}
			} else {
				error("El código " + codigo + " no existe", txtCodigo);
			}
		} catch (Exception e) {
			error("Ingrese CÓDIGO correcto", txtCodigo);
		}
	}

	void modificarAlumno() {
		try {
			Alumno x = alumnos.buscarPorCodigo(leerCodigo());
			if (x != null) {
				String nombres = leerNombres();
				String apellidos = leerApellidos();
				if (nombres.length() > 0) {
					if (apellidos.length() > 0) {
						try {
							int edad = leerEdad();
							try {
								int celular = leerCelular();
								int estado = 0;
								x.setNombres(nombres);
								x.setApellidos(apellidos);
								x.setEdad(edad);
								x.setCelular(celular);
								x.setEstado(estado);
								if (alumnos.modificarBD(x)) {
									listar();
									txtCodigo.requestFocus();
									mensaje("Alumno modificado correctamente");

								} else {

									error("No se pudo modificar alumno en MySQL", txtCodigo);
								}

							} catch (Exception e) {
								error("Ingrese CELULAR correcta", txtCelular);
							}
						} catch (Exception e) {
							error("Ingrese EDAD correcta", txtEdad);
						}
					} else {
						error("Ingrese APELLIDOS correctamente", txtApellidos);
					}
				} else {
					error("Ingrese NOMBRES correctamente", txtNombres);
				}
			} else {
				error("El código " + leerCodigo() + " no existe", txtCodigo);
			}
		} catch (Exception e) {
			error("Ingrese CÓDIGO correcto", txtCodigo);
		}
	}

	// metodos tipo void (con parámetros)
	void ponerTextoRojo(JTextField campo) {
		campo.setForeground(Color.blue);
	}

	void error(String s, JTextField txt) {
		mensaje(s);
		txt.setText("");
		txt.requestFocus();
	}

	void mensaje(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	void habilitarBotones(boolean sino) {
		if (tipoOperacion != ADICIONAR)
			btnAdicionar.setEnabled(sino);
		btnConsultar.setEnabled(sino);
		btnModificar.setEnabled(sino);
		btnEliminar.setEnabled(sino);
		if (tipoOperacion == CONSULTAR)
			btnAceptar.setEnabled(false);
		else
			btnAceptar.setEnabled(!sino);
		btnVolver.setEnabled(!sino);
	}

	void habilitarEntradas(boolean sino) {
		txtNombres.setEditable(sino);
		txtApellidos.setEditable(sino);
		txtDni.setEditable(sino);
		txtEdad.setEditable(sino);
		txtCelular.setEditable(sino);
		cboEstado.setEnabled(sino);
	}

	// METODOS QUE RETORNAN VALOR (SIN PARAMETROS)
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

	// METODO QUE RETORNAN VALOR
	int anchoColumna(int porcentaje) {
		return porcentaje * scrollPane.getWidth() / 100;
	}

	String enTextoEstadoCivil(int i) {
		return cboEstado.getItemAt(i);
	}
}
