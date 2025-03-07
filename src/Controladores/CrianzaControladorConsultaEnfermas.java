package Controladores;
import java.awt.event.*;
import javax.swing.JOptionPane;
import Modelos.CrianzaModelo;
import Plantillas.Cria;
import Vistas.CrianzaVistaConsultaEnfermas;

public class CrianzaControladorConsultaEnfermas implements ActionListener {
	
	private CrianzaVistaConsultaEnfermas Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorConsultaEnfermas(CrianzaVistaConsultaEnfermas Vista, CrianzaModelo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getRbUnico()) {
			Vista.getBtnBuscar().setEnabled(true);
			Vista.getTxtID().setEnabled(true);
			if(Vista.getCrias().size() == 1)
				Vista.setCria(Vista.getCrias().get(0));
			return;
		}
		if(Evt.getSource() == Vista.getRbTodos()) {
			Vista.getTxtID().setEnabled(false);
			Vista.getTxtID().setText("");
			Vista.getBtnBuscar().setEnabled(false);
			Vista.BorraTodosLosRenglones();
			Vista.ActualizaTablaTodosRenglones(Modelo.ObtenCriasEnfermas());
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
			Cria C = Modelo.ObtenUnicaCriaEnferma(ID + "");
			if(C == null) {
				JOptionPane.showMessageDialog(Vista, "No se encontró la cría no." + ID, "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Vista.BorraTodosLosRenglones();
			Vista.ActualizaTablaUnicoRenglon(C);
			return;
		}
		if(Evt.getSource() == Vista.getBtnHistorial()) {
			String Cria = "";
			if(Vista.getRbTodos().isSelected()) {
				int RenglonTabla = Vista.getTabla().getSelectedRow();
				if(RenglonTabla == -1) {
					JOptionPane.showMessageDialog(Vista, "Selecciona la cría que deseas analizar", "******", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Cria = Vista.getCrias().get(RenglonTabla).getId() + "";
			}
			else {
				if(Vista.getModelo().getRowCount() == 0) {
					JOptionPane.showMessageDialog(Vista, "No se ha filtrado ninguna cría", "******", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Cria = Vista.getCria().getId() + "";
			}
			Vista.MuestraHistorial(Modelo.ObtenHistorialCria(Cria), Cria);
			return;
		}
		if(Vista.getRbTodos().isSelected()) {
			int RenglonTabla = Vista.getTabla().getSelectedRow();
			if(RenglonTabla == -1) {
				JOptionPane.showMessageDialog(Vista, "Seleccione la cría que deseas curar", "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
			if(Opcion != 0)
				return;
			int IDCria = Vista.getCrias().get(RenglonTabla).getId();
			int IDCorral = Modelo.CuraCria(IDCria + "");
			if(IDCorral != 0) {
				Vista.getModelo().removeRow(RenglonTabla);
				Vista.getCrias().remove(RenglonTabla);
				JOptionPane.showMessageDialog(Vista, "*** Corral ID: " + IDCorral + " ***");
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
		int IDCorral = Modelo.CuraCria(IDCria + "");
		if(IDCorral != 0) {
			Vista.getModelo().removeRow(0);
			JOptionPane.showMessageDialog(Vista, "*** Corral ID:" + IDCorral + " ***");
			return;
		}
		JOptionPane.showMessageDialog(Vista, "Hubo un error con la base de datos", "******", JOptionPane.ERROR_MESSAGE);
	}
}
