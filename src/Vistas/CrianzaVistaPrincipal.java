package Vistas;
import javax.swing.*;
import Controladores.CrianzaControladorPrincipal;
import Utilerias.Rutinas;
import java.awt.*;
import java.util.ArrayList;

public class CrianzaVistaPrincipal extends JFrame {
	
	private JFrame PtllInicio;
	private JTextField TxtUsuario;
	private JPasswordField TxtContraseña;
	private JButton BtnIniciar;
	
	private JMenuBar Barra;
	private JMenu ProcUno;
	private JMenuItem MnItAddCria, MnItSigProceso, MnItConsultaEnfermas, MnItCuarentena, MnItSacrificio, MnItConsultaCriasProcesadas, 
					  MnItConsultaCrias, MnItActLogSensor;
	private Graphics g;
	private Image backbuffer;
	
	public CrianzaVistaPrincipal() {
		HazInterfaz();
	}
	
	public void HazInterfaz() {
		setSize(800, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		
		Barra = new JMenuBar();
		ProcUno = new JMenu("Proceso 1");
		setJMenuBar(Barra);
		Barra.add(ProcUno);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void HazMenu(ArrayList<String> Items, CrianzaControladorPrincipal C) {
		JMenuItem Aux;
		for(int i = 0 ; i < Items.size() ; i++) {
			Aux = new JMenuItem(Items.get(i));
			Aux.addActionListener(C);
			ProcUno.add(Aux);
		}
	}
	
	public void setControladorBtnIniciar(CrianzaControladorPrincipal C) {
		BtnIniciar.addActionListener(C);
	}
	
	public void Muestra() {
		setVisible(true);
		backbuffer = createImage(getWidth(), getHeight());
		g = backbuffer.getGraphics();
		Dibuja();
	}
	
	public void paint(Graphics g) {
		g.drawImage(backbuffer, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void Dibuja() {
		super.paint(g);
		g.drawImage(Rutinas.AjustarImagen("img/ganado.png", getWidth(), getHeight()).getImage(), 1, 45, null);
		repaint();
	}
	
	public JMenuItem getMnItAddCria() {
		return MnItAddCria;
	}
	
	public JMenuItem getMnItSigProceso() {
		return MnItSigProceso;
	}
	
	public JMenuItem getMnItConsultaEnfermas() {
		return MnItConsultaEnfermas;
	}
	
	public JMenuItem getMnItCuarentena() {
		return MnItCuarentena;
	}
	
	public JMenuItem getMnItSacrificio() {
		return MnItSacrificio;
	}
	
	public JMenuItem getMnItConsultaCriasProcesadas() {
		return MnItConsultaCriasProcesadas;
	}
	
	public JMenuItem getMnItConsultaCrias() {
		return MnItConsultaCrias;
	}
	
	public JMenuItem getMnItActLogSensor() {
		return MnItActLogSensor;
	}
	
	public void IniciaSesion() {
		PtllInicio = new JFrame("Inicio de sesión");
		PtllInicio.setSize(300, 200);
		PtllInicio.setLocationRelativeTo(null);
		PtllInicio.setResizable(false);
		PtllInicio.setLayout(null);
		
		JLabel EtqUsuario = new JLabel("Usuario", JLabel.CENTER), EtqContraseña = new JLabel("Contraseña", JLabel.CENTER);
		TxtUsuario = new JTextField();
		TxtContraseña = new JPasswordField();
		BtnIniciar = new JButton("Iniciar sesión");
		
		EtqUsuario.setBounds(50, 40, 50, 10);
		TxtUsuario.setBounds(101, 30, 150, 30);
		EtqContraseña.setBounds(23, 90, 80, 10);
		TxtContraseña.setBounds(101, 80, 150, 30);
		BtnIniciar.setBounds(85, 125, 130, 35);
		
		PtllInicio.add(EtqUsuario);
		PtllInicio.add(TxtUsuario);
		PtllInicio.add(EtqContraseña);
		PtllInicio.add(TxtContraseña);
		PtllInicio.add(BtnIniciar);
		
		PtllInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PtllInicio.setVisible(true);
	}
	
	public JFrame getPtllInicio() {
		return PtllInicio;
	}
	
	public JTextField getTxtUsuario() {
		return TxtUsuario;
	}
	
	public JPasswordField getTxtContraseña() {
		return TxtContraseña;
	}
}
