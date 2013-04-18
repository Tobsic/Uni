import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	int[][] Chips;
	int OFFSET;
	
	public GamePanel(){
		OFFSET = (Game.FIELD_SIZE - Game.CIRCLE_SIZE) / 2;
		Chips = new int[8][3];
		Chips[2][0] = 1;
		Chips[5][1] = 2;
		System.out.write(Chips.length);
		this.setSize(Game.FIELD_SIZE, Game.FIELD_SIZE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Draw Background
		g.setColor(Game.BACK_COLOR);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		
		// Set drawing color
		g.setColor(Game.LINE_COLOR);
		drawVLine(g, offset(0   ,  -1), 2 * (Game.FIELD_SIZE - Game.CIRCLE_SIZE) / 6);
		drawVLine(g, offset(0   ,1/3f), 2 * (Game.FIELD_SIZE - Game.CIRCLE_SIZE) / 6);
		drawHLine(g, offset(-1  ,   0), 2 * (Game.FIELD_SIZE - Game.CIRCLE_SIZE) / 6);
		drawHLine(g, offset(1/3f,   0), 2 * (Game.FIELD_SIZE - Game.CIRCLE_SIZE) / 6);
		for(int j = 0; j < 3; j++){
			float pos1 = (float)(j + 1) / 3f;
			Point	pOL = offset(-pos1,-pos1),
					pUR = offset( pos1, pos1);
			g.setColor(Game.LINE_COLOR);
			g.fillRect(pOL.x, pOL.y - Game.LINE_SIZE / 2, pUR.x - pOL.x, Game.LINE_SIZE);
			g.fillRect(pUR.x - Game.LINE_SIZE / 2, pOL.y, Game.LINE_SIZE, pUR.y - pOL.y);
			g.fillRect(pOL.x, pUR.y - Game.LINE_SIZE / 2, pUR.x - pOL.x, Game.LINE_SIZE);
			g.fillRect(pOL.x - Game.LINE_SIZE / 2, pOL.y, Game.LINE_SIZE, pUR.y - pOL.y);
			for(int i = 0; i < 8; i++){
				float pos2 = (float)i;
				drawPos(g,
					i < 2 ? offset((pos2 - 1) * pos1	, -pos1				) :
					i < 4 ? offset( pos1				, (pos2 - 3) * pos1 ) :
					i < 6 ? offset((5 - pos2) * pos1	,  pos1				) :
							offset(-pos1				, (7 - pos2) * pos1	),
					(Chips[i][j] == 1) ? Game.PLAYER1_COLOR : ((Chips[i][j] == 2) ? Game.PLAYER2_COLOR : Game.BACK_COLOR));
			}
		}
	}

	private void drawHLine(Graphics g, Point p, int length){
		drawHLine(g, p.x, p.y, length);
	}
	
	private void drawHLine(Graphics g, int x, int y, int length){
		g.fillRect(x, y - Game.LINE_SIZE / 2, length, Game.LINE_SIZE);
	}

	private void drawVLine(Graphics g, Point p, int length){
		drawVLine(g, p.x, p.y, length);
	}
	
	private void drawVLine(Graphics g, int x, int y, int length){
		g.fillRect(x - Game.LINE_SIZE / 2, y, Game.LINE_SIZE, length);
	}
	
	private void drawPos(Graphics g, Point p, Color c){
		drawPos(g, p.x, p.y, c);
	}
		
	private void drawPos(Graphics g, int x, int y, Color c){
		
		g.setColor(Game.LINE_COLOR);
		g.fillOval(x - Game.CIRCLE_SIZE / 2, y - Game.CIRCLE_SIZE / 2, Game.CIRCLE_SIZE, Game.CIRCLE_SIZE);
		g.setColor(c);
		g.fillOval(x - Game.CIRCLE_SIZE / 2 + Game.LINE_SIZE, y - Game.CIRCLE_SIZE  / 2 + Game.LINE_SIZE, Game.CIRCLE_SIZE -  2 * Game.LINE_SIZE, Game.CIRCLE_SIZE - 2 * Game.LINE_SIZE);
	}

		private Point offset(float x, float y){
		return new Point(
				Game.FIELD_SIZE/2 + (int)(x * OFFSET),
				Game.FIELD_SIZE/2 + (int)(y * OFFSET));
	}
}
