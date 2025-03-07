package Controladores;
import Modelos.CrianzaModelo;
import Plantillas.Cria;
import Vistas.CrianzaVistaConsultaCrias;
import java.awt.event.*;
import javax.swing.*;

public class CrianzaControladorConsultaCrias implements ActionListener {
	
	private CrianzaVistaConsultaCrias Vista;
	private CrianzaModelo Modelo;
	
	public CrianzaControladorConsultaCrias(CrianzaVistaConsultaCrias Vista, CrianzaModelo Modelo) {
		this.Vista = Vista;
		this.Modelo = Modelo;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() == Vista.getRbUnico()) {
			Vista.getBtnBuscar().setEnabled(true);
			Vista.getTxtID().setEnabled(true);
			return;
		}
		if(Evt.getSource() == Vista.getRbTodos()) {
			Vista.getBtnBuscar().setEnabled(false);
			Vista.getTxtID().setText("");
			Vista.getTxtID().setEnabled(false);
			Vista.BorraTodosLosRenglones();
			if(Vista.getTipoConsulta() == 1) {
				Vista.ActualizaTablaProcesadaTodosRenglones(Modelo.ObtenInformesCriasProcesadas());
				return;
			}
			Vista.ActualizaTablaGeneralTodosRenglones(Modelo.ObtenCriasGeneral());
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
			Cria C;
			if(Vista.getTipoConsulta() == 1) {
				C = Modelo.ObtenUnicaCriaProcesada(ID + "");
				if(C == null) {
					JOptionPane.showMessageDialog(Vista, "No se encontró la cría no." + ID, "******", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Vista.BorraTodosLosRenglones();
				Vista.ActualizaTablaProcesadaUnicoRenglon(C);
				return;
			}
			C = Modelo.ObtenUnicaCriaDatoGenerales(ID + "");
			if(C == null) {
				JOptionPane.showMessageDialog(Vista, "No se encontró la cría no." + ID, "******", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Vista.BorraTodosLosRenglones();
			Vista.ActualizaTablaGeneralUnicoRenglon(C);
		}
	}
}
