
package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;

import Conexion.Conexion;
import jasper.ReporteJasperPDF;

public class ReportCurso extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnPDF;
	private JLabel lblFondo;
	private JLabel lblLogo;

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			try {
				ReportCurso frame = new ReportCurso();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ReportCurso() {

		setTitle("Reporte: Alumnos Matriculados por Curso");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(15, 42, 91));
		setContentPane(contentPane);

		// TITULO CENTRADO
		JLabel lblTitulo = new JLabel("ALUMNOS MATRICULADOS POR CURSO", SwingConstants.CENTER);

		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(40, 40, 604, 35);
		contentPane.add(lblTitulo);

		// LOGO DE CIBERTEC
		lblLogo = new JLabel("", SwingConstants.CENTER);

		java.net.URL logo = ReportCurso.class.getResource("/imagenes/LOGO.jpg");

		if (logo != null) {

			ImageIcon icono = new ImageIcon(logo);

			// Evita ampliar una imagen pequeña
			int anchoOriginal = icono.getIconWidth();
			int altoOriginal = icono.getIconHeight();

			if (anchoOriginal > 0 && altoOriginal > 0) {

				double escala = Math.min(1.0, Math.min(220.0 / anchoOriginal, 140.0 / altoOriginal));
				int ancho = (int) Math.round(anchoOriginal * escala);

				int alto = (int) Math.round(altoOriginal * escala);

				Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

				lblLogo.setIcon(new ImageIcon(imagen));
			}
		}

		lblLogo.setBounds(192, 115, 300, 200);
		contentPane.add(lblLogo);

		// BOTON EXPORTAR PDF CENTRADO
		btnPDF = new JButton("EXPORTAR PDF");

		btnPDF.setFont(new Font("Arial", Font.BOLD, 15));
		btnPDF.setBackground(new Color(255, 215, 0));
		btnPDF.setForeground(Color.BLACK);
		btnPDF.setFocusPainted(false);
		btnPDF.setBounds(247, 330, 190, 45);
		btnPDF.addActionListener(this);

		contentPane.add(btnPDF);

		// FONDO AZUL DEL PROYECTO
		lblFondo = new JLabel("");

		java.net.URL fondo = ReportCurso.class.getResource("/imagenes/Fondo.Proyecto.jpg");

		if (fondo != null) {

			ImageIcon iconoFondo = new ImageIcon(fondo);

			Image imagenFondo = iconoFondo.getImage().getScaledInstance(684, 461, Image.SCALE_SMOOTH);

			lblFondo.setIcon(new ImageIcon(imagenFondo));
		}

		lblFondo.setBounds(0, 0, 684, 461);
		contentPane.add(lblFondo);

		// COLOCAR FONDO DETRAS DE TODO
		contentPane.setComponentZOrder(lblFondo, contentPane.getComponentCount() - 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnPDF) {
			generarPDF();
		}
	}

	
	private void generarPDF() {
              ReporteJasperPDF reporte =new ReporteJasperPDF();
              reporte.generarReporte("CURSO");
	}



}
