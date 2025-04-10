package Vistas;
import javax.swing.*;
import javax.swing.table.*;
import javax.xml.ws.Service.Mode;

import Controladores.CrianzaControladorConsultaCrias;
import Plantillas.Cria;
import java.util.*;

public class CrianzaVistaConsultaCrias extends JDialog {
	
	private int TipoConsulta;
	private JScrollPane Barra;
	private JTable Tabla;
	private DefaultTableModel Modelo;
	private JRadioButton RbTodos, RbUnico;
	private JTextField TxtID;
	private JButton BtnBuscar;
	
	public CrianzaVistaConsultaCrias(int TipoConsulta) {
		this.TipoConsulta = TipoConsulta;
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
		
		Modelo = new DefaultTableModel();
		if(TipoConsulta == 1)
			CreaTablaProcesada();
		else
			CreaTablaGeneral();
		
		Tabla = new JTable(Modelo);
		Tabla.setEnabled(false);
		Barra = new JScrollPane(Tabla);
		Barra.setBounds(1, 80, getWidth(), getHeight() - 100);
		add(Barra);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
	}
	
	public void CreaTablaProcesada() {
		Modelo.addColumn("ID");
		Modelo.addColumn("Clasificación");
		Modelo.addColumn("Estado");
		Modelo.addColumn("No. de proceso");
		Modelo.addColumn("Días activa");
		Modelo.addColumn("No. de veces que enfermó");
	}
	
	public void ActualizaTablaProcesadaTodosRenglones(ArrayList<Cria> Crias) {
		Object [] Fila;
		for(int i = 0 ; i < Crias.size() ; i++) {
			Fila = new Object [6];
			Fila[0] = Crias.get(i).getId() + "";
			Fila[1] = Crias.get(i).getClasificacion();
			Fila[2] = Crias.get(i).getEdo();
			Fila[3] = Crias.get(i).getNoProceso() + "";
			Fila[4] = Crias.get(i).getDiasActiva() + "";
			Fila[5] = Crias.get(i).getVecesQueEnfermo() + "";
			Modelo.addRow(Fila);
		}
	}
	
	public void ActualizaTablaProcesadaUnicoRenglon(Cria C) {
		Object [] Fila;
		Fila = new Object [6];
		Fila[0] = C.getId() + "";
		Fila[1] = C.getClasificacion();
		Fila[2] = C.getEdo();
		Fila[3] = C.getNoProceso() + "";
		Fila[4] = C.getDiasActiva() + "";
		Fila[5] = C.getVecesQueEnfermo() + "";
		Modelo.addRow(Fila);
	}
	
	public void CreaTablaGeneral() {
		Modelo.addColumn("ID");
		Modelo.addColumn("Estatus");
		Modelo.addColumn("Fecha de ingreso");
		Modelo.addColumn("No. de proceso");
		Modelo.addColumn("Estado");
		Modelo.addColumn("Clasificacion");
	}
	
	public void ActualizaTablaGeneralTodosRenglones(ArrayList<Cria> Crias) {
		Object [] Fila;
		for(int i = 0 ; i < Crias.size() ; i++) {
			Fila = new Object [6];
			Fila[0] = Crias.get(i).getId() + "";
			Fila[1] = Crias.get(i).getEstatus();
			Fila[2] = Crias.get(i).getFechaIngreso();
			Fila[3] = Crias.get(i).getNoProceso() + "";
			Fila[4] = Crias.get(i).getEdo();
			Fila[5] = Crias.get(i).getClasificacion();
			Modelo.addRow(Fila);	
		}
	}
	
	public void ActualizaTablaGeneralUnicoRenglon(Cria C) {
		Object [] Fila;
		Fila = new Object [6];
		Fila[0] = C.getId() + "";
		Fila[1] = C.getEstatus();
		Fila[2] = C.getFechaIngreso();
		Fila[3] = C.getNoProceso() + "";
		Fila[4] = C.getEdo();
		Fila[5] = C.getClasificacion();
		Modelo.addRow(Fila);
	}
	
	public void BorraTodosLosRenglones() {
		int NumTuplas = Modelo.getRowCount();
		for(int i = 0 ; i < NumTuplas ; i++)
			Modelo.removeRow(0);
	}
	
	public void setControlador(CrianzaControladorConsultaCrias C) {
		RbTodos.addActionListener(C);
		RbUnico.addActionListener(C);
		BtnBuscar.addActionListener(C);
	}
	
	public void Muestra() {
		setVisible(true);
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
	
	public JTextField getTxtID() {
		return TxtID;
	}
	
	public int getTipoConsulta() {
		return TipoConsulta;
	}
	
	public DefaultTableModel getModeloTabla() {
		return Modelo;
	}
}
