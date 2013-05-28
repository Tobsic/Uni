import javax.swing.SwingUtilities;


public class ReversiMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new ReversiWindow(8);
			}
		});
	}
}
