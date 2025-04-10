package Controladores;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import Modelos.*;
import Vistas.*;

public class CrianzaControladorPrincipal implements ActionListener {
	
	private CrianzaModelo Modelo;
	private CrianzaVistaPrincipal Vista;
	
	public CrianzaControladorPrincipal(CrianzaModelo Modelo, CrianzaVistaPrincipal Vista) {
		this.Modelo = Modelo;
		this.Vista = Vista;
	}
	
	public void actionPerformed(ActionEvent Evt) {
		if(Evt.getSource() instanceof JMenuItem) {
			String Opcion = ((JMenuItem) Evt.getSource()).getText();
			switch(Opcion) {
			case "Registrar nueva cría":
				CrianzaVistaRegistraCria VistaRC = new CrianzaVistaRegistraCria(Modelo.ObtenColoresCria(), Modelo.ObtenEdos());
				CrianzaControladorRegistraCria ControladorRC = new CrianzaControladorRegistraCria(VistaRC, Modelo);
				VistaRC.setControlador(ControladorRC);
				VistaRC.Muestra();
				return;

			case "Procesar crías":
				CrianzaVistaSigProceso VistaSP = new CrianzaVistaSigProceso();
				CrianzaControladorSigProceso ControladorSP = new CrianzaControladorSigProceso(VistaSP, Modelo);
				VistaSP.setControlador(ControladorSP);
				VistaSP.Muestra();
				return;

			case "Consultar crías enfermas":
				CrianzaVistaConsultaEnfermas VistaCE = new CrianzaVistaConsultaEnfermas();
				CrianzaControladorConsultaEnfermas ControladorCE = new CrianzaControladorConsultaEnfermas(VistaCE, Modelo);
				VistaCE.setControlador(ControladorCE);
				VistaCE.Muestra();
				return;

			case "Registrar cuarentena":
				CrianzaVistaCuarentena VistaCUA = new CrianzaVistaCuarentena(Modelo.ObtenIDCriasPropensasEnfermar(), Modelo.ObtenNombreDietas());
				CrianzaControladorCuarentena ControladorCUA = new CrianzaControladorCuarentena(VistaCUA, Modelo);
				VistaCUA.setControlador(ControladorCUA);
				VistaCUA.Muestra();
				return;

			case "Sacrificar crías":
				CrianzaVistaSacrificio VistaSACRI = new CrianzaVistaSacrificio(Modelo.ObtenerCriasValidasSacrificio());
				CrianzaControladorSacrificio ControladorSACRI = new CrianzaControladorSacrificio(Modelo, VistaSACRI);
				VistaSACRI.setControlador(ControladorSACRI);
				VistaSACRI.Muestra();
				return;

			case "Consultar crías con más de 5 meses":
				CrianzaVistaConsultaCrias VistaPROC = new CrianzaVistaConsultaCrias(1);
				CrianzaControladorConsultaCrias ControladorPROC = new CrianzaControladorConsultaCrias(VistaPROC, Modelo);
				VistaPROC.setControlador(ControladorPROC);
				VistaPROC.Muestra();
				return;

			case "Consultar todas las crías":
				CrianzaVistaConsultaCrias VistaConsultaCrias = new CrianzaVistaConsultaCrias(2);
				CrianzaControladorConsultaCrias ControladorConsultaCrias = new CrianzaControladorConsultaCrias(VistaConsultaCrias, Modelo);
				VistaConsultaCrias.setControlador(ControladorConsultaCrias);
				VistaConsultaCrias.Muestra();
				return;

			case "Actualizar LOG de los sensores":
				Modelo.ActualizaSensorLog();
				ArrayList<String> CriasPropensas = Modelo.ObtenIDCriasPropensasEnfermar();
				if(CriasPropensas.size() == 0) {
					JOptionPane.showMessageDialog(Vista, "No hay crías propensas a enfermar");
					return;
				}
				JOptionPane.showMessageDialog(Vista, "¡¡Hay " + CriasPropensas.size() + " cría(s) propensa(s) a enfermar!!", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
				return;
				
			case "Registrar dietas":
				CrianzaVistaRegistraDietas VRD = new CrianzaVistaRegistraDietas();
				CrianzaControladorRegistraDietas CRD = new CrianzaControladorRegistraDietas(VRD, Modelo);
				VRD.setControlador(CRD);
				VRD.setVisible(true);
				return;
				
			case "Registrar sensores":
				int ID = Modelo.RegistraSensor();
				if(ID != 0) {
					JOptionPane.showMessageDialog(Vista, "El sensor se registró con el ID no. " + ID);
					return;
				}
				JOptionPane.showMessageDialog(Vista, "Hubo un error en la base de datos", "******", JOptionPane.ERROR_MESSAGE);
				return;
				
			case "Registrar corrales":
				CrianzaVistaRegistraCorrales VRC = new CrianzaVistaRegistraCorrales();
				CrianzaControladorRegistraCorrales CRC = new CrianzaControladorRegistraCorrales(VRC, Modelo);
				VRC.setControlador(CRC);
				VRC.Muestra();
				return;
			}
		}
		String Usuario = Vista.getTxtUsuario().getText(), Contraseña = Vista.getTxtContraseña().getText();
		if(Modelo.IniciarConexion(Usuario, Contraseña)) {
			Vista.getPtllInicio().dispose();
			Vista.HazMenu(Modelo.ObtenPermisosUsuario(Usuario), this);
			Vista.setTitle("Sesión - " + Usuario);
			Modelo.ActualizaSensorLog();
			ArrayList<String> CriasPropensas = Modelo.ObtenIDCriasPropensasEnfermar();
			if(CriasPropensas.size() != 0)
				JOptionPane.showMessageDialog(Vista, "¡¡Hay " + CriasPropensas.size() + " cría(s) propensa(s) a enfermar!!", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
			Vista.Muestra();
		}
	}
}
