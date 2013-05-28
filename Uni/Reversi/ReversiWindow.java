import javax.swing.JFrame;


public class ReversiWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public ReversiWindow(int size) {
		super("Reversi");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new ReversiPanel());
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
