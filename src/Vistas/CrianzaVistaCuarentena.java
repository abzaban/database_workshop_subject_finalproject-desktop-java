package Vistas;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import ComboOrdenado.JComboOrdenado;
import Controladores.CrianzaControladorCuarentena;
import Utilerias.Rutinas;
import Plantillas.*;

public class CrianzaVistaCuarentena extends JDialog {
	
	private JComboBox<String> CmbCrias, CmbDietas;
	private JDateChooser TxtFecha;
	private JButton BtnGuardar, BtnLimpiar, BtnHistorial;
	private ArrayList<String> Crias, Dietas;
	
	public CrianzaVistaCuarentena(ArrayList<String> Crias, ArrayList<String> Dietas) {
		this.Crias = Crias;
		this.Dietas = Dietas;
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(300, 270);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		JLabel EtqCria = new JLabel("Cría ", JLabel.RIGHT);
		EtqCria.setBounds(1, 21, 100, 20);
		JLabel EtqDieta = new JLabel("Dieta ", JLabel.RIGHT);
		EtqDieta.setBounds(1, 62, 100, 20);
		JLabel EtqFecha = new JLabel("Fecha ", JLabel.RIGHT);
		EtqFecha.setBounds(1, 120, 100, 20);
		
		CmbCrias = new JComboBox<String>();
		CmbCrias.setBounds(102, 1, 150, 60);
		ColocaCrias();
		CmbDietas = new JComboBox<String>();
		CmbDietas.setBounds(102, 42, 150, 60);
		ColocaDietas();
		
		TxtFecha = new JDateChooser();
		TxtFecha.setBounds(102, 103, 150, 60);
		TxtFecha.getDateEditor().setDate(new Date());
		
		BtnGuardar = new JButton("Guardar");
		BtnGuardar.setBounds(50, 180, 100, 40);
		BtnLimpiar = new JButton("Limpiar");
		BtnLimpiar.setBounds(151, 180, 100, 40);
		BtnHistorial = new JButton();
		BtnHistorial.setIcon(Rutinas.AjustarImagen("img/historial.png", 30, 30));
		BtnHistorial.setBounds(30, 18, 25, 25);
		
		add(EtqCria);
		add(EtqDieta);
		add(EtqFecha);
		add(CmbCrias);
		add(CmbDietas);
		add(TxtFecha);
		add(BtnGuardar);
		add(BtnLimpiar);
		add(BtnHistorial);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}
	
	public void ColocaCrias() {
		CmbCrias.addItem("Seleccione");
		for(int i = 0 ; i < Crias.size() ; i++)
			CmbCrias.addItem(Crias.get(i));
	}
	
	public void ColocaDietas() {
		CmbDietas.addItem("Seleccione");
		for(int i = 0 ; i < Dietas.size() ; i++)
			CmbDietas.addItem(Dietas.get(i));
	}
	
	public void MuestraHistorial(ArrayList<Historial> Historial, String Cria) {
		JDialog PtllHistorial = new JDialog();
		PtllHistorial.setSize(550, 200);
		PtllHistorial.setLocationRelativeTo(null);
		PtllHistorial.setResizable(false);
		PtllHistorial.setLayout(null);
		
		JLabel Etq = new JLabel("Historial de los últimos dos días de la cría no. " + Cria, JLabel.CENTER);
		Etq.setBounds(115, 20, 330, 10);
		PtllHistorial.add(Etq);
		
		Vector<String> Columnas = new Vector<String>();
		Columnas.add("Fecha");
		Columnas.add("Temperatura");
		Columnas.add("Ritmo Cardiaco");
		Vector<Vector<String>> Renglones = new Vector<Vector<String>>();
		Vector<String> Renglon;
		for(int i = 0 ; i < Historial.size() ; i++) {
			Renglon = new Vector<String>();
			Renglon.add(Historial.get(i).getFecha());
			Renglon.add(Historial.get(i).getTemperatura() + "");
			Renglon.add(Historial.get(i).getRitmoCardiaco() + "");
			Renglones.add(Renglon);
		}
		
		JTable TablaHistorial = new JTable(Renglones, Columnas);
		TablaHistorial.setEnabled(false);
		JScrollPane BarraHistorial = new JScrollPane(TablaHistorial);
		BarraHistorial.setBounds(1, 51, PtllHistorial.getWidth(), 128);
		PtllHistorial.add(BarraHistorial);
		
		PtllHistorial.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		PtllHistorial.setModal(true);
		PtllHistorial.setVisible(true);
	}
	
	
	public void setControlador(CrianzaControladorCuarentena C) {
		BtnGuardar.addActionListener(C);
		BtnLimpiar.addActionListener(C);
		BtnHistorial.addActionListener(C);
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
	
	public JButton getBtnHistorial() {
		return BtnHistorial;
	}
	
	public JComboBox<String> getCmbCrias() {
		return CmbCrias;
	}
	
	public JComboBox<String> getCmbDietas() {
		return CmbDietas;
	}
	
	public JDateChooser getTxtFecha() {
		return TxtFecha;
	}
	
	public String getFechaSeleccionada() {
		DateFormat Fecha = new SimpleDateFormat("yyyy-MM-dd");
		return Fecha.format(TxtFecha.getDate());
	}
}
