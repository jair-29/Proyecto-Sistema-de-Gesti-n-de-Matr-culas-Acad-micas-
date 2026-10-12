package gui;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import arreglos.ArregloAlumnos;
import arreglos.ArregloCursos;
import arreglos.ArregloMatriculas;
import objetos.Alumno;
import objetos.Matricula;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

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
	private JButton btnAdicionar;
	private JButton btnConsultar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnAceptar;
	private JButton btnVolver;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;

	private int tipoOperacion;

	public final static int ADICIONAR = 0;
	public final static int CONSULTAR = 1;
	public final static int MODIFICAR = 2;
	public final static int ELIMINAR = 3;
	
	 // Declaraciones globales
	ArregloMatriculas matriculas;
	ArregloAlumnos alumnos;
	ArregloCursos cursos;
	private JLabel lblNewLabel;

	public RegisMatricula(ArregloMatriculas matriculas, ArregloAlumnos alumnos, ArregloCursos cursos) {
	    this.matriculas = matriculas;
	    this.alumnos = alumnos;
	    this.cursos = cursos;
	    this.cursos.cargardesdeBD();
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegisMatricula.class.getResource("/imagenes/logoPrincipal.jpeg")));
		setTitle("Registro de Matrículas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 705, 500);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título
		JLabel lblTitulo = new JLabel("REGISTRO DE MATRÍCULAS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(230, 10, 300, 30);
		contentPane.add(lblTitulo);

		// Número de matrícula (generado automáticamente)
		lblNumMat = new JLabel("N° Matrícula:");
		lblNumMat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumMat.setBounds(79, 60, 90, 20);
		contentPane.add(lblNumMat);
		txtNumMatricula = new JTextField();
		txtNumMatricula.setBounds(179, 60, 100, 20);
		txtNumMatricula.setEditable(false);
		contentPane.add(txtNumMatricula);

		// Código de alumno
		lblCodAlumno = new JLabel("Cod. Alumno:");
		lblCodAlumno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodAlumno.setBounds(79, 90, 90, 20);
		contentPane.add(lblCodAlumno);
		txtCodAlumno = new JTextField();
		txtCodAlumno.setBounds(179, 90, 100, 20);
		contentPane.add(txtCodAlumno);

		// Código de curso
		lblCodCurso = new JLabel("Cod. Curso:");
		lblCodCurso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodCurso.setBounds(79, 120, 90, 20);
		contentPane.add(lblCodCurso);
		txtCodCurso = new JTextField();
		txtCodCurso.setBounds(179, 120, 100, 20);
		contentPane.add(txtCodCurso);

		// Fecha
		lblFecha = new JLabel("Fecha:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setBounds(79, 150, 90, 20);
		contentPane.add(lblFecha);
		txtFecha = new JTextField();
		txtFecha.setBounds(179, 150, 100, 20);
		txtFecha.setEditable(false); // Será llenado automáticamente
		contentPane.add(txtFecha);

		// Hora
		lblHora = new JLabel("Hora:");
		lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHora.setBounds(79, 180, 90, 20);
		contentPane.add(lblHora);
		txtHora = new JTextField();
		txtHora.setBounds(179, 180, 100, 20);
		txtHora.setEditable(false); // Será llenado automáticamente
		contentPane.add(txtHora);

		// Botones
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(102, 205, 170));
		btnAdicionar.addActionListener(this);
		btnAdicionar.setBounds(336, 75, 120, 50);
		contentPane.add(btnAdicionar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(102, 205, 170));
		btnConsultar.addActionListener(this);
		btnConsultar.setBounds(483, 75, 120, 50);
		contentPane.add(btnConsultar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(102, 205, 170));
		btnModificar.addActionListener(this);
		btnModificar.setBounds(336, 150, 120, 50);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(102, 205, 170));
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(483, 150, 120, 50);
		contentPane.add(btnEliminar);

		// Tabla de visualización
		model = new DefaultTableModel();
		model.addColumn("N° Matrícula");
		model.addColumn("Cod. Alumno");
		model.addColumn("Cod. Curso");
		model.addColumn("Fecha");
		model.addColumn("Hora");
		table = new JTable(model);
		table.setBackground(new Color(255, 248, 220));
		// Centrar texto en todas las columnas
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(new Color(255, 255, 224)); 
		scrollPane.setBounds(20, 265, 651, 184);
		contentPane.add(scrollPane);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBackground(new Color(240, 230, 140));
		btnAceptar.addActionListener(this);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAceptar.setBounds(104, 229, 100, 25);
		contentPane.add(btnAceptar);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.setBackground(new Color(240, 230, 140));
		btnVolver.addActionListener(this);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnVolver.setBounds(229, 229, 100, 25);
		contentPane.add(btnVolver);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RegisMatricula.class.getResource("/imagenes/insignia_mini.jpg")));
		lblNewLabel.setBounds(616, 10, 55, 60);
		contentPane.add(lblNewLabel);
	    matriculas.cargardesdeBD();
	    alumnos.cargarDesdeBD();
		listar();
	}
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
	    } catch (Exception e1) {
	        mensaje("Error en la operación");
	    }
	}
	protected void actionPerformedBtnVolver(ActionEvent e) {
	    // Limpiar todos los campos de texto
	    txtNumMatricula.setText(""); 
	    txtCodAlumno.setText(""); 
	    txtCodCurso.setText(""); 
	    txtFecha.setText(""); 
	    txtHora.setText(""); 

	    // Habilitar entradas y botones al estado inicial
	    habilitarEntradas(false);
	    habilitarBotones(true);
	}
	protected void actionPerformedBtnAdicionar(ActionEvent e) {
		tipoOperacion = ADICIONAR;
	    // Generas el siguiente código
	    txtNumMatricula.setText("" + matriculas.codigoCorrelativo());
	    habilitarEntradas(true);
	    habilitarBotones(false);
	    txtCodAlumno.requestFocus();
	}
	protected void actionPerformedBtnConsultar(ActionEvent e) {
	    tipoOperacion = CONSULTAR;

	    // Habilita solo NumMatricula
	    txtNumMatricula.setEditable(true);

	    // Deshabilita otros campos
	    txtCodAlumno.setEditable(false);
	    txtCodCurso.setEditable(false);
	    txtFecha.setEditable(false);
	    txtHora.setEditable(false);

	    habilitarBotones(false);
	    btnAceptar.setEnabled(true);
	    btnVolver.setEnabled(true);
	    txtNumMatricula.requestFocus();
	}
	protected void actionPerformedBtnEliminar(ActionEvent e) {
	    tipoOperacion = ELIMINAR;
	    habilitarEntradas(false);
	    habilitarBotones(false);
	    btnAceptar.setEnabled(true);
	    txtNumMatricula.setEditable(true);
	    txtNumMatricula.requestFocus();
	}
	protected void actionPerformedBtnModificar(ActionEvent e) {
	    tipoOperacion = MODIFICAR;
	    habilitarEntradas(false);
	    txtCodCurso.setEditable(true);
	    habilitarBotones(false);
	    btnAceptar.setEnabled(true);
	    txtCodCurso.requestFocus();
	}
	

    // MÉTODOS DE LÓGICA DE NEGOCIO
	void adicionarMatricula() {
	    try {
	        // Leer datos de la pantalla
	        int numMatricula = Integer.parseInt(txtNumMatricula.getText().trim());
	        int codAlumno = Integer.parseInt(txtCodAlumno.getText().trim());
	        int codCurso = Integer.parseInt(txtCodCurso.getText().trim());

	        // Validación: Ver si existe el alumno
	        if (alumnos.buscarPorCodigo(codAlumno) == null) {
	            error("El alumno con código " + codAlumno + " no existe.", txtCodAlumno);
	            return;
	        }

	        // Validación: Ver si existe el curso
	        if (cursos.buscarPorCodigo(codCurso) == null) {
	            error("El curso con código " + codCurso + " no existe.", txtCodCurso);
	            return;
	        }

	        // Verificación de que NO exista la misma matrícula para el mismo alumno y curso
	        if (matriculas.alumnoYaMatriculadoEnCurso(codAlumno, codCurso)) {
	            error("El alumno ya está matriculado en este curso.", txtCodAlumno);
	            return;
	        }

	        // Generar fecha y hora actuales
	        String fecha = obtenerFechaActual();
	        String hora = obtenerHoraActual();

	        // Crear y adicionar la matrícula
	        Matricula nueva = new Matricula(numMatricula, codAlumno, codCurso, fecha, hora);
	        if(!matriculas.adicionarBD(nueva)) {
	        	error("No se pudo guardar  la matricula en MySQL.",txtCodAlumno);
	        	return;
	        }
	        matriculas.adicionar(nueva);
	        listar();
	        
	        // Ahora cambiamos el estado del alumno a "Matriculado" (1)
	        Alumno alum = alumnos.buscarPorCodigo(codAlumno);
	        if (alum != null) {
	            alum.setEstado(1);
	            alumnos.modificarBD(alum);
	        }

	        mensaje("Matrícula adicionada correctamente.");
	        limpiarEntradas();
	        habilitarBotones(true);
	        habilitarEntradas(false);
	        txtNumMatricula.setText("" + matriculas.codigoCorrelativo());
	        txtCodAlumno.requestFocus();

	    } catch (NumberFormatException e) {
	        error("Ingrese datos numéricos válidos para Cód. Alumno y Cód. Curso.", txtCodAlumno);
	    }
	}

    void consultarMatricula() {
        try {
            int numMat = Integer.parseInt(txtNumMatricula.getText().trim());
            Matricula m = matriculas.buscar(numMat);
            if (m != null) {
                // Carga los datos
                txtCodAlumno.setText("" + m.getCodAlumno());
                txtCodCurso.setText("" + m.getCodCurso());
                txtFecha.setText(m.getFecha());
                txtHora.setText(m.getHora());

                // Ahora desactiva todas las entradas
                txtNumMatricula.setEditable(false);
                txtCodAlumno.setEditable(false);
                txtCodCurso.setEditable(false);
                txtFecha.setEditable(false);
                txtHora.setEditable(false);

                // Ahora habilita los botones para continuar
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);

            } else {
                error("N° de matrícula " + numMat + " no existe", txtNumMatricula);
            }
        } catch (NumberFormatException e) {
            error("Ingrese N° de matrícula válido", txtNumMatricula);
        }
    }

    void modificarMatricula() {
        // Cambia solo el curso matriculado
        try {
            int numMat = Integer.parseInt(txtNumMatricula.getText().trim());
            Matricula m = matriculas.buscar(numMat);
            if (m != null) {
            	int nuevoCodCurso = Integer.parseInt(txtCodCurso.getText().trim());

            	int cursoAnterior = m.getCodCurso();

            	m.setCodCurso(nuevoCodCurso);

            	if (!matriculas.modificarBD(m)) {
            	    m.setCodCurso(cursoAnterior);
            	    mensaje("No se pudo modificar la matrícula en MySQL.");
            	    return;
            	}
              
                
                listar();
                mensaje("Matrícula modificada correctamente.");
                
                limpiarEntradas();
                habilitarBotones(true);
                habilitarEntradas(false);
                
            } else {
                error("N° de matrícula " + numMat + " no existe.", txtNumMatricula);
            }
        } catch (NumberFormatException e) {
            error("Ingrese un N° de matrícula válido.", txtNumMatricula);
        }
    }

    void eliminarMatricula() {
        // Elimina solo si el estado del alumno NO es 2 (retirado).
        try {
            // Leer el número de matrícula
            int numMat = Integer.parseInt(txtNumMatricula.getText().trim());

            // Buscar la matrícula
            Matricula m = matriculas.buscar(numMat);
            if (m == null) {
                error("La matrícula " + numMat + " no existe.", txtNumMatricula);
                return;
            }

            // Verificar si el alumno correspondiente está retirado (estado == 2).
            Alumno alum = alumnos.buscarPorCodigo(m.getCodAlumno());
            
            if(alum !=null) {
            	System.out.println("Codigo alumno:"+alum.getCodAlumno());
            	System.out.println("Estado leido por java :"+alum.getEstado());
            }
            if (alum != null && alum.getEstado() == 2) {
                error("No se puede eliminar. El alumno está en estado 'Retirado'.", txtNumMatricula);
                return;
            }

            // Confirmación de eliminación
            int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar la matrícula N° " + numMat + "?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
            );
            if (resp == JOptionPane.YES_OPTION) {
                // Eliminar y actualizar tabla
            	if(!matriculas.eliminarBD(numMat)) {
            		mensaje("No se pudo eliminar matricula en MySQL");
            		return;
            	}
                matriculas.eliminar(numMat);
                listar();
                limpiarEntradas();
                habilitarBotones(true);
                mensaje("Matrícula eliminada correctamente.");
            }

        } catch (NumberFormatException e) {
            error("Ingrese un N° de matrícula válido.", txtNumMatricula);
        }
    }

    void listar() {
        // Llena la tabla con todas las matrículas actuales
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

    // MÉTODOS DE UTILIDAD
    void habilitarEntradas(boolean sino) {
        // Habilita o deshabilita los campos de texto
        txtCodAlumno.setEditable(sino);
        txtCodCurso.setEditable(sino);
    }

    void habilitarBotones(boolean sino) {
        // Habilita o deshabilita los botones según la operación
        if (tipoOperacion != ADICIONAR) 
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
    
    void limpiarEntradas() {
        txtNumMatricula.setText("");
        txtCodAlumno.setText("");
        txtCodCurso.setText("");
        txtFecha.setText("");
        txtHora.setText("");
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
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatoFecha.format(new Date());
    }

    String obtenerHoraActual() {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        return formatoHora.format(new Date());
    }
    
}
