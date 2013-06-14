import javax.swing.SwingUtilities;


/**
 *Main class to create a new game window
 *
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public class ReversiMain {
	/**
	 * main function that should run on executing the project
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new ReversiWindow();
			}
		});
	}
}
