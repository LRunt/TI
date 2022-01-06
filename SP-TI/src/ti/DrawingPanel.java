package ti;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import javax.swing.JOptionPane;

/**
 * @author Lukas Runt, Miroslav Vdoviak
 * @version 1.1 (06-01-2022)
 */
public class DrawingPanel extends Component {
	private final double DELKA_HROTU = 20.0;
	private final int SIRKA_VENTILU = 20;
	private final int VYSKA_VENTILU = 30;
	private final int SIRKA_CARY = 3;
	private final int SIRKA_TANKU = 75;
	private final int VYSKA_TANKU = 100;
	private final int VELIKOST_OP = 150;
	private final int VELIKOST_KONTROLKY = 50;
	private final int VELIKOST_TEXTU = 25;
	/** Logicka jednicka - Zelena barva */
	private final Color LOG_JEDN = Color.GREEN;
	/** Logicka nula - Cervena barva */
	private final Color LOG_NULA = Color.RED;
	/** Barva lihu - seda */
	private final Color LIH = Color.GRAY;
	/** Barva vody - modra */
	private final Color VODA = Color.BLUE;
	/** Barva car - cerna */
	private final Color CARA = Color.BLACK;
	/** Jak se rychle budou napustet a vypoustet tanky */
	private final int RYCHLOST_PRUTOKU = 2;
	/** Pri kolika procentech naplneni bude mit horni cidlo tanku logickou jednicku*/
	private final int HORNI_CIDLO = 100;
	/** Pri kolika procentech naplneni bude mit dolni cidlo tanku logickou jednicku*/
	private final int DOLNI_CIDLO = 1; 
	private FontMetrics font;
	
	private Color tlacitkoA;
	private Color tlacitkoB;
	private Color zarovka;
	private Color V1;
	private Color V2;
	private Color V3;
	private Color V4;
	private Color V5;
	private Color V6;
	private Color V7;
	private Color LA01;
	private Color LA02;
	private Color LA03;
	private Color LA04;
	private Color cerpadlo;
	private Color ph;
	
	/** plnost tanku v % */
	private int tank1 = 0;
	private int tank2 = 0;
	/** Typ kapaliny v Tanku */
	private Napln NTank1 = Napln.NIC;
	private Napln NTank2 = Napln.NIC;
	/** V jakem stavu procesu jsme 0 - nic se nedeje */
	private int stav;
	private int phTanku;

	/**
	 * 
	 */
	public DrawingPanel() {
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(600, 500));
		tlacitkoA = LOG_NULA;
		tlacitkoB = LOG_NULA;
		zarovka = Color.GRAY;
		V1 = LOG_NULA;
		V2 = LOG_NULA;
		V3 = LOG_NULA;
		V4 = LOG_NULA;
		V5 = LOG_NULA;
		V6 = LOG_NULA;
		V7 = LOG_NULA;
		cerpadlo = LOG_NULA;
		ph = LOG_JEDN;
		stav = 0;
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a') {
					tlacitkoA = LOG_NULA;
					repaint();
					stavA1();
				}
				if (e.getKeyChar() == 'B' || e.getKeyChar() == 'b') {
					tlacitkoB = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '1') {
					V1 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '2') {
					V2 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '3') {
					V3 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '4') {
					V4 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '5') {
					V5 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '6') {
					V6 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '7') {
					V7 = LOG_NULA;
					repaint();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a') {
					tlacitkoA = LOG_JEDN;
					repaint();
					Frame frame = new Frame();
					if(tank1 > 0) {
						JOptionPane.showMessageDialog(frame,
						    "Tank nen� pr�zdn�. Nejd��ve se mus� vypustit",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
						return;
					}
					stav = 1;
				}
				if (e.getKeyChar() == 'B' || e.getKeyChar() == 'b') {
					tlacitkoB = LOG_JEDN;
					repaint();
					Frame frame = new Frame();
					if(tank2 > 0) {
						JOptionPane.showMessageDialog(frame,
						    "Tank nen� pr�zdn�. Nejd��ve se mus� vypustit",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
						return;
					}
					stav = 6;
				}
				if (e.getKeyChar() == '1') {
					V1 = LOG_JEDN;
					repaint();
				}
				if (e.getKeyChar() == '2') {
					V2 = LOG_JEDN;
					repaint();
				}
				if (e.getKeyChar() == '3') {
					V3 = LOG_JEDN;
					repaint();
				}
				if (e.getKeyChar() == '4') {
					V4 = LOG_JEDN;
					repaint();
				}
				if (e.getKeyChar() == '5') {
					V5 = LOG_JEDN;
					repaint();
				}
				if (e.getKeyChar() == '6') {
					V6 = LOG_JEDN;
					repaint();
				}
				if (e.getKeyChar() == '7') {
					V7 = LOG_JEDN;
					repaint();
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		drawModel(g2);
	}

	/**
	 * Metoda kresli model
	 * @param g2 grafika
	 */
	private void drawModel(Graphics2D g2) {
		aktualizujStav();
		g2.setFont(new Font("Calibri", Font.BOLD, VELIKOST_TEXTU));
		font = g2.getFontMetrics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g2.drawLine(50, 50, 200, 50);
		g2.drawString("Louh", 200 - font.stringWidth("Louh") / 2, 40);
		drawArrow(200, 80, 200, 50, g2);
		//g2.drawRect(175, 80, 50, 50);
		g2.setColor(LIH);
		g2.fillRect(175, 80, 50, 50);
		g2.setColor(CARA);
		g2.drawLine(175, 80, 175, 130);
		g2.drawLine(175, 130, 225, 130);
		g2.drawLine(225, 80, 225, 130);
		g2.drawLine(200, 130, 200, 150);
		drawVentil(g2, 190, 150, V1, "V1");
		g2.drawLine(200, 180, 200, 200);
		g2.drawLine(180, 200, 325, 200);
		g2.drawLine(180, 200, 180, 220);
		drawVentil(g2, 170, 220, V2, "V2");
		g2.drawLine(180, 250, 180, 270);
		drawTank(g2, 180 - SIRKA_TANKU/2, 270, LA01, LA02, tank1, NTank1, "LA01", "LA02");
		g2.drawLine(180, 370, 180, 390);
		drawVentil(g2, 180 - SIRKA_VENTILU/2, 390, V3, "V3");
		g2.drawLine(180, 420, 180, 440);
		g2.drawLine(300, 200, 300, 180);
		drawVentil(g2, 300 - SIRKA_VENTILU/2, 150, V4, "V4");	
		g2.drawLine(300, 150, 300, 80);
		g2.drawString("Voda", 300 - font.stringWidth("Voda") / 2, 40);
		drawArrow(300, 80, 300, 50, g2);
		g2.drawLine(325, 200, 325, 220);
		drawVentil(g2, 325 - SIRKA_VENTILU/2, 220, V5, "V5");
		g2.drawLine(325, 250, 325, 270);
		drawTank(g2, 325 - SIRKA_TANKU/2, 270, LA03, LA04, tank2, NTank2, "LA03", "LA04");
		g2.drawLine(325, 370, 325, 390);
		drawVentil(g2, 325 - SIRKA_VENTILU/2, 390, V6, "V6");
		g2.drawLine(325, 420, 325, 440);
		g2.drawLine(50, 50, 50, 440);
		g2.drawLine(50, 440, 400, 440);
		g2.drawLine(100, 440, 80, 460);
		g2.drawLine(100, 440, 120, 460);
		g2.drawLine(80, 460, 120, 460);
		//cerpadlo
		g2.setColor(cerpadlo);
		g2.fillOval(85, 425, 30, 30);
		g2.setColor(Color.BLACK);
		g2.drawOval(85, 425, 30, 30);
		g2.drawString("P", 110 - font.stringWidth("P"), 480);
		AffineTransform oldTr = g2.getTransform();
		g2.translate(400, 440);
		g2.rotate(Math.toRadians(-90));
		drawVentil(g2, 0 - SIRKA_VENTILU/2, 0, V7, "V7");
		g2.setTransform(oldTr);
		drawArrow(550, 440, 430, 440, g2);
		g2.drawLine(500, 440, 500, 420);
		//Kontrola ph
		g2.setColor(ph);
		g2.fillOval(490, 410, 20, 20);
		g2.setColor(Color.BLACK);
		g2.drawOval(490, 410, 20, 20);
		g2.drawString("Q - ph", 510 - font.stringWidth("Q - ph")/2, 400);
		drawOvladaciPanel(g2, 400, 50, tlacitkoA, tlacitkoB);
	}
	
	/**
	 * Metoda kresl� �ipku
	 * @param x1 x-ov� sou�adnice za��tku �ipky 
	 * @param y1 y-ov� sou�adnice za��tku �ipky
	 * @param x2 x-ov� sou�adnice konce �ipky
	 * @param y2 y-ov� sou�adnice konce �ipky
	 * @param g2 Grafika
	 */
	private void drawArrow(double x1, double y1, double x2, double y2, Graphics2D g2) {

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(SIRKA_CARY, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		
		//vypocet U
		double u_x = x1 - this.getWidth()/2;
		double u_y = y1 - this.getHeight()/2;
		double u_len1 = 1 / Math.sqrt(u_x * u_x + u_y * u_y);
		//jednotkove U
		u_x *= u_len1;
		u_y *= u_len1;
		g2.draw(new Line2D.Double(x1, y1, x2, y2));
		//--------------hrot--------------------------------------------------
		//hrot
		double u_x2 = x1 - x2;
		double u_y2 = y1 - y2;
		//jednotkove U
		double u_len2 = 1 / Math.sqrt(u_x2 * u_x2 + u_y2 * u_y2);
		u_x2 *= u_len2;
		u_y2 *= u_len2;
		//smer kolmy (jednotkova delka)
		double v_x = u_y2;
		double v_y = -u_x2;
		//smer kolmy - delka o 1/2 sirky hrotu
		v_x *= 0.35 * DELKA_HROTU;
		v_y *= 0.35 * DELKA_HROTU;
		double c_x = x1 - u_x2 * DELKA_HROTU;
		double c_y = y1 - u_y2 * DELKA_HROTU;
		g2.draw(new Line2D.Double(c_x + v_x, c_y + v_y, x1, y1));
		g2.draw(new Line2D.Double(c_x - v_x, c_y - v_y, x1, y1));
	}
	
	/**
	 * Metoda kresl� ventil
	 * @param g2 Grafika
	 * @param x x-ov� sou�adnice ventilu
	 * @param y y-ov� sou�adnice ventilu
	 * @param color barva(stav) ventilu, Zelen� = ventil otev�en, �erven� = ventil zav�en
	 * @param text nazev ventilu 
	 */
	private void drawVentil(Graphics2D g2, int x, int y, Color color, String text) {
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g2.setColor(Color.black);
		g2.drawLine(x, y, x + SIRKA_VENTILU, y);
		g2.drawLine(x, y + VYSKA_VENTILU, x + SIRKA_VENTILU, y + VYSKA_VENTILU);
		g2.drawLine(x, y, x + SIRKA_VENTILU, y + VYSKA_VENTILU);
		g2.drawLine(x + SIRKA_VENTILU, y, x, y + VYSKA_VENTILU);
		g2.drawLine(x + SIRKA_VENTILU/2, y + VYSKA_VENTILU/2, x + SIRKA_VENTILU, y + VYSKA_VENTILU/2);
		g2.setColor(color);
		g2.fillOval(x + SIRKA_VENTILU, (y + VYSKA_VENTILU/2) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setColor(Color.BLACK);
		g2.drawOval(x + SIRKA_VENTILU, (y + VYSKA_VENTILU/2) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g2.drawString(text, x + SIRKA_VENTILU + VYSKA_VENTILU - font.stringWidth(text)/4, y + font.getHeight() / 4 + VYSKA_VENTILU / 2);
	}
	
	/**
	 * Metoda kresl� tank
	 * @param g2 grafika
	 * @param x x-ova souradnice 
	 * @param y y-ova sou�adnice
	 * @param color1 barva horniho senzoru, kter� zna�� stav
	 * @param color2 barva spodniho senzoru, kter� zna�� stav
	 * @param vyskaNaplne jak je tank naplnen
	 * @param napln co je v tanku (LIH, VODA, NIC), podle tohoto se urcuje barva kterou je napln obarvena
	 * @param sensorH horni senzor
	 * @param sensorL dolni senzor
	 */
	private void drawTank(Graphics2D g2, int x, int y, Color color1, Color color2, int vyskaNaplne, Napln napln, String sensorH, String sensorL) {
		//Kresleni naplne tanku
		Color kapalina = Color.WHITE;
		if(napln == Napln.LIH) kapalina = LIH;
		if(napln == Napln.VODA) kapalina = VODA;
		g2.setColor(kapalina);
		g2.fillRect(x,(int)(y + ((100 - vyskaNaplne) / 100.0) * VYSKA_TANKU), SIRKA_TANKU, (int)((vyskaNaplne / 100.0) * VYSKA_TANKU));
		//Kresleni tanku
		g2.setColor(Color.BLACK);
		g2.drawRect(x, y, SIRKA_TANKU, VYSKA_TANKU);
		g2.drawLine(x, (int)(y + (1.0/4.0 * VYSKA_TANKU)), x - 10, (int)(y + (1.0/4.0 * VYSKA_TANKU)));
		g2.drawLine(x, (int)(y + (3.0/4.0 * VYSKA_TANKU)), x - 10, (int)(y + (3.0/4.0 * VYSKA_TANKU)));
		g2.setColor(color1);
		g2.fillOval(x - (10 + (int)(VYSKA_VENTILU/1.5)), (int)(y + (1.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setColor(color2);
		g2.fillOval(x - (10 + (int)(VYSKA_VENTILU/1.5)), (int)(y + (3.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setColor(Color.BLACK);
		g2.drawOval(x - (10 + (int)(VYSKA_VENTILU/1.5)), (int)(y + (1.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.drawOval(x - (10 + (int)(VYSKA_VENTILU/1.5)), (int)(y + (3.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.drawString(sensorH, x - (10 + (int)(VYSKA_VENTILU/1.5)) - 30, (int)(y + (1.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2 - 5);
		g2.drawString(sensorL, x - (10 + (int)(VYSKA_VENTILU/1.5)) - 30, (int)(y + (3.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2 - 5);
	}
	
	/**
	 * Metoda kresli ovladaci panel
	 * @param g2 grafika
	 * @param x x-ova souranice ovladaciho panelu
	 * @param y y-ova souradnice ovladaciho panelu
	 * @param colorA barva tlacitka A
	 * @param colorB barva tlacitka B
	 */
	private void drawOvladaciPanel(Graphics2D g2,int x, int y, Color colorA, Color colorB) {
		g2.drawRect(x, y, VELIKOST_OP, VELIKOST_OP + 20);
		//A
		g2.setColor(colorA);
		g2.fillOval(x + VELIKOST_OP/3 - VELIKOST_KONTROLKY + 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.setColor(Color.BLACK);
		g2.drawOval(x + VELIKOST_OP/3 - VELIKOST_KONTROLKY + 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.drawString("A", x + VELIKOST_OP/4 - font.stringWidth("A")/2, y + VELIKOST_KONTROLKY + 30);
		//B
		g2.setColor(colorB);
		g2.fillOval(x + 2 * VELIKOST_OP/3 - 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.setColor(Color.BLACK);
		g2.drawOval(x + 2 * VELIKOST_OP/3 - 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.drawString("B", x + 3 * VELIKOST_OP/4 - font.stringWidth("B")/2, y + VELIKOST_KONTROLKY + 30);
		//Zarovka
		g2.setColor(zarovka);
		g2.fillOval(x + VELIKOST_OP/2 - VELIKOST_KONTROLKY/2, y + VELIKOST_OP/2 + VELIKOST_KONTROLKY/2 - 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.setColor(Color.BLACK);
		g2.drawOval(x + VELIKOST_OP/2 - VELIKOST_KONTROLKY/2, y + VELIKOST_OP/2 + VELIKOST_KONTROLKY/2 - 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.drawString("Z", x + VELIKOST_OP/2 - font.stringWidth("Z")/2, y + VELIKOST_OP + VELIKOST_TEXTU / 2);
	}
	
	/**
	 * Metoda aktrualizuje stav v modelu (vetsinou kontrola snimacu) 
	 */
	private void aktualizujStav() {
		/*if(V1 == LOG_JEDN && V2 == LOG_JEDN) {
			tank1 += RYCHLOST_PRUTOKU;
			NTank1 = Napln.LIH;
			if(tank1 > 100) {
				tank1 = 100;
			}
		}*/
		/*if(V3 == LOG_JEDN) {
			tank1 -= rychlostPrutoku;
			if(tank1 < 0) {
				tank1 = 0;
				NTank1 = Napln.NIC;
			}
		}*/
		//tank1
		if(tank1 > 100) tank1 = 100;
		if(tank1 < 0) tank1 = 0;
		if(tank1 >= HORNI_CIDLO) {
			LA01 = LOG_JEDN;
			LA02 = LOG_JEDN;
		} else if(tank1 >= DOLNI_CIDLO) {
			LA01 = LOG_NULA;
			LA02 = LOG_JEDN;
		} else {
			LA01 = LOG_NULA;
			LA02 = LOG_NULA;
		}
		//tank2
		if(tank2 > 100) tank2 = 100;
		if(tank2 < 0) tank2 = 0;
		if(tank2 >= HORNI_CIDLO) {
			LA03 = LOG_JEDN;
			LA04 = LOG_JEDN;
		} else if(tank2 >= DOLNI_CIDLO) {
			LA03 = LOG_NULA;
			LA04 = LOG_JEDN;
		} else {
			LA03 = LOG_NULA;
			LA04 = LOG_NULA;
		}
	}
	
	/**
	 * Konecny automat, podle stavu se spusti dalsi metoda
	 */
	public void zjistiStav(){
		switch(stav) {
		  case 1:
		    stavA1();
		    break;
		  case 2:
		    stavA2();
		    break;
		  case 3:
			stavA3();
			break;
		  case 4:
			stavA4();
			break;
		  case 5:
			stavA5();
			break;
		  case 6:
			stavB1();
			break;
		  case 7:
			stavB2();
			break;
		  case 8:
		    stavB3();
			break;
		  case 9:
		    stavB4();
			break;
		  case 10:
			stavB5();
			break;
		  default:
		}

	}
	
	/**
	 * Stav, ve kterem se plni tank1 lihem
	 */
	private void stavA1() {
		NTank1 = Napln.LIH;
		tank1 += RYCHLOST_PRUTOKU;
		repaint();
		if(LA01 == LOG_JEDN) {
			stav++;
		}
	}
	
	/**
	 * Stav, ve kterem se z tanku1 cerpa lih dokud neni zcela vycerpan
	 */
	private void stavA2() {
		cerpadlo = LOG_JEDN;
		V3 = LOG_JEDN;
		tank1 -= RYCHLOST_PRUTOKU;
		repaint();
		if(LA02 == LOG_NULA) {
			stav++;
			cerpadlo = LOG_NULA;
			V3 = LOG_NULA;
		}
	}
	
	/**
	 * Stav, ve kterem se tank1 plni vodou
	 */
	private void stavA3() {
		NTank1 = Napln.VODA;
		V2 = LOG_JEDN;
		V4 = LOG_JEDN;
		repaint();
		tank1 += RYCHLOST_PRUTOKU;
		if(LA01 == LOG_JEDN) {
			stav++;
			V2 = LOG_NULA;
			V4 = LOG_NULA;
			phTanku = 100;
		}
	}
	
	/**
	 * Stav, ve kterem se proplachuje tank1 vodou dokud neni ph v norme 
	 */
	private void stavA4() {
		V3 = LOG_JEDN;
		V7 = LOG_JEDN;
		ph = LOG_NULA;
		phTanku -= 1;
		if(tank1 >= 25) {
			tank1 -= RYCHLOST_PRUTOKU/2;
		}
		repaint();
		if(phTanku <= 0) {
			stav++;
			ph = LOG_JEDN;
		}
	}
	
	/**
	 * Vypousteni zbytku vody z tanku1
	 */
	private void stavA5() {
		V3 = LOG_JEDN;
		V7 = LOG_JEDN;
		tank1 -= RYCHLOST_PRUTOKU;
		repaint();
		if(LA02 == LOG_NULA) {
			stav = 0;
			V3 = LOG_NULA;
			V7 = LOG_NULA;
		}
	}
	
	/**
	 * Stav, ve kterem se plni tank2 lihem
	 */
	private void stavB1() {
		NTank2 = Napln.LIH;
		tank2 += RYCHLOST_PRUTOKU;
		repaint();
		if(LA03 == LOG_JEDN) {
			stav++;
		}
	}
	
	/**
	 * Stav, ve kterem se z tanku2 cerpa lih dokud neni zcela vycerpan
	 */
	private void stavB2() {
		cerpadlo = LOG_JEDN;
		V6 = LOG_JEDN;
		tank2 -= RYCHLOST_PRUTOKU;
		repaint();
		if(LA04 == LOG_NULA) {
			stav++;
			cerpadlo = LOG_NULA;
			V6 = LOG_NULA;
		}
	}
	
	/**
	 * Stav, ve kterem se tank2 plni vodou
	 */
	private void stavB3() {
		NTank2 = Napln.VODA;
		V4 = LOG_JEDN;
		V5 = LOG_JEDN;
		repaint();
		tank2 += RYCHLOST_PRUTOKU;
		if(LA03 == LOG_JEDN) {
			stav++;
			V4 = LOG_NULA;
			V5 = LOG_NULA;
			phTanku = 100;
		}
	}
	
	/**
	 * Stav, ve kterem se proplachuje tank2 vodou dokud neni ph v norme 
	 */
	private void stavB4() {
		V6 = LOG_JEDN;
		V7 = LOG_JEDN;
		ph = LOG_NULA;
		phTanku -= 1;
		if(tank2 >= 25) {
			tank2 -= RYCHLOST_PRUTOKU/2;
		}
		repaint();
		if(phTanku <= 0) {
			stav++;
			ph = LOG_JEDN;
		}
	}
	
	/**
	 * Vypousteni zbytku vody z tanku2
	 */
	private void stavB5() {
		V6 = LOG_JEDN;
		V7 = LOG_JEDN;
		tank2 -= RYCHLOST_PRUTOKU;
		repaint();
		if(LA04 == LOG_NULA) {
			stav = 0;
			V6 = LOG_NULA;
			V7 = LOG_NULA;
		}
	}
	
}
