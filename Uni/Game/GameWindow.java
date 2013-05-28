import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;


public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	int[][] Chips;
	GamePlace[][] Places;

	public GameWindow(){
		super("Mühle");
		GamePanel gp = new GamePanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
		this.setContentPane(gp);
		Dimension gpDim = gp.getSize();
		Insets thisInsets = this.getInsets();
		this.setSize(thisInsets.left + gpDim.width + thisInsets.right, thisInsets.top + gpDim.height + thisInsets.bottom);
		this.setResizable(false);
	}
}
