import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class ReversiButton extends JComponent implements MouseListener {
	private static final long serialVersionUID = 1L;
	private int player, xIndex, yIndex;
	private ArrayList<ActionListener> listeners;

	public ReversiButton(int X, int Y, ActionListener listener) {
		super();
		listeners = new ArrayList<ActionListener>();
		listeners.add(listener);
		enableInputMethods(true);
		addMouseListener(this);
		this.setBackground(new Color(0,0,0,0));
		xIndex = X;
		yIndex = Y;
		player = 0;
	}

	public void setPlayer(int P) {
		player = (P == 1 || P == 2) ? P : 0;
		switch (P) {
		case 1:
			player = 1;
			setCanSet(false);
			break;
		case 2:
			player = 2;
			setCanSet(false);
			break;
		default:
			player = 0;
			setCanSet(true);
		}
	}

	public int getPlayer() {
		return player;
	}

	public int getRow() {
		return xIndex;
	}

	public int getColumn() {
		return yIndex;
	}

	public boolean getCanSet() {
		return this.isEnabled();
	}

	public void setCanSet(boolean isSetable) {
		this.setEnabled(isSetable);
		this.invalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		int width  = this.getWidth();
		if(this.isEnabled()){
			g.setColor(Color.GREEN);
			g.fillOval(width / 4, width / 4, width / 2, width / 2);
		}
		else if(player == 1 || player == 2){
			g.setColor(player == 1 ? Color.WHITE : Color.BLACK);
			g.fillOval(2, 2, width - 4, width - 4);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.isEnabled())
			synchronized(listeners){
				ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiers());
				for(ActionListener listener : listeners)
					listener.actionPerformed(ae);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }
}