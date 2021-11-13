/**
 * 
 */
package idk;

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
				
				okno.add(new DrawingPanel());//pridani komponenty
				okno.pack(); //udela resize okna dle komponent
				
				okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//skonceni po zavreni okna
				okno.setLocationRelativeTo(null);//vycentrovat na obrazovce
				okno.setVisible(true);


	}

}
