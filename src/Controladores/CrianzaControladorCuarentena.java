package Controladores;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import Modelos.CrianzaModelo;
import Vistas.CrianzaVistaCuarentena;

public class CrianzaControladorCuarentena implements ActionListener {
	
	private CrianzaVistaCuarentena Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorCuarentena(CrianzaVistaCuarentena Vista, CrianzaModelo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		String Cria = (String) Vista.getCmbCrias().getSelectedItem();
		if(Evt.getSource() == Vista.getBtnHistorial()) {
			if(Cria.compareTo("Seleccione") == 0) {
				JOptionPane.showMessageDialog(Vista, "Elija una cría válida");
				return;
			}
			Vista.MuestraHistorial(Modelo.ObtenHistorialCria(Cria), Cria);
			return;
		}
		if(Evt.getSource() == Vista.getBtnLimpiar()) {
			Vista.getCmbCrias().setSelectedItem("Seleccione");
			Vista.getCmbDietas().setSelectedItem("Seleccione");
			Vista.getTxtFecha().getDateEditor().setDate(new Date());
			return;
		}
		String Dieta = (String) Vista.getCmbDietas().getSelectedItem();
		if(Cria.compareTo("Seleccione") == 0 || Dieta.compareTo("Seleccione") == 0) {
			JOptionPane.showMessageDialog(Vista, "Elija datos válidos");
			return;
		}
		int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
		if(Opcion != 0)
			return;
		int IDCorral = Modelo.ActualizaCriasEnfermas(Cria, Dieta, Vista.getFechaSeleccionada());
		if(IDCorral != 0) {
			Vista.getCmbCrias().removeItem(Cria);
			JOptionPane.showMessageDialog(Vista, "*** Corral ID:" + IDCorral + " ***");
			return;
		}
		JOptionPane.showMessageDialog(Vista, "Hubo un error con la base de datos", "******", JOptionPane.ERROR_MESSAGE);
		return;
	}
}
