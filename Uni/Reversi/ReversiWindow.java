import javax.swing.JFrame;


/**
 * Main window of the game
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public class ReversiWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize a new game window
	 */
	public ReversiWindow() {
		super("Reversi");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new ReversiPanel());
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
