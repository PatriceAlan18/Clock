package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class Principal extends JPanel{

	private Image fondo, reloj, aguijon, aguijon2, bufferReloj, bufferHoras, bufferMinutos, bufferSegundos, bufferDigital, bufferNumerosDigitales;
	private BufferedImage  segundero, minutero, horario;
	private int horas, minutos, segundos,  rotacionSeg, rotacionMin, rotacionHr;
	private Font custom, custom2;
	private Hilo miHilo = null;
	private Clip tic;
	private boolean pm = false;
	
	public Principal() {
		this.setSize(800,550);
		tic ();
		fuentes();
		fondo = Toolkit.getDefaultToolkit().createImage("src/visuales/Ciudad.png");
		reloj = Toolkit.getDefaultToolkit().createImage("src/visuales/relojNegro2.png");
		aguijon = Toolkit.getDefaultToolkit().createImage("src/visuales/aguijon.png");
		aguijon2 = Toolkit.getDefaultToolkit().createImage("src/visuales/aguijon2.png");

		}
	
	public void tic() {
		
		try {
			tic = AudioSystem.getClip();
			tic.open(AudioSystem.getAudioInputStream(new File("src/visuales/tic.wav")));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fuentes() {
			try {
				custom = Font.createFont(Font.TRUETYPE_FONT, new File("src/visuales/fuente.ttf")).deriveFont(36f);
				custom2 = Font.createFont(Font.TRUETYPE_FONT, new File("src/visuales/fuente.ttf")).deriveFont(60f);

			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

	
	public void paintComponent(Graphics gr) {
			super.paintComponent(gr);
			Graphics2D g = (Graphics2D) gr;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(custom);
 			g.drawImage(fondo, 0, 0,800,450,this);
 			crearReloj(g);
 			g.drawImage(bufferReloj, 200, 22,400,402,this);
 			crearManecillas();
 			g.drawImage(bufferSegundos, 200,22,400,402,this);
 			crearDigital();
 			g.drawImage(bufferNumerosDigitales, 0,450, 800,75,this);
 			
 			if(miHilo==null) {
 				miHilo = new Hilo();
 				miHilo.start();}
	}
	
	public void crearReloj(Graphics g) {
		BufferedImage temporal = null;
		try {
			temporal = ImageIO.read(new File("src/visuales/Ciudad.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		bufferReloj = createImage(400,402);
		Graphics2D gr = (Graphics2D) bufferReloj.getGraphics();
		Shape anillo = crearAnillo();
		gr.setFont(custom);
		gr.drawImage(temporal.getSubimage(200, 22, 400, 402), 0,0, 400,402, this);
 		gr.drawImage(reloj, 15, 15,370,370,this);
 		gr.setColor(Color.black);
 		gr.fill(anillo);
 		gr.draw(anillo);
 		gr.setColor(Color.white);
 		gr.drawArc(0, 2,400,400, 0, 360);
 		gr.drawArc(50,50,300,300, 0, 360);	
 		String[] numeros = {" 3"," 2"," 1","12"," 11"," 10"," 9"," 8"," 7"," 6"," 5"," 4"};
 		double angulo = 360.00;
 		for(int i = 0; i<12; i++) {
 			int x = (int) (192+178*Math.cos(Math.toRadians(angulo)));
 			int y = (int) (192+178*Math.sin(Math.toRadians(angulo)));
 			gr.drawString(numeros[i],x-12, y+25);
 			angulo = angulo - 30;
		}
 		
	}
	
	public void crearManecillas() {
		LocalDateTime fecha = LocalDateTime.now();
		horas = fecha.getHour();
		if(horas>12)horas-=12;
		minutos = fecha.getMinute();
		segundos = fecha.getSecond();
		
		
		//Hardcodeo de horas para prueba
		//horas = 12;
		//minutos = 59;
		//segundos = 45;
		//System.out.println(horas+":"+minutos+":"+segundos);
		if(fecha.getHour()>=12) {
			pm = true;
			System.out.print("Es pm");
		}
		try {
			segundero = ImageIO.read(new File("src/visuales/nail1.png"));
			minutero = ImageIO.read(new File("src/visuales/nail2Chico.png"));
			horario = ImageIO.read(new File("src/visuales/nail3Chico.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		posicionarManecillas();
		
	}
	
	
	
	public void crearDigital() {
		bufferDigital  = createImage(800,75);
		bufferNumerosDigitales = createImage(800,75);
		Graphics2D gD = (Graphics2D) bufferDigital.getGraphics();
		Graphics2D gN = (Graphics2D) bufferNumerosDigitales.getGraphics();
		gD.setColor(Color.white);
		gD.drawLine(0,0,800,0);
		gD.setColor(Color.black);
		Shape rectangulo = new Rectangle2D.Double(0,1,800,73);
		gD.fill(rectangulo);
		gD.draw(rectangulo);
		gD.drawImage(aguijon, 0, 1, 245,75,this);	
		
		gD.drawImage(aguijon2, 550, 1, 245,75,this);	
		gD.setColor(Color.black);
		Shape rectangulo2 = new Rectangle2D.Double(245,1,305,59);
		gD.setColor(Color.white);
		gD.draw(rectangulo2);
		gN.setColor(Color.white);
		gN.drawImage(bufferDigital, 0,0,800,75, this);
		gN.setFont(custom2);
		if(pm)gN.drawString(getHora()+":"+getMinuto()+":"+getSegundo()+" PM", 250, 50);
		else gN.drawString(getHora()+":"+getMinuto()+":"+getSegundo()+" AM", 250, 50);
		
	}
	
	
	public void posicionarManecillas() {
		
		bufferHoras = createImage(400,402);
		bufferMinutos = createImage(400,402);
		bufferSegundos = createImage(400,402);
		
		Graphics2D gSec = (Graphics2D) segundero.getGraphics();
		Graphics2D gMin = (Graphics2D) minutero.getGraphics();
		Graphics2D gHor = (Graphics2D) horario.getGraphics();
		Graphics2D gBufH = (Graphics2D) bufferHoras.getGraphics();
		Graphics2D gBufM = (Graphics2D) bufferMinutos.getGraphics();
		Graphics2D gBufS = (Graphics2D) bufferSegundos.getGraphics();
		
		rotacionHr = 180+(horas*30);
		gBufH.drawImage(bufferReloj, 0,0, 400,402, this);
		gBufH.rotate(Math.toRadians(rotacionHr), 200,201);
		gBufH.drawImage(horario, 190, 201,20,100,this);

		rotacionMin = 180+(minutos*6);
		gBufM.drawImage(bufferHoras, 0,0, 400,402, this);
		gBufM.rotate(Math.toRadians(rotacionMin), 200,201);
		gBufM.drawImage(minutero, 190, 201,20,140,this);
		
		rotacionSeg = 180+(segundos*6);
		gBufS.drawImage(bufferMinutos, 0,0, 400,402, this);
		gBufS.rotate(Math.toRadians(rotacionSeg), 200,201);
		gBufS.drawImage(segundero, 190, 201,20,180,this);
		gBufS.setColor(Color.white);
		gBufS.fillOval(187, 188, 26, 26);

	}
	
	public void pasarSegundo() {
		Graphics2D gSeg = (Graphics2D) bufferSegundos.getGraphics();
		System.out.println(rotacionSeg);
		if(rotacionSeg == 534) {
			pasarMinuto();
			System.out.println("Minuto pasado");
		}
		if((rotacionSeg==540)) rotacionSeg = 180;
		rotacionSeg +=6;
		gSeg.drawImage(bufferMinutos, 0,0,400,402,this);
		gSeg.rotate(Math.toRadians(rotacionSeg),200,201 );
		gSeg.drawImage(segundero, 190, 201,20,180,this);	
		gSeg.setColor(Color.white);
		gSeg.fillOval(187, 188, 26, 26);
		}
	
	public void pasarMinuto() {
		Graphics2D gMin = (Graphics2D) bufferMinutos.getGraphics();
		if(rotacionMin==534) pasarHora();
		if(rotacionMin==540) rotacionMin = 180;
		rotacionMin +=6;
		gMin.drawImage(bufferHoras, 0,0,400,402,this);
		gMin.rotate(Math.toRadians(rotacionMin),200,201 );
		gMin.drawImage(minutero, 190, 201,20,140,this);	
		//getGraphics().drawImage(bufferMinutos, 200,22,400,402,this);

	}
	
	public void pasarHora() {
		Graphics2D gHor = (Graphics2D) bufferHoras.getGraphics();
		if(rotacionHr==540) {
			rotacionHr = 180;
			pm = !pm;
		}
		rotacionHr +=30;
		gHor.drawImage(bufferReloj, 0,0,400,402,this);
		gHor.rotate(Math.toRadians(rotacionHr),200,201 );
		gHor.drawImage(horario, 190, 201,20,100,this);	

	}
	
	public void dibujar() {	
		getGraphics().drawImage(bufferSegundos, 200,22,400,402,this);
		actualizarDigital();
		getGraphics().drawImage(bufferNumerosDigitales, 0,450, 800,75,this);
	}
	
	public void reproducirTic() {
		tic.start();
		tic();
	}
	
	
	public Shape crearAnillo() {
		Ellipse2D exterior = new Ellipse2D.Double(0, 2,400,400);
		Ellipse2D interior = new Ellipse2D.Double(50,50,300,300);
		Area area = new Area(exterior);
		area.subtract(new Area(interior));
		return area;
	}
	
	public void actualizarDigital() {
		Graphics2D gD = (Graphics2D) bufferDigital.getGraphics();
		Graphics2D gN = (Graphics2D) bufferNumerosDigitales.getGraphics();
		gN.drawImage(bufferDigital,0,0,800,75,this);
		gN.setColor(Color.white);
		gN.setFont(custom2);
		if(pm)gN.drawString(getHora()+":"+getMinuto()+":"+getSegundo()+" PM", 250, 50);
		else gN.drawString(getHora()+":"+getMinuto()+":"+getSegundo()+" AM", 250, 50);

	}
	
	public void avanzarReloj() {
		pasarSegundo();
		reproducirTic();
		dibujar();
	}

	public String getSegundo() {
		int segundos = (rotacionSeg-180)/6;
		if(segundos==60)return "00";
		if(segundos<10)return "0"+segundos;
		return segundos+"";
	}
	
	public String getMinuto() {
		int minutos = (rotacionMin-180)/6;
		if(minutos==60)return "00";
		if(minutos<10)return "0"+minutos;
		return minutos+"";

	}
	public String getHora() {
		int hora = (rotacionHr-180)/30;
		if(hora == 0) return "12";
		if(hora<10)return "0"+hora;
		return hora+"";

	}
	
	private class Hilo extends Thread{
		
		public void Hilo() {}
		
		@Override
		public void run() {
			while(true) {
				long inicio = System.currentTimeMillis();
				try {
					sleep(993);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				avanzarReloj();
				System.out.println("Tiempo: "+(System.currentTimeMillis()-inicio));
			}
			

		}
		
	}


	
}
