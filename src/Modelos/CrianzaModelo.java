package Modelos;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import BasesDeDatos.ConexionCrianzaDB;
import Plantillas.*;

public class CrianzaModelo {
	
	private Statement BD;

	public CrianzaModelo() {}
	
	public boolean IniciarConexion(String Usuario, String Contraseña) {
		BD = ConexionCrianzaDB.getConexion("localhost", "CRIANZA", "1433", Usuario, Contraseña);
		if(BD == null) {
			JOptionPane.showMessageDialog(null, "Conexión no realizada", "*********", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public ArrayList<String> ObtenCorralesSanos() {
		ArrayList<String> Corrales = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from CorralesSanas");
			while(rs.next())
				Corrales.add(rs.getInt(1) + "");
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Corrales;
	}
	
	public ArrayList<String> ObtenColoresCria() {
		ArrayList<String> Colores = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from ColoresDeMusculosCrias");
			while(rs.next())
				Colores.add(rs.getString(1));
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Colores;
	}
	
	public ArrayList<String> ObtenEdos() {
		ArrayList<String> Edos = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from NombresEstados");
			while(rs.next())
				Edos.add(rs.getString(1));
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Edos;
	}
	
	public int[] RegistraCria(String Peso, String CantGrasa, String ColorMusculo, String Edo, String TiempoProc) {
		CallableStatement cstm;
		int [] Regresos = new int [3];
		try {
			cstm = BD.getConnection().prepareCall("{call dbo.SPInsercionCria(?, ?, ?, ?, ?, ?, ?, ?)}");
			cstm.setDouble("cr_peso", Double.parseDouble(Peso));
			cstm.setDouble("cr_cantidadGrasa", Double.parseDouble(CantGrasa));
			cstm.setString("cr_colorMusculo", ColorMusculo);
			cstm.setString("edo_nombre", Edo);
			cstm.setString("cr_fechaIngreso", TiempoProc);
			cstm.registerOutParameter("cr_id", java.sql.Types.INTEGER);
			cstm.registerOutParameter("sen_id", java.sql.Types.INTEGER);
			cstm.registerOutParameter("cor_id", java.sql.Types.INTEGER);
			cstm.execute();
			Regresos[0] = cstm.getInt("cr_id");
			Regresos[1] = cstm.getInt("cor_id");
			Regresos[2] = cstm.getInt("sen_id");
		}
		catch(SQLException SQLE) {
			SQLE.printStackTrace();
			Regresos[0] = 0;
			return Regresos;
		}
		return Regresos;
	}
	
	public ArrayList<Cria> ObtenCriasPreparadasAvanzar() {
		ArrayList<Cria> Crias = new ArrayList<Cria>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from CriasListasProcesoUno");
			Cria C;
			while(rs.next()) {
				C = new Cria();
				C.setId(rs.getInt(1));
				C.setClasificacion(rs.getString(2));
				C.setDiasActiva(rs.getInt(3));
				Crias.add(C);
			}
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Crias;
	}
	
	public boolean AvanzaCria(String ID) {
		try {
			BD.execute("exec SPAvanzaCriaProcesoDos " + ID);
		}
		catch(SQLException SQLE) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Cria> ObtenCriasEnfermas() {
		ArrayList<Cria> Crias = new ArrayList<Cria>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from DatosDeCriasEnfermas");
			Cria C;
			while(rs.next()) {
				C = new Cria();
				C.setId(rs.getInt(1));
				C.setClasificacion(rs.getString(2));
				C.setEdo(rs.getString(3));
				C.setDieta(rs.getString(4));
				C.setDiasEnferma(rs.getInt(5));
				Crias.add(C);
			}
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Crias;
	}
	
	public ArrayList<String> ObtenIDCriasPropensasEnfermar() {
		ArrayList<String> CriasFinal = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = BD.executeQuery("select * from CriasPropensasAEnfermar");
			
			while(rs.next())
				CriasFinal.add(rs.getInt(1) + "");
		}
		catch(SQLException SQLE) {
			return null;
		}
		return CriasFinal;
	}
	
	public ArrayList<String> ObtenCorralesEnfermas() {
		ArrayList<String> CorralesFinal = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from CorralesEnfermas");
			while(rs.next())
				CorralesFinal.add(rs.getString(1) + "");
		}
		catch(SQLException SQLE) {
			return null;
		}
		return CorralesFinal;
	}
	
	public ArrayList<String> ObtenNombreDietas() {
		ArrayList<String> DietasFinal = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from NombresDietas");
			while(rs.next())
				DietasFinal.add(rs.getString(1) + "");
		}
		catch(SQLException SQLE) {
			return null;
		}
		return DietasFinal;
	}
	
	public int ActualizaCriasEnfermas(String ID, String Dieta, String Fecha) {
		CallableStatement cstm;
		int IDCorral = -1;
		try {
			cstm = BD.getConnection().prepareCall("{call dbo.SPEnfermaCria(?, ?, ?, ?)}");
			cstm.setInt("cr_id", Integer.parseInt(ID));
			cstm.setString("dieta_nombre", Dieta);
			cstm.setString("cua_fechaIngreso", Fecha);
			cstm.registerOutParameter("cor_id", java.sql.Types.INTEGER);
			cstm.execute();
			IDCorral = cstm.getInt("cor_id");
		}
		catch(SQLException SQLE) {
			return IDCorral;
		}
		return IDCorral;
	}
	
	public int CuraCria(String ID) {
		CallableStatement cstm;
		int IDCorral = -1;
		try {
			cstm = BD.getConnection().prepareCall("{call dbo.SPCuraCria(?, ?)}");
			cstm.setInt("cr_id", Integer.parseInt(ID));
			cstm.registerOutParameter("cor_id", java.sql.Types.INTEGER);
			cstm.execute();
			IDCorral = cstm.getInt("cor_id");
		}
		catch(SQLException SQLE) {
			return IDCorral;
		}
		return IDCorral;
	}
	
	public boolean ActualizaSensorLog() {
		try {
			BD.execute("exec SPActualizaSensorLog");
			return true;
		}
		catch(SQLException SQLE) {
			return false;
		}
	}
	
	public ArrayList<String> ObtenerCriasValidasSacrificio() {
		ArrayList<String> CriasFinal = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from CriasValidasParaSacrificar");
			while(rs.next())
				CriasFinal.add(rs.getInt(1) + "");
		}
		catch(SQLException SQLE) {
			return null;
		}
		return CriasFinal;
	}
	
	public boolean SacrificaCria(String ID) {
		try {
			BD.execute("exec SPSacrificaCria " + ID);
		}
		catch(SQLException SQLE) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Cria> ObtenInformesCriasProcesadas() {
		ArrayList<Cria> Crias = new ArrayList<Cria>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from InformesCriasProcesadas");
			Cria C;
			while(rs.next()) {
				C = new Cria();
				C.setId(rs.getInt(1));
				C.setClasificacion(rs.getString(2));
				C.setEdo(rs.getString(3));
				C.setNoProceso(rs.getInt(4));
				C.setDiasActiva(rs.getInt(5));
				C.setVecesQueEnfermo(rs.getInt(6));
				Crias.add(C);
			}
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Crias;
	}
	
	public ArrayList<Cria> ObtenCriasGeneral() {
		ArrayList<Cria> Crias = new ArrayList<Cria>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("select * from CriasGeneral");
			Cria C;
			while(rs.next()) {
				C = new Cria();
				C.setId(rs.getInt(1));
				C.setEstatus(rs.getString(2));
				C.setFechaIngreso(rs.getString(3));
				C.setNoProceso(rs.getInt(4));
				C.setEdo(rs.getString(5));
				C.setClasificacion(rs.getString(6));
				Crias.add(C);
			}
		}
		catch(SQLException SQLE) {
			SQLE.printStackTrace();
			return null;
		}
		return Crias;
	}
	
	public Cria ObtenUnicaCriaDatoGenerales(String ID) {
		Cria C = new Cria();
		ResultSet rs;
		try {
			rs = BD.executeQuery("exec SPObtenUnicaCriaDatosGenerales " + ID);
			rs.next();
			C.setId(rs.getInt(1));
			C.setEstatus(rs.getString(2));
			C.setFechaIngreso(rs.getString(3));
			C.setNoProceso(rs.getInt(4));
			C.setEdo(rs.getString(5));
			C.setClasificacion(rs.getString(6));
			if(C.getId() == 0)
				return null;
		}
		catch(SQLException SQLE) {
			return null;
		}
		return C;
	}
	
	public Cria ObtenUnicaCriaProcesada(String ID) {
		Cria C = new Cria();
		ResultSet rs;
		try {
			rs = BD.executeQuery("exec SPObtenUnicaCriaProcesada " + ID);
			rs.next();
			C.setId(rs.getInt(1));
			C.setClasificacion(rs.getString(2));
			C.setEdo(rs.getString(3));
			C.setNoProceso(rs.getInt(4));
			C.setDiasActiva(rs.getInt(5));
			C.setVecesQueEnfermo(rs.getInt(6));
			if(C.getId() == 0)
				return null;
		}
		catch(SQLException SQLE) {
			return null;
		}
		return C;
	}
	
	public ArrayList<Historial> ObtenHistorialCria(String ID) {
		ArrayList<Historial> Historial = new ArrayList<Historial>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("exec SPObtenHistorialCria " + ID);
			Historial H;
			while(rs.next()) {
				H = new Historial(rs.getInt(3), rs.getInt(2), rs.getString(1));
				Historial.add(H);
			}
		}
		catch(SQLException SQLE) {
			return null;
		}
		return Historial;
	}
	
	public Cria ObtenUnicaCriaEnferma(String ID) {
		Cria C = new Cria();
		ResultSet rs;
		try {
			rs = BD.executeQuery("exec SPObtenUnicaCriaEnferma " + ID);
			rs.next();
			C.setId(rs.getInt(1));
			C.setClasificacion(rs.getString(2));
			C.setEdo(rs.getString(3));
			C.setDieta(rs.getString(4));
			C.setDiasEnferma(rs.getInt(5));
			if(C.getId() == 0)
				return null;
		}
		catch(SQLException SQLE) {
			return null;
		}
		return C;
	}
	
	public Cria ObtenUnicaCriaListaProcesoUno(String ID) {
		Cria C = new Cria();
		ResultSet rs;
		try {
			rs = BD.executeQuery("exec SPObtenUnicaCriaListaProcesoUno " + ID);
			rs.next();
			C.setId(rs.getInt(1));
			C.setClasificacion(rs.getString(2));
			C.setDiasActiva(rs.getInt(3));
			if(C.getId() == 0)
				return null;
		}
		catch(SQLException SQLE) {
			return null;
		}
		return C;
	}
	
	public ArrayList<String> ObtenPermisosUsuario(String Nombre) {
		ArrayList<String> Permisos = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = BD.executeQuery("exec SPObtenPermisosUsuario " + Nombre);
			while(rs.next())
				Permisos.add(rs.getString(1));
		}
		catch(SQLException SQLE) {
			SQLE.printStackTrace();
			return null;
		}
		return Permisos;
	}
	
	public int RegistraDieta(String Nombre, String Desc) {
		CallableStatement cstm;
		int ID;
		try {
			cstm = BD.getConnection().prepareCall("{call dbo.SPInsercionDieta(?, ?, ?)}");
			cstm.setString("dieta_nombre", Nombre);
			cstm.setString("dieta_descripcion", Desc);
			cstm.registerOutParameter("dieta_id", java.sql.Types.INTEGER);
			cstm.execute();
			ID = cstm.getInt("dieta_id");
		}
		catch(SQLException SQLE) {
			return 0;
		}
		return ID;
	}
	
	public int RegistraSensor() {
		CallableStatement cstm;
		int ID;
		try {
			cstm = BD.getConnection().prepareCall("{call dbo.SPInsercionSensor(?)}");
			cstm.registerOutParameter("sensor_id", java.sql.Types.INTEGER);
			cstm.execute();
			ID = cstm.getInt("sensor_id");
		}
		catch(SQLException SQLE) {
			return 0;
		}
		return ID;
	}
	
	public int RegistraCorral(String Tipo, String Limite) {
		CallableStatement cstm;
		int ID;
		try {
			cstm = BD.getConnection().prepareCall("{call dbo.SPInsercionCorral(?, ?, ?)}");
			cstm.setString("cor_tipo", Tipo);
			cstm.setInt("cor_limite", Integer.parseInt(Limite));
			cstm.registerOutParameter("cor_id", java.sql.Types.INTEGER);
			cstm.execute();
			ID = cstm.getInt("cor_id");
		}
		catch(SQLException SQLE) {
			return 0;
		}
		return ID;
	}
}
