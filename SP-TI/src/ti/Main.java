package ti;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

/**
 * Hlavni trida..
 * @author Lukáš Runt, Miroslav Vdoviak
 * @version 1.1 (05-01-2022)
 */
public class Main {

	/**
	 * Vstupni metoda
	 * @param args vstupni argumenty
	 */
	public static void main(String[] args) {
				//Vytvoreni okna
				JFrame okno = new JFrame();
				okno.setTitle("TI");
				okno.setSize(640, 480);
				
				DrawingPanel panel = new DrawingPanel();
				
				okno.add(panel);//pridani komponenty
				okno.pack(); //udela resize okna dle komponent
				okno.setResizable(false);
				
				okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
				okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
				okno.setVisible(true);
				
				Timer tm = new Timer();
				tm.schedule(new TimerTask() {
					@Override
					public void run() {
						panel.zjistiStav();
						//panel.repaint();
					}
				}, 0, 50);
	}

}
