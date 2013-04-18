import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;


public class GamePlace extends JComponent {
	private static final long serialVersionUID = 1L;

	private  int Player;
	
	public GamePlace() {
		Player = 0;
		this.setSize(Game.CIRCLE_SIZE, Game.CIRCLE_SIZE);
	}
	
	public void setPlayer(int Player){
		this.Player = Player <= 2 && Player >= 0 ? Player : 0;
		this.invalidate();
	}
	
	public void setCenter(int x, int y){
		this.setBounds(x - Game.CIRCLE_SIZE / 2, y - Game.CIRCLE_SIZE / 2, Game.CIRCLE_SIZE, Game.CIRCLE_SIZE);
	}
	
	public int getPlayer(){
		return Player;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Game.LINE_COLOR);
		g.fillOval(0, 0, Game.CIRCLE_SIZE, Game.CIRCLE_SIZE);
		g.setColor(Player == 1 ? Game.PLAYER1_COLOR : Player == 2 ? Game.PLAYER2_COLOR : Game.BACK_COLOR);
		g.fillOval(Game.CIRCLE_SIZE / 2, Game.CIRCLE_SIZE  / 2, Game.CIRCLE_SIZE -  2 * Game.LINE_SIZE, Game.CIRCLE_SIZE - 2 * Game.LINE_SIZE);
	}

	
	
	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		super.processMouseMotionEvent(e);
		//if(Math.sqrt(Math.pow(e.getX() - Game.CIRCLE_SIZE / 2, 2) + Math.pow(e.getY() - Game.CIRCLE_SIZE / 2, 2)) <= Game.CIRCLE_SIZE)
			
	}

	
}
