package Vistas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controladores.CrianzaControladorConsultaEnfermas;
import java.util.*;
import Plantillas.*;
import Utilerias.Rutinas;

public class CrianzaVistaConsultaEnfermas extends JDialog {
	
	private JTable Tabla;
	private DefaultTableModel Modelo;
	private JButton BtnCurar;
	private JRadioButton RbTodos, RbUnico;
	private JTextField TxtID;
	private JButton BtnBuscar, BtnHistorial;
	private ArrayList<Cria> Crias;
	private Cria Cria;
	
	public CrianzaVistaConsultaEnfermas() {
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(900, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		RbTodos = new JRadioButton("Todas");
		RbTodos.setBounds(340, 10, 70, 20);
		RbUnico = new JRadioButton("Una en específico");
		RbUnico.setBounds(440, 10, 150, 20);
		ButtonGroup Criterio = new ButtonGroup();
		Criterio.add(RbTodos);
		Criterio.add(RbUnico);
		add(RbTodos);
		add(RbUnico);
		RbUnico.setSelected(true);
		
		JLabel Etq = new JLabel("ID de la cría", JLabel.CENTER);
		Etq.setBounds(340, 45, 80, 20);
		add(Etq);
		TxtID = new JTextField();
		TxtID.setBounds(421, 40, 85, 30);
		BtnBuscar = new JButton("Buscar cría");
		BtnBuscar.setBounds(506, 45, 80, 20);
		add(TxtID);
		add(BtnBuscar);
		
		BtnHistorial = new JButton();
		BtnHistorial.setIcon(Rutinas.AjustarImagen("img/historial.png", 30, 30));
		BtnHistorial.setBounds(380, 100, 25, 25);
		add(BtnHistorial);
		
		BtnCurar = new JButton("Curar");
		BtnCurar.setBounds(410, 100, 80, 30);
		add(BtnCurar);
		
		Modelo = new DefaultTableModel();
		Modelo.addColumn("ID");
		Modelo.addColumn("Clasificación");
		Modelo.addColumn("Estado");
		Modelo.addColumn("Dieta");
		Modelo.addColumn("Dias enferma");
		Tabla = new JTable(Modelo);
		JScrollPane Barra = new JScrollPane(Tabla);
		Barra.setBounds(1, 140, getWidth() - 1, 338);
		add(Barra);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}
	
	public void setControlador(CrianzaControladorConsultaEnfermas C) {
		BtnBuscar.addActionListener(C);
		BtnCurar.addActionListener(C);
		BtnHistorial.addActionListener(C);
		RbUnico.addActionListener(C);
		RbTodos.addActionListener(C);
	}
	
	public void Muestra() {
		setVisible(true);
	}
	
	public JTable getTabla() {
		return Tabla;
	}
	
	public DefaultTableModel getModelo() {
		return Modelo;
	}
	
	public JRadioButton getRbTodos() {
		return RbTodos;
	}
	
	public JRadioButton getRbUnico() {
		return RbUnico;
	}
	
	public JButton getBtnBuscar() {
		return BtnBuscar;
	}
	
	public JButton getBtnCurar() {
		return BtnCurar;
	}
	
	public JButton getBtnHistorial() {
		return BtnHistorial;
	}
	
	public JTextField getTxtID() {
		return TxtID;
	}
	
	public void ActualizaTablaTodosRenglones(ArrayList<Cria> Crias) {
		Object [] Fila;
		for(int i = 0 ; i < Crias.size() ; i++) {
			Fila = new Object [6];
			Fila[0] = Crias.get(i).getId() + "";
			Fila[1] = Crias.get(i).getClasificacion();
			Fila[2] = Crias.get(i).getEdo();
			Fila[3] = Crias.get(i).getDieta();
			Fila[4] = Crias.get(i).getDiasEnferma() + "";
			Modelo.addRow(Fila);	
		}
		this.Crias = Crias;
	}
	
	public void ActualizaTablaUnicoRenglon(Cria C) {
		Object [] Fila;
		Fila = new Object [6];
		Fila[0] = C.getId() + "";
		Fila[1] = C.getClasificacion();
		Fila[2] = C.getEdo();
		Fila[3] = C.getDieta();
		Fila[4] = C.getDiasEnferma() + "";
		Modelo.addRow(Fila);
		Cria = C;
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
		BarraHistorial.setBounds(1, 51, PtllHistorial.getWidth(), 228);
		PtllHistorial.add(BarraHistorial);
		
		PtllHistorial.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		PtllHistorial.setModal(true);
		PtllHistorial.setVisible(true);
	}
	
	public void BorraTodosLosRenglones() {
		int NumTuplas = Modelo.getRowCount();
		for(int i = 0 ; i < NumTuplas ; i++)
			Modelo.removeRow(0);
	}
	
	public ArrayList<Cria> getCrias() {
		return Crias;
	}
	
	public void setCrias(ArrayList<Cria> Crias) {
		this.Crias = Crias;
	}
	
	public Cria getCria() {
		return Cria;
	}
	
	public void setCria(Cria C) {
		Cria = C;
	}
}
