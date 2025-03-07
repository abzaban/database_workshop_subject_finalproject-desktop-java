package Controladores;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import Vistas.CrianzaVistaRegistraCria;
import Modelos.CrianzaModelo;

public class CrianzaControladorRegistraCria implements ActionListener {
	
	private CrianzaVistaRegistraCria Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorRegistraCria(CrianzaVistaRegistraCria VRC, CrianzaModelo Modelo) {
		this.Vista = VRC;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getBtnGuardar()) {
			String Peso = Vista.getTxtPeso().getText(), CantGrasa = Vista.getTxtCantGrasa().getText(), 
				   Color = Vista.getCmbColores().getSelectedItem() + "", Edo = Vista.getCmbEdos().getSelectedItem() + "", 
				   Fecha = Vista.getFechaSeleccionada();
			if(Peso.compareTo("") == 0 || CantGrasa.compareTo("") == 0 || Color.compareTo("Seleccione") == 0 || 
			   Edo.compareTo("Seleccione") == 0 || Fecha.compareTo("Seleccione") == 0) {
				JOptionPane.showMessageDialog(Vista, "Ingrese información válida", "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				Double.parseDouble(Peso);
				Double.parseDouble(CantGrasa);
			} catch(NumberFormatException NFE) {
				JOptionPane.showMessageDialog(Vista, "Ingrese números", "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int Opcion = JOptionPane.showConfirmDialog(Vista, "¿Está seguro de realizar cambios?");
			if(Opcion != 0)
				return;
			int [] IDs = Modelo.RegistraCria(Peso, CantGrasa, Color, Edo, Fecha);
			if(IDs[0] != 0 && IDs[2] == 0) {
				JOptionPane.showMessageDialog(Vista, "Cría ID: " + IDs[0] + ", Corral ID: " +  IDs[1]);
				return;
			}
			if(IDs[0] != 0 && IDs[2] != 0) {
				JOptionPane.showMessageDialog(Vista, "Cría ID: " + IDs[0] + ", Corral ID: " +  IDs[1] + ", Sensor ID: " + IDs[2]);
				return;
			}
			JOptionPane.showMessageDialog(Vista, "Hubo un error con la base de datos", "******", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(Evt.getSource() == Vista.getBtnLimpiar()) {
			Vista.getTxtCantGrasa().setText("");
			Vista.getCmbColores().setSelectedIndex(0);
			Vista.getTxtPeso().setText("");
			Vista.getCmbEdos().setSelectedIndex(0);
			Vista.getTxtTiempoProc().getDateEditor().setDate(new Date());
			return;
		}
	}
}
