import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.dnd.DragSource;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	GamePlace[][] Places;
	boolean activePlayer; // false = player1; true = player2
	int turn;
	GamePlace selectedPlace = null;
	
	
	public GamePanel(){
		activePlayer = false;
		turn = 0;
		setLayout(null);
		Places = new GamePlace[8][3];
		for(int i = 0; i < 8; i++){
			float p1 = i;
			for(int j = 0; j < 3; j++){
				float p2 = (float)(j + 1) / 3f;
				this.add(Places[i][j] = new GamePlace(new Point(i, j),
					i < 2 ? offset((p1 - 1) * p2	, -p2			) :
					i < 4 ? offset( p2				, (p1 - 3) * p2 ) :
					i < 6 ? offset((5 - p1) * p2	,  p2			) :
							offset(-p2				, (7 - p1) * p2	)
				));
			}
		}
		Places[0][0].setPlayer(1);
		this.setSize(Game.FIELD_SIZE, Game.FIELD_SIZE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw Background
		g.setColor(Game.BACK_COLOR);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		
		// Set drawing color
		g.setColor(Game.LINE_COLOR);
		// Draw lines from inside to outside
		int length = (Game.FIELD_SIZE - Game.CIRCLE_RADIUS * 2) / 3;
		drawVLine(g, offset(0   ,  -1), length);
		drawVLine(g, offset(0   ,1/3f), length);
		drawHLine(g, offset(-1  ,   0), length);
		drawHLine(g, offset(1/3f,   0), length);
		for(int j = 0; j < 3; j++){
			float pos1 = (float)(j + 1) / 3f;
			Point	pOL = offset(-pos1,-pos1),
					pUR = offset( pos1, pos1);
			g.setColor(Game.LINE_COLOR);
			g.fillRect(pOL.x, pOL.y - Game.LINE_SIZE / 2, pUR.x - pOL.x, Game.LINE_SIZE);
			g.fillRect(pUR.x - Game.LINE_SIZE / 2, pOL.y, Game.LINE_SIZE, pUR.y - pOL.y);
			g.fillRect(pOL.x, pUR.y - Game.LINE_SIZE / 2, pUR.x - pOL.x, Game.LINE_SIZE);
			g.fillRect(pOL.x - Game.LINE_SIZE / 2, pOL.y, Game.LINE_SIZE, pUR.y - pOL.y);
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

	private Point offset(float x, float y){
		return new Point(
				Game.FIELD_SIZE / 2 + (int)(x * ((float)Game.FIELD_SIZE / 2f - (float)Game.CIRCLE_RADIUS)),
				Game.FIELD_SIZE / 2 + (int)(y * ((float)Game.FIELD_SIZE / 2f - (float)Game.CIRCLE_RADIUS)));
	}
	
	public boolean hoverPlace(GamePlace gp){
		if(turn > 18){
			if(gp == selectedPlace){

				return true;
			}
			if(gp.getPlayer() != 0){
				
				return false;
			}else{
				
				return true;
			}
		}else{
			if(gp.getPlayer() != 0){
				gp.setCursor(DragSource.DefaultCopyNoDrop);
				return false;
			}else{
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				return true;
			}
		}
	}
}
