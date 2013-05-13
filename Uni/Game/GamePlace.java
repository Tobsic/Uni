import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;


public class GamePlace extends JComponent {
	private static final long serialVersionUID = 1L;

	private  int Player;
	
	public GamePlace() {
		this(0, 0);
	}
	
	GamePlace(Point p){
		this(p.x, p.y);
	}
	
	GamePlace(int x, int y){
		Player = 0;
		this.setCenter(x, y);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void setPlayer(int Player){
		this.Player = Player <= 2 && Player >= 0 ? Player : 0;
		this.invalidate();
	}
	
	public void setCenter(Point p){
		setCenter(p.x, p.y);
	}
	
	public void setCenter(int x, int y){
		this.setBounds(x - Game.CIRCLE_RADIUS, y - Game.CIRCLE_RADIUS, Game.CIRCLE_RADIUS * 2, Game.CIRCLE_RADIUS * 2);
	}
	
	public int getPlayer(){
		return Player;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Game.LINE_COLOR);
		g.fillOval(0, 0, Game.CIRCLE_RADIUS * 2, Game.CIRCLE_RADIUS * 2);
		g.setColor(Player == 1 ? Game.PLAYER1_COLOR : Player == 2 ? Game.PLAYER2_COLOR : Game.BACK_COLOR);
		g.fillOval(Game.LINE_SIZE, Game.LINE_SIZE, 2 * (Game.CIRCLE_RADIUS - Game.LINE_SIZE), 2 * (Game.CIRCLE_RADIUS - Game.LINE_SIZE));
	}

	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		super.processMouseMotionEvent(e);
		//if(Math.sqrt(Math.pow(e.getX() - Game.CIRCLE_SIZE / 2, 2) + Math.pow(e.getY() - Game.CIRCLE_SIZE / 2, 2)) <= Game.CIRCLE_SIZE)
			
	}

//	@Override
//	@Deprecated
//	public boolean inside(int arg0, int arg1) {
//		return super.inside(arg0, arg1) && Math.pow(arg0 - this.getX() - Game.CIRCLE_RADIUS, 2) + Math.pow(arg1 - this.getY() - Game.CIRCLE_RADIUS, 2) <= Math.pow(Game.CIRCLE_RADIUS, 2);
//	}

	@Override
	public boolean contains(int arg0, int arg1) {
		return super.contains(arg0, arg1) && (Math.pow(arg0 - Game.CIRCLE_RADIUS, 2) + Math.pow(arg1 - Game.CIRCLE_RADIUS, 2) <= Math.pow(Game.CIRCLE_RADIUS, 2));
	}

	@Override
	public boolean contains(Point arg0) {
		return this.contains(arg0.x, arg0.y);
	}
	
	
}
