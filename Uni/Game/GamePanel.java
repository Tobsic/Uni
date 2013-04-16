import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	int[][] Chips;
	Color BACK_COLOR	= Color.WHITE,
		  PLAYER1_COLOR	= Color.RED,
		  PLAYER2_COLOR	= Color.YELLOW,
		  LINE_COLOR	= Color.BLACK;
	int FIELD_SIZE	= 400,
		CIRCLE_SIZE	=  32,
		LINE_SIZE	=   4,
		OFFSET;
	
	public GamePanel(){
		OFFSET = (FIELD_SIZE - CIRCLE_SIZE) / 2;
		Chips = new int[8][3];
		Chips[2][0] = 1;
		Chips[5][1] = 2;
		System.out.write(Chips.length);
		this.setSize(FIELD_SIZE, FIELD_SIZE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Draw Background
		g.setColor(BACK_COLOR);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		
		// Set drawing color
		g.setColor(LINE_COLOR);
		drawVLine(g, offset(0   ,  -1), 2 * (FIELD_SIZE - CIRCLE_SIZE) / 6);
		drawVLine(g, offset(0   ,1/3f), 2 * (FIELD_SIZE - CIRCLE_SIZE) / 6);
		drawHLine(g, offset(-1  ,   0), 2 * (FIELD_SIZE - CIRCLE_SIZE) / 6);
		drawHLine(g, offset(1/3f,   0), 2 * (FIELD_SIZE - CIRCLE_SIZE) / 6);
		for(int j = 0; j < 3; j++){
			float pos1 = (float)(j + 1) / 3f;
			Point	pOL = offset(-pos1,-pos1),
					pUR = offset( pos1, pos1);
			g.setColor(LINE_COLOR);
			g.fillRect(pOL.x, pOL.y - LINE_SIZE / 2, pUR.x - pOL.x, LINE_SIZE);
			g.fillRect(pUR.x - LINE_SIZE / 2, pOL.y, LINE_SIZE, pUR.y - pOL.y);
			g.fillRect(pOL.x, pUR.y - LINE_SIZE / 2, pUR.x - pOL.x, LINE_SIZE);
			g.fillRect(pOL.x - LINE_SIZE / 2, pOL.y, LINE_SIZE, pUR.y - pOL.y);
			for(int i = 0; i < 8; i++){
				float pos2 = (float)i;
				drawPos(g,
					i < 2 ? offset((pos2 - 1) * pos1	, -pos1				) :
					i < 4 ? offset( pos1				, (pos2 - 3) * pos1 ) :
					i < 6 ? offset((5 - pos2) * pos1	,  pos1				) :
							offset(-pos1				, (7 - pos2) * pos1	),
					(Chips[i][j] == 1) ? PLAYER1_COLOR : ((Chips[i][j] == 2) ? PLAYER2_COLOR : BACK_COLOR));
			}
		}
	}

	private void drawHLine(Graphics g, Point p, int length){
		drawHLine(g, p.x, p.y, length);
	}
	
	private void drawHLine(Graphics g, int x, int y, int length){
		g.fillRect(x, y - LINE_SIZE / 2, length, LINE_SIZE);
	}

	private void drawVLine(Graphics g, Point p, int length){
		drawVLine(g, p.x, p.y, length);
	}
	
	private void drawVLine(Graphics g, int x, int y, int length){
		g.fillRect(x - LINE_SIZE / 2, y, LINE_SIZE, length);
	}
	
	private void drawPos(Graphics g, Point p, Color c){
		drawPos(g, p.x, p.y, c);
	}
		
	private void drawPos(Graphics g, int x, int y, Color c){
		
		g.setColor(LINE_COLOR);
		g.fillOval(x - CIRCLE_SIZE / 2, y - CIRCLE_SIZE / 2, CIRCLE_SIZE, CIRCLE_SIZE);
		g.setColor(c);
		g.fillOval(x - CIRCLE_SIZE / 2 + LINE_SIZE, y - CIRCLE_SIZE  / 2 + LINE_SIZE, CIRCLE_SIZE -  2 * LINE_SIZE, CIRCLE_SIZE - 2 * LINE_SIZE);
	}

		private Point offset(float x, float y){
		return new Point(
				FIELD_SIZE/2 + (int)(x * OFFSET),
				FIELD_SIZE/2 + (int)(y * OFFSET));
	}
}
