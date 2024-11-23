package Plantillas;

public class Historial {
	
	private int Temperatura, RitmoCardiaco;
	private String Fecha;
	
	public Historial(int Temperatura, int RitmoCardiaco, String Fecha) {
		this.Temperatura = Temperatura;
		this.RitmoCardiaco = RitmoCardiaco;
		this.Fecha = Fecha;
	}

	public int getTemperatura() {
		return Temperatura;
	}

	public int getRitmoCardiaco() {
		return RitmoCardiaco;
	}

	public String getFecha() {
		return Fecha;
	}
}
