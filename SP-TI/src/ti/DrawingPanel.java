package ti;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JOptionPane;

/**
 * @author Lukas Runt, Miroslav Vdoviak
 * @version 2.0 (22-01-2022)
 */
@SuppressWarnings("serial")
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
	private final int VELIKOST_STAVU = 50;
	/** Barva, ktera znaci neaktivni stav v prechodovem grafu */
	private final Color NEAKTIVNI_STAV = Color.WHITE;
	/** Barva, ktera znaci aktivni stav v prechodovem grafu */
	private final Color AKTIVNI_STAV = Color.GREEN;
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
	/** Barva zhasle zarovky */
	private final Color ZAROVKA_ZHASLA = Color.GRAY;
	/** Barva rozsvicene zarovky */
	private final Color ZAROVKA_SVITI = Color.YELLOW;
	/** Jak se rychle budou napustet a vypoustet tanky */
	private final int RYCHLOST_PRUTOKU = 2;
	/** Pri kolika procentech naplneni bude mit horni cidlo tanku logickou jednicku*/
	private final int HORNI_CIDLO = 100;
	/** Pri kolika procentech naplneni bude mit dolni cidlo tanku logickou jednicku*/
	private final int DOLNI_CIDLO = 1; 
	private FontMetrics font;
	/** zda jsou filtrovany tanky */
	private boolean beziAkce = false;
	/** pole popisu stavu */
	private String[] popis;
	
	private Color tlacitkoA;
	private Color tlacitkoB;
	private Color zarovka;
	private Color V1;
	private Color V3;
	private Color V5;
	private Color V2;
	private Color V4;
	private Color V6;
	private Color V7;
	private Color LA01;
	private Color LA02;
	private Color LA03;
	private Color LA04;
	private Color cerpadlo;
	private Color ph;
	private Color stav0 = AKTIVNI_STAV;
	private Color stav1 = NEAKTIVNI_STAV;
	private Color stav2 = NEAKTIVNI_STAV;
	private Color stav3 = NEAKTIVNI_STAV;
	private Color stav4 = NEAKTIVNI_STAV;
	private Color stav5 = NEAKTIVNI_STAV;
	private Color stav6 = NEAKTIVNI_STAV;
	private Color stav7 = NEAKTIVNI_STAV;
	private Color stav8 = NEAKTIVNI_STAV;
	private Color stav9 = NEAKTIVNI_STAV;
	private Color stav10 = NEAKTIVNI_STAV;
	
	/** plnost tanku v % */
	private int tank1 = 0;
	private int tank2 = 0;
	/** Typ kapaliny v Tanku */
	private Napln NTank1 = Napln.NIC;
	private Napln NTank2 = Napln.NIC;
	/** V jakem stavu procesu jsme 0 - nic se nedeje */
	private int stav;
	private int phTanku;
	/** Rozhoduje zda bude automaticka simulace vypnuta nebo zapnuta*/
	public boolean simulace = false;
	private String vstup = "";

	/**
	 * 
	 */
	public DrawingPanel() {
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(1200, 500));
		tlacitkoA = LOG_NULA;
		tlacitkoB = LOG_NULA;
		zarovka = ZAROVKA_ZHASLA;
		V1 = LOG_NULA;
		V3 = LOG_NULA;
		V5 = LOG_NULA;
		V2 = LOG_NULA;
		V4 = LOG_NULA;
		V6 = LOG_NULA;
		V7 = LOG_NULA;
		cerpadlo = LOG_NULA;
		ph = LOG_NULA;
		stav = 0;
		LA01 = LOG_NULA;
		LA02 = LOG_NULA;
		LA03 = LOG_NULA;
		LA04 = LOG_NULA;
		inicializacePopisuStavu();
		
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
				if (e.getKeyChar() == '3') {
					V3 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '5') {
					V5 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '2') {
					V2 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == '4') {
					V4 = LOG_NULA;
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
				if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
					cerpadlo = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == 'Q' || e.getKeyChar() == 'q') {
					ph = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == 'I' || e.getKeyChar() == 'i') {
					LA01 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == 'J' || e.getKeyChar() == 'j') {
					LA02 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == 'O' || e.getKeyChar() == 'o') {
					LA03 = LOG_NULA;
					repaint();
				}
				if (e.getKeyChar() == 'K' || e.getKeyChar() == 'k') {
					LA04 = LOG_NULA;
					repaint();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a') {
					tlacitkoA = LOG_JEDN;
					repaint();
					if(beziAkce) {
						zarovka = ZAROVKA_SVITI;
						vypisChybu("Warning", "Zrovna se sanitarizuje jeden z tanku.\nPockejte prosim az dobehne akce do konce.");
						tlacitkoA = LOG_NULA;
						zarovka = ZAROVKA_ZHASLA;
						return;
					}
					if(tank1 > 0) {
						zarovka = ZAROVKA_SVITI;
						vypisChybu("Warning", "Tank neni prazdny. Nejdrive se musi vypustit");
						tlacitkoA = LOG_NULA;
						zarovka = ZAROVKA_ZHASLA;
						return;
					}
					stav = 1;
					stav0 = NEAKTIVNI_STAV;
					stav1 = AKTIVNI_STAV;
					beziAkce = true;
				}
				if (e.getKeyChar() == 'B' || e.getKeyChar() == 'b') {
					tlacitkoB = LOG_JEDN;
					repaint();
					if(beziAkce) {
						zarovka = ZAROVKA_SVITI;
						vypisChybu("Warning", "Zrovna se sanitarizuje jeden z tanku.\nPockejte prosim az dobehne akce do konce.");
						tlacitkoB = LOG_NULA;
						zarovka = ZAROVKA_ZHASLA;
						return;
					}
					if(tank2 > 0) {
						zarovka = ZAROVKA_SVITI;
						vypisChybu("Warning", "Tank neni prazdny. Nejdrive se musi vypustit");
						tlacitkoB = LOG_NULA;
						zarovka = ZAROVKA_ZHASLA;
						return;
					}
					stav = 6;
					stav0 = NEAKTIVNI_STAV;
					stav6 = AKTIVNI_STAV;
					beziAkce = true;
				}
				if (e.getKeyChar() == '1') {
					if(isOvladaniPovoleno()) {
						V1 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == '3') {
					if(isOvladaniPovoleno()) {
						V3 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == '5') {
					if(isOvladaniPovoleno()) {
						V5 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == '2') {
					if(isOvladaniPovoleno()) {
						V2 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == '4') {
					if(isOvladaniPovoleno()) {
						V4 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == '6') {
					if(isOvladaniPovoleno()) {
						V6 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == '7') {
					if(isOvladaniPovoleno()) {
						V7 = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
					if(isOvladaniPovoleno()) {
						cerpadlo = LOG_JEDN;
						manualniOvladani();
						repaint();
					}
				}
				if (e.getKeyChar() == 'S' || e.getKeyChar() == 's') {
					simulace = !simulace;
					repaint();
					if(simulace) {
						ph = LOG_JEDN;
					}else {
						ph = LOG_NULA;
					}
				}
				if (e.getKeyChar() == 'Q' || e.getKeyChar() == 'q') {
					ph = LOG_JEDN;
					vstup = "Q";
					zjistiStavManual();
					repaint();
				}
				if (e.getKeyChar() == 'I' || e.getKeyChar() == 'i') {
					LA01 = LOG_JEDN;
					vstup = "I";
					zjistiStavManual();
					repaint();
				}
				if (e.getKeyChar() == 'J' || e.getKeyChar() == 'j') {
					LA02 = LOG_JEDN;
					vstup = "J";
					zjistiStavManual();
					repaint();
				}
				if (e.getKeyChar() == 'O' || e.getKeyChar() == 'o') {
					LA03 = LOG_JEDN;
					vstup = "O";
					zjistiStavManual();
					repaint();
				}
				if (e.getKeyChar() == 'K' || e.getKeyChar() == 'k') {
					LA04 = LOG_JEDN;
					vstup = "K";
					zjistiStavManual();
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
		if(simulace) {
			aktualizujStav();
		}
		g2.setFont(new Font("Calibri", Font.BOLD, VELIKOST_TEXTU));
		font = g2.getFontMetrics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setColor(CARA);
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
		drawVentil(g2, 170, 220, V3, "V3");
		g2.drawLine(180, 250, 180, 270);
		drawTank(g2, 180 - SIRKA_TANKU/2, 270, LA01, LA02, tank1, NTank1, "LA01", "LA02");
		g2.drawLine(180, 370, 180, 390);
		drawVentil(g2, 180 - SIRKA_VENTILU/2, 390, V5, "V5");
		g2.drawLine(180, 420, 180, 440);
		g2.drawLine(300, 200, 300, 180);
		drawVentil(g2, 300 - SIRKA_VENTILU/2, 150, V2, "V2");	
		g2.drawLine(300, 150, 300, 80);
		g2.drawString("Voda", 300 - font.stringWidth("Voda") / 2, 40);
		drawArrow(300, 80, 300, 50, g2);
		g2.drawLine(325, 200, 325, 220);
		drawVentil(g2, 325 - SIRKA_VENTILU/2, 220, V4, "V4");
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
		g2.setColor(CARA);
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
		g2.setColor(CARA);
		g2.drawOval(490, 410, 20, 20);
		g2.drawString("Q - ph", 510 - font.stringWidth("Q - ph")/2, 400);
		drawOvladaciPanel(g2, 400, 50, tlacitkoA, tlacitkoB);
		//Stav
		g2.drawString("Stav: " + stav, 600, 50);
		g2.drawString("Popis: " + popis[stav], 600, 75);
		if(simulace) {
			g2.drawString("Automaticka simulace: ZAPNUTO", 600, 100);
		}else {
			g2.drawString("Automaticka simulace: VYPNUTO", 600, 100);
		}
		//------------------------------------------------------------------
		//Prechodovy graf
		//------------------------------------------------------------------
		drawArrow(720, 215, 650, 300, g2);
		drawArrow(720, 380, 650, 300, g2);
		drawArrow(805, 200, 765, 200, g2);
		drawArrow(895, 200, 855, 200, g2);
		drawArrow(985, 200, 945, 200, g2);
		drawArrow(1080, 210, 1035, 200, g2);
		drawArrow(805, 400, 765, 400, g2);
		drawArrow(895, 400, 855, 400, g2);
		drawArrow(985, 400, 945, 400, g2);
		drawArrow(1080, 390, 1035, 400, g2);
		drawArrow(670, 310, 1100, 370, g2);
		drawArrow(670, 290, 1100, 230, g2);
		drawStav(g2, 650, 300, stav0, "0");
		drawStav(g2, 740, 200, stav1, "1");
		drawStav(g2, 830, 200, stav2, "2");
		drawStav(g2, 920, 200, stav3, "3");
		drawStav(g2, 1010, 200, stav4, "4");
		drawStav(g2, 1100, 220, stav5, "5");
		drawStav(g2, 740, 400, stav6, "6");
		drawStav(g2, 830, 400, stav7, "7");
		drawStav(g2, 920, 400, stav8, "8");
		drawStav(g2, 1010, 400, stav9, "9");
		drawStav(g2, 1100, 380, stav10, "10");
		//popisky
		//horni vetev
		g2.drawString("A", 675, 240);
		g2.drawString("LA01", 760, 160);
		g2.drawString("(I)", 770, 180);
		g2.drawString("LA02", 850, 160);
		g2.drawString("(J)", 860, 180);
		g2.drawString("LA01", 940, 160);
		g2.drawString("(I)", 950, 180);
		g2.drawString("Q", 1040, 190);
		g2.drawString("LA02", 1020, 260);
		g2.drawString("(J)", 1030, 280);
		//spodni vetev
		g2.drawString("B", 675, 365);
		g2.drawString("LA03", 760, 455);
		g2.drawString("(O)", 770, 430);
		g2.drawString("LA04", 850, 455);
		g2.drawString("(K)", 860, 430);
		g2.drawString("LA03", 940, 455);
		g2.drawString("(O)", 950, 430);
		g2.drawString("Q", 1040, 420);
		g2.drawString("LA04", 1020, 330);
		g2.drawString("(K)", 1030, 350);
	}
	
	/**
	 * Metoda kresli sipku
	 * @param x1 x-ova souradnice zacatku sipky 
	 * @param y1 y-ova souradnice zacatku sipky
	 * @param x2 x-ova souradnice konce sipky
	 * @param y2 y-ova souradnice konce sipky
	 * @param g2 Grafika
	 */
	private void drawArrow(double x1, double y1, double x2, double y2, Graphics2D g2) {

		g2.setColor(CARA);
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
	 * Metoda kresli ventil
	 * @param g2 Grafika
	 * @param x x-ova souradnice ventilu
	 * @param y y-ova souradnice ventilu
	 * @param color barva(stav) ventilu, Zelena = ventil otevren, Cervena = ventil zavren
	 * @param text nazev ventilu 
	 */
	private void drawVentil(Graphics2D g2, int x, int y, Color color, String text) {
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g2.setColor(CARA);
		g2.drawLine(x, y, x + SIRKA_VENTILU, y);
		g2.drawLine(x, y + VYSKA_VENTILU, x + SIRKA_VENTILU, y + VYSKA_VENTILU);
		g2.drawLine(x, y, x + SIRKA_VENTILU, y + VYSKA_VENTILU);
		g2.drawLine(x + SIRKA_VENTILU, y, x, y + VYSKA_VENTILU);
		g2.drawLine(x + SIRKA_VENTILU/2, y + VYSKA_VENTILU/2, x + SIRKA_VENTILU, y + VYSKA_VENTILU/2);
		g2.setColor(color);
		g2.fillOval(x + SIRKA_VENTILU, (y + VYSKA_VENTILU/2) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setColor(CARA);
		g2.drawOval(x + SIRKA_VENTILU, (y + VYSKA_VENTILU/2) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g2.drawString(text, x + SIRKA_VENTILU + VYSKA_VENTILU - font.stringWidth(text)/4, y + font.getHeight() / 4 + VYSKA_VENTILU / 2);
	}
	
	/**
	 * Metoda kresli tank
	 * @param g2 grafika
	 * @param x x-ova souradnice 
	 * @param y y-ova souradnice
	 * @param color1 barva horniho senzoru, ktery znaci stav
	 * @param color2 barva spodniho senzoru, ktery znaci stav
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
		g2.setColor(CARA);
		g2.drawRect(x, y, SIRKA_TANKU, VYSKA_TANKU);
		g2.drawLine(x, (int)(y + (1.0/4.0 * VYSKA_TANKU)), x - 10, (int)(y + (1.0/4.0 * VYSKA_TANKU)));
		g2.drawLine(x, (int)(y + (3.0/4.0 * VYSKA_TANKU)), x - 10, (int)(y + (3.0/4.0 * VYSKA_TANKU)));
		g2.setColor(color1);
		g2.fillOval(x - (10 + (int)(VYSKA_VENTILU/1.5)), (int)(y + (1.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setColor(color2);
		g2.fillOval(x - (10 + (int)(VYSKA_VENTILU/1.5)), (int)(y + (3.0/4.0 * VYSKA_TANKU)) - (int)(VYSKA_VENTILU/1.5)/2, (int)(VYSKA_VENTILU/1.5), (int)(VYSKA_VENTILU/1.5));
		g2.setColor(CARA);
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
		g2.setColor(CARA);
		g2.drawOval(x + VELIKOST_OP/3 - VELIKOST_KONTROLKY + 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.drawString("A", x + VELIKOST_OP/4 - font.stringWidth("A")/2, y + VELIKOST_KONTROLKY + 30);
		//B
		g2.setColor(colorB);
		g2.fillOval(x + 2 * VELIKOST_OP/3 - 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.setColor(CARA);
		g2.drawOval(x + 2 * VELIKOST_OP/3 - 10, y + 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.drawString("B", x + 3 * VELIKOST_OP/4 - font.stringWidth("B")/2, y + VELIKOST_KONTROLKY + 30);
		//Zarovka
		g2.setColor(zarovka);
		g2.fillOval(x + VELIKOST_OP/2 - VELIKOST_KONTROLKY/2, y + VELIKOST_OP/2 + VELIKOST_KONTROLKY/2 - 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.setColor(CARA);
		g2.drawOval(x + VELIKOST_OP/2 - VELIKOST_KONTROLKY/2, y + VELIKOST_OP/2 + VELIKOST_KONTROLKY/2 - 10, VELIKOST_KONTROLKY, VELIKOST_KONTROLKY);
		g2.drawString("Z", x + VELIKOST_OP/2 - font.stringWidth("Z")/2, y + VELIKOST_OP + VELIKOST_TEXTU / 2);
	}
	
	private void drawStav(Graphics2D g2, int x, int y, Color barva, String text) {
		g2.setColor(barva);
		g2.fillOval(x - VELIKOST_STAVU/2, y - VELIKOST_STAVU/2, VELIKOST_STAVU, VELIKOST_STAVU);
		g2.setColor(CARA);
		g2.drawOval(x - VELIKOST_STAVU/2, y - VELIKOST_STAVU/2, VELIKOST_STAVU, VELIKOST_STAVU);
		g2.drawString(text, x - font.stringWidth(text)/2, y + 7);
	}
	
	/**
	 * Metoda aktrualizuje stav v modelu (vetsinou kontrola snimacu) 
	 */
	private void aktualizujStav() {
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
		V1 = LOG_JEDN;
		V3 = LOG_JEDN;
		NTank1 = Napln.LIH;
		tank1 += RYCHLOST_PRUTOKU;
		repaint();
		if(LA01 == LOG_JEDN) {
			stav++;
			V1 = LOG_NULA;
			V3 = LOG_NULA;
			stav1 = NEAKTIVNI_STAV;
			stav2 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Stav, ve kterem se z tanku1 cerpa lih dokud neni zcela vycerpan
	 */
	private void stavA2() {
		cerpadlo = LOG_JEDN;
		V5 = LOG_JEDN;
		tank1 -= RYCHLOST_PRUTOKU;
		repaint();
		if(LA02 == LOG_NULA) {
			stav++;
			cerpadlo = LOG_NULA;
			V5 = LOG_NULA;
			stav2 = NEAKTIVNI_STAV;
			stav3 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Stav, ve kterem se tank1 plni vodou
	 */
	private void stavA3() {
		NTank1 = Napln.VODA;
		V3 = LOG_JEDN;
		V2 = LOG_JEDN;
		repaint();
		tank1 += RYCHLOST_PRUTOKU;
		if(LA01 == LOG_JEDN) {
			stav++;
			phTanku = 100;
			stav3 = NEAKTIVNI_STAV;
			stav4 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Stav, ve kterem se proplachuje tank1 vodou dokud neni ph v norme 
	 */
	private void stavA4() {
		V5 = LOG_JEDN;
		V7 = LOG_JEDN;
		ph = LOG_NULA;
		phTanku -= 1;
		if(tank1 >= 25) {
			tank1 -= RYCHLOST_PRUTOKU/2;
		}
		repaint();
		if(phTanku <= 0) {
			stav++;
			V3 = LOG_NULA;
			V2 = LOG_NULA;
			ph = LOG_JEDN;
			stav4 = NEAKTIVNI_STAV;
			stav5 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Vypousteni zbytku vody z tanku1
	 */
	private void stavA5() {
		V5 = LOG_JEDN;
		V7 = LOG_JEDN;
		tank1 -= RYCHLOST_PRUTOKU;
		repaint();
		if(LA02 == LOG_NULA) {
			stav = 0;
			V5 = LOG_NULA;
			V7 = LOG_NULA;
			beziAkce = false;
			stav5 = NEAKTIVNI_STAV;
			stav0 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Stav, ve kterem se plni tank2 lihem
	 */
	private void stavB1() {
		V1 = LOG_JEDN;
		V4 = LOG_JEDN;
		NTank2 = Napln.LIH;
		tank2 += RYCHLOST_PRUTOKU;
		repaint();
		if(LA03 == LOG_JEDN) {
			stav++;
			V1 = LOG_NULA;
			V4 = LOG_NULA;
			stav6 = NEAKTIVNI_STAV;
			stav7 = AKTIVNI_STAV;
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
			stav7 = NEAKTIVNI_STAV;
			stav8 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Stav, ve kterem se tank2 plni vodou
	 */
	private void stavB3() {
		NTank2 = Napln.VODA;
		V2 = LOG_JEDN;
		V4 = LOG_JEDN;
		repaint();
		tank2 += RYCHLOST_PRUTOKU;
		if(LA03 == LOG_JEDN) {
			stav++;
			phTanku = 100;
			stav8 = NEAKTIVNI_STAV;
			stav9 = AKTIVNI_STAV;
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
			V2 = LOG_NULA;
			V4 = LOG_NULA;
			ph = LOG_JEDN;
			stav9 = NEAKTIVNI_STAV;
			stav10 = AKTIVNI_STAV;
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
			beziAkce = false;
			stav10 = NEAKTIVNI_STAV;
			stav0 = AKTIVNI_STAV;
		}
	}
	
	/**
	 * Metoda umoznuje manualni otevirani ventilu a ovladani cerpadla
	 */
	private void manualniOvladani() {
		if(V1 == LOG_JEDN && V3 == LOG_JEDN) {
			if(tank1 > 0 && NTank1 == Napln.VODA) {
				vypisChybu("Warning", "Nelze michat lih s vodou!");
				V1 = LOG_NULA;
				V3 = LOG_NULA;
				return;
			}
			tank1 += RYCHLOST_PRUTOKU;
			NTank1 = Napln.LIH;
			if(tank1 > 100) {
				tank1 = 100;
			}
		}
		if(V1 == LOG_JEDN && V4 == LOG_JEDN) {
			if(tank2 > 0 && NTank2 == Napln.VODA) {
				vypisChybu("Warning", "Nelze michat lih s vodou!");
				V1 = LOG_NULA;
				V4 = LOG_NULA;
				return;
			}
			tank2 += RYCHLOST_PRUTOKU;
			NTank2 = Napln.LIH;
			if(tank2 > 100) {
				tank2 = 100;
			}
		}
		if(V3 == LOG_JEDN && V2 == LOG_JEDN) {
			if(tank1 > 0 && NTank1 == Napln.LIH) {
				vypisChybu("Warning", "Nelze michat vodu s lihem!");
				V3 = LOG_NULA;
				V2 = LOG_NULA;
				return;
			}
			tank1 += RYCHLOST_PRUTOKU;
			NTank1 = Napln.VODA;
			if(tank1 > 100) {
				tank1 = 100;
			}
		}
		if(V2 == LOG_JEDN && V4 == LOG_JEDN) {
			if(tank2 > 0 && NTank2 == Napln.LIH) {
				vypisChybu("Warning", "Nelze michat vodu s lihem!");
				V2 = LOG_NULA;
				V4 = LOG_NULA;
				return;
			}
			tank2 += RYCHLOST_PRUTOKU;
			NTank2 = Napln.VODA;
			if(tank2 > 100) {
				tank2 = 100;
			}
		}
		if(V5 == LOG_JEDN && V7 == LOG_JEDN) {
			if(tank1 > 0 && NTank1 == Napln.LIH) {
				vypisChybu("Warning", "Je zakazano vylevat lih potrubim!");
				V5 = LOG_NULA;
				V7 = LOG_NULA;
				return;
			}
			tank1 -= RYCHLOST_PRUTOKU;
			if(tank1 < 0) {
				tank1 = 0;
				NTank1 = Napln.NIC;
			}
		}
		if(V6 == LOG_JEDN && V7 == LOG_JEDN) {
			if(tank2 > 0 && NTank2 == Napln.LIH) {
				vypisChybu("Warning", "Je zakazano vylevat lih potrubim!");
				V6 = LOG_NULA;
				V7 = LOG_NULA;
				return;
			}
			tank2 -= RYCHLOST_PRUTOKU;
			if(tank2 < 0) {
				tank2 = 0;
				NTank2 = Napln.NIC;
			}
		}
		if(V6 == LOG_JEDN && cerpadlo == LOG_JEDN) {
			if(tank2 > 0 && NTank2 == Napln.VODA) {
				vypisChybu("Warning", "Nelze cerpat vodu k lihu!");
				V6 = LOG_NULA;
				cerpadlo = LOG_NULA;
				return;
			}
			tank2 -= RYCHLOST_PRUTOKU;
			if(tank2 < 0) {
				tank2 = 0;
				NTank2 = Napln.NIC;
			}
		}
		if(V5 == LOG_JEDN && cerpadlo == LOG_JEDN) {
			if(tank1 > 0 && NTank1 == Napln.VODA) {
				vypisChybu("Warning", "Nelze cerpat vodu k lihu!");
				V6 = LOG_NULA;
				cerpadlo = LOG_NULA;
				return;
			}
			tank1 -= RYCHLOST_PRUTOKU;
			if(tank1 < 0) {
				tank1 = 0;
				NTank1 = Napln.NIC;
			}
		}
	}
	
	/**
	 * Metoda testuje zda je manualni ovladani povoleno
	 * @return Maualni ovladani povoleno true/false
	 */
	private boolean isOvladaniPovoleno() {
		if(beziAkce) {
			vypisChybu("Warning", "Bezi akce sanitace tanku. Manualni ovladani neni povoleno!");
			return false;
		}
		return true;
	}
	
	/**
	 * Vypis chyby 
	 * @param nazevOkna jak se bude jmenovat okno
	 * @param chybovaHlaska co bude vypsano v okne
	 */
	private void vypisChybu(String nazevOkna, String chybovaHlaska) {
		Frame frame = new Frame();
		zarovka = ZAROVKA_SVITI;
		JOptionPane.showMessageDialog(frame,
			    chybovaHlaska,
			    nazevOkna,
			    JOptionPane.WARNING_MESSAGE);
		zarovka = ZAROVKA_ZHASLA;
	}
	
	/**
	 * Metoda inicializuje pole popisu jednotlivych stavu
	 */
	private void inicializacePopisuStavu() {
		popis = new String[11];
		popis[0] = "System je v necinnosti";
		popis[1] = "Tank A se napousti lihem";
		popis[2] = "Tanku A se precerpava cerpadlem";
		popis[3] = "Tank A se plni vodou";
		popis[4] = "Tank A se proplachuje dokud neni ph v normalu";
		popis[5] = "Tank A se vypousti";
		popis[6] = "Tank B se napousti lihem";
		popis[7] = "Tanku B se precerpava cerpadlem";
		popis[8] = "Tank B se plni vodou";
		popis[9] = "Tank B se proplachuje dokud neni ph v normalu";
		popis[10] = "Tank B se vypousti";
	}
	
	/**
	 * Konecny automat, podle stavu se spusti dalsi metoda
	 */
	public void zjistiStavManual(){
		switch(stav) {
		  case 1:
		    stav1();
		    break;
		  case 2:
		    stav2();
		    break;
		  case 3:
			stav3();
			break;
		  case 4:
			stav4();
			break;
		  case 5:
			stav5();
			break;
		  case 6:
			stav6();
			break;
		  case 7:
			stav7();
			break;
		  case 8:
		    stav8();
			break;
		  case 9:
		    stav9();
			break;
		  case 10:
			stav10();
			break;
		  default:
			 break;
			}
		}
		
		/**
		 * Stav, ve kterem se plni tank1 lihem
		 */
		private void stav1() {
			NTank1 = Napln.LIH;
			V1 = LOG_JEDN;
			V3 = LOG_JEDN;
			repaint();
			if(vstup.equals("I")) {
				stav++;
				V1 = LOG_NULA;
				V3 = LOG_NULA;
				cerpadlo = LOG_JEDN;
				V5 = LOG_JEDN;
				tank1 = 100;
				stav1 = NEAKTIVNI_STAV;
				stav2 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se z tanku1 cerpa lih dokud neni zcela vycerpan
		 */
		private void stav2() {
			repaint();
			if(vstup.equals("J")) {
				stav++;
				cerpadlo = LOG_NULA;
				V5 = LOG_NULA;
				tank1 = 0;
				V3 = LOG_JEDN;
				V2 = LOG_JEDN;
				stav2 = NEAKTIVNI_STAV;
				stav3 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se tank1 plni vodou
		 */
		private void stav3() {
			NTank1 = Napln.VODA;
			repaint();
			tank1 += RYCHLOST_PRUTOKU;
			if(vstup.equals("I")) {
				stav++;
				tank1 = 100;
				V5 = LOG_JEDN;
				V7 = LOG_JEDN;
				stav3 = NEAKTIVNI_STAV;
				stav4 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se proplachuje tank1 vodou dokud neni ph v norme 
		 */
		private void stav4() {
			repaint();
			if(vstup.equals("Q")) {
				stav++;
				V3 = LOG_NULA;
				V2 = LOG_NULA;
				ph = LOG_JEDN;
				tank1 = 25;
				stav4 = NEAKTIVNI_STAV;
				stav5 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Vypousteni zbytku vody z tanku1
		 */
		private void stav5() {
			ph = LOG_JEDN;
			V5 = LOG_JEDN;
			V7 = LOG_JEDN;
			repaint();
			if(vstup.equals("J")) {
				stav = 0;
				tank1 = 0;
				V5 = LOG_NULA;
				V7 = LOG_NULA;
				ph = LOG_NULA;
				beziAkce = false;
				stav5 = NEAKTIVNI_STAV;
				stav0 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se plni tank2 lihem
		 */
		private void stav6() {
			V1 = LOG_JEDN;
			V4 = LOG_JEDN;
			NTank2 = Napln.LIH;
			tank2 += RYCHLOST_PRUTOKU;
			repaint();
			if(LA03 == LOG_JEDN) {
				stav++;
				V1 = LOG_NULA;
				V4 = LOG_NULA;
				stav6 = NEAKTIVNI_STAV;
				stav7 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se z tanku2 cerpa lih dokud neni zcela vycerpan
		 */
		private void stav7() {
			cerpadlo = LOG_JEDN;
			V6 = LOG_JEDN;
			tank2 -= RYCHLOST_PRUTOKU;
			repaint();
			if(LA04 == LOG_NULA) {
				stav++;
				cerpadlo = LOG_NULA;
				V6 = LOG_NULA;
				stav7 = NEAKTIVNI_STAV;
				stav8 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se tank2 plni vodou
		 */
		private void stav8() {
			NTank2 = Napln.VODA;
			V2 = LOG_JEDN;
			V4 = LOG_JEDN;
			repaint();
			tank2 += RYCHLOST_PRUTOKU;
			if(LA03 == LOG_JEDN) {
				stav++;
				phTanku = 100;
				stav8 = NEAKTIVNI_STAV;
				stav9 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Stav, ve kterem se proplachuje tank2 vodou dokud neni ph v norme 
		 */
		private void stav9() {
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
				V2 = LOG_NULA;
				V4 = LOG_NULA;
				ph = LOG_JEDN;
				stav9 = NEAKTIVNI_STAV;
				stav10 = AKTIVNI_STAV;
			}
		}
		
		/**
		 * Vypousteni zbytku vody z tanku2
		 */
		private void stav10() {
			V6 = LOG_JEDN;
			V7 = LOG_JEDN;
			tank2 -= RYCHLOST_PRUTOKU;
			repaint();
			if(LA04 == LOG_NULA) {
				stav = 0;
				V6 = LOG_NULA;
				V7 = LOG_NULA;
				beziAkce = false;
				stav10 = NEAKTIVNI_STAV;
				stav0 = AKTIVNI_STAV;
			}
		}
}
