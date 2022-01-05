/**
 * 
 */
package idk;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

/**
 * @author Lenovo
 *
 */
public class Main {

	/**
	 * @param args
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
