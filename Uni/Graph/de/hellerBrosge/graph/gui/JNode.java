package de.hellerBrosge.graph.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import de.hellerBrosge.graph.graph.Node;

/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public class JNode extends JComponent implements ActionListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private boolean draging = false;
	private Point lastPos;
	private Node<JNode> node;
	private JPopupMenu context;
	private JMenuItem conRename, conConnect, conDisconnect, conDelete;
	private transient GraphListener listener;

	/**
	 * Create a new component that represent a node
	 * @param graphListener Listener for events
	 * @param name The name of the new node. The name is shown on a string
	 */
	public JNode(GraphListener graphListener, String name){
		super();
		listener = graphListener;
		draging = false;
		node =  new Node<JNode>(this);
		conRename = new JMenuItem("Umbenennen");
		conRename.addActionListener(this);
		conConnect = new JMenuItem("Verbinden mit ...");
		conConnect.addActionListener(this);
		conDisconnect = new JMenuItem("Trennen von ...");
		conDisconnect.addActionListener(this);
		conDelete = new JMenuItem("Löschen");
		conDelete.addActionListener(this);
		context = new JPopupMenu();
		context.add(conRename);
		context.add(conConnect);
		context.add(conDisconnect);
		context.add(conDelete);
		this.setName(name);
		this.setSize(60, 60);
		this.setComponentPopupMenu(context);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * Get the node, that is represented
	 * @return The represented node
	 */
	public Node<JNode> getNode(){
		return node;
	}

	/**
	 * Get the center of this component
	 * @return The point at the center of this component
	 */
	public Point getCenter(){
		return new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
	}
	
	/**
	 * Set the center of this component
	 * @param center The new center of this component
	 */
	public void setCenter(Point center){
		this.setLocation(center.x - this.getWidth() / 2 , center.y - this.getHeight() / 2);
	}
	
	/**
	 * Set the listener where the event actions are performed
	 * @param graphListener Listener where the event actions are performed
	 */
	public void setListener(GraphListener graphListener){
		listener = graphListener;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1){
			draging = true;
			lastPos = arg0.getLocationOnScreen();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(draging && arg0.getButton() == MouseEvent.BUTTON1)
			draging = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(draging){
			Point point = arg0.getLocationOnScreen();
			Point location = this.getLocation();
			this.setLocation(location.x + point.x - lastPos.x, location.y + point.y - lastPos.y);
			lastPos = point;
			listener.NodeMoved(this);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) { }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		g.setColor(Color.BLACK);
		g.fillOval(2, 2, width - 4, width - 4);
		g.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawString(this.getName(), 8, 4 + width / 2);
	}

	@Override
	public boolean contains(int x, int y) {
		int halfWidth = this.getWidth() / 2 - 2;
		return super.contains(x, y) && Math.sqrt(Math.pow(x - halfWidth, 2) + Math.pow(y - halfWidth, 2)) < halfWidth;
	}
	
	@Override
	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if(sender == conRename){
			String newName = (String)JOptionPane.showInputDialog(this, "Enter new Name:", "New name of \"" + this.getName() + "\"", JOptionPane.QUESTION_MESSAGE, null, null, this.getName());
			if(newName != null){
				this.setName(newName);
				this.repaint();
			}
		}else if(listener != null)
			if(sender == conConnect)
				listener.NodeConnect(this);
			else if(sender == conDisconnect)
				listener.NodeDisconnect(this);
			else if(sender == conDelete)
				listener.NodeDelete(this);
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
