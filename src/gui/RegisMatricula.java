
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
import objetos.Alumno;
import objetos.Matricula;

public class RegisMatricula extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JTextField txtNumMatricula;
    private JTextField txtCodAlumno;
    private JTextField txtCodCurso;
    private JTextField txtFecha;
    private JTextField txtHora;

    private JLabel lblHora;
    private JLabel lblFecha;
    private JLabel lblCodCurso;
    private JLabel lblCodAlumno;
    private JLabel lblNumMat;
    private JLabel lblNewLabel;

    private JButton btnAdicionar;
    private JButton btnConsultar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnAceptar;
    private JButton btnVolver;

    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;

    private int tipoOperacion = -1;

    public static final int ADICIONAR = 0;
    public static final int CONSULTAR = 1;
    public static final int MODIFICAR = 2;
    public static final int ELIMINAR = 3;

    ArregloMatriculas matriculas;
    ArregloAlumnos alumnos;
    ArregloCursos cursos;

    public RegisMatricula(
            ArregloMatriculas matriculas,
            ArregloAlumnos alumnos,
            ArregloCursos cursos) {

        this.matriculas = matriculas;
        this.alumnos = alumnos;
        this.cursos = cursos;

        // ICONO DEL PROYECTO
        java.net.URL icono = RegisMatricula.class.getResource(
                "/imagenes/ICONO.LOGO.png");

        if (icono != null) {
            setIconImage(
                Toolkit.getDefaultToolkit().getImage(icono));
        }

        setTitle("Registro de Matrículas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 705, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(143, 188, 143));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // TITULO
        JLabel lblTitulo =
                new JLabel("REGISTRO DE MATRÍCULAS");

        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(230, 10, 300, 30);
        contentPane.add(lblTitulo);

        // NUMERO DE MATRICULA
        lblNumMat = new JLabel("N° Matrícula:");
        lblNumMat.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNumMat.setForeground(Color.WHITE);
        lblNumMat.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNumMat.setBounds(79, 60, 90, 20);
        contentPane.add(lblNumMat);

        txtNumMatricula = new JTextField();
        txtNumMatricula.setBounds(179, 60, 100, 20);
        txtNumMatricula.setEditable(false);
        contentPane.add(txtNumMatricula);

        // CODIGO DE ALUMNO
        lblCodAlumno = new JLabel("Cod. Alumno:");
        lblCodAlumno.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCodAlumno.setForeground(Color.WHITE);
        lblCodAlumno.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCodAlumno.setBounds(79, 90, 90, 20);
        contentPane.add(lblCodAlumno);

        txtCodAlumno = new JTextField();
        txtCodAlumno.setBounds(179, 90, 100, 20);
        contentPane.add(txtCodAlumno);

        // CODIGO DE CURSO
        lblCodCurso = new JLabel("Cod. Curso:");
        lblCodCurso.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCodCurso.setForeground(Color.WHITE);
        lblCodCurso.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCodCurso.setBounds(79, 120, 90, 20);
        contentPane.add(lblCodCurso);

        txtCodCurso = new JTextField();
        txtCodCurso.setBounds(179, 120, 100, 20);
        contentPane.add(txtCodCurso);

        // FECHA
        lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFecha.setBounds(79, 150, 90, 20);
        contentPane.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(179, 150, 100, 20);
        txtFecha.setEditable(false);
        contentPane.add(txtFecha);

        // HORA
        lblHora = new JLabel("Hora:");
        lblHora.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblHora.setForeground(Color.WHITE);
        lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHora.setBounds(79, 180, 90, 20);
        contentPane.add(lblHora);

        txtHora = new JTextField();
        txtHora.setBounds(179, 180, 100, 20);
        txtHora.setEditable(false);
        contentPane.add(txtHora);

        // BOTON ADICIONAR
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(new Color(102, 205, 170));
        btnAdicionar.setBounds(336, 75, 120, 50);
        btnAdicionar.addActionListener(this);
        contentPane.add(btnAdicionar);

        // BOTON CONSULTAR
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBackground(new Color(102, 205, 170));
        btnConsultar.setBounds(483, 75, 120, 50);
        btnConsultar.addActionListener(this);
        contentPane.add(btnConsultar);

        // BOTON MODIFICAR
        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(102, 205, 170));
        btnModificar.setBounds(336, 150, 120, 50);
        btnModificar.addActionListener(this);
        contentPane.add(btnModificar);

        // BOTON ELIMINAR
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(102, 205, 170));
        btnEliminar.setBounds(483, 150, 120, 50);
        btnEliminar.addActionListener(this);
        contentPane.add(btnEliminar);

        // TABLA DE MATRICULAS
        model = new DefaultTableModel();

        model.addColumn("N° Matrícula");
        model.addColumn("Cod. Alumno");
        model.addColumn("Cod. Curso");
        model.addColumn("Fecha");
        model.addColumn("Hora");

        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer centrado =
                new DefaultTableCellRenderer();

        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centrado);
        }

        scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(
                new Color(255, 255, 224));

        scrollPane.setBounds(20, 265, 651, 151);
        contentPane.add(scrollPane);

        // BOTON ACEPTAR
        btnAceptar = new JButton("ACEPTAR");
        btnAceptar.setBackground(new Color(240, 230, 140));
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAceptar.setBounds(104, 229, 100, 25);
        btnAceptar.addActionListener(this);
        contentPane.add(btnAceptar);

        // BOTON VOLVER
        btnVolver = new JButton("VOLVER");
        btnVolver.setBackground(new Color(240, 230, 140));
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnVolver.setBounds(229, 229, 100, 25);
        btnVolver.addActionListener(this);
        contentPane.add(btnVolver);

        // IMAGEN DE FONDO
        lblNewLabel = new JLabel("");

        java.net.URL fondo = RegisMatricula.class.getResource(
                "/imagenes/Fondo.Proyecto.jpg");

        if (fondo != null) {
            lblNewLabel.setIcon(new ImageIcon(fondo));
        }

        lblNewLabel.setBounds(0, -67, 778, 528);
        contentPane.add(lblNewLabel);

        // CARGAR DATOS DESDE MYSQL
        this.cursos.cargardesdeBD();
        this.matriculas.cargardesdeBD();
        this.alumnos.cargarDesdeBD();

        listar();

        habilitarEntradas(false);
        habilitarBotones(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnModificar) {
            actionPerformedBtnModificar(e);
        }

        if (e.getSource() == btnEliminar) {
            actionPerformedBtnEliminar(e);
        }

        if (e.getSource() == btnConsultar) {
            actionPerformedBtnConsultar(e);
        }

        if (e.getSource() == btnAdicionar) {
            actionPerformedBtnAdicionar(e);
        }

        if (e.getSource() == btnVolver) {
            actionPerformedBtnVolver(e);
        }

        if (e.getSource() == btnAceptar) {
            actionPerformedBtnAceptar(e);
        }
    }

    // ACEPTAR
    protected void actionPerformedBtnAceptar(ActionEvent e) {

        try {

            switch (tipoOperacion) {

                case ADICIONAR:
                    adicionarMatricula();
                    break;

                case CONSULTAR:
                    consultarMatricula();
                    break;

                case MODIFICAR:
                    modificarMatricula();
                    break;

                case ELIMINAR:
                    eliminarMatricula();
                    break;

                default:
                    mensaje("Seleccione la operación");
            }

        } catch (Exception ex) {
            mensaje("Error en la operación: " + ex.getMessage());
        }
    }

    // VOLVER
    protected void actionPerformedBtnVolver(ActionEvent e) {

        limpiarEntradas();

        txtNumMatricula.setEditable(false);
        habilitarEntradas(false);
        habilitarBotones(true);
    }

    // ADICIONAR
    protected void actionPerformedBtnAdicionar(ActionEvent e) {

        tipoOperacion = ADICIONAR;

        limpiarEntradas();

        txtNumMatricula.setText(
                "" + matriculas.codigoCorrelativo());

        txtNumMatricula.setEditable(false);

        habilitarEntradas(true);
        habilitarBotones(false);

        txtCodAlumno.requestFocus();
    }

    // CONSULTAR
    protected void actionPerformedBtnConsultar(ActionEvent e) {

        tipoOperacion = CONSULTAR;

        txtNumMatricula.setEditable(true);

        habilitarEntradas(false);
        habilitarBotones(false);

        btnAceptar.setEnabled(true);
        btnVolver.setEnabled(true);

        txtNumMatricula.requestFocus();
    }

    // ELIMINAR
    protected void actionPerformedBtnEliminar(ActionEvent e) {

        tipoOperacion = ELIMINAR;

        habilitarEntradas(false);
        habilitarBotones(false);

        btnAceptar.setEnabled(true);

        txtNumMatricula.setEditable(true);
        txtNumMatricula.requestFocus();
    }

    // MODIFICAR
    protected void actionPerformedBtnModificar(ActionEvent e) {

        tipoOperacion = MODIFICAR;

        txtNumMatricula.setEditable(
                txtNumMatricula.getText().trim().isEmpty());

        habilitarEntradas(false);

        txtCodCurso.setEditable(true);

        habilitarBotones(false);
        btnAceptar.setEnabled(true);

        if (txtNumMatricula.isEditable()) {
            txtNumMatricula.requestFocus();
        } else {
            txtCodCurso.requestFocus();
        }
    }

    // ADICIONAR MATRICULA EN MYSQL
    void adicionarMatricula() {

        try {

            int numMatricula = Integer.parseInt(
                    txtNumMatricula.getText().trim());

            int codAlumno = Integer.parseInt(
                    txtCodAlumno.getText().trim());

            int codCurso = Integer.parseInt(
                    txtCodCurso.getText().trim());

            // VALIDAR ALUMNO
            if (alumnos.buscarPorCodigo(codAlumno) == null) {

                error("El alumno con código "
                        + codAlumno + " no existe.",
                        txtCodAlumno);
                return;
            }

            // VALIDAR CURSO
            if (cursos.buscarPorCodigo(codCurso) == null) {

                error("El curso con código "
                        + codCurso + " no existe.",
                        txtCodCurso);
                return;
            }

            // EVITAR MATRICULAS DUPLICADAS
            if (matriculas.alumnoYaMatriculadoEnCurso(
                    codAlumno, codCurso)) {

                error("El alumno ya está matriculado "
                        + "en este curso.", txtCodAlumno);
                return;
            }

            String fecha = obtenerFechaActual();
            String hora = obtenerHoraActual();

            Matricula nueva = new Matricula(
                    numMatricula, codAlumno,
                    codCurso, fecha, hora);

            // GUARDAR EN MYSQL
            if (!matriculas.adicionarBD(nueva)) {

                mensaje("No se pudo guardar la matrícula en MySQL.");
                return;
            }

            // AGREGAR AL ARREGLO
            matriculas.adicionar(nueva);

            // ACTUALIZAR ESTADO DEL ALUMNO
            Alumno alum = alumnos.buscarPorCodigo(codAlumno);

            boolean estadoActualizado = true;

            if (alum != null) {

                int estadoAnterior = alum.getEstado();

                alum.setEstado(1);

                if (!alumnos.modificarBD(alum)) {

                    alum.setEstado(estadoAnterior);
                    estadoActualizado = false;
                }
            }

            listar();

            if (estadoActualizado) {

                mensaje("Matrícula adicionada correctamente.");

            } else {

                mensaje("La matrícula se guardó, pero no se pudo "
                        + "actualizar el estado del alumno en MySQL.");
            }

            limpiarEntradas();
            habilitarBotones(true);
            habilitarEntradas(false);

            txtNumMatricula.setEditable(false);

        } catch (NumberFormatException e) {

            mensaje("Ingrese datos numéricos válidos "
                    + "para el alumno y el curso.");
        }
    }

    // CONSULTAR MATRICULA
    void consultarMatricula() {

        try {

            int numMat = Integer.parseInt(
                    txtNumMatricula.getText().trim());

            Matricula m = matriculas.buscar(numMat);

            if (m != null) {

                txtCodAlumno.setText("" + m.getCodAlumno());
                txtCodCurso.setText("" + m.getCodCurso());
                txtFecha.setText(m.getFecha());
                txtHora.setText(m.getHora());

                txtNumMatricula.setEditable(false);
                txtCodAlumno.setEditable(false);
                txtCodCurso.setEditable(false);
                txtFecha.setEditable(false);
                txtHora.setEditable(false);

                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);

            } else {

                error("N° de matrícula " + numMat
                        + " no existe", txtNumMatricula);
            }

        } catch (NumberFormatException e) {

            error("Ingrese N° de matrícula válido",
                    txtNumMatricula);
        }
    }

    // MODIFICAR MATRICULA EN MYSQL
    void modificarMatricula() {

        try {

            int numMat = Integer.parseInt(
                    txtNumMatricula.getText().trim());

            Matricula m = matriculas.buscar(numMat);

            if (m == null) {

                error("N° de matrícula " + numMat
                        + " no existe.", txtNumMatricula);
                return;
            }

            int nuevoCodCurso = Integer.parseInt(
                    txtCodCurso.getText().trim());

            // VALIDAR QUE EXISTA EL CURSO
            if (cursos.buscarPorCodigo(nuevoCodCurso) == null) {

                error("El curso " + nuevoCodCurso
                        + " no existe.", txtCodCurso);
                return;
            }

            // EVITAR DUPLICADOS
            if (nuevoCodCurso != m.getCodCurso()
                    && matriculas.alumnoYaMatriculadoEnCurso(
                            m.getCodAlumno(), nuevoCodCurso)) {

                error("El alumno ya está matriculado "
                        + "en ese curso.", txtCodCurso);
                return;
            }

            int cursoAnterior = m.getCodCurso();

            m.setCodCurso(nuevoCodCurso);

            // MODIFICAR EN MYSQL
            if (!matriculas.modificarBD(m)) {

                m.setCodCurso(cursoAnterior);

                mensaje("No se pudo modificar "
                        + "la matrícula en MySQL.");
                return;
            }

            listar();

            mensaje("Matrícula modificada correctamente.");

            limpiarEntradas();
            habilitarBotones(true);
            habilitarEntradas(false);

            txtNumMatricula.setEditable(false);

        } catch (NumberFormatException e) {

            mensaje("Ingrese un número de matrícula "
                    + "y código de curso válidos.");
        }
    }

    // ELIMINAR MATRICULA DE MYSQL
    void eliminarMatricula() {

        try {

            int numMat = Integer.parseInt(
                    txtNumMatricula.getText().trim());

            Matricula m = matriculas.buscar(numMat);

            if (m == null) {

                error("La matrícula " + numMat
                        + " no existe.", txtNumMatricula);
                return;
            }

            Alumno alum = alumnos.buscarPorCodigo(
                    m.getCodAlumno());

            // NO ELIMINAR SI EL ALUMNO ESTA RETIRADO
            if (alum != null && alum.getEstado() == 2) {

                mensaje("No se puede eliminar. "
                        + "El alumno está en estado Retirado.");
                return;
            }

            int resp = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de eliminar la matrícula N° "
                            + numMat + "?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {

                // ELIMINAR EN MYSQL
                if (!matriculas.eliminarBD(numMat)) {

                    mensaje("No se pudo eliminar "
                            + "la matrícula en MySQL.");
                    return;
                }

                // ELIMINAR DEL ARREGLO
                matriculas.eliminar(numMat);

                listar();
                limpiarEntradas();
                habilitarBotones(true);

                txtNumMatricula.setEditable(false);

                mensaje("Matrícula eliminada correctamente.");
            }

        } catch (NumberFormatException e) {

            error("Ingrese un N° de matrícula válido.",
                    txtNumMatricula);
        }
    }

    // LISTAR MATRICULAS
    void listar() {

        model.setRowCount(0);

        for (int i = 0; i < matriculas.tamagno(); i++) {

            Matricula m = matriculas.obtener(i);

            Object[] fila = {
                m.getNumMatricula(),
                m.getCodAlumno(),
                m.getCodCurso(),
                m.getFecha(),
                m.getHora()
            };

            model.addRow(fila);
        }
    }

    // HABILITAR ENTRADAS
    void habilitarEntradas(boolean sino) {

        txtCodAlumno.setEditable(sino);
        txtCodCurso.setEditable(sino);

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

    // LIMPIAR ENTRADAS
    void limpiarEntradas() {

        txtNumMatricula.setText("");
        txtCodAlumno.setText("");
        txtCodCurso.setText("");
        txtFecha.setText("");
        txtHora.setText("");
    }

    // MOSTRAR MENSAJE
    void mensaje(String s) {

        JOptionPane.showMessageDialog(this, s);
    }

    // MOSTRAR ERROR
    void error(String s, JTextField txt) {

        mensaje(s);
        txt.setText("");
        txt.requestFocus();
    }

    // FECHA ACTUAL
    String obtenerFechaActual() {

        SimpleDateFormat formatoFecha =
                new SimpleDateFormat("dd/MM/yyyy");

        return formatoFecha.format(new Date());
    }

    // HORA ACTUAL
    String obtenerHoraActual() {

        SimpleDateFormat formatoHora =
                new SimpleDateFormat("HH:mm");

        return formatoHora.format(new Date());
    }
}
