package Controladores;
import Modelos.CrianzaModelo;
import Plantillas.Cria;
import Vistas.CrianzaVistaSigProceso;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CrianzaControladorSigProceso implements ActionListener {
	
	private CrianzaVistaSigProceso Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorSigProceso(CrianzaVistaSigProceso Vista, CrianzaModelo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getRbUnico()) {
			Vista.getBtnBuscar().setEnabled(true);
			Vista.getTxtID().setEnabled(true);
			if(Vista.getCrias().size() == 1) {
				Vista.setCria(Vista.getCrias().get(0));
				return;
			}
			return;
		}
		if(Evt.getSource() == Vista.getRbTodos()) {
			Vista.getTxtID().setEnabled(false);
			Vista.getTxtID().setText("");
			Vista.getBtnBuscar().setEnabled(false);
			Vista.BorraTodosLosRenglones();
			Vista.ActualizaTablaTodosRenglones(Modelo.ObtenCriasPreparadasAvanzar());
			return;
		}
		if(Evt.getSource() == Vista.getBtnBuscar()) {
			int ID;
			try {
				ID = Integer.parseInt(Vista.getTxtID().getText());
			}
			catch(NumberFormatException NFE) {
				JOptionPane.showMessageDialog(Vista, "Ingrese un número válido", "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Cria C = Modelo.ObtenUnicaCriaListaProcesoUno(ID + "");
			if(C == null) {
				JOptionPane.showMessageDialog(Vista, "No se encontró la cría no." + ID, "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Vista.BorraTodosLosRenglones();
			Vista.ActualizaTablaUnicoRenglon(C);
			return;
		}
		if(Vista.getRbTodos().isSelected()) {
			int RenglonTabla = Vista.getTabla().getSelectedRow();
			if(RenglonTabla == -1) {
				JOptionPane.showMessageDialog(Vista, "Selecciona la cría que deseas avanzar de proceso", "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
			if(Opcion != 0)
				return;
			int IDCria = Vista.getCrias().get(RenglonTabla).getId();
			if(Modelo.AvanzaCria(IDCria + "")) {
				Vista.getModelo().removeRow(RenglonTabla);
				Vista.getCrias().remove(RenglonTabla);
				JOptionPane.showMessageDialog(Vista, "*** Cambios realizados con éxito ***");
				return;
			}
			JOptionPane.showMessageDialog(Vista, "Hubo un error con la base de datos", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(Vista.getCria() == null && Vista.getCrias() == null) {
			JOptionPane.showMessageDialog(Vista, "Filtre una cría", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(Vista.getCrias().size() > 1 && Vista.getCria() == null) {
			JOptionPane.showMessageDialog(Vista, "Filtre únicamente una cría", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
		if(Opcion != 0)
			return;
		int IDCria = Vista.getCria().getId();
		if(Modelo.AvanzaCria(IDCria + "")) {
			Vista.getModelo().removeRow(0);
			Vista.setCria(null);
			JOptionPane.showMessageDialog(Vista, "*** Cambios realizados con éxito ***");
			return;
		}
		JOptionPane.showMessageDialog(Vista, "Hubo un error con la base de datos", "******", JOptionPane.ERROR_MESSAGE);
	}
}
