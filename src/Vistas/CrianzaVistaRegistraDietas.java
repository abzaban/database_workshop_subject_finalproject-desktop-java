package Vistas;
import javax.swing.*;

import Controladores.CrianzaControladorRegistraDietas;

import java.awt.*;

public class CrianzaVistaRegistraDietas extends JDialog {
	
	private JTextField TxtNombre;
	private JTextArea TxtDescripcion;
	private JButton BtnGuardar, BtnLimpiar;
	
	public CrianzaVistaRegistraDietas() {
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(300, 280);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		JLabel EtqNombre = new JLabel("Nombre", JLabel.RIGHT);
		EtqNombre.setBounds(20, 30, 50, 20);
		add(EtqNombre);
		
		TxtNombre = new JTextField();
		TxtNombre.setBounds(71, 25, 200, 35);
		add(TxtNombre);
		
		JLabel EtqDesc = new JLabel("Descripción", JLabel.RIGHT);
		EtqDesc.setBounds(15, 81, 80, 20);
		add(EtqDesc);
		
		TxtDescripcion = new JTextArea();
		TxtDescripcion.setBounds(15, 102, 260, 80);
		add(TxtDescripcion);
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.setBounds(30, 200, 120, 35);
		add(BtnGuardar);
		
		BtnLimpiar = new JButton("Limpiar");
		BtnLimpiar.setBounds(160, 200, 120, 35);
		add(BtnLimpiar);
		
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public void setControlador(CrianzaControladorRegistraDietas C) {
		BtnGuardar.addActionListener(C);
		BtnLimpiar.addActionListener(C);
	}
	
	public void Muestra() {
		setVisible(true);
	}
	
	public JButton getBtnGuardar() {
		return BtnGuardar;
	}
	
	public JButton getBtnLimpiar() {
		return BtnLimpiar;
	}
	
	public JTextField getTxtNombre() {
		return TxtNombre;
	}
	
	public JTextArea getTxtDescripcion() {
		return TxtDescripcion;
	}
}
