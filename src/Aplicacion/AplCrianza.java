package Aplicacion;
import Controladores.CrianzaControladorPrincipal;
import Modelos.CrianzaModelo;
import Vistas.CrianzaVistaPrincipal;

public class AplCrianza {
	
	public AplCrianza() {
		CrianzaModelo Modelo = new CrianzaModelo();
		CrianzaVistaPrincipal Vista = new CrianzaVistaPrincipal();
		CrianzaControladorPrincipal Controlador = new CrianzaControladorPrincipal(Modelo, Vista);
		Vista.IniciaSesion();
		Vista.setControladorBtnIniciar(Controlador);
	}
	
	public static void main(String [] a) {
		new AplCrianza();
	}
}
