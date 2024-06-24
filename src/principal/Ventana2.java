package principal;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


public class Ventana2 extends JFrame{

	private Principal fondo;
	private Clip sonido;
	
	public Ventana2() {
		fondo = new Principal();
		this.setContentPane(fondo);
		this.setTitle("Reloj");
		this.setBounds(400,150,800,550);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		reproducirMusica();
	}
	
	public static void main(String[] args) {
		Ventana2 miVentana = new Ventana2();
	}
	
	public void reproducirMusica() {
		try {
			sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File("src/visuales/ciudadAudio.wav")));
			sonido.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		iniciarMusica();
	}
	
	
	
	public void detenerMusica() {
		sonido.stop();
	}
	
	public void iniciarMusica() {
		sonido.start();
	}
	
}
