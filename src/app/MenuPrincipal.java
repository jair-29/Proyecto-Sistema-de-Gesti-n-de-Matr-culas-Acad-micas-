
package app;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import gui.MantAlumno;
import gui.MantCursos;
import gui.RegisMatricula;
import gui.RegisRetiro;
import gui.ConsAlumno;
import gui.ConsMatricula;
import gui.ReportCurso;
import gui.ReportPendientes;
import gui.ReportVigentes;
import gui.Ayuda;

import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import arreglos.ArregloRetiros;

public class MenuPrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JMenuItem mntmAlumnos, mntmCursos;
    private JMenuItem mntmMatricula, mntmRetiro;
    private JMenuItem mntmConsAlumno, mntmConsMatricula;
    private JMenuItem mntmRepPendiente, mntmRepVigente, mntmRepCurso;
    private JMenuItem mntmAyuda;

    private JLabel lblLogoPrincipal;
    private JLabel lblColegio;

    // COLECCIONES ORIGINALES DEL PROYECTO
    public static ArregloAlumnos alumnos = new ArregloAlumnos();
    public static ArregloCursos cursos = new ArregloCursos();
    public static ArregloMatriculas matriculas = new ArregloMatriculas();
    public static ArregloRetiros retiros = new ArregloRetiros();

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                MenuPrincipal frame = new MenuPrincipal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // CONSTRUCTOR
    public MenuPrincipal() {

        setTitle("Sistema de Matrícula ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // ICONO DE LA VENTANA
        java.net.URL icono = MenuPrincipal.class.getResource(
                "/imagenes/ICONO.LOGO.png");

        if (icono != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(icono));
        }

        // BARRA DE MENÚ
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 128, 192));
        menuBar.setBorderPainted(false);
        setJMenuBar(menuBar);

        // MENÚ MANTENIMIENTO
        JMenu mnMantenimiento = new JMenu("Mantenimiento");
        configurarMenu(mnMantenimiento);
        menuBar.add(mnMantenimiento);

        mntmAlumnos = new JMenuItem("Alumnos");
        configurarItem(mntmAlumnos);
        mnMantenimiento.add(mntmAlumnos);

        mntmCursos = new JMenuItem("Cursos");
        configurarItem(mntmCursos);
        mnMantenimiento.add(mntmCursos);

        // MENÚ REGISTRO
        JMenu mnRegistro = new JMenu("Registro");
        configurarMenu(mnRegistro);
        menuBar.add(mnRegistro);

        mntmMatricula = new JMenuItem("Matrícula");
        configurarItem(mntmMatricula);
        mnRegistro.add(mntmMatricula);

        mntmRetiro = new JMenuItem("Retiro");
        configurarItem(mntmRetiro);
        mnRegistro.add(mntmRetiro);

        // MENÚ CONSULTA
        JMenu mnConsulta = new JMenu("Consulta");
        configurarMenu(mnConsulta);
        menuBar.add(mnConsulta);

        mntmConsAlumno = new JMenuItem("Alumnos y Cursos");
        configurarItem(mntmConsAlumno);
        mnConsulta.add(mntmConsAlumno);

        mntmConsMatricula = new JMenuItem("Matrículas y Retiros");
        configurarItem(mntmConsMatricula);
        mnConsulta.add(mntmConsMatricula);

        // MENÚ REPORTE
        JMenu mnReporte = new JMenu("Reporte");
        configurarMenu(mnReporte);
        menuBar.add(mnReporte);

        mntmRepPendiente = new JMenuItem("Pendientes");
        configurarItem(mntmRepPendiente);
        mnReporte.add(mntmRepPendiente);

        mntmRepVigente = new JMenuItem("Vigentes");
        configurarItem(mntmRepVigente);
        mnReporte.add(mntmRepVigente);

        mntmRepCurso = new JMenuItem("Por Curso");
        configurarItem(mntmRepCurso);
        mnReporte.add(mntmRepCurso);

        // MENÚ AYUDA
        JMenu mnAyuda = new JMenu("Ayuda");
        configurarMenu(mnAyuda);
        menuBar.add(mnAyuda);

        mntmAyuda = new JMenuItem("Acerca de...");
        configurarItem(mntmAyuda);
        mnAyuda.add(mntmAyuda);

        // PANEL PRINCIPAL
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // LOGO PRINCIPAL
        lblLogoPrincipal = new JLabel("");
        lblLogoPrincipal.setBackground(Color.WHITE);

        java.net.URL logoPrincipal = MenuPrincipal.class.getResource(
                "/imagenes/LOGO.jpg");

        if (logoPrincipal != null) {
            lblLogoPrincipal.setIcon(new ImageIcon(logoPrincipal));
        }

        lblLogoPrincipal.setBounds(48, 191, 676, 182);
        lblLogoPrincipal.setOpaque(true);
        contentPane.add(lblLogoPrincipal);

        // LOGO DEL COLEGIO
        lblColegio = new JLabel("");

        java.net.URL logoColegio = MenuPrincipal.class.getResource(
                "/imagenes/logo.cibertec2.png");

        if (logoColegio != null) {
            lblColegio.setIcon(new ImageIcon(logoColegio));
        }

        lblColegio.setBounds(235, 23, 247, 157);
        contentPane.add(lblColegio);

        contentPane.setComponentZOrder(lblColegio, 0);

        // CARGAR DATOS ORIGINALES
        alumnos.cargarAlumnos();
        cursos.cargarCursos();
        matriculas.cargarMatriculas();
        retiros.cargarRetiros();
    }

    // ESTILO DE LOS MENÚS
    private void configurarMenu(JMenu menu) {

        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Arial", Font.BOLD, 13));
    }

    // ESTILO DE LOS ELEMENTOS
    private void configurarItem(JMenuItem item) {

        item.setBackground(new Color(0, 128, 192));
        item.setForeground(Color.WHITE);
        item.setOpaque(true);
        item.setFont(new Font("Arial", Font.PLAIN, 13));
        item.addActionListener(this);
    }

    // EVENTOS ORIGINALES DEL PROYECTO
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == mntmAlumnos)
            new MantAlumno(alumnos).setVisible(true);

        if (e.getSource() == mntmCursos)
            new MantCursos(cursos).setVisible(true);

        if (e.getSource() == mntmMatricula)
            new RegisMatricula(matriculas, alumnos, cursos).setVisible(true);

        if (e.getSource() == mntmRetiro)
            new RegisRetiro(retiros, matriculas, alumnos, cursos).setVisible(true);

        if (e.getSource() == mntmConsAlumno)
            new ConsAlumno(alumnos, cursos, matriculas).setVisible(true);

        if (e.getSource() == mntmConsMatricula)
            new ConsMatricula(matriculas, retiros, alumnos, cursos).setVisible(true);

        if (e.getSource() == mntmRepPendiente)
            new ReportPendientes().setVisible(true);

        if (e.getSource() == mntmRepVigente)
            new ReportVigentes().setVisible(true);

        if (e.getSource() == mntmRepCurso)
            new ReportCurso().setVisible(true);

        if (e.getSource() == mntmAyuda)
            new Ayuda().setVisible(true);
    }
}
