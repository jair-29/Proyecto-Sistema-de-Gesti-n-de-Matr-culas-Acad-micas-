package app;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Toolkit;

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

	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagenes/logo.png")));
		setTitle("Sistema de Matr\u00EDcula - CODEV STUDIO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setResizable(false);
		getContentPane().setLayout(null);

		// Crear barra de menú
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menú: Mantenimiento
		JMenu mnMantenimiento = new JMenu("Mantenimiento");
		menuBar.add(mnMantenimiento);

		mntmAlumnos = new JMenuItem("Alumnos");
		mntmAlumnos.addActionListener(this);
		mnMantenimiento.add(mntmAlumnos);

		mntmCursos = new JMenuItem("Cursos");
		mntmCursos.addActionListener(this);
		mnMantenimiento.add(mntmCursos);

		// Menú: Registro
		JMenu mnRegistro = new JMenu("Registro");
		menuBar.add(mnRegistro);

		mntmMatricula = new JMenuItem("Matrícula");
		mntmMatricula.addActionListener(this);
		mnRegistro.add(mntmMatricula);

		mntmRetiro = new JMenuItem("Retiro");
		mntmRetiro.addActionListener(this);
		mnRegistro.add(mntmRetiro);

		// Menú: Consulta
		JMenu mnConsulta = new JMenu("Consulta");
		menuBar.add(mnConsulta);

		mntmConsAlumno = new JMenuItem("Alumnos y Cursos");
		mntmConsAlumno.addActionListener(this);
		mnConsulta.add(mntmConsAlumno);

		mntmConsMatricula = new JMenuItem("Matrículas y Retiros");
		mntmConsMatricula.addActionListener(this);
		mnConsulta.add(mntmConsMatricula);

		// Menú: Reporte
		JMenu mnReporte = new JMenu("Reporte");
		menuBar.add(mnReporte);

		mntmRepPendiente = new JMenuItem("Pendientes");
		mntmRepPendiente.addActionListener(this);
		mnReporte.add(mntmRepPendiente);

		mntmRepVigente = new JMenuItem("Vigentes");
		mntmRepVigente.addActionListener(this);
		mnReporte.add(mntmRepVigente);

		mntmRepCurso = new JMenuItem("Por Curso");
		mntmRepCurso.addActionListener(this);
		mnReporte.add(mntmRepCurso);

		// Menú: Ayuda
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		mntmAyuda = new JMenuItem("Acerca de...");
		mntmAyuda.addActionListener(this);
		mnAyuda.add(mntmAyuda);

		// Panel principal vacío
		contentPane = new JPanel();
		contentPane.setBackground(new Color(60, 179, 113));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblLogoPrincipal = new JLabel("");
		lblLogoPrincipal.setIcon(new ImageIcon(getClass().getResource("/imagenes/logoPrincipal.jpeg")));
		lblLogoPrincipal.setBounds(17, 15, 700, 411);
		lblLogoPrincipal.setOpaque(true);
		contentPane.add(lblLogoPrincipal);
		
		lblColegio = new JLabel("");
		lblColegio.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/logoColegio.png")));
		lblColegio.setBounds(230, 50, 312, 63);
		contentPane.add(lblColegio);
		contentPane.setComponentZOrder(lblColegio, 0);
		setVisible(true);
	    
	    alumnos.cargarAlumnos();
	    cursos.cargarCursos();
	    matriculas.cargarMatriculas();
	    retiros.cargarRetiros();
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmAlumnos)
			new MantAlumno(alumnos).setVisible(true);
		if (e.getSource() == mntmCursos)
			new MantCursos(cursos).setVisible(true);
		if (e.getSource() == mntmMatricula)
			new RegisMatricula(matriculas, alumnos, cursos).setVisible(true);
		if (e.getSource() == mntmRetiro)
			new RegisRetiro(retiros,matriculas, alumnos,cursos).setVisible(true);
		if (e.getSource() == mntmConsAlumno)
			new ConsAlumno(alumnos,cursos,matriculas).setVisible(true);
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
