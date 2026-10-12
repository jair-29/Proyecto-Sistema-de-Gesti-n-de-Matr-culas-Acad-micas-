package gui;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import objetos.Alumno;
import objetos.Curso;
import objetos.Matricula;

public class ConsAlumno extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// Campos de b�squeda
	private JTextField txtCodAlumno;
	private JTextField txtCodCurso;

	// �rea de resultados
	private JTextArea txtResultado;

	// Botones
	private JButton btnBuscarAlumno;
	private JButton btnBuscarCurso;
	private JButton btnVolver;

	 // Declaraciones globales
	ArregloMatriculas matriculas;
	ArregloAlumnos alumnos;
	ArregloCursos cursos;
	private JLabel lblNewLabel;

	public ConsAlumno(ArregloAlumnos alumnos, ArregloCursos cursos, ArregloMatriculas matriculas) {
        this.alumnos = alumnos;
        this.cursos = cursos;
        this.matriculas = matriculas;
        this.alumnos.cargarDesdeBD();
        this.cursos.cargardesdeBD();
        this.matriculas.cargardesdeBD();
		setTitle("Consulta: Alumnos y Cursos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("CONSULTA DE ALUMNOS Y CURSOS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(180, 10, 400, 30);
		contentPane.add(lblTitulo);

		// Secci�n Alumno
		JLabel lblAlumno = new JLabel("C�digo de Alumno:");
		lblAlumno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlumno.setBounds(30, 61, 131, 20);
		contentPane.add(lblAlumno);
		txtCodAlumno = new JTextField();
		txtCodAlumno.setBounds(170, 61, 100, 20);
		contentPane.add(txtCodAlumno);

		btnBuscarAlumno = new JButton("Buscar Alumno");
		btnBuscarAlumno.addActionListener(this);
		btnBuscarAlumno.setBackground(new Color(102, 205, 170));
		btnBuscarAlumno.setBounds(300, 61, 150, 25);
		contentPane.add(btnBuscarAlumno);

		// Secci�n Curso
		JLabel lblCurso = new JLabel("C�digo de Curso:");
		lblCurso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurso.setBounds(30, 101, 131, 20);
		contentPane.add(lblCurso);
		txtCodCurso = new JTextField();
		txtCodCurso.setBounds(170, 101, 100, 20);
		contentPane.add(txtCodCurso);

		btnBuscarCurso = new JButton("Buscar Curso");
		btnBuscarCurso.addActionListener(this);
		btnBuscarCurso.setBackground(new Color(102, 205, 170));
		btnBuscarCurso.setBounds(300, 101, 150, 25);
		contentPane.add(btnBuscarCurso);

		// �rea de resultados
		txtResultado = new JTextArea();
		txtResultado.setForeground(new Color(0, 0, 139));
		txtResultado.setBackground(new Color(255, 250, 205));
		txtResultado.setEditable(false);
		txtResultado.setFont(new Font("Monospaced", Font.BOLD, 14));

		JScrollPane scrollResultado = new JScrollPane(txtResultado);
		scrollResultado.setBounds(30, 150, 426, 298);
		contentPane.add(scrollResultado);
		
		btnVolver = new JButton("BORRAR");
		btnVolver.setBackground(new Color(250, 128, 114));
		btnVolver.addActionListener(this);
		btnVolver.setBounds(522, 74, 86, 30);
		contentPane.add(btnVolver);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ConsAlumno.class.getResource("/imagenes/insigna.jpg")));
		lblNewLabel.setBounds(489, 196, 150, 197);
		contentPane.add(lblNewLabel);
	}
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
	protected void actionPerformedBtnVolver(ActionEvent e) {
	    txtResultado.setText("");
	    txtCodAlumno.setText("");
	    txtCodCurso.setText("");
	    txtCodAlumno.requestFocus();
	}
	protected void actionPerformedBtnBuscarAlumno(ActionEvent e) {
        try {
            int cod = Integer.parseInt(txtCodAlumno.getText().trim());
            Alumno a = alumnos.buscarPorCodigo(cod);
            if (a == null) {
                mostrar("	Alumno no encontrado.");
                return;
            }
            String estado = switch (a.getEstado()) {
                case 0 -> "NO MATRICULADO";
                case 1 -> "MATRICULADO";
                case 2 -> "RETIRADO";
                default -> "DESCONOCIDO";
            };
            StringBuilder sb = new StringBuilder();
            sb.append("	\n	ALUMNO ENCONTRADO:\n");
            sb.append("	C�digo		: ").append(a.getCodAlumno()).append("\n");
            sb.append("	Nombre		: ").append(a.getNombres()).append("\n");
            sb.append("	Estado		: ").append(estado).append("\n");

            // Buscar matr�cula
            for (int i = 0; i < matriculas.tamagno(); i++) {
                Matricula m = matriculas.obtener(i);
                if (m.getCodAlumno() == cod) {
                    Curso c = cursos.buscarPorCodigo(m.getCodCurso());
                    if (c != null) {
                        sb.append("\n	CURSO MATRICULADO:\n");
                        sb.append("	Asignatura	: ").append(c.getAsignatura()).append("\n");
                        sb.append("	Cr�ditos	: ").append(c.getCreditos()).append("\n");
                        sb.append("	Horas		: ").append(c.getHoras()).append("\n");
                        sb.append("	Ciclo		: ").append(c.getCiclo()).append("\n");
                    }
                    break;
                }
            }

            mostrar(sb.toString());
        } catch (NumberFormatException ex) {
            mostrar("C�digo inv�lido...");
        }
	}
	protected void actionPerformedBtnBuscarCurso(ActionEvent e) {
        try {
            int cod = Integer.parseInt(txtCodCurso.getText().trim());
            Curso c = cursos.buscarPorCodigo(cod);
            if (c == null) {
                mostrar("Curso no encontrado...");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("	\n	CURSO ENCONTRADO:\n");
            sb.append("	C�digo	: ").append(c.getCodCurso()).append("\n");
            sb.append("	Asignatura	: ").append(c.getAsignatura()).append("\n");
            sb.append("	Ciclo		: ").append(c.getCiclo()).append("\n");
            sb.append("	Cr�ditos	: ").append(c.getCreditos()).append("\n");
            sb.append("	Horas		: ").append(c.getHoras()).append("\n");

            mostrar(sb.toString());
        } catch (NumberFormatException ex) {
            mostrar("C�digo inv�lido...");
        }
	}
	
    void mostrar(String s) {
        txtResultado.setText(s);
    }
}
