package Controladores;
import java.awt.event.*;

import javax.swing.JOptionPane;

import Modelos.CrianzaModelo;
import Vistas.CrianzaVistaRegistraDietas;

public class CrianzaControladorRegistraDietas implements ActionListener {
	
	private CrianzaVistaRegistraDietas Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorRegistraDietas(CrianzaVistaRegistraDietas Vista, CrianzaModelo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getBtnLimpiar()) {
			Vista.getTxtNombre().setText("");
			Vista.getTxtDescripcion().setText("");;
			return;
		}
		String Nombre = Vista.getTxtNombre().getText(), Desc = Vista.getTxtDescripcion().getText();
		if(Nombre.compareTo("") == 0 || Desc.compareTo("") == 0) {
			JOptionPane.showMessageDialog(Vista, "Ingrese datos válidos", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
		if(Opcion != 0)
			return;
		int ID = Modelo.RegistraDieta(Nombre, Desc);
		if(ID != 0) {
			JOptionPane.showMessageDialog(Vista, "La dieta se registró con el ID no. " + ID);
			return;
		}
		JOptionPane.showMessageDialog(Vista, "Hubo un error en la base de datos", "******", JOptionPane.ERROR_MESSAGE);
	}
}
