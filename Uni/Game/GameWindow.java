import javax.swing.JFrame;


public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	int[][] Chips;
	GamePlace[][] Places;

	public GameWindow(){
		super("Mühle");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
		this.setContentPane(new GamePanel());
	}
}
