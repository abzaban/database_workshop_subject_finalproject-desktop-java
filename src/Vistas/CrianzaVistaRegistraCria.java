package Vistas;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import Controladores.CrianzaControladorRegistraCria;

public class CrianzaVistaRegistraCria extends JDialog {
	
	private ArrayList<String> Colores, Edos;
	private JDateChooser TxtTiempoProc;
	private JComboBox<String> CmbColores, CmbEdos;
	private JTextField TxtPeso, TxtCantGrasa;
	private JButton BtnGuardar, BtnLimpiar;
	
	public CrianzaVistaRegistraCria(ArrayList<String> Colores, ArrayList<String> Edos) {
		this.Colores = Colores;
		this.Edos = Edos;
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(300, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(0, 2));
		
		TxtPeso = new JTextField();
		TxtCantGrasa = new JTextField();
		TxtTiempoProc = new JDateChooser();
		TxtTiempoProc.getDateEditor().setDate(new Date());
		BtnGuardar = new JButton("Guardar");
		BtnLimpiar = new JButton("Limpiar");
		CmbColores = new JComboBox<String>();
		ColocaColores();
		CmbEdos = new JComboBox<String>();
		ColocaEdos();
		
		add(new JLabel("Peso", JLabel.RIGHT));
		add(TxtPeso);
		add(new JLabel("Cantidad de grasa", JLabel.RIGHT));
		add(TxtCantGrasa);
		add(new JLabel("Color de músculo", JLabel.RIGHT));
		add(CmbColores);
		add(new JLabel("Estado", JLabel.RIGHT));
		add(CmbEdos);
		add(new JLabel("Fecha ingreso", JLabel.RIGHT));
		add(TxtTiempoProc);
		add(BtnGuardar);
		add(BtnLimpiar);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}
	
	public void ColocaColores() {
		CmbColores.addItem("Seleccione");
		for(int i = 0 ; i < Colores.size() ; i++)
			CmbColores.addItem(Colores.get(i));
	}
	
	public void ColocaEdos() {
		CmbEdos.addItem("Seleccione");
		for(int i = 0 ; i < Edos.size() ; i++)
			CmbEdos.addItem(Edos.get(i));
	}
	
	public void setControlador(CrianzaControladorRegistraCria CRC) {
		BtnGuardar.addActionListener(CRC);
		BtnLimpiar.addActionListener(CRC);
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
	
	public JTextField getTxtPeso() {
		return TxtPeso;
	}
	
	public JTextField getTxtCantGrasa() {
		return TxtCantGrasa;
	}
	
	public JDateChooser getTxtTiempoProc() {
		return TxtTiempoProc;
	}
	
	public String getFechaSeleccionada() {
		DateFormat Fecha = new SimpleDateFormat("yyyy-MM-dd");
		return Fecha.format(TxtTiempoProc.getDate());
	}
	
	public JComboBox<String> getCmbEdos() {
		return CmbEdos;
	}
	
	public JComboBox<String> getCmbColores() {
		return CmbColores;
	}
}
