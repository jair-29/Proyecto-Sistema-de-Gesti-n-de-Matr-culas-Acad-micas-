
package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import Conexion.Conexion;
import jasper.ReporteJasperPDF;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ReportVigentes extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton btnPDF;
	private JLabel lblLogo;
	private JLabel lblFondo;

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			try {
				ReportVigentes frame = new ReportVigentes();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ReportVigentes() {
		

		setTitle("Reporte: Alumnos con Matrícula Vigente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(15, 42, 91));
		setContentPane(contentPane);

		// TITULO CENTRADO
		JLabel lblTitulo = new JLabel("ALUMNOS CON MATRÍCULA VIGENTE", SwingConstants.CENTER);

		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(40, 40, 604, 35);
		contentPane.add(lblTitulo);

		// LOGO DEL SISTEMA DE MATRICULA
		lblLogo = new JLabel("", SwingConstants.CENTER);

		java.net.URL logo = ReportVigentes.class.getResource("/imagenes/LOGO.jpg");

		if (logo != null) {

			ImageIcon icono = new ImageIcon(logo);

			int anchoOriginal = icono.getIconWidth();
			int altoOriginal = icono.getIconHeight();

			if (anchoOriginal > 0 && altoOriginal > 0) {

				double escala = Math.min(300.0 / anchoOriginal, 200.0 / altoOriginal);

				int ancho = (int) Math.round(anchoOriginal * escala);

				int alto = (int) Math.round(altoOriginal * escala);

				Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

				lblLogo.setIcon(new ImageIcon(imagen));
			}
		}

		lblLogo.setBounds(192, 115, 300, 200);
		contentPane.add(lblLogo);

		// BOTON EXPORTAR PDF
		btnPDF = new JButton("EXPORTAR PDF");
		btnPDF.setFont(new Font("Arial", Font.BOLD, 15));
		btnPDF.setBackground(new Color(255, 215, 0));
		btnPDF.setForeground(Color.BLACK);
		btnPDF.setFocusPainted(false);
		btnPDF.setBounds(247, 330, 190, 45);
		btnPDF.addActionListener(this);
		contentPane.add(btnPDF);

		// FONDO AZUL
		lblFondo = new JLabel("");

		java.net.URL fondo = ReportVigentes.class.getResource("/imagenes/Fondo.Proyecto.jpg");

		if (fondo != null) {

			ImageIcon iconoFondo = new ImageIcon(fondo);

			Image imagenFondo = iconoFondo.getImage().getScaledInstance(684, 461, Image.SCALE_SMOOTH);

			lblFondo.setIcon(new ImageIcon(imagenFondo));
		}

		lblFondo.setBounds(0, 0, 684, 461);
		contentPane.add(lblFondo);

		// FONDO DETRAS DE TODOS LOS COMPONENTES
		contentPane.setComponentZOrder(lblFondo, contentPane.getComponentCount() - 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnPDF) {
			generarPDF();
		}
	}

	// GENERAR PDF DESDE MYSQL
	private void generarPDF() {
              ReporteJasperPDF reporte=new ReporteJasperPDF();
              reporte.generarReporte("MATRICULADO");
		
	}


	
}
