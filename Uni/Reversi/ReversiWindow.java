import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class ReversiWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel lblMessage, lblPoints;
	Image bgImg;
	int bgWidth, bgHeight;

	public ReversiWindow(int size) {
		super("Reversi");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new ReversiPanel());
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
