package Vistas;
import javax.swing.*;
import Controladores.CrianzaControladorRegistraCorrales;

public class CrianzaVistaRegistraCorrales extends JDialog {
	
	private JComboBox<String> CmbTipo;
	private JTextField TxtLimite;
	private JButton BtnGuardar, BtnLimpiar;
	
	public CrianzaVistaRegistraCorrales() {
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(300, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		JLabel EtqTipo = new JLabel("Tipo del corral", JLabel.RIGHT);
		EtqTipo.setBounds(15, 30, 100, 20);
		add(EtqTipo);
		
		CmbTipo = new JComboBox<String>();
		CmbTipo.addItem("Seleccione");
		CmbTipo.addItem("Sanas");
		CmbTipo.addItem("Enfermas");
		CmbTipo.setBounds(131, 25, 160, 30);
		add(CmbTipo);
		
		JLabel EtqLimite = new JLabel("Límite de crías", JLabel.RIGHT);
		EtqLimite.setBounds(15, 80, 100, 20);
		add(EtqLimite);
		
		TxtLimite = new JTextField();
		TxtLimite.setBounds(120, 75, 100, 30);
		add(TxtLimite);
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.setBounds(30, 120, 120, 35);
		add(BtnGuardar);
		
		BtnLimpiar = new JButton("Limpiar");
		BtnLimpiar.setBounds(160, 120, 120, 35);
		add(BtnLimpiar);
		
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public void setControlador(CrianzaControladorRegistraCorrales C) {
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
	
	public JComboBox<String> getCmbTipo() {
		return CmbTipo;
	}
	
	public JTextField getTxtLimite() {
		return TxtLimite;
	}
}
