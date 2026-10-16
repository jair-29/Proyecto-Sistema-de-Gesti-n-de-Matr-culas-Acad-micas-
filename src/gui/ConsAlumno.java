
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;

import objetos.Alumno;
import objetos.Curso;
import objetos.Matricula;

public class ConsAlumno extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    // Campos de búsqueda
    private JTextField txtCodAlumno;
    private JTextField txtCodCurso;

    // Área de resultados
    private JTextArea txtResultado;

    // Botones
    private JButton btnBuscarAlumno;
    private JButton btnBuscarCurso;
    private JButton btnVolver;

    // Arreglos originales
    ArregloMatriculas matriculas;
    ArregloAlumnos alumnos;
    ArregloCursos cursos;

    private JLabel lblNewLabel;

    public ConsAlumno(ArregloAlumnos alumnos,
                      ArregloCursos cursos,
                      ArregloMatriculas matriculas) {

        this.alumnos = alumnos;
        this.cursos = cursos;
        this.matriculas = matriculas;

        // CONSERVAMOS LA CARGA DESDE MYSQL
        this.alumnos.cargarDesdeBD();
        this.cursos.cargardesdeBD();
        this.matriculas.cargardesdeBD();

        setTitle("Consulta: Alumnos y Cursos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // PANEL PRINCIPAL
        contentPane = new JPanel();
        contentPane.setBackground(new Color(143, 188, 143));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // TÍTULO
        JLabel lblTitulo = new JLabel(
                "CONSULTA DE ALUMNOS Y CURSOS");

        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(158, 11, 400, 30);
        contentPane.add(lblTitulo);

        // SECCIÓN ALUMNO
        JLabel lblAlumno = new JLabel("Código de Alumno:");
        lblAlumno.setForeground(Color.WHITE);
        lblAlumno.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAlumno.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAlumno.setBounds(30, 61, 131, 20);
        contentPane.add(lblAlumno);

        txtCodAlumno = new JTextField();
        txtCodAlumno.setBounds(170, 61, 100, 20);
        contentPane.add(txtCodAlumno);

        btnBuscarAlumno = new JButton("Buscar Alumno");
        btnBuscarAlumno.setBackground(new Color(102, 205, 170));
        btnBuscarAlumno.setBounds(300, 61, 150, 25);
        btnBuscarAlumno.addActionListener(this);
        contentPane.add(btnBuscarAlumno);

        // SECCIÓN CURSO
        JLabel lblCurso = new JLabel("Código de Curso:");
        lblCurso.setForeground(Color.WHITE);
        lblCurso.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCurso.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCurso.setBounds(30, 101, 131, 20);
        contentPane.add(lblCurso);

        txtCodCurso = new JTextField();
        txtCodCurso.setBounds(170, 101, 100, 20);
        contentPane.add(txtCodCurso);

        btnBuscarCurso = new JButton("Buscar Curso");
        btnBuscarCurso.setBackground(new Color(102, 205, 170));
        btnBuscarCurso.setBounds(300, 101, 150, 25);
        btnBuscarCurso.addActionListener(this);
        contentPane.add(btnBuscarCurso);

        // ÁREA DE RESULTADOS
        txtResultado = new JTextArea();
        txtResultado.setForeground(new Color(0, 0, 139));
        txtResultado.setBackground(Color.WHITE);
        txtResultado.setEditable(false);
        txtResultado.setFont(
                new Font("Monospaced", Font.BOLD, 14));

        JScrollPane scrollResultado =
                new JScrollPane(txtResultado);

        scrollResultado.setBounds(30, 150, 426, 298);
        contentPane.add(scrollResultado);

        // BOTÓN BORRAR
        btnVolver = new JButton("BORRAR");
        btnVolver.setBackground(new Color(250, 128, 114));
        btnVolver.setBounds(522, 74, 86, 30);
        btnVolver.addActionListener(this);
        contentPane.add(btnVolver);

        // FONDO DEL PROYECTO COMPRIMIDO
        lblNewLabel = new JLabel("");

        java.net.URL fondo = ConsAlumno.class.getResource(
                "/imagenes/Fondo.Proyecto.jpg");

        if (fondo != null) {
            lblNewLabel.setIcon(new ImageIcon(fondo));
        }

        lblNewLabel.setBounds(0, 0, 684, 461);
        contentPane.add(lblNewLabel);
    }

    // EVENTOS ORIGINALES
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnBuscarCurso) {
            actionPerformedBtnBuscarCurso(e);
        }

        if (e.getSource() == btnBuscarAlumno) {
            actionPerformedBtnBuscarAlumno(e);
        }

        if (e.getSource() == btnVolver) {
            actionPerformedBtnVolver(e);
        }
    }

    // BORRAR CAMPOS
    protected void actionPerformedBtnVolver(ActionEvent e) {

        txtResultado.setText("");
        txtCodAlumno.setText("");
        txtCodCurso.setText("");
        txtCodAlumno.requestFocus();
    }

    // BUSCAR ALUMNO
    protected void actionPerformedBtnBuscarAlumno(ActionEvent e) {

        try {

            int cod = Integer.parseInt(
                    txtCodAlumno.getText().trim());

            Alumno a = alumnos.buscarPorCodigo(cod);

            if (a == null) {
                mostrar("\tAlumno no encontrado.");
                return;
            }

            String estado = switch (a.getEstado()) {
                case 0 -> "NO MATRICULADO";
                case 1 -> "MATRICULADO";
                case 2 -> "RETIRADO";
                default -> "DESCONOCIDO";
            };

            StringBuilder sb = new StringBuilder();

            sb.append("\t\n\tALUMNO ENCONTRADO:\n");
            sb.append("\tCódigo\t\t: ")
              .append(a.getCodAlumno()).append("\n");

            sb.append("\tNombre\t\t: ")
              .append(a.getNombres()).append("\n");

            sb.append("\tEstado\t\t: ")
              .append(estado).append("\n");

            // BUSCAR MATRÍCULA DEL ALUMNO
            for (int i = 0; i < matriculas.tamagno(); i++) {

                Matricula m = matriculas.obtener(i);

                if (m.getCodAlumno() == cod) {

                    Curso c = cursos.buscarPorCodigo(
                            m.getCodCurso());

                    if (c != null) {

                        sb.append("\n\tCURSO MATRICULADO:\n");

                        sb.append("\tAsignatura\t: ")
                          .append(c.getAsignatura()).append("\n");

                        sb.append("\tCréditos\t: ")
                          .append(c.getCreditos()).append("\n");

                        sb.append("\tHoras\t\t: ")
                          .append(c.getHoras()).append("\n");

                        sb.append("\tCiclo\t\t: ")
                          .append(c.getCiclo()).append("\n");
                    }

                    break;
                }
            }

            mostrar(sb.toString());

        } catch (NumberFormatException ex) {

            mostrar("Código inválido...");
        }
    }

    // BUSCAR CURSO
    protected void actionPerformedBtnBuscarCurso(ActionEvent e) {

        try {

            int cod = Integer.parseInt(
                    txtCodCurso.getText().trim());

            Curso c = cursos.buscarPorCodigo(cod);

            if (c == null) {
                mostrar("Curso no encontrado...");
                return;
            }

            StringBuilder sb = new StringBuilder();

            sb.append("\t\n\tCURSO ENCONTRADO:\n");

            sb.append("\tCódigo\t: ")
              .append(c.getCodCurso()).append("\n");

            sb.append("\tAsignatura\t: ")
              .append(c.getAsignatura()).append("\n");

            sb.append("\tCiclo\t\t: ")
              .append(c.getCiclo()).append("\n");

            sb.append("\tCréditos\t: ")
              .append(c.getCreditos()).append("\n");

            sb.append("\tHoras\t\t: ")
              .append(c.getHoras()).append("\n");

            mostrar(sb.toString());

        } catch (NumberFormatException ex) {

            mostrar("Código inválido...");
        }
    }

    // MOSTRAR RESULTADOS
    void mostrar(String s) {
        txtResultado.setText(s);
    }
}
