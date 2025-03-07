package Vistas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controladores.CrianzaControladorSigProceso;
import Plantillas.Cria;
import java.util.*;

public class CrianzaVistaSigProceso extends JDialog {
	
	private JTable Tabla;
	private DefaultTableModel Modelo;
	private JScrollPane Barra;
	private JButton BtnEnviar, BtnBuscar;
	private JRadioButton RbTodos, RbUnico;
	private JTextField TxtID;
	private ArrayList<Cria> Crias;
	private Cria Cria;
	
	public CrianzaVistaSigProceso() {
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(450, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		
		RbTodos = new JRadioButton("Todas");
		RbTodos.setBounds(150, 10, 70, 20);
		RbUnico = new JRadioButton("Una en específico");
		RbUnico.setBounds(235, 10, 150, 20);
		ButtonGroup Criterio = new ButtonGroup();
		Criterio.add(RbTodos);
		Criterio.add(RbUnico);
		add(RbTodos);
		add(RbUnico);
		RbUnico.setSelected(true);
		
		JLabel Etq = new JLabel("ID de la cría", JLabel.CENTER);
		Etq.setBounds(120, 45, 80, 20);
		add(Etq);
		TxtID = new JTextField();
		TxtID.setBounds(201, 40, 85, 30);
		BtnBuscar = new JButton("Buscar cría");
		BtnBuscar.setBounds(286, 45, 80, 20);
		add(TxtID);
		add(BtnBuscar);
		
		BtnEnviar = new JButton("Enviar");
		BtnEnviar.setBounds(175, 70, 100, 30);
		add(BtnEnviar);
		
		Modelo = new DefaultTableModel();
		Modelo.addColumn("ID de la cría");
		Modelo.addColumn("Clasificación");
		Modelo.addColumn("Días activa");
		Tabla = new JTable(Modelo);
		JScrollPane Barra = new JScrollPane(Tabla);
		Barra.setBounds(1, 100, getWidth(), 300);
		add(Barra);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}
	
	public void Muestra() {
		setVisible(true);
	}
	
	public void setControlador(CrianzaControladorSigProceso C) {
		BtnEnviar.addActionListener(C);
		BtnBuscar.addActionListener(C);
		RbUnico.addActionListener(C);
		RbTodos.addActionListener(C);
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
	
	public JButton getBtnEnviar() {
		return BtnEnviar;
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
			Fila[2] = Crias.get(i).getDiasActiva() + "";
			Modelo.addRow(Fila);	
		}
		this.Crias = Crias;
	}
	
	public void ActualizaTablaUnicoRenglon(Cria C) {
		Object [] Fila;
		Fila = new Object [6];
		Fila[0] = C.getId() + "";
		Fila[1] = C.getClasificacion();
		Fila[2] = C.getDiasActiva() + "";
		Modelo.addRow(Fila);
		Cria = C;
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
