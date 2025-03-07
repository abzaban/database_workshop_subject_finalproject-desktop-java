package Controladores;
import javax.swing.*;
import java.awt.event.*;
import Modelos.CrianzaModelo;
import Vistas.CrianzaVistaSacrificio;

public class CrianzaControladorSacrificio implements ActionListener {
	
	private CrianzaVistaSacrificio Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorSacrificio(CrianzaModelo Modelo, CrianzaVistaSacrificio Vista) {
		this.Modelo = Modelo;
		this.Vista = Vista;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		String Seleccion = (String) Vista.getCmbCrias().getSelectedItem();
		if(Seleccion.compareTo("Seleccione") == 0) {
			JOptionPane.showMessageDialog(Vista, "Eliga una opción válida");
			return;
		}
		int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
		if(Opcion != 0)
			return;
		if(Modelo.SacrificaCria(Seleccion)) {
			Vista.getCmbCrias().removeItem(Seleccion);
			JOptionPane.showMessageDialog(Vista, "*** Cambios realizados con éxito ***");
			return;
		}
		JOptionPane.showMessageDialog(Vista, "Hubo un error con la base de datos", "******", JOptionPane.ERROR_MESSAGE);
		return;
	}
}
