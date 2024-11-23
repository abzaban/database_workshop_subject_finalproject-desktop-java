package Controladores;
import java.awt.event.*;

import javax.swing.JOptionPane;

import Modelos.CrianzaModelo;
import Vistas.CrianzaVistaRegistraCorrales;

public class CrianzaControladorRegistraCorrales implements ActionListener {
	
	private CrianzaVistaRegistraCorrales Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorRegistraCorrales(CrianzaVistaRegistraCorrales Vista, CrianzaModelo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getBtnLimpiar()) {
			Vista.getTxtLimite().setText("");
			Vista.getCmbTipo().setSelectedItem("Seleccione");
			return;
		}
		String Tipo = Vista.getCmbTipo().getSelectedItem() + "", Limite = Vista.getTxtLimite().getText();
		if(Tipo.compareTo("Seleccione") == 0 || Limite.compareTo("") == 0) {
			JOptionPane.showMessageDialog(Vista, "Ingrese datos válidos", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			Integer.parseInt(Limite);
		}
		catch(NumberFormatException NFE) {
			JOptionPane.showMessageDialog(Vista, "Ingrese un número válido para el límite de las crías", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(Tipo.compareTo("Sanas") == 0)
			Tipo = "S";
		else
			Tipo = "E";
		int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
		if(Opcion != 0)
			return;
		int ID = Modelo.RegistraCorral(Tipo, Limite);
		if(ID != 0) {
			JOptionPane.showMessageDialog(Vista, "El corral se registró con el ID no. " + ID);
			return;
		}
		JOptionPane.showMessageDialog(Vista, "Hubo un error en la base de datos", "******", JOptionPane.ERROR_MESSAGE);
	}
}
