/**
 * 
 */
package idk;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * @author Lenovo
 *
 */
public class DrawingPanel extends Component {
	private final double DELKA_HROTU = 20.0;
	private final int SIRKA_VENTILU = 20;
	private final int VYSKA_VENTILU = 30;
	private final int SIRKA_CARY = 3;
	private final int SIRKA_TANKU = 75;
	private final int VYSKA_TANKU = 100;

	/**
	 * 
	 */
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(700, 600));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		drawModel(g2);
	}

	private void drawModel(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(SIRKA_CARY));
		g2.drawLine(50, 50, 200, 50);
		drawArrow(200, 80, 200, 50, g2);
		//g2.drawRect(175, 80, 50, 50);
		g2.drawLine(175, 80, 175, 130);
		g2.drawLine(175, 130, 225, 130);
		g2.drawLine(225, 80, 225, 130);
		g2.drawLine(200, 130, 200, 150);
		drawVentil(g2, 190, 150, Color.RED);
		g2.drawLine(200, 180, 200, 200);
		g2.drawLine(180, 200, 300, 200);
		g2.drawLine(180, 200, 180, 220);
		drawVentil(g2, 170, 220, Color.RED);
		g2.drawLine(180, 250, 180, 270);
		drawTank(g2, 180 - SIRKA_TANKU/2, 270);
		g2.drawLine(180, 370, 180, 390);
		drawVentil(g2, 180 - SIRKA_VENTILU/2, 390, Color.RED);
		g2.drawLine(180, 420, 180, 440);
		
	}
	
	private void drawArrow(double x1, double y1, double x2, double y2,Graphics2D g2) {

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
	
	private void drawVentil(Graphics2D g2, int x, int y, Color color) {
		g2.setStroke(new BasicStroke(SIRKA_CARY - 1));
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
	}
	
	private void drawTank(Graphics2D g2, int x, int y) {
		g2.drawRect(x, y, SIRKA_TANKU, VYSKA_TANKU);
	}

}
