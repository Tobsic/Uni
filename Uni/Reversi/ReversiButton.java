import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * 
 * A button that represents a position on the play field
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public class ReversiButton extends JComponent implements MouseListener {
	private static final long serialVersionUID = 1L;
	private int player, xIndex, yIndex;
	private ArrayList<ActionListener> listeners;

	/**
	 * Initialize a new ReversiButton without an owner
	 * @param Column Column of this button on the play field
	 * @param Row Row of this button on the play field
	 * @param listener The ActionListener, that is required
	 */
	public ReversiButton(int Column, int Row, ActionListener listener) {
		super();
		listeners = new ArrayList<ActionListener>();
		listeners.add(listener);
		enableInputMethods(true);
		addMouseListener(this);
		this.setBackground(new Color(0,0,0,0));
		xIndex = Column;
		yIndex = Row;
		player = 0;
	}

	/**
	 * Set the owner of this button
	 * @param Player Player that should own the button
	 */
	public void setPlayer(int Player) {
		player = (Player == 1 || Player == 2) ? Player : 0;
		switch (Player) {
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

	/**
	 * Get the owner of this button
	 * @return The owner of this button
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * Get the row on the play field
	 * @return The row on the play field
	 */
	public int getRow() {
		return xIndex;
	}

	/**
	 * Get the column on the play field
	 * @return The column on the play field
	 */
	public int getColumn() {
		return yIndex;
	}

	/**
	 * Check if it is able to set here
	 * @return True if it is possible to set here
	 */
	public boolean getCanSet() {
		return this.isEnabled();
	}

	/**
	 * Set the ability to set here. Deactivate the button if it is not able to set here
	 * @param isSetable True if it is possible to set here
	 */
	public void setCanSet(boolean isSetable) {
		this.setEnabled(isSetable);
		this.invalidate();
	}

	/**
	 * Draw every time a white rectangle.
	 * If the owner is player 1, a white circle will be drawn.
	 * If the owner is player 2, a black circle will be drawn.
	 * If no one is the owner and it is possible to set here, a green circle will be drawn
	 * else nothing be drawn
	 * @param g Graphic where the component is drawn
	 */
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

	/**
	 * Perform an action event on the listener, if the button is enabled
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.isEnabled())
			synchronized(listeners){
				ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiers());
				for(ActionListener listener : listeners)
					listener.actionPerformed(ae);
		}
	}

	/**
	 * Only needed to overwrite
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) { }

	/**
	 * Only needed to overwrite
	 */
	@Override
	public void mouseExited(MouseEvent arg0) { }

	/**
	 * Only needed to overwrite
	 */
	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }
}