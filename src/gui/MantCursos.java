
package gui;

import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import objetos.Curso;
import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private JTextField txtCodigo, txtAsignatura;
    private JTextField txtCreditos, txtHoras;

    private JButton btnAdicionar, btnConsultar;
    private JButton btnModificar, btnEliminar;
    private JButton btnAceptar, btnVolver;
    private JButton btnCargar, btnBorrar;

    private JScrollPane scrollPane;

    private JLabel lblTitulo, lblCodigo, lblAsignatura;
    private JLabel lblCreditos, lblHoras, lblCiclo;
    private JLabel lblNewLabel;

    private JComboBox<String> cboCiclo;

    private JTable tblTabla;
    private DefaultTableModel modelo;

    private int tipoOperacion = -1;

    public static final int ADICIONAR = 0;
    public static final int CONSULTAR = 1;
    public static final int MODIFICAR = 2;
    public static final int ELIMINAR = 3;

    ArregloCursos cursos;
    ArregloMatriculas matriculas;

    public MantCursos(ArregloCursos cursos) {

        this.cursos = cursos;
        this.matriculas = new ArregloMatriculas();

        java.net.URL icono = MantCursos.class.getResource(
                "/imagenes/ICONO.LOGO.png");

        if (icono != null) {
            setIconImage(
                Toolkit.getDefaultToolkit().getImage(icono));
        }

        setResizable(false);
        setTitle("Mantenimiento de Cursos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 744, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(143, 188, 143));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // TITULO
        lblTitulo = new JLabel("REGISTRO DE CURSOS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(260, 8, 300, 30);
        contentPane.add(lblTitulo);

        // CODIGO
        lblCodigo = new JLabel("Código:");
        lblCodigo.setForeground(Color.WHITE);
        lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCodigo.setBounds(26, 86, 83, 20);
        contentPane.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(126, 86, 111, 20);
        txtCodigo.setEditable(false);
        contentPane.add(txtCodigo);

        // ASIGNATURA
        lblAsignatura = new JLabel("Asignatura:");
        lblAsignatura.setForeground(Color.WHITE);
        lblAsignatura.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAsignatura.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAsignatura.setBounds(26, 116, 83, 20);
        contentPane.add(lblAsignatura);

        txtAsignatura = new JTextField();
        txtAsignatura.setBounds(125, 117, 189, 20);
        contentPane.add(txtAsignatura);

        // CREDITOS
        lblCreditos = new JLabel("Créditos:");
        lblCreditos.setForeground(Color.WHITE);
        lblCreditos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCreditos.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCreditos.setBounds(26, 178, 83, 20);
        contentPane.add(lblCreditos);

        txtCreditos = new JTextField();
        txtCreditos.setBounds(126, 178, 73, 20);
        contentPane.add(txtCreditos);

        // HORAS
        lblHoras = new JLabel("Horas:");
        lblHoras.setForeground(Color.WHITE);
        lblHoras.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblHoras.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHoras.setBounds(36, 209, 73, 20);
        contentPane.add(lblHoras);

        txtHoras = new JTextField();
        txtHoras.setBounds(126, 209, 73, 20);
        contentPane.add(txtHoras);

        // CICLO
        lblCiclo = new JLabel("Ciclo:");
        lblCiclo.setForeground(Color.WHITE);
        lblCiclo.setFont(new Font("Tahoma", Font.PLAIN, 12));
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

        // TABLA
        scrollPane = new JScrollPane();
        scrollPane.setBounds(15, 269, 697, 270);
        contentPane.add(scrollPane);

        modelo = new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Asignatura");
        modelo.addColumn("Ciclo");
        modelo.addColumn("Créditos");
        modelo.addColumn("Horas");

        tblTabla = new JTable(modelo) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTabla.setBackground(Color.WHITE);
        tblTabla.setFillsViewportHeight(true);
        scrollPane.setViewportView(tblTabla);

        DefaultTableCellRenderer centrado =
                new DefaultTableCellRenderer();

        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tblTabla.getColumnCount(); i++) {
            tblTabla.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centrado);
        }

        tblTabla.getTableHeader().setResizingAllowed(false);

        // BOTON ADICIONAR
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(new Color(102, 205, 170));
        btnAdicionar.setBounds(394, 86, 100, 45);
        btnAdicionar.addActionListener(this);
        contentPane.add(btnAdicionar);

        // BOTON CONSULTAR
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBackground(new Color(102, 205, 170));
        btnConsultar.setBounds(520, 86, 100, 45);
        btnConsultar.addActionListener(this);
        contentPane.add(btnConsultar);

        // BOTON MODIFICAR
        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(102, 205, 170));
        btnModificar.setBounds(394, 146, 100, 45);
        btnModificar.addActionListener(this);
        contentPane.add(btnModificar);

        // BOTON ELIMINAR
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(102, 205, 170));
        btnEliminar.setBounds(520, 146, 100, 45);
        btnEliminar.addActionListener(this);
        contentPane.add(btnEliminar);

        // BOTON ACEPTAR
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBackground(new Color(240, 230, 140));
        btnAceptar.setBounds(359, 210, 138, 30);
        btnAceptar.addActionListener(this);
        contentPane.add(btnAceptar);

        // BOTON VOLVER
        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(240, 230, 140));
        btnVolver.setBounds(514, 210, 150, 30);
        btnVolver.addActionListener(this);
        contentPane.add(btnVolver);

        // BOTON LISTAR
        btnCargar = new JButton("LISTAR");
        btnCargar.setBackground(new Color(240, 230, 140));
        btnCargar.setBounds(36, 22, 77, 29);
        btnCargar.addActionListener(this);
        contentPane.add(btnCargar);

        // BOTON BORRAR
        btnBorrar = new JButton("BORRAR");
        btnBorrar.setBackground(new Color(250, 128, 114));
        btnBorrar.setBounds(123, 22, 83, 29);
        btnBorrar.addActionListener(this);
        contentPane.add(btnBorrar);

        // IMAGEN DE FONDO
        lblNewLabel = new JLabel("");

        java.net.URL fondo = MantCursos.class.getResource(
                "/imagenes/Fondo.Proyecto.jpg");

        if (fondo != null) {
            lblNewLabel.setIcon(new ImageIcon(fondo));
        }

        lblNewLabel.setBounds(0, 0, 728, 561);
        contentPane.add(lblNewLabel);

        // CARGAR DATOS DE MYSQL
        ajustarAnchoColumnas();

        cursos.cargardesdeBD();
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

        txtCodigo.setText("");
        txtCodigo.setEditable(true);

        txtAsignatura.setText("");
        txtCreditos.setText("");
        txtHoras.setText("");

        cboCiclo.setSelectedIndex(0);

        habilitarEntradas(true);
        habilitarBotones(false);

        txtCodigo.requestFocus();
    }

    // CONSULTAR
    protected void actionPerformedBtnConsultar(ActionEvent e) {

        tipoOperacion = CONSULTAR;

        txtCodigo.setEditable(true);
        habilitarEntradas(false);
        habilitarBotones(false);

        btnAceptar.setEnabled(true);
        txtCodigo.requestFocus();
    }

    // MODIFICAR
    protected void actionPerformedBtnModificar(ActionEvent e) {

        tipoOperacion = MODIFICAR;

        txtCodigo.setEditable(false);
        habilitarEntradas(true);
        habilitarBotones(false);

        txtAsignatura.requestFocus();
    }

    // ELIMINAR
    protected void actionPerformedBtnEliminar(ActionEvent e) {

        tipoOperacion = ELIMINAR;

        if (!txtCodigo.getText().trim().isEmpty()) {

            habilitarEntradas(false);
            habilitarBotones(false);

            mensaje("Presione ACEPTAR para eliminar el curso.");

        } else {

            txtCodigo.setEditable(true);
            habilitarEntradas(false);
            habilitarBotones(false);

            btnAceptar.setEnabled(true);
            txtCodigo.requestFocus();
        }
    }

    // ACEPTAR
    protected void actionPerformedBtnAceptar(ActionEvent e) {

        try {

            switch (tipoOperacion) {

                case ADICIONAR:
                    adicionarCurso();
                    break;

                case CONSULTAR:
                    consultarCurso();

                    if (cursos.buscarPorCodigo(
                            leerCodigo()) != null) {

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
                    mensaje("Selecciona la operación");
            }

        } catch (Exception ex) {

            mensaje("Verifique los datos ingresados.");
        }
    }

    // VOLVER
    protected void actionPerformedBtnVolver(ActionEvent e) {

        limpieza();

        txtCodigo.setEditable(false);
        habilitarEntradas(false);
        habilitarBotones(true);

        cboCiclo.setSelectedIndex(0);
    }

    // LISTAR
    protected void actionPerformedBtnListar(ActionEvent e) {

        cursos.cargardesdeBD();
        listar();

        mensaje("Se actualizaron los cursos de la tabla.");
    }

    // BORRAR TODOS
    protected void actionPerformedBtnBorrar(ActionEvent e) {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar TODOS los cursos?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            if (cursos.eliminarTodosBD()) {

                cursos.eliminarTodo();
                listar();

                mensaje("Todos los cursos fueron eliminados.");

            } else {

                mensaje("No se pudieron eliminar los cursos de MySQL.");
            }
        }
    }

    // AJUSTAR COLUMNAS
    void ajustarAnchoColumnas() {

        TableColumnModel tcm = tblTabla.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(anchoColumna(10));
        tcm.getColumn(1).setPreferredWidth(anchoColumna(30));
        tcm.getColumn(2).setPreferredWidth(anchoColumna(20));
        tcm.getColumn(3).setPreferredWidth(anchoColumna(20));
        tcm.getColumn(4).setPreferredWidth(anchoColumna(20));
    }

    // LIMPIAR CAMPOS
    void limpieza() {

        txtCodigo.setText("");
        txtAsignatura.setText("");
        txtCreditos.setText("");
        txtHoras.setText("");

        txtCodigo.requestFocus();
    }

    // LISTAR CURSOS
    void listar() {

        modelo.setRowCount(0);

        for (int i = 0; i < cursos.tamagno(); i++) {

            Curso c = cursos.obtener(i);

            Object[] fila = {
                c.getCodCurso(),
                c.getAsignatura(),
                c.getCicloTexto(),
                c.getCreditos(),
                c.getHoras()
            };

            modelo.addRow(fila);
        }
    }

    // CONSULTAR CURSO EN MYSQL
    void consultarCurso() {

        try {

            int codigo = leerCodigo();

            Connection cn = Conexion.conectar();

            if (cn == null) {
                mensaje("No se pudo conectar a MySQL.");
                return;
            }

            String sql =
                    "SELECT * FROM curso WHERE codCurso = ?";

            try (Connection conexion = cn;
                 PreparedStatement ps =
                         conexion.prepareStatement(sql)) {

                ps.setInt(1, codigo);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        txtAsignatura.setText(
                                rs.getString("asignatura"));

                        cboCiclo.setSelectedIndex(
                                rs.getInt("ciclo"));

                        txtCreditos.setText(
                                "" + rs.getInt("creditos"));

                        txtHoras.setText(
                                "" + rs.getInt("horas"));

                        txtCodigo.requestFocus();

                    } else {

                        error("El código " + codigo
                                + " no existe", txtCodigo);
                    }
                }
            }

        } catch (NumberFormatException e) {

            error("Ingrese CÓDIGO correcto", txtCodigo);

        } catch (SQLException e) {

            mensaje("Error al consultar curso en MySQL: "
                    + e.getMessage());
        }
    }

    // ADICIONAR CURSO EN MYSQL
    void adicionarCurso() {

        try {

            int codigo = leerCodigo();

            if (codigo < 1000 || codigo > 9999) {

                error("El código debe tener 4 dígitos "
                        + "(entre 1000 y 9999).", txtCodigo);
                return;
            }

            if (cursos.buscarPorCodigo(codigo) != null) {

                error("El curso con código " + codigo
                        + " ya existe.", txtCodigo);
                return;
            }

            String asignatura = leerAsignatura();

            if (asignatura.isEmpty()) {

                error("Ingrese una asignatura válida.",
                        txtAsignatura);
                return;
            }

            int ciclo = leerCiclo();
            int creditos = leerCreditos();
            int horas = leerHoras();

            Connection cn = Conexion.conectar();

            if (cn == null) {
                mensaje("No se pudo conectar a MySQL.");
                return;
            }

            String sql =
                    "INSERT INTO curso "
                    + "(codCurso, asignatura, ciclo, creditos, horas) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (Connection conexion = cn;
                 PreparedStatement ps =
                         conexion.prepareStatement(sql)) {

                ps.setInt(1, codigo);
                ps.setString(2, asignatura);
                ps.setInt(3, ciclo);
                ps.setInt(4, creditos);
                ps.setInt(5, horas);

                int filas = ps.executeUpdate();

                if (filas > 0) {

                    Curso nuevo = new Curso(
                            codigo, asignatura, ciclo,
                            creditos, horas);

                    cursos.adicionar(nuevo);
                    listar();

                    mensaje("Curso adicionado correctamente.");

                    limpieza();
                    txtCodigo.requestFocus();

                } else {

                    mensaje("No se pudo guardar el curso.");
                }
            }

        } catch (NumberFormatException e) {

            mensaje("Ingrese valores numéricos válidos "
                    + "en código, créditos y horas.");

        } catch (SQLException e) {

            mensaje("Error al guardar curso en MySQL: "
                    + e.getMessage());
        }
    }

    // ELIMINAR CURSO
    void eliminarCurso() {

        try {

            int codigo = leerCodigo();

            Curso x = cursos.buscarPorCodigo(codigo);

            if (x == null) {

                error("El código " + codigo
                        + " no existe", txtCodigo);
                return;
            }

            // Comprobar matrículas asociadas.
            // Es necesario tenerlas cargadas en el arreglo.
            matriculas.cargardesdeBD();

            if (matriculas.existeMatriculaConCurso(codigo)) {

                mensaje("No se puede eliminar el curso. "
                        + "Hay alumnos matriculados en él.");
                return;
            }

            int resp = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de eliminar el curso?\n"
                    + "Curso: " + x.getAsignatura(),
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {

                if (cursos.eliminarBD(codigo)) {

                    cursos.eliminar(x);
                    listar();
                    limpieza();
                    habilitarBotones(true);

                    mensaje("Curso eliminado correctamente.");

                } else {

                    mensaje("No se pudo eliminar el curso de MySQL.");
                }
            }

        } catch (NumberFormatException e) {

            error("Ingrese CÓDIGO correctamente", txtCodigo);
        }
    }

    // MODIFICAR CURSO
    void modificarCurso() {

        try {

            int codigo = leerCodigo();

            Curso x = cursos.buscarPorCodigo(codigo);

            if (x == null) {

                error("El código " + codigo
                        + " no existe", txtCodigo);
                return;
            }

            String asignatura = leerAsignatura();

            if (asignatura.isEmpty()) {

                error("Ingrese ASIGNATURA correctamente",
                        txtAsignatura);
                return;
            }

            int ciclo = leerCiclo();
            int creditos = leerCreditos();
            int horas = leerHoras();

            // Preparar un objeto nuevo para MySQL.
            Curso actualizado = new Curso(
                    codigo, asignatura, ciclo,
                    creditos, horas);

            if (cursos.modificarDB(actualizado)) {

                x.setAsignatura(asignatura);
                x.setCiclo(ciclo);
                x.setCreditos(creditos);
                x.setHoras(horas);

                listar();
                limpieza();
                habilitarBotones(true);

                mensaje("Curso modificado correctamente.");

            } else {

                mensaje("No se pudo modificar el curso en MySQL.");
            }

        } catch (NumberFormatException e) {

            mensaje("Verifique que créditos y horas "
                    + "sean números válidos.");
        }
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

        txtAsignatura.setEditable(sino);
        txtCreditos.setEditable(sino);
        txtHoras.setEditable(sino);

        cboCiclo.setEnabled(sino);
    }

    // LEER DATOS
    int leerCodigo() {

        return Integer.parseInt(
                txtCodigo.getText().trim());
    }

    String leerAsignatura() {

        return txtAsignatura.getText().trim();
    }

    int leerCreditos() {

        return Integer.parseInt(
                txtCreditos.getText().trim());
    }

    int leerHoras() {

        return Integer.parseInt(
                txtHoras.getText().trim());
    }

    int leerCiclo() {

        return cboCiclo.getSelectedIndex();
    }

    int anchoColumna(int porcentaje) {

        return porcentaje * scrollPane.getWidth() / 100;
    }

    String enTextoCiclo(int i) {

        return cboCiclo.getItemAt(i);
    }
}
