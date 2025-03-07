package Vistas;
import java.util.*;
import javax.swing.*;
import ComboOrdenado.JComboOrdenado;
import Controladores.CrianzaControladorSacrificio;

public class CrianzaVistaSacrificio extends JDialog {
	
	private JComboBox<String> CmbCrias;
	private JButton BtnSacrificar;
	private ArrayList<String> Crias;
	
	public CrianzaVistaSacrificio(ArrayList<String> Crias) {
		this.Crias = Crias;
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(300, 180);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		JLabel EtqPri = new JLabel("Las crías enfermas, con 40 días o más, son:");
		EtqPri.setBounds(10, 20, 300, 20);
		
		CmbCrias = new JComboBox<String>();
		CmbCrias.setBounds(50, 40, 200, 60);
		ColocaCrias();
		
		BtnSacrificar = new JButton("Sacrificar");
		BtnSacrificar.setBounds(80, 100, 140, 40);
		
		add(EtqPri);
		add(CmbCrias);
		add(BtnSacrificar);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}
	
	public void ColocaCrias() {
		CmbCrias.addItem("Seleccione");
		for(int i = 0 ; i < Crias.size() ; i++)
			CmbCrias.addItem(Crias.get(i));
	}
	
	public void setControlador(CrianzaControladorSacrificio C) {
		BtnSacrificar.addActionListener(C);
	}
	
	public void Muestra() {
		setVisible(true);
	}
	
	public JComboBox<String> getCmbCrias() {
		return CmbCrias;
	}
}
