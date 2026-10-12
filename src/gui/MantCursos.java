package gui;

import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import objetos.Curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MantCursos extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo, txtAsignatura, txtCreditos, txtHoras;
	private JButton btnAdicionar, btnConsultar, btnModificar, btnEliminar, btnAceptar, btnVolver;;
	private JScrollPane scrollPane;
	private JLabel lblTitulo, lblCodigo, lblAsignatura, lblCreditos, lblHoras, lblCiclo;
	private JComboBox<String> cboCiclo;
	private JTable tblTabla;
	private DefaultTableModel modelo;
	private JButton btnCargar;
	private JButton btnBorrar;

	// TIPO DE OPERACION A PROCESAR
	private int tipoOperacion;

	// CONSTANTES PARA TIPO DE OPERACION
	public final static int ADICIONAR = 0;
	public final static int CONSULTAR = 1;
	public final static int MODIFICAR = 2;
	public final static int ELIMINAR = 3;

	// Declaracion global
	ArregloCursos cursos;
	ArregloMatriculas matriculas;
	private JLabel lblNewLabel;

	/**
	 * Create the frame (solo interfaz).
	 */
	public MantCursos(ArregloCursos cursos) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MantCursos.class.getResource("/imagenes/logo.png")));
		this.cursos = cursos;
		matriculas = new ArregloMatriculas();
		setResizable(false);
		setTitle("Mantenimiento de Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 744, 600);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		lblTitulo = new JLabel("REGISTRO DE CURSOS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(260, 8, 300, 30);
		contentPane.add(lblTitulo);

		// Campos de texto y etiquetas
		lblCodigo = new JLabel("C�digo:");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(26, 86, 83, 20);
		contentPane.add(lblCodigo);
		txtCodigo = new JTextField();
		txtCodigo.setBounds(126, 86, 111, 20);
		txtCodigo.setEditable(false);
		contentPane.add(txtCodigo);

		lblAsignatura = new JLabel("Asignatura:");
		lblAsignatura.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAsignatura.setBounds(26, 116, 83, 20);
		contentPane.add(lblAsignatura);
		txtAsignatura = new JTextField();
		txtAsignatura.setBounds(125, 117, 189, 20);
		contentPane.add(txtAsignatura);

		lblCreditos = new JLabel("Creditos:");
		lblCreditos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCreditos.setBounds(26, 178, 83, 20);
		contentPane.add(lblCreditos);
		txtCreditos = new JTextField();
		txtCreditos.setBounds(126, 178, 73, 20);
		contentPane.add(txtCreditos);

		lblHoras = new JLabel("Horas:");
		lblHoras.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHoras.setBounds(36, 209, 73, 20);
		contentPane.add(lblHoras);
		txtHoras = new JTextField();
		txtHoras.setBounds(126, 209, 73, 20);
		contentPane.add(txtHoras);

		lblCiclo = new JLabel("Ciclo:");
		lblCiclo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCiclo.setBounds(36, 147, 73, 20);
		contentPane.add(lblCiclo);
		cboCiclo = new JComboBox<>();
		cboCiclo.setBounds(126, 148, 150, 20);
		cboCiclo.addItem("Primero (0)");
		cboCiclo.addItem("Segundo (1)");
		cboCiclo.addItem("Tercero (2)");
		cboCiclo.addItem("Cuarto (3)");
		cboCiclo.addItem("Quinto (4)");
		cboCiclo.addItem("Sexto (5)");
		contentPane.add(cboCiclo);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 269, 697, 270);
		contentPane.add(scrollPane);

		// Tabla (sin datos todav�a)
		tblTabla = new JTable();
		tblTabla.setBackground(new Color(255, 248, 220));
		tblTabla.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblTabla);

		modelo = new DefaultTableModel();
		modelo.addColumn("C�digo");
		modelo.addColumn("Asignatura");
		modelo.addColumn("Ciclo");
		modelo.addColumn("Creditos");
		modelo.addColumn("Horas");
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
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBounds(394, 86, 100, 45);
		contentPane.add(btnAdicionar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(102, 205, 170));
		btnConsultar.addActionListener(this);
		btnConsultar.setBounds(520, 86, 100, 45);
		contentPane.add(btnConsultar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(102, 205, 170));
		btnModificar.addActionListener(this);
		btnModificar.setBounds(394, 146, 100, 45);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(102, 205, 170));
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(520, 146, 100, 45);
		contentPane.add(btnEliminar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(new Color(240, 230, 140));
		btnAceptar.addActionListener(this);
		btnAceptar.setBounds(359, 210, 138, 30);
		contentPane.add(btnAceptar);

		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(240, 230, 140));
		btnVolver.addActionListener(this);
		btnVolver.setBounds(514, 210, 150, 30);
		contentPane.add(btnVolver);

		btnCargar = new JButton("LISTAR");
		btnCargar.setBackground(new Color(240, 230, 140));
		btnCargar.addActionListener(this);
		btnCargar.setBounds(36, 22, 77, 29);
		contentPane.add(btnCargar);

		btnBorrar = new JButton("BORRAR");
		btnBorrar.setBackground(new Color(250, 128, 114));
		btnBorrar.addActionListener(this);
		btnBorrar.setBounds(123, 22, 83, 29);
		contentPane.add(btnBorrar);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MantCursos.class.getResource("/imagenes/insignia_mini.jpg")));
		lblNewLabel.setBounds(631, 8, 53, 57);
		contentPane.add(lblNewLabel);

		ajustarAnchoColumnas();
		cursos.cargardesdeBD();
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
		txtCodigo.setText("");
		txtCodigo.setEditable(true);
		txtAsignatura.setText("");
		txtCreditos.setText("");
		txtHoras.setText("");
		habilitarEntradas(true);
		habilitarBotones(false);
		txtCodigo.requestFocus();
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
		habilitarEntradas(true);
		habilitarBotones(false);
		txtAsignatura.requestFocus();

	}

	protected void actionPerformedBtnEliminar(ActionEvent e) {
		tipoOperacion = ELIMINAR;

		// Si el txtCodigo NO est� vac�o (usado para Consultar primero), entonces:
		if (!txtCodigo.getText().trim().isEmpty()) {
			mensaje("Presione ACEPTAR para confirmar, luego nuevamente ACEPTAR para eliminar el CURSO");
			habilitarBotones(false);
		} else {
			// Si NO hay datos cargados en txtCodigo, habilita para que pueda ingresar el
			// c�digo
			txtCodigo.setEditable(true);
			habilitarEntradas(false);
			habilitarBotones(false);
			btnAceptar.setEnabled(true);
			txtCodigo.requestFocus();
		}
	}

	protected void actionPerformedBtnAceptar(ActionEvent e) {
		try {

			switch (tipoOperacion) {
			case ADICIONAR:
				adicionarCurso();
				break;
			case CONSULTAR:
				consultarCurso();
				if (cursos.buscarPorCodigo(leerCodigo()) != null) {
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
				}
				break;
			case MODIFICAR:
				modificarCurso();
				break;
			case ELIMINAR:
				eliminarCurso();
				break;
			default:
				mensaje("Selecciona la operaci�n");
			}
		} catch (Exception e1) {
			mensaje("Selecciona la operacion");
		}
	}

	protected void actionPerformedBtnVolver(ActionEvent e) {
		txtCodigo.setText("");
		txtAsignatura.setText("");
		txtCreditos.setText("");
		txtHoras.setText("");
		txtCodigo.setEditable(false);
		habilitarEntradas(false);
		habilitarBotones(true);
	}

	protected void actionPerformedBtnListar(ActionEvent e) {
		listar();
		mensaje("Se cargaron cursos de ejemplo");
	}

	protected void actionPerformedBtnBorrar(ActionEvent e) {
		int respuesta = JOptionPane.showConfirmDialog(this, "�Est�s seguro de eliminar TODOS los registros?",
				"Confirmar", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			if (cursos.eliminarTodosBD()) {
				cursos.eliminarTodo();
				listar();
				mensaje("Todos los registros fueron eliminados.");
			}

		}
	}

	// Metodos tipo void (sin parametros)
	void ajustarAnchoColumnas() {
		TableColumnModel tcm = tblTabla.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(anchoColumna(10)); // C�digo
		tcm.getColumn(1).setPreferredWidth(anchoColumna(30)); // Asignatura
		tcm.getColumn(2).setPreferredWidth(anchoColumna(20)); // Ciclo
		tcm.getColumn(3).setPreferredWidth(anchoColumna(20)); // Cr�ditos
		tcm.getColumn(4).setPreferredWidth(anchoColumna(20)); // Horas
	}

	void limpieza() {
		txtCodigo.setText("");
		txtAsignatura.setText("");
		txtCreditos.setText("");
		txtHoras.setText("");
		txtCodigo.requestFocus();
	}

	void listar() {
		// Limpiar todas las filas actuales para no repetir registros
		modelo.setRowCount(0);
		for (int i = 0; i < cursos.tamagno(); i++) {
			Object fila[] = { cursos.obtener(i).getCodCurso(), cursos.obtener(i).getAsignatura(),
					cursos.obtener(i).getCicloTexto(), cursos.obtener(i).getCreditos(), cursos.obtener(i).getHoras(), };
			modelo.addRow(fila);
		}
	}

	void consultarCurso() {
		try {
			int codigo = leerCodigo();
			Connection cn = Conexion.conectar();
			String sql = "SELECT * FROM curso where codCurso=?";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, codigo);
			java.sql.ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				txtAsignatura.setText(rs.getString("asignatura"));
				cboCiclo.setSelectedIndex(rs.getInt("ciclo"));
				txtCreditos.setText("" + rs.getInt("creditos"));
				txtHoras.setText("" + rs.getInt("horas"));
				txtCodigo.requestFocus();
			} else {
				error("El codigo" + codigo + "no existe", txtCodigo);
			}

			rs.close();
			ps.close();
			cn.close();
		} catch (NumberFormatException e) {
			error("Ingrese  codigo correcto", txtCodigo);

		} catch (SQLException e) {
			mensaje("Error al consultar curso en MySQL:" + e.getMessage());
		}
	}

	void adicionarCurso() {
		try {
			int codigo = Integer.parseInt(txtCodigo.getText().trim());
			// Validaci�n simple: entre 1000 y 9999
			if (codigo < 1000 || codigo > 9999) {
				error("El c�digo debe ser de 4 d�gitos (entre 1000 y 9999).", txtCodigo);
				return;
			}
			if (cursos.buscarPorCodigo(codigo) != null) {
				error("El curso con el c�digo " + codigo + " ya existe.", txtCodigo);
				return;
			}

			String asignatura = leerAsignatura();
			int ciclo = leerCiclo();
			int creditos = leerCreditos();
			int horas = leerHoras();

			if (asignatura.length() == 0) {
				error("Ingrese una asignatura válida.", txtAsignatura);
				return;
			}

			// CONEXION A MYSQL
			Connection cn = Conexion.conectar();
			String sql = "INSERT INTO  curso (codCurso,asignatura,ciclo,creditos,horas)VALUES(?,?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ps.setString(2, asignatura);
			ps.setInt(3, ciclo);
			ps.setInt(4, creditos);
			ps.setInt(5, horas);

			ps.executeUpdate();
			// agregar tambien al ArrayList

			Curso nuevo = new Curso(codigo, asignatura, ciclo, creditos, horas);

			cursos.adicionar(nuevo);
			ps.close();
			cn.close();
			listar();

			mensaje("Curso adicionado correctamente.");
			txtCodigo.setText("" + cursos.generarCodigo(1000, 9999));
			limpieza();
			txtCodigo.requestFocus();

		} catch (NumberFormatException e) {
			error("Ingrese datos numericos validos.", txtCodigo);

		} catch (SQLException e) {
			mensaje("Error al guardar curso en MySql:" + e.getMessage());
		}
	}

	void eliminarCurso() {
		try {
			int codigo = leerCodigo();
			Curso x = cursos.buscarPorCodigo(codigo);

			if (x != null) {
				// Verificaci�n clave
				if (matriculas.existeMatriculaConCurso(codigo)) {
					error("No se puede eliminar el curso. Hay alumnos matriculados en �l.", txtCodigo);
					return;
				}
				// Confirmamos si eliminamos
				int resp = JOptionPane.showConfirmDialog(this,
						"�Est� seguro de eliminar el curso registrado?\n" + "Curso: " + x.getAsignatura(),
						"Confirmaci�n", JOptionPane.YES_NO_OPTION);

				if (resp == JOptionPane.YES_OPTION) {

					if (!cursos.eliminarBD(codigo)) {
						error("No se puedo eliminar el curso de MySQL", txtCodigo);
						return;
					}
					cursos.eliminar(x);
					listar();
					limpieza();
					habilitarBotones(true);
					mensaje("Curso eliminado correctamente.");
				}
			} else {
				error("El c�digo " + codigo + " no existe", txtCodigo);
			}
		} catch (Exception e) {
			error("Ingrese C�DIGO correctamente", txtCodigo);
		}
	}

	void modificarCurso() {
		try {
			int codigo = leerCodigo();
			Curso x = cursos.buscarPorCodigo(codigo);
			if (x != null) {
				String asignatura = leerAsignatura();
				int ciclo = leerCiclo();
				int creditos = leerCreditos();
				int horas = leerHoras();

				if (asignatura.length() == 0) {
					error("Ingrese ASIGNATURA correctamente", txtAsignatura);
					return;
				}

				x.setAsignatura(asignatura);
				x.setCiclo(ciclo);
				x.setCreditos(creditos);
				x.setHoras(horas);
				if (!cursos.modificarDB(x)) {
					error("No se puedo modificar curso en MySQL", txtCodigo);
					return;
				}

				listar();
				limpieza();
				habilitarBotones(true);
				mensaje("Curso modificado correctamente.");
			} else {
				error("El c�digo " + codigo + " no existe", txtCodigo);
			}
		} catch (Exception e) {
			error("Verifique que todos los datos sean correctos", txtCodigo);
		}
	}

	// metodos tipo void (con par�metros)
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
		txtAsignatura.setEditable(sino);
		txtCreditos.setEditable(sino);
		txtHoras.setEditable(sino);
		cboCiclo.setEnabled(sino);
	}

	// METODOS QUE RETORNAN VALOR (SIN PARAMETROS)
	int leerCodigo() {
		return Integer.parseInt(txtCodigo.getText().trim());
	}

	String leerAsignatura() {
		return txtAsignatura.getText().trim();
	}

	int leerCreditos() {
		return Integer.parseInt(txtCreditos.getText().trim());
	}

	int leerHoras() {
		return Integer.parseInt(txtHoras.getText().trim());
	}

	int leerCiclo() {
		return cboCiclo.getSelectedIndex();
	}

	// METODO QUE RETORNAN VALOR
	int anchoColumna(int porcentaje) {
		return porcentaje * scrollPane.getWidth() / 100;
	}

	String enTextoCiclo(int i) {
		return cboCiclo.getItemAt(i);
	}
}
